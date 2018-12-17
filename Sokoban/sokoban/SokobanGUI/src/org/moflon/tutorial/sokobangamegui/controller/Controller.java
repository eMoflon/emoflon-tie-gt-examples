package org.moflon.tutorial.sokobangamegui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.emoflon.ibex.handbook.api.RunParser;
import org.emoflon.ibex.handbook.api.RunSerialiser;
import org.emoflon.ibex.handbook.sokobanExchangeFormat.Board;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
import org.emoflon.ibex.tgg.run.sokobanimportexport.SYNC_App;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tutorial.sokobangamegui.rules.Result;
import org.moflon.tutorial.sokobangamegui.rules.SokobanRules;
import org.moflon.tutorial.sokobangamegui.view.View;

import SokobanLanguage.Field;
import SokobanLanguage.Figure;
import SokobanLanguage.SokobanLanguagePackage;

/**
 * This is the controller class which controls the board and view.
 * 
 * @author Initial version by Matthias Senker (Comments by Lukas Hermanns).
 *         Reworked for IBeX by Anthony Anjorin.
 */
public class Controller {

	private static final Logger logger = Logger.getLogger(Controller.class);

	/* The controller class knows all objects, the view and the board */
	private BiFunction<Controller, SokobanLanguage.Board, View> viewSupplier;
	private View view;
	private SokobanLanguage.Board board;
	private SokobanRules sokobanRules;
	public String currentStatus = "";
	private SYNC sync;

	/**
	 * Main function or rather program entry point.
	 * 
	 * @param args Specifies the program arguments (or rather parameters).
	 */
	public static void main(String[] args) {
		Logger.getRootLogger().setLevel(Level.INFO);

		/* Create an instance of this class and create an empty board */
		Controller controller = new Controller((c, b) -> new View(c, b));
		SokobanLanguage.Board board = BoardCreator.createEmptyBoard(8, 8);
		controller.switchBoard(board);
	}

	public Controller(BiFunction<Controller, SokobanLanguage.Board, View> viewSupplier) {
		this.viewSupplier = viewSupplier;
	}

	/**
	 * Store a reference to the new given board object and allocates a new view.
	 * 
	 * @param board Specifies the new board object.
	 */
	public void switchBoard(SokobanLanguage.Board board) {
		if (board != null) {
			/* Dispose new view before allocating a new one */
			if (view != null) {
				view.dispose();
			}

			/* Store reference to the new board and allocate a new view */
			this.board = board;
			this.view = viewSupplier.apply(this, board);

			sokobanRules = new SokobanRules(board);
			currentStatus = view.getStatus();
		}
	}

	/**
	 * Clears the board and updates the view.
	 */
	public void clearBoard() {
		try {
			board.getFields().forEach(f -> f.setFigure(null));
			view.updateView();
		} catch (UnsupportedOperationException e) {
			/* Ignore internal exceptions */
		}
	}

	/**
	 * Saves the current eMoflon model.
	 * 
	 * @param filePath Specifies the full XMI filename.
	 */
	public void saveModel(String filePath) {
		eMoflonEMFUtil.saveModel(board, filePath);
	}

	/**
	 * Loads the specified eModflon model.
	 * 
	 * @param filePath Specifies the full XMI filename.
	 */
	public void loadModel(String filePath) {
		/* Load the specified model and switch to the new board */
		SokobanLanguage.Board newBoard = (SokobanLanguage.Board) eMoflonEMFUtil.loadModel(filePath);
		switchBoard(newBoard);
	}

