package com.stefankopieczek.cellsheets;

import java.util.List;

public abstract class CellSheet 
{
	public abstract int getState(int x, int y);
	
	public abstract void setState(int x, int y, int value);
	
	public abstract int getCurrentMinX();
	
	public abstract int getCurrentMaxX();
	
	public abstract int getCurrentMinY();
	
	public abstract int getCurrentMaxY();
	
	public abstract List<Pair> getDrawList();
	
	public class Pair
	{
		final int mX;
		final int mY;
		public Pair(int x, int y)
		{
			mX = x;
			mY = y;
		}
	}
}
