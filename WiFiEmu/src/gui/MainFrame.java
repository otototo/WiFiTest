/**
 * 
 */
package gui;

import gui.panels.MainPanel;
import helpers.EmuDataNotifier;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import data.EmuData;

/**
 * @author Paul Mora
 *
 */
public class MainFrame
{
	MainPanel mainPanel;
	
	/**
     * 
     */
    public MainFrame()
    {    	
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) 
        {
        	ex.printStackTrace();
        }
        EmuDataNotifier notifier = new EmuDataNotifier();
        EmuData emuData = new EmuData(notifier);
        mainPanel = new MainPanel(emuData, notifier);
        
        JFrame frame = new JFrame("WiFi Simulator");

        frame.setSize(800, 800);
        frame.setMinimumSize(new Dimension(800, 800));
	    
	    MenuBar menuBar = new MenuBar(emuData);
	    frame.setJMenuBar(menuBar);
	    Container con = frame.getContentPane();
	    con.add(mainPanel);
	    frame.pack();
	    frame.setVisible(true);
	    
	    frame.setResizable(false);
	    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    
    }
}
