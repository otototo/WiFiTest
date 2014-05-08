package gui.panels;

import helpers.ChangeIdentifier;
import helpers.EmuDataListener;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data.EmuData;

/**
 * Cannot put into list
 * */


public class ListPanel extends JPanel implements EmuDataListener, ListSelectionListener
{
	private EmuData emuData;
	private String myData[] = {"<Empty>"};
	
	private DefaultListModel<String> mobileNames;
	private DefaultListModel<String> wifiRealNames;
	
	private JList<String> wifis; 
	private int prevSelectedIndex = 0;
	
	/**
	 * @param emuData 
     * 
     */
    public ListPanel(EmuData emuData)
    {
	   	super();
	   	setEmuData(emuData);
	   	setLayout(new GridLayout(2, 1));
	   	
	   	mobileNames = new DefaultListModel<String>();
	   	JList<String> mobiles = new JList<String>();
	   	mobiles.setModel(mobileNames);
	   	
	   	JScrollPane scrollbar1 = new JScrollPane(mobiles); 
	   	scrollbar1.setEnabled(true);
	    add(scrollbar1); 

	   	wifiRealNames = new DefaultListModel<String>();
	   	wifis = new JList<String>();
	   	wifis.setModel(wifiRealNames);	   	   	
	   	wifis.addListSelectionListener(this);
	   	
	   	JScrollPane scrollbar2 = new JScrollPane(wifis); 
	   	scrollbar2.setEnabled(true);
	    add(scrollbar2); 
	    
	   	setBackground(Color.WHITE);
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
	/* (non-Javadoc)
	 * @see helpers.EmuDataListener#onEmuDataChange()
	 */
    @Override
    public void onEmuDataChange(boolean isRealDataChange, ChangeIdentifier id)
    {	    
    	if ((id == ChangeIdentifier.WIFIR) || (id == ChangeIdentifier.ALL))
    	{
    		String[] wifir = emuData.getWiFiStationsRealNames();
    		changeListModeler(wifir, wifiRealNames);
    	}
    	if ((id == ChangeIdentifier.MOBILE) || (id == ChangeIdentifier.ALL))
    	{
    		String[] mobiles = emuData.getMobileDevicesNames();
    		changeListModeler(mobiles, mobileNames);
    	}
    }
    
    public void changeListModeler(String[] data, DefaultListModel<String> model)
    {
		int count = data.length;
		if ((count < 1) && (model.getSize() > 0))
		{
			model.clear();
		}
		else if (count > 0)
		{
    		for (int i = 0; i < data.length; i++)
    		{
    			System.out.println("data="+data[i]);
    			if (!model.contains(data[i]))
    				model.addElement(data[i]);
    		}
		}
    }
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
		if (!e.getValueIsAdjusting())
		{
//			emuData.setWiFiSRIsSelected(prevSelectedIndex, false);
//			emuData.setWiFiSRIsSelected(wifis.getSelectedIndex(), true);
			
			emuData.getWiFiStationReal(prevSelectedIndex).setSelected(false);
			emuData.getWiFiStationReal(wifis.getSelectedIndex()).setSelected(true);
		}
    }
}
