package org.moflon.tutorial.sokobangui.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.moflon.tutorial.sokobangamegui.controller.BoardCreator;
import org.moflon.tutorial.sokobangamegui.controller.Controller;

class TestSokobanBoardImportExport {
	
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
	public void testValidBoardImportFile() {
		controller.loadSOKFile("boards/chaos.sok");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		assertEquals(ExpectedBoards.validBoardFileImport(), view.printBoard());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testValidBoardExportFile() {
		view.createSokoban(2,2);
		view.createBlock(5,6);
		view.createBoulder(3,2);
		view.createEndPos(2,5);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		controller.saveSOKFile("boards/gen_board.sok");
	}

}
