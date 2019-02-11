package SokobanTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import SokobanRules.Board;
import SokobanRules.Field;
import SokobanRules.SokobanRulesFactory;
import SokobanRules.SokobanTutorialAppendix;

public class SokobanTutorialAppendixTest {

	private static final SokobanRulesFactory FACTORY = SokobanRulesFactory.eINSTANCE;
	private Board board;
	private SokobanTutorialAppendix appendix;

	@BeforeEach
	public void setUp() {
		board = FACTORY.createBoard();
		appendix = FACTORY.createSokobanTutorialAppendix();
	}

	@Test
	void test_moveSokoban() throws Exception {
		// Create a square
		final Field field11 = FACTORY.createField();
		board.getFields().add(field11);
		final Field field12 = FACTORY.createField();
		board.getFields().add(field12);
		final Field field21 = FACTORY.createField();
		board.getFields().add(field21);
		final Field field22 = FACTORY.createField();
		board.getFields().add(field22);

		field11.setBottom(field21);
		field11.setRight(field12);
		field12.setBottom(field22);
		field21.setRight(field22);

		field11.setEndPos(true);
		field12.setEndPos(true);
		FACTORY.createBlock().setField(field11);
		FACTORY.createBlock().setField(field12);
		FACTORY.createBlock().setField(field21);

		appendix.removeEndFieldsWithBlock(board);

		assertNull(field11.getFigure());
		assertNull(field12.getFigure());
		assertNotNull(field21.getFigure());
	}
}
