package com.stefankopieczek.automata.gameofzombies;

import com.stefankopieczek.automata.AutomataDisplay;

public class GameOfZombies 
{
	public static final int SIZE = 15;
	public static final float HUMAN_CHANCE = 0.7f;
	public static final float ZOMBIE_CHANCE = 0.05f;
	public static final float SCALE = 2.5f;
	public static final int INITIAL_DELAY = 3000;
	public static final int GENERATION_GAP = 50;
	public static final int REDRAW_GAP = 50;
	
	public static void main(String[] args)
	{
		final ZombiesSheet firstSheet = ZombiesSheet.getRandomSheet(-SIZE, -SIZE, SIZE, SIZE, HUMAN_CHANCE, ZOMBIE_CHANCE);
		
		AutomataDisplay.doEverything(SIZE, SCALE, INITIAL_DELAY, GENERATION_GAP, REDRAW_GAP, firstSheet);
	}
}