	/**
	 * Load, parse, and transform the given .sok file to a {@link Board}.
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void loadSOKFile(String filePath) {
		RunParser sokParser = new RunParser(filePath);
		Optional<org.emoflon.ibex.handbook.sokobanExchangeFormat.Board> board = sokParser.parse();

		board.ifPresent(b -> {
			try {
				initialiseFwdSynchroniser();

				sync.getSourceResource().getContents().add(b);

				logger.info("Starting sync");
				long tic = System.currentTimeMillis();
				sync.forward();
				long toc = System.currentTimeMillis();
				logger.info("Finished in " + (toc - tic) / 1000.0 + "s");

				SokobanLanguage.Board sokBoard = (SokobanLanguage.Board) sync.getTargetResource().getContents().get(0);
				postprocess(sokBoard);

				switchBoard(sokBoard);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void postprocess(SokobanLanguage.Board b) {
		b.getFields().stream().max((f1, f2) -> f1.getRow() - f2.getRow()).ifPresent(f -> b.setHeight(f.getRow() + 1));
		b.getFields().stream().max((f1, f2) -> f1.getCol() - f2.getCol()).ifPresent(f -> b.setWidth(f.getCol() + 1));
	}

	private void initialiseFwdSynchroniser() throws IOException {
		if (sync != null)
			sync.terminate();

		sync = new SYNC_App();
	}

	private void initialiseBwdSynchroniser() throws IOException {
		if (sync == null)
			sync = new SYNC_App();
	}

	public void saveSOKFile(String filePath) {
		try {
			initialiseBwdSynchroniser();

			sync.getTargetResource().getContents().add(board);

			long tic = System.currentTimeMillis();
			logger.info("Starting sync");
			sync.backward();
			long toc = System.currentTimeMillis();
			logger.info("Finished: " + (toc - tic) / 1000.0 + "s");

			switchBoard(board);
		} catch (IOException e) {
			e.printStackTrace();
		}

		RunSerialiser serialiser = new RunSerialiser();
		Board board = (Board) sync.getSourceResource().getContents().get(0);
		serialiser.unparse(filePath, board);
	}

	/**
	 * Selects the given field
	 * 
	 * @param field Specifies the field object which is to be selected.
	 */
	public void selectField(Field field) {
		try {
			/* Select field object and update view */
			if (board.getSelectedFigure() == null) {
				board.setSelectedFigure(field.getFigure());
			} else {
				moveTo(field);
			}

			view.updateView();
		} catch (UnsupportedOperationException e) {
			/* Ignore internal exceptions */
		}
	}

	private void moveTo(Field field) {
		Figure figure = board.getSelectedFigure();
		Result result = sokobanRules.move(figure, field);
		board.setSelectedFigure(null);
		view.showMessage(result.getReason());
	}

	/**
	 * Gets a list of all figure class types.
	 * 
	 * @return List of EClass containing all figure class types.
	 */
	public List<EClass> getFigureClasses() {
		/* Allocate output array list */
		List<EClass> result = new ArrayList<EClass>();

		/* Get template class type to compare with */
		EClass elementClass = SokobanLanguagePackage.eINSTANCE.getFigure();

		/* Iterate over all eMoflon classifiers to find the figure class types */
		for (EClassifier c : SokobanLanguagePackage.eINSTANCE.getEClassifiers()) {
			/* Check if the current classifier matches the template class type */
			if (c instanceof EClass) {
				EClass figure = (EClass) c;
				if (elementClass.isSuperTypeOf(figure) && !figure.isAbstract()) {
					/* Add the current classifier to the output list */
					result.add((EClass) c);
				}
			}
		}

		return result;
	}

	/**
	 * Places the given figure to the given field and updates the view.
	 * 
	 * @param field  Specifies the field object where the figure is to be placed on.
	 * @param figure Specifies the figure object which is to be placed on the field.
	 */
	public void setFigure(Field field, Figure figure) {
		field.setFigure(figure);
		view.updateView();
	}

	public SokobanLanguage.Board getBoard() {
		return board;
	}

	public void newBoard(int width, int height) {
		switchBoard(BoardCreator.createEmptyBoard(width, height));
	}

	public boolean boardIsValid() {
		Result result = sokobanRules.validateBoard(board);
		view.showMessage(result.getReason());
		return result.isSuccess();
	}
}
