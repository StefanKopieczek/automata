package com.stefankopieczek.cellsheets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import com.stefankopieczek.cellsheets.CellSheet.Pair;

public class CellSheetCanvas extends JComponent 
{
	private CellSheet mCellSheet;
	
	private int mXOffset = 0;
	private int mYOffset = 0;
	private float mScale = 0.0f;
	
	private ArrayList<Color> mColors = new ArrayList<Color>();
	
	public CellSheetCanvas(CellSheet cellSheet)
	{
		mCellSheet = cellSheet;
		
		mColors.add(Color.WHITE);
		mColors.add(Color.BLACK);
		mColors.add(Color.RED);
		mColors.add(Color.BLUE);
		mColors.add(Color.GREEN);
		mColors.add(new Color(0x88, 0x00, 0x88));
		mColors.add(Color.ORANGE);
		mColors.add(Color.CYAN);
		mColors.add(Color.MAGENTA);
		mColors.add(Color.PINK);
		
		setPreferredSize(new Dimension(320, 320));
		setBackground(mColors.get(0));
	}
	
	public synchronized void setCellSheet(CellSheet cellSheet)
	{
		mCellSheet = cellSheet;
	}
	
	private CellSheet getCellSheet()
	{
		return mCellSheet;
	}
	
	public void setColor(int state, Color color)
	{
		mColors.set(state, color);
	}
	
	public void setScale(float scale)
	{
		mScale = scale;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{					
		int width = getWidth();
		int height = getHeight();
		
		if (mScale == 0.0f)
		{			
			int xExtent = Math.max(Math.abs(mCellSheet.getCurrentMaxX()), Math.abs(mCellSheet.getCurrentMinX()));
			int yExtent = Math.max(Math.abs(mCellSheet.getCurrentMaxY()), Math.abs(mCellSheet.getCurrentMinY()));
			mScale = Math.min(width / (xExtent * 2), height / (yExtent * 2));				
		}							
		
//		minX = Math.min(minX, mCellSheet.getCurrentMinX());
//		maxX = Math.min(maxY, mCellSheet.getCurrentMaxX());
//		minY = Math.min(minY, mCellSheet.getCurrentMinY());
//		maxY = Math.min(maxY, mCellSheet.getCurrentMaxY());							
				
		List<Pair> drawList = mCellSheet.getDrawList();
		if (drawList == null)
		{
			synchronized(this)
			{
				g.setColor(mColors.get(0));
				g.fillRect(0, 0, width, height);
			
				int minX = (int)Math.floor((-width/2 + mXOffset) / mScale);
				int maxX = (int)Math.ceil((width/2 + mXOffset) / mScale);
				int minY = (int)Math.floor((-height/2 + mYOffset) / mScale);
				int maxY = (int)Math.ceil((height/2 + mYOffset) / mScale);
				
				for (int y = minY; y < maxY; y++)
				{			
					for (int x = minX; x < maxX; x++)
					{								
						drawPoint(g, x, y, width, height);
					}
				}
			}
		}
		else
		{
			for (Pair pair : drawList)
			{
				drawPoint(g, pair.mX, pair.mY, width, height);
			}
		}				
	}		
		
	private void drawPoint(Graphics g, int x, int y, int width, int height)
	{
		int state = mCellSheet.getState(x, y);
		if (state == 0)
			return;
		
		g.setColor(mColors.get(state));				
		double xCoord = x * mScale - mXOffset + width/2;
		double yCoord = y * mScale - mYOffset + height/2;		
		g.fillRect((int)Math.floor(xCoord), (int)Math.floor(yCoord),
				(int)Math.ceil(mScale), (int)Math.ceil(mScale));
	}
}
