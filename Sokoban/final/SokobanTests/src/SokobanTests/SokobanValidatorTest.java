package SokobanTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import SokobanRules.Block;
import SokobanRules.Board;
import SokobanRules.Field;
import SokobanRules.Sokoban;
import SokobanRules.SokobanRulesFactory;
import SokobanRules.SokobanValidator;

public class SokobanValidatorTest {

	private static final SokobanRulesFactory FACTORY = SokobanRulesFactory.eINSTANCE;
	private Board board;
	private SokobanValidator validator;

	@BeforeEach
	public void setUp() {
		board = FACTORY.createBoard();
		validator = FACTORY.createSokobanValidator();
	}

	@Test
	public void test_boardWithNoSokobanIsInvalid() throws Exception {
		assertFalse(validator.validateBoard(board));
	}

	@Test
	public void test_boardWithSokobanButNoEndFieldIsInvalid() throws Exception {
		final Field field = FACTORY.createField();
		board.getFields().add(field);
		final Sokoban sokoban = FACTORY.createSokoban();
		sokoban.setField(field);
		assertFalse(validator.validateBoard(board));
	}

	@Test
	public void test_boardWithSokobanAndNoBlockAndEndFieldInValid() throws Exception {
		final Field fieldWithSokoban = FACTORY.createField();
		fieldWithSokoban.setEndPos(true);
		board.getFields().add(fieldWithSokoban);

		final Sokoban sokoban = FACTORY.createSokoban();
		sokoban.setField(fieldWithSokoban);
		assertFalse(validator.validateBoard(board));
	}

	@Test
	public void test_boardWithSokobanAndBlockAndEndFieldValid() throws Exception {
		final Field fieldWithBlock = FACTORY.createField();
		board.getFields().add(fieldWithBlock);

		final Block block = FACTORY.createBlock();
		fieldWithBlock.setFigure(block);

		final Field fieldWithSokoban = FACTORY.createField();
		fieldWithSokoban.setEndPos(true);
		board.getFields().add(fieldWithSokoban);

		final Sokoban sokoban = FACTORY.createSokoban();
		sokoban.setField(fieldWithSokoban);
		assertTrue(validator.validateBoard(board));
	}

	@Test
	public void test_boardWithTwoSokobansIsInvalid() throws Exception {
		{
			final Field field = FACTORY.createField();
			board.getFields().add(field);
			final Sokoban sokoban = FACTORY.createSokoban();
			sokoban.setField(field);
		}
		{
			final Field field = FACTORY.createField();
			board.getFields().add(field);
			final Sokoban sokoban = FACTORY.createSokoban();
			sokoban.setField(field);
		}
		assertFalse(validator.validateBoard(board));
	}
}
