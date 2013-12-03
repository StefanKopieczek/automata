package com.stefankopieczek.automata.langtonsant;

import com.stefankopieczek.automata.AutomataSheet;

public class LangtonSheet extends AutomataSheet
{
	private static final int WHITE = 0;
	private static final int BLACK = 1;
	private static final int ANT_ON_BLACK_UP = 2;
	private static final int ANT_ON_BLACK_LEFT = 3;
	private static final int ANT_ON_BLACK_DOWN = 4;
	private static final int ANT_ON_BLACK_RIGHT = 5;
	private static final int ANT_ON_WHITE_UP = 6;
	private static final int ANT_ON_WHITE_LEFT = 7;
	private static final int ANT_ON_WHITE_DOWN = 8;
	private static final int ANT_ON_WHITE_RIGHT = 9;
	
	@Override
	protected int getStateFromNeighbours(int currentState, int[] neighbours) 
	{
		if (currentState == WHITE)
		{
			if (neighbours[1] == ANT_ON_BLACK_RIGHT || neighbours[1] == ANT_ON_WHITE_RIGHT)
				return ANT_ON_WHITE_DOWN;
			else if (neighbours[3] == ANT_ON_BLACK_DOWN || neighbours[3] == ANT_ON_WHITE_DOWN)
				return ANT_ON_WHITE_LEFT;
			else if (neighbours[4] == ANT_ON_BLACK_UP || neighbours[4] == ANT_ON_WHITE_UP)
				return ANT_ON_WHITE_RIGHT;
			else if (neighbours[6] == ANT_ON_BLACK_LEFT || neighbours[6] == ANT_ON_WHITE_LEFT)
				return ANT_ON_WHITE_UP;
		}
		else if (currentState == BLACK)
		{
			if (neighbours[1] == ANT_ON_BLACK_RIGHT || neighbours[1] == ANT_ON_WHITE_RIGHT)
				return ANT_ON_BLACK_UP;
			else if (neighbours[3] == ANT_ON_BLACK_DOWN || neighbours[3] == ANT_ON_WHITE_DOWN)
				return ANT_ON_BLACK_RIGHT;
			else if (neighbours[4] == ANT_ON_BLACK_UP || neighbours[4] == ANT_ON_WHITE_UP)
				return ANT_ON_BLACK_LEFT;
			else if (neighbours[6] == ANT_ON_BLACK_LEFT || neighbours[6] == ANT_ON_WHITE_LEFT)
				return ANT_ON_BLACK_DOWN;
		}
		else if (currentState == ANT_ON_WHITE_UP || currentState == ANT_ON_WHITE_LEFT ||
				 currentState == ANT_ON_WHITE_DOWN || currentState == ANT_ON_WHITE_RIGHT)
		{
			return BLACK;
		}
		else if (currentState == ANT_ON_BLACK_UP || currentState == ANT_ON_BLACK_LEFT ||
				 currentState == ANT_ON_BLACK_DOWN || currentState == ANT_ON_BLACK_RIGHT)
		{
			return WHITE;
		}
		
		return currentState;		
	}

	@Override
	public AutomataSheet newBlankSheet() 
	{
		return new LangtonSheet();
	}
	
	public static LangtonSheet getInitialSheet()
	{
		LangtonSheet sheet = new LangtonSheet();
		sheet.setState(0, 0, ANT_ON_WHITE_UP);
		return sheet;
	}

}
