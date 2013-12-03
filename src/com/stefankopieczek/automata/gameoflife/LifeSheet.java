package com.stefankopieczek.automata.gameoflife;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.stefankopieczek.automata.AutomataSheet;
import com.stefankopieczek.cellsheets.SimpleCellSheet;

public class LifeSheet extends AutomataSheet 
{	
	protected int getStateFromNeighbours(int currentState, int[] neighbours)
	{
		int total = 0;
		for (int neighbour : neighbours)
		{
			total += neighbour;
		}
						
		int next = 0;
		if (currentState == 0 && total == 3)
		{
			next = 1;			
		}
		else if (currentState == 1 && total < 2)
		{
			next = 0;
		}
		else if (currentState == 1 && total > 3)
		{
			next = 0;
		}
		else
		{
			next = currentState;
		}		
		
		return next;
	}	
	
	public static LifeSheet getRandomSheet(int minX, int minY, int maxX, int maxY, float lifeChance)
	{
		Random random = new Random();
		LifeSheet sheet = new LifeSheet();		
		for (int x = minX; x <= maxX; x++)
		{
			for (int y = minY; y <= maxY; y++)
			{
				int state = (random.nextFloat() > lifeChance ? 0 : 1);
				sheet.setState(x, y, state);		
			}
		}
		
		return sheet;
	}

	@Override
	public LifeSheet newBlankSheet() 
	{
		return new LifeSheet(); 
	}
}
