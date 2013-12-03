package com.stefankopieczek.automata.gameoflife;

import com.stefankopieczek.automata.AutomataDisplay;

public class GameOfLife 
{
	public static final int SIZE = 10;
	public static final float DENSITY = 0.7f;
	public static final float SCALE = 5.0f;
	public static final int INITIAL_DELAY = 1000;
	public static final int GENERATION_GAP = 50;
	public static final int REDRAW_GAP = 50;
	
	public static void main(String[] args)
	{
		final LifeSheet firstSheet = LifeSheet.getRandomSheet(-SIZE, -SIZE, SIZE, SIZE, DENSITY);
		
		AutomataDisplay.doEverything(SIZE, SCALE, INITIAL_DELAY, GENERATION_GAP, REDRAW_GAP, firstSheet);
	}
}
