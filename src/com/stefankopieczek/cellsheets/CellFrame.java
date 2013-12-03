package com.stefankopieczek.cellsheets;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CellFrame extends JFrame 
{
	private CellSheetCanvas mCanvas;
	
	public CellFrame(CellSheet cellSheet)
	{
		super();				
		mCanvas = new CellSheetCanvas(cellSheet);			
		getContentPane().add(mCanvas);		
		pack();
		repaint();
	}
		
	public void setCellSheet(CellSheet cellSheet)
	{
		mCanvas.setCellSheet(cellSheet);		
	}
	
	public void setScale(float scale)
	{
		mCanvas.setScale(scale);
	}
	
	public CellSheetCanvas getCanvas()
	{
		return mCanvas;
	}
}
