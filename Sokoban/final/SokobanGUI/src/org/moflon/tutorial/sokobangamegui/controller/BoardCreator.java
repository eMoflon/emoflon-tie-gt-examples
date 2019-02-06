package org.moflon.tutorial.sokobangamegui.controller;

import SokobanRules.Board;
import SokobanRules.Field;
import SokobanRules.SokobanRulesFactory;

/**
 * The BoardCreator class has only a single, static function to create an
 * instance of the "Board" class.
 * 
 * @author Matthias Senker (Comments by Lukas Hermanns)
 */
public class BoardCreator {

	/**
	 * This method is needed to create an empty board even without the create
	 * function of Board, because that is only implemented in chapter 2.
	 * 
	 * @param width
	 *            Specifies the board width or rather the count of fields in the X
	 *            direction.
	 * @param height
	 *            Specifies the board height or rather the count of fields in the Y
	 *            direction.
	 * @return New instance of the "Board" class containing an empty board with the
	 *         given size.
	 */
	public static Board createEmptyBoard(int width, int height) {
		/* Setup tutorial factory and create board with given size */
		SokobanRulesFactory factory = SokobanRulesFactory.eINSTANCE;
		Board board = factory.createBoard();
		board.setWidth(width);
		board.setHeight(height);

		/* Allocate field array */
		Field[][] fields = new Field[height][width];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				/* Create new field with tutorial factory */
				Field f = factory.createField();

				/* Insert field into array */
				fields[row][col] = f;

				/* Setup field attributes */
				f.setBoard(board);
				f.setRow(row);
				f.setCol(col);
			}
		}

		/* Setup field connections to the surrounding fields */
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (row > 0)
					fields[row][col].setTop(fields[row - 1][col]);
				if (row < height - 1)
					fields[row][col].setBottom(fields[row + 1][col]);
				if (col > 0)
					fields[row][col].setLeft(fields[row][col - 1]);
				if (col < width - 1)
					fields[row][col].setRight(fields[row][col + 1]);
			}
		}

		return board;
	}

}
