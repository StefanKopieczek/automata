package com.stefankopieczek.cellsheets;

import java.util.List;
import java.util.Vector;

public class SimpleCellSheet extends CellSheet 
{
	/**
	 * The rows in the spreadsheet, in Cauchy ordering
	 * (0, 1, -1, 2, -2, 3, -3, ...).
	 * Each row is a vector of integers, in the same ordering.
	 */
	private Vector<Vector<Integer>> mRows;
	
	private int mXmin = 0;
	
	private int mXmax = 0;
	
	private int mYmin = 0;
	
	private int mYmax = 0;
	
	public SimpleCellSheet()
	{
		mRows = new Vector<Vector<Integer>>();
	}
	
	@Override
	public int getState(int x, int y) 
	{				
		int result;
		try
		{
			Vector<Integer> row = mRows.get(getArrayIdx(y));
			Integer colVal = row.get(getArrayIdx(x));
			if (colVal == null)
				colVal = 0;
			result = colVal.intValue();
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			result = 0;
		}
		
		return result;
	}

	@Override
	public void setState(int x, int y, int value) 
	{
		while (mRows.size() <= Math.abs(y) * 2 + 2)
		{
			mRows.add(new Vector<Integer>());
		}
				
		Vector<Integer> row = mRows.get(getArrayIdx(y));
		
		while (row.size() <= Math.abs(x) * 2 + 3)
		{
			row.add(new Integer(0));
		}
		
		row.set(getArrayIdx(x), value);
		
		if (value != 0)
		{
			updateBounds(x, y);
		}
	}
	
	private int getArrayIdx(int coord)
	{
		int idx = Math.abs(coord * 2);
		if (coord > 0)
			idx -= 1;
		
		return idx;
	}

	@Override
	public int getCurrentMinX() 
	{
		return mXmin;
	}

	@Override
	public int getCurrentMaxX() 
	{
		return mXmax;
	}

	@Override
	public int getCurrentMinY() 
	{
		return mYmin;
	}

	@Override
	public int getCurrentMaxY() 
	{
		return mYmax;
	}

	protected void updateBounds(int x, int y)
	{
		if (x < mXmin)
			mXmin = x;
		
		if (x > mXmax)
			mXmax = x;
		
		if (y < mYmin)
			mYmin = y;
		
		if (y > mYmax)
			mYmax = y; 
	}

	@Override
	public List<Pair> getDrawList() 
	{
		return null;
	}
}
