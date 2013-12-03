package com.stefankopieczek.automata;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import com.stefankopieczek.automata.AutomataSheet;
import com.stefankopieczek.automata.gameoflife.LifeSheet;
import com.stefankopieczek.cellsheets.CellFrame;

public abstract class AutomataDisplay 
{	
	public static void doEverything(int size, float scale, final int initialDelay, final int generationGap, final int redrawGap, final AutomataSheet firstSheet)
	{								
		final CellFrame frame = new CellFrame(firstSheet);				
		frame.setScale(scale);
		frame.setVisible(true);				
		
		final Switch isRunning = new Switch(true);
		
		final Thread genThread = new Thread()
		{							
			public void run()
			{
				AutomataSheet sheet = firstSheet;
				justSleep(initialDelay);
				
				while (isRunning.mValue)
				{					
					justSleep(generationGap);
					sheet = sheet.getNextGeneration();
					frame.setCellSheet(sheet);					
				}
			}
						
			public void justSleep(long time)
			{
				try
				{
					sleep(time);
				}
				catch (InterruptedException e) {}
			}
		};
		genThread.setDaemon(true);
		genThread.start();
		
		final Thread drawThread = new Thread()
		{			
			public void run()
			{
				while (isRunning.mValue)
				{
					try
					{
						sleep(redrawGap);						
						SwingUtilities.invokeAndWait(new Runnable()
						{
							public void run()
							{
								frame.repaint();
							}
						});
					}
					catch (InterruptedException e) {}
					catch (InvocationTargetException e) {}
				}
			}
		};
		drawThread.setDaemon(true);
		drawThread.start();
		
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent ev)
			{
				isRunning.mValue = false;	
				
				System.exit(0);
			}
		});
	}
	
	private static class Switch
	{
		public boolean mValue;
		
		public Switch(boolean value)
		{
			mValue = value;
		}
	}
}
