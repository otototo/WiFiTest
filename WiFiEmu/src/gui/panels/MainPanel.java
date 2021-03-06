/**
 * 
 */
package gui.panels;

import gui.grid.GridPanel;
import helpers.EmuDataNotifier;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import data.EmuData;

/**
 * @author Paul Mora
 *
 */
public class MainPanel extends JPanel
{
	TopPanel topPanel;
	GridPanel gridPanel;
	ListPanel listPanel;
	
	private EmuData emuData;
	/**
	 * @param notifier 
     * 
     */
    public MainPanel(EmuData emuData, EmuDataNotifier notifier)
    {
	    super();

	    setLayout(new BorderLayout());
	    
	    gridPanel = new GridPanel(emuData);
	    notifier.addListener(gridPanel);
	    add(gridPanel, BorderLayout.CENTER);
	    
	    topPanel = new TopPanel(emuData, gridPanel);
	    add(topPanel, BorderLayout.PAGE_START);
	    notifier.addListener(topPanel);
	    
//	    listPanel = new ListPanel(emuData);
//	    add(listPanel, BorderLayout.LINE_END);
//	    notifier.addListener(listPanel);
	    
//	    JScrollPane scrollbar = new JScrollPane(gridPanel);  /*neveikia*/
//	    add(scrollbar, BorderLayout.CENTER);  
    }
	/**
	 * @return the emuData
	 */
    public EmuData getEmuData()
    {
	    return emuData;
    }
	/**
	 * @param emuData the emuData to set
	 */
    public void setEmuData(EmuData emuData)
    {
	    this.emuData = emuData;
    }
}
