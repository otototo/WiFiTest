package gui.panels;

import helpers.EmuDataListener;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import data.EmuData;

/**
 * Cannot put into list
 * */


public class ListPanel extends JPanel implements EmuDataListener
{
	private EmuData emuData;
	private String myData[] = {"<Empty>"};
	private JList<String> dataList1;
	private JList<String> dataList2;
	
	private ListModel<String> mobileListModeler;
	private ListModel<String> wifiListModeler;
	/**
	 * @param emuData 
     * 
     */
    public ListPanel(EmuData emuData)
    {
	   	super();
	   	setEmuData(emuData);
	   	setLayout(new FlowLayout());
//	   	setDataList1(new JList<String>(emuData.getMobileDevicesNames()));
//	   	setDataList2(new JList<String>(emuData.getWiFiStationsRealNames()));
	   	setDataList1(new JList<String>(myData));
	   	mobileListModeler = dataList1.getModel();
	   	
	   	setDataList2(new JList<String>(myData));
	   	wifiListModeler = dataList2.getModel();
	   	
	   	dataList1.setFixedCellWidth(200);
	   	add(dataList1);
	   	add(dataList2);
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
	/**
	 * @return the dataList2
	 */
    public JList<String> getDataList2()
    {
	    return dataList2;
    }
	/**
	 * @param jList the dataList2 to set
	 */
    public void setDataList2(JList<String> jList)
    {
	    this.dataList2 = jList;
    }
	/**
	 * @return the dataList1
	 */
    public JList<String> getDataList1()
    {
	    return dataList1;
    }
	/**
	 * @param dataList1 the dataList1 to set
	 */
    public void setDataList1(JList<String> dataList1)
    {
	    this.dataList1 = dataList1;
    }
	/* (non-Javadoc)
	 * @see helpers.EmuDataListener#onEmuDataChange()
	 */
    @Override
    public void onEmuDataChange(boolean isRealDataChange)
    {	    
//    	repaint();
    }
}
