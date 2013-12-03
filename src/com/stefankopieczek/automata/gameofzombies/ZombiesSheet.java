package com.stefankopieczek.automata.gameofzombies;

import java.util.Random;

import com.stefankopieczek.automata.AutomataSheet;
import com.stefankopieczek.automata.gameoflife.LifeSheet;

public class ZombiesSheet extends AutomataSheet 
{
	public static final int DEAD = 0;
	public static final int HUMAN = 1;
	public static final int ZOMBIE = 2;
	
	/**
	 * How tough the zombies are. Between 2 and 3 is a sensible range.
	 */
	private static final float LETHALITY = 3f;
	
	@Override
	protected int getStateFromNeighbours(int currentState, int[] neighbours) 
	{		
		int deadNeighbours = countState(DEAD, neighbours);
		int humanNeighbours = countState(HUMAN, neighbours);
		int zombieNeighbours = countState(ZOMBIE, neighbours);
		
		if (currentState == DEAD)
		{
			if (humanNeighbours == 3)
				return HUMAN;
		}
		else if (currentState == HUMAN)
		{
			if (humanNeighbours < zombieNeighbours * LETHALITY)
			{
				return ZOMBIE;
			}			
			else if (8 - deadNeighbours > 4)
			{
				// Overcrowding
				return DEAD;
			}
			else if (humanNeighbours < 2)
			{
				// Loneliness
				return DEAD;
			}
		}
		else if (currentState == ZOMBIE)
		{
			if (humanNeighbours >= 5)
			{
				return DEAD;
			}
			else if (humanNeighbours == 0)
			{
				return DEAD;
			}
		}
		
		return currentState;		
	}

	@Override
	public AutomataSheet newBlankSheet() 
	{
		return new ZombiesSheet();
	}
	
	private int countState(int desired, int[] states)
	{
		int total = 0;
		for (int state : states)
		{
			if (state == desired)
				total++;
		}
		
		return total;
	}
	
	public static ZombiesSheet getRandomSheet(int minX, int minY, int maxX, int maxY, float humanChance, float zombieChance)
	{
		Random random = new Random();
		ZombiesSheet sheet = new ZombiesSheet();		
		for (int x = minX; x <= maxX; x++)
		{
			for (int y = minY; y <= maxY; y++)
			{
				float roll = random.nextFloat();
				int state;
				if (roll < humanChance)
					state = HUMAN;
				else if (roll < humanChance + zombieChance)
					state = ZOMBIE;
				else
					state = DEAD;
				
				sheet.setState(x, y, state);		
			}
		}
		
		return sheet;
	}
}
