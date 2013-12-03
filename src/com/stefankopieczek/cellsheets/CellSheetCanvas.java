package com.stefankopieczek.cellsheets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

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
		
		setKeyBindings();
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
	
	private void setKeyBindings()
	{
		KeyStroke MINUS_1 = KeyStroke.getKeyStroke("MINUS");
		KeyStroke MINUS_2 = KeyStroke.getKeyStroke("SUBTRACT");
		KeyStroke PLUS_1 = 
				KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 
						               KeyEvent.SHIFT_DOWN_MASK);
		KeyStroke PLUS_2 = KeyStroke.getKeyStroke("ADD");		
		KeyStroke LEFT = KeyStroke.getKeyStroke("LEFT");
		KeyStroke RIGHT = KeyStroke.getKeyStroke("RIGHT");
		KeyStroke UP = KeyStroke.getKeyStroke("UP");
		KeyStroke DOWN = KeyStroke.getKeyStroke("DOWN");
		
		InputMap inputMap = getInputMap();
		inputMap.put(MINUS_1, "evt_zoom_out");
		inputMap.put(MINUS_2, "evt_zoom_out");
		inputMap.put(PLUS_1, "evt_zoom_in");
		inputMap.put(PLUS_2, "evt_zoom_in");
		inputMap.put(LEFT, "evt_pan_left");
		inputMap.put(RIGHT, "evt_pan_right");
		inputMap.put(UP, "evt_pan_up");
		inputMap.put(DOWN, "evt_pan_down");
		
		ActionMap actionMap = getActionMap();
		actionMap.put("evt_zoom_out", new ZoomAction(0.9));
		actionMap.put("evt_zoom_in", new ZoomAction(1.2));
		actionMap.put("evt_pan_left", new PanAction(-1, 0));
		actionMap.put("evt_pan_right", new PanAction(1, 0));
		actionMap.put("evt_pan_up", new PanAction(0, -1));
		actionMap.put("evt_pan_down", new PanAction(0, 1));
	}
	
	private class ZoomAction extends AbstractAction
	{
		private double mScaleScale;
		
		public ZoomAction(double scaleScale)
		{
			mScaleScale = scaleScale;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{			
			mScale *= mScaleScale; 
		}		
	}
	
	private class PanAction extends AbstractAction
	{
		private int mXPan;
		private int mYPan;
		
		public PanAction(int xPan, int yPan)
		{
			mXPan = xPan;
			mYPan = yPan;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			mXOffset += mXPan;
			mYOffset += mYPan;
		}		
	}
}
