package org.moflon.tutorial.sokobangamegui.rules;

import SokobanLanguage.Board;
import SokobanLanguage.Field;
import SokobanLanguage.Figure;
import SokobanLanguage.SokobanLanguageFactory;
import SokobanLanguage.SokobanValidator;

public class SokobanRules {
	private String allsWell = "Everything seems to be ok...";
	private SokobanValidator validator;

	public SokobanRules(Board board) {
		this.validator=SokobanLanguageFactory.eINSTANCE.createSokobanValidator();
	}

	public Result move(Figure figure, Field field) {
			if(validator.move(figure, field)) {
				return new Result(true, allsWell);
			}
			else {
				return new Result(false,"Move is not allowed");
			}
	}

	public Result validateBoard(Board board) {
		if(validator.validateBoard(board)) {
			return new Result(true, allsWell);
		}
		else return new Result(false,"Something is not allowed");
		// FIXME: Check if the board is valid
	}
}
