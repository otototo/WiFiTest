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

import data.EmuData;

/**
 * Cannot put into list
 * */


public class ListPanel extends JPanel implements EmuDataListener
{
	private EmuData emuData;
	private String myData[] = {"<Empty>"};
	
	private DefaultListModel<String> mobileNames;
	private DefaultListModel<String> wifiRealNames;
	
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

	   	wifiRealNames = new DefaultListModel<String>();
	   	JList<String> wifis = new JList<String>();
	   	wifis.setModel(wifiRealNames);	   	   	
	   	
	   	JScrollPane scrollbar1 = new JScrollPane(mobiles);  /*neveikia*/
	   	scrollbar1.setEnabled(true);
	    add(scrollbar1); 
	   	JScrollPane scrollbar2 = new JScrollPane(wifis);  /*neveikia*/
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
    	System.out.println("ListPanel.onEmuDataChange()+");
    	System.out.println("id:"+id);
    	if ((id == ChangeIdentifier.WIFIR) || (id == ChangeIdentifier.ALL))
    	{
    		String[] wifir = emuData.getWiFiStationsRealNames();
    		System.out.println("count="+emuData.getWiFiStationsRealCount());
    		for (int i = 0; i < wifir.length; i++)
    		{
    			System.out.println("wifir="+wifir[i]);
    			if (!wifiRealNames.contains(wifir[i]))
    				wifiRealNames.addElement(wifir[i]);
    		}
    	}
    	if ((id == ChangeIdentifier.MOBILE) || (id == ChangeIdentifier.ALL))
    	{
    		String[] mobiles = emuData.getMobileDevicesNames();
    		System.out.println("count="+emuData.getMobileDevicesCount());
    		for (int i = 0; i < mobiles.length; i++)
    		{
    			System.out.println("mobiles="+mobiles[i]);
    			if (!mobileNames.contains(mobiles[i]))
    				mobileNames.addElement(mobiles[i]);
    		}
    	}
    	System.out.println("ListPanel.onEmuDataChange()-");
    }
}
