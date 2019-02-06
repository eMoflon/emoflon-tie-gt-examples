package org.moflon.tutorial.sokobangamegui.rules;

import SokobanRules.Board;
import SokobanRules.Field;
import SokobanRules.Figure;
import SokobanRules.SokobanRulesFactory;
import SokobanRules.SokobanValidator;

public class SokobanRules {
	private final String allsWell = "Everything seems to be ok...";
	private final SokobanValidator validator;

	public SokobanRules(final Board board) {
		this.validator = SokobanRulesFactory.eINSTANCE.createSokobanValidator();
	}

	public Result move(final Figure figure, final Field field) {
		if (validator.move(figure, field)) {
			return new Result(true, allsWell);
		} else {
			return new Result(false, "Move is not allowed");
		}
	}

	public Result validateBoard(final Board board) {
		if (validator.validateBoard(board))
			return new Result(true, allsWell);
		else
			return new Result(false, "Something is not allowed");
	}
}
