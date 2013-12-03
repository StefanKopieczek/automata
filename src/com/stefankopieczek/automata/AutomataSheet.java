package com.stefankopieczek.automata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.stefankopieczek.automata.gameoflife.LifeSheet;
import com.stefankopieczek.cellsheets.SimpleCellSheet;

public abstract class AutomataSheet extends SimpleCellSheet 
{
	/**
	 * If not null, need only draw the pairs in this set. 
	 */
	private List<Pair> mNeedToDraw = null;
	
	protected int[] getNeighbours(int x, int y)
	{
		return new int[]
		{
			getState(x-1, y-1),
			getState(x-1, y),
			getState(x-1, y+1),
			getState(x, y-1),			
			getState(x, y+1),
			getState(x+1, y-1),
			getState(x+1, y),
			getState(x+1, y+1)
		};
	}
	
	public void needToDraw(int x, int y)
	{
		if (mNeedToDraw == null)
		{
			mNeedToDraw = new ArrayList<Pair>();
		}
		
		mNeedToDraw.add(new Pair(x, y));
	}
	
	protected int getNextState(int x, int y)
	{
		int currentState = getState(x, y);
		int[] neighbours = getNeighbours(x, y);
		return getStateFromNeighbours(currentState, neighbours);
		
	}
	
	protected abstract int getStateFromNeighbours(int currentState, int[] neighbours);
	
	public AutomataSheet getNextGeneration()
	{
		AutomataSheet next = newBlankSheet();
		
		for (int x = getCurrentMinX()-1; x <= getCurrentMaxX()+1; x++)
		{
			for (int y = getCurrentMinY()-1; y <= getCurrentMaxY()+1; y++)
			{
				next.setState(x, y, getNextState(x, y));
				if (next.getState(x, y) != getState(x, y))
					next.needToDraw(x, y);
			}
		}
		
		return next;
	}
	
	public abstract AutomataSheet newBlankSheet();
}
