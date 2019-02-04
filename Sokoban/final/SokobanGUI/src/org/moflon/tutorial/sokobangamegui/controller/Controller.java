package org.moflon.tutorial.sokobangamegui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tutorial.sokobangamegui.rules.Result;
import org.moflon.tutorial.sokobangamegui.rules.SokobanRules;
import org.moflon.tutorial.sokobangamegui.view.View;

import SokobanLanguage.Board;
import SokobanLanguage.Field;
import SokobanLanguage.Figure;
import SokobanLanguage.SokobanLanguagePackage;

/**
 * This is the controller class which controls the board and view.
 * @author Matthias Senker (Comments by Lukas Hermanns)
 */
public class Controller {
	
	/* The controller class knows all objects, the view and the board */
	private View view;
	private Board board;
	private SokobanRules sokobanRules;
	
	/**
	 * Main function or rather program entry point.
	 * @param args Specifies the program arguments (or rather parameters).
	 */
	public static void main(String[] args) {
		/* Create an instance of this class and create an empty board */
		Controller controller = new Controller();
		controller.switchBoard(BoardCreator.createEmptyBoard(8, 8));
	}
	
	/**
	 * Store a reference to the new given board object and allocates a new view.
	 * @param board Specifies the new board object.
	 */
	public void switchBoard(Board board) {
		if (board != null) {
			/* Dispose new view before allocating a new one */
			if (view != null) {
				view.dispose();
			}
			
			/* Store reference to the new board and allocate a new view */
			this.board = board;
			view = new View(this, board);
			
			sokobanRules = new SokobanRules(board);
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
			// Only print the stack trace
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves the current eMoflon model.
	 * @param filePath Specifies the full XMI filename.
	 */
	public void saveModel(String filePath) {
		eMoflonEMFUtil.saveModel(board, filePath);
	}
	
	/**
	 * Loads the specified eModflon model.
	 * @param filePath Specifies the full XMI filename.
	 */
	public void loadModel(String filePath) {
		/* Load the specified model and switch to the new board */
		Board newBoard = (Board)eMoflonEMFUtil.loadModel(filePath);
		switchBoard(newBoard);
	}
	
	/**
	 * Selects the given field
	 * @param field Specifies the field object which is to be selected.
	 */
	public void selectField(Field field) {
		try {
			/* Select field object and update view */
			if(board.getSelectedFigure() == null) {
				board.setSelectedFigure(field.getFigure());
			} else {
				moveTo(field);
			}
			
			view.updateView();
		} catch (UnsupportedOperationException e) {
			// Only print the stack trace
			e.printStackTrace();
		}
	}
	
	private void moveTo(Field field) {
		Figure figure = board.getSelectedFigure();
		Result result = sokobanRules.move(figure, field);
		
		if(result.isSuccess()) {
			board.setSelectedFigure(null);
		}
		
		view.showMessage(result.getReason());
	}

	/**
	 * Gets a list of all figure class types.
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
					result.add((EClass)c);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Places the given figure to the given field and updates the view.
	 * @param field Specifies the field object where the figure is to be placed on.
	 * @param figure Specifies the figure object which is to be placed on the field.
	 */
	public void setFigure(Field field, Figure figure) {
		field.setFigure(figure);
		view.updateView();
	}
	
	/**
	 * Prints the board as a kind of ASCII art to the console.
	 * @param board Specifies the board object which is to be printed.
	 */
	public void printBoard(Board board) {
		/* Check parameter validity */
		if (board == null) {
			return;
		}
		
		/* Print field array */
		System.out.println(Arrays.toString(board.getFields().toArray()));
		
		/* Allocate temporary field array */
		int w = board.getWidth();
		int h = board.getHeight();
		
		Field[][] fields = new Field[h][w];
		
		/* Fill temporary field array with the board fields */
		for (Field f : board.getFields()) {
			fields[f.getRow()][f.getCol()] = f;
		}
		
		/* Print each row */
		for (int r = 0; r < h; r++) {
			/* Print each column */
			for (int c = 0; c < w; c++) {
				printField(fields[r][c].getBottom());
			}
			System.out.println();
		}
	}
	
	/**
	 * Prints the given field object.
	 * @param field Specifies the field object which is to be printed.
	 */
	private void printField(Field field) {
		if (field == null) {
			System.out.print("[-,-]");
		} else {
			System.out.print("[" + field.getRow() + "," + field.getCol() + "]");
		}
	}
	
	
	public Board getBoard() {
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
