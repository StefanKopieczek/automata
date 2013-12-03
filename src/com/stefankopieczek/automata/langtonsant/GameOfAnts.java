package com.stefankopieczek.automata.langtonsant;

import com.stefankopieczek.automata.AutomataDisplay;

public class GameOfAnts 
{
	public static final int SIZE = 15;
	public static final float HUMAN_CHANCE = 0.7f;
	public static final float ZOMBIE_CHANCE = 0.05f;
	public static final float SCALE = 2f;
	public static final int INITIAL_DELAY = 1000;
	public static final int GENERATION_GAP = 0;
	public static final int REDRAW_GAP = 20;
	
	public static void main(String[] args)
	{
		final LangtonSheet firstSheet = LangtonSheet.getInitialSheet();
		
		AutomataDisplay.doEverything(SIZE, SCALE, INITIAL_DELAY, GENERATION_GAP, REDRAW_GAP, firstSheet);
	}
}
