package gui.panels;

import gui.grid.GridPanel;
import helpers.WiFiCalcUpdate;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import data.EmuData;

public class TopPanel extends JPanel implements ActionListener
{
	private JButton realButton = new JButton("Reality");
	private JButton calcButton = new JButton("Calculated");
	private JButton mds = new JButton("MDS");
	
	private EmuData emuData;
	private WiFiCalcUpdate calcUpdate;
	/**
	 * @param emuData 
	 * @param gridPanel 
     * 
     */
    public TopPanel(EmuData emuData, GridPanel gridPanel) 
    {
	    super();
	    setEmuData(emuData);
//	    add(deviceButton);
//	    add(stationButton);
	    setLayout(new GridLayout());
	    
	    realButton.setEnabled(false);
	    add(realButton);
	    realButton.addActionListener(this);
	    
	    calcButton.setEnabled(true);
	    calcButton.addActionListener(this);
	    add(calcButton);
//	    setSize(20,20);
	    
	    
//	    setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	    setBorder(BorderFactory.createEtchedBorder());
	    
	    calcUpdate = new WiFiCalcUpdate(emuData, gridPanel);
    }
	/**
	 * @param emuData2
	 */
    private void setEmuData(EmuData emuData)
    {
	    this.emuData = emuData;
    }
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
    @Override
    public void actionPerformed(ActionEvent e)
    {
    	System.out.println(e);
	    if (e.getSource() == realButton)
	    {
	    	emuData.setRealView(true);
	    	calcButton.setEnabled(true);
	    	realButton.setEnabled(false);
	    }
	    else if (e.getSource() == calcButton)
	    {
	    	emuData.setRealView(false);
	    	calcButton.setEnabled(false);
	    	realButton.setEnabled(true);
	    	calcUpdate.calculateWiFiPostitions();
	    }
    }
    
    
}
