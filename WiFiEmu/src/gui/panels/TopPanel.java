package gui.panels;

import gui.buttons.DeviceButton;
import gui.buttons.StationButton;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import data.EmuData;

public class TopPanel extends JPanel
{
	private StationButton stationButton = new StationButton();
	private DeviceButton deviceButton = new DeviceButton();
	
	private EmuData emuData;
	/**
	 * @param emuData 
     * 
     */
    public TopPanel(EmuData emuData)
    {
	    super();
//	    add(deviceButton);
//	    add(stationButton);
	    
//	    setSize(20,20);
	    
	    
//	    setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	    setBorder(BorderFactory.createEtchedBorder());
    }
}
