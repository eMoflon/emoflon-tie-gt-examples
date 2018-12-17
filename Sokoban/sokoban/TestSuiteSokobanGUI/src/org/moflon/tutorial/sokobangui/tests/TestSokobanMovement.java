package org.moflon.tutorial.sokobangui.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.moflon.tutorial.sokobangamegui.controller.BoardCreator;
import org.moflon.tutorial.sokobangamegui.controller.Controller;

class TestSokobanMovement {
	
	private TestView view;
	private Controller controller;
	
	@BeforeEach
	public void createView() {
		controller = new Controller((c, b) -> {
			view = new TestView(c, b);
			return view;
		});
		controller.switchBoard(BoardCreator.createEmptyBoard(8, 8));
	}

	@Test
	public void testValidBoardMoveSokoban() {
		view.createSokoban(2,2);
		view.createBlock(5,6);
		view.createBoulder(3,2);
		view.createEndPos(2,5);
		assertEquals(ExpectedBoards.validBoard(), view.printBoard());
		assertTrue(controller.boardIsValid());
		view.setPlayModus(true);
		view.moveFigure(view.getField(2,2), view.getField(2,3));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testValidBoardMoveBlock() {
		view.createSokoban(2,2);
		view.createBlock(2,3);
		view.createBoulder(3,2);
		view.createEndPos(2,5);
		assertEquals(ExpectedBoards.validBoardMoveBlock(), view.printBoard());
		assertTrue(controller.boardIsValid());
		view.setPlayModus(true);
		view.moveFigure(view.getField(2,2), view.getField(2,3));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		view.moveFigure(view.getField(2,3), view.getField(2,4));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(ExpectedBoards.validBoardMoveBlockAfterMove(), view.printBoard());
	}
	
	@Test
	public void testValidBoardMoveBoulder() {
		view.createSokoban(2,2);
		view.createBlock(3,2);
		view.createBoulder(2,3);
		view.createEndPos(2,5);
		assertEquals(ExpectedBoards.validBoardMoveBoulder(), view.printBoard());
		assertTrue(controller.boardIsValid());
		view.setPlayModus(true);
		view.moveFigure(view.getField(2,2), view.getField(2,3));
		//view.moveFigure(view.getField(2,3), view.getField(2,4));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(ExpectedBoards.validBoardMoveBoulder(), view.printBoard());
	}

}
