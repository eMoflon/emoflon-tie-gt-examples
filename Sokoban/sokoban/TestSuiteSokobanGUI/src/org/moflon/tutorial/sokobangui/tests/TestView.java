package org.moflon.tutorial.sokobangui.tests;

import org.moflon.tutorial.sokobangamegui.controller.Controller;
import org.moflon.tutorial.sokobangamegui.view.View;

import SokobanLanguage.Board;
import SokobanLanguage.Field;
import SokobanLanguage.SokobanLanguagePackage;

public class TestView extends View {
	private static final long serialVersionUID = 1L;

	public TestView(Controller controller, Board board) {
		super(controller, board);
	}

	public void createSokoban(int x, int y) {
		createFigure(SokobanLanguagePackage.eINSTANCE.getSokoban(), buttons[x][y]);
	}

	public void createBlock(int x, int y) {
		createFigure(SokobanLanguagePackage.eINSTANCE.getBlock(), buttons[x][y]);
	}

	public void createBoulder(int x, int y) {
		createFigure(SokobanLanguagePackage.eINSTANCE.getBoulder(), buttons[x][y]);
	}

	public void createEndPos(int x, int y) {
		createEndFigure(buttons[x][y]);
	}

	/**
	 * Prints the board as a kind of ASCII art to the console.
	 */
	public String printBoard() {
		String rep = "";

		/* Check parameter validity */
		if (board == null)
			return rep;

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
				rep += printField(fields[r][c]);
			}
			rep += String.format("%n");
		}

		return rep;
	}

	/**
	 * Prints the given field object.
	 * 
	 * @param field Specifies the field object which is to be printed.
	 */
	private String printField(Field field) {
		if (field.isEndPos()) {
			if (field.getFigure() == null) {
				return "[.]";
			} else {
				switch (field.getFigure().eClass().getName()) {
				case "Sokoban":
					return "[+]";
				case "Block":
					return "[*]";
				default:
					return "[?]";
				}
			}
		} else {
			if (field.getFigure() == null) {
				return "[ ]";
			} else {
				switch (field.getFigure().eClass().getName()) {
				case "Sokoban":
					return "[@]";
				case "Block":
					return "[$]";
				case "Boulder":
					return "[#]";
				default:
					return "[?]";
				}
			}
		}
	}

	@Override
	protected String imageFolder() {
		return "../SokobanGUI/images/";
	}

	public void setPlayModus(boolean b) {
		playAction.setPlayModus(b);
	}

	public Field getField(int i, int j) {
		return buttons[i][j].getField();
	}

	public void moveFigure(Field from, Field to) {
		selectField(from);
		selectField(to);
	}
}
