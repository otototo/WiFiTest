/**
 * 
 */
package helpers;

import gui.grid.GridPanel;

import java.util.ArrayList;
import java.util.List;

import algorithm.SignalCalc;
import algorithm.WiFiPositionCalc;
import data.Device;
import data.EmuData;

/**
 * @author paulina
 *
 */
public class WiFiCalcUpdate
{
	private WiFiPositionCalc calculateWiFiPosition;
	private EmuData emuData;
	private GridPanel gridPanel;
	
	/**
	 * @param gridPanel 
     * 
     */
    public WiFiCalcUpdate(EmuData emuData, GridPanel gridPanel)
    {
	    this.setCalculateWiFiPosition(new WiFiPositionCalc());	
	    this.setEmuData(emuData);
	    this.setGridPanel(gridPanel);
    }
	


	/**
	 * 
	 */
    public void calculateWiFiPostitions()
    {
	    System.out.println("WiFiCalcUpdate.calculateWiFiPostitions()+");
	    
	    List<Device> wifis = getEmuData().getWiFiStationsReal();
	    List<Device> mobiles = new ArrayList<Device>(); 
	    List<Device> allMobiles = emuData.getMobileDevices(); 
	    
	    Device wifical;
	    
	    double[][] coord;
	    double strength;
	    double[] distance;
	   
	    int wifiId;
		Device wifi;
	    int ret;
	    
	    for (int i = 0; i < wifis.size(); i++)
	    {
	    	wifi = wifis.get(i);
	    	wifiId = wifi.getId();
	    	coord = new double[allMobiles.size()][2];
	    	distance = new double[allMobiles.size()];
	    	
	    	for (int j = 0; j < allMobiles.size(); j++)
	    	{
	    		coord[j][0] = allMobiles.get(j).getX();
	    		coord[j][1] = allMobiles.get(j).getY();
	    		strength = allMobiles.get(j).getSignalStrength(wifiId);
	    		distance[j] = signalToDistance(strength);
	    	}
	    	
	    	ret = calculateWiFiPosition.calculate(coord, distance);
	    	System.out.println("calculateWiFiPostitions.calculate ret="+ret);
	    	if (ret == 1)
	    	{
	    		double x = calculateWiFiPosition.getIntersection()[0];
	    		double y = calculateWiFiPosition.getIntersection()[1];
		    	System.out.println("calculateWiFiPostitions x="+x+" y="+y);
	    		wifical = new Device(wifi.getDeviceType(), x, y, wifiId);
	    		
	    		int cellx = (int)x;
	    		int celly = (int)y;
	    		
	    		getGridPanel().addPrediction(wifical, cellx, celly);
	    		
	    		emuData.addWiFiStationCalculated(wifical, true);
	    	}
	    }
	    
	    
	    System.out.println("WiFiCalcUpdate.calculateWiFiPostitions()-");
    }


   
	/**
	 * @return the calculateWiFiPosition
	 */
    public WiFiPositionCalc getCalculateWiFiPosition()
    {
	    return calculateWiFiPosition;
    }


	/**
	 * @param calculateWiFiPosition the calculateWiFiPosition to set
	 */
    public void setCalculateWiFiPosition(WiFiPositionCalc calculateWiFiPosition)
    {
	    this.calculateWiFiPosition = calculateWiFiPosition;
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
	 * @return the gridPanel
	 */
    public GridPanel getGridPanel()
    {
	    return gridPanel;
    }


	/**
	 * @param gridPanel the gridPanel to set
	 */
    public void setGridPanel(GridPanel gridPanel)
    {
	    this.gridPanel = gridPanel;
    }
    private double signalToDistance(Double signal)
    {
	    return SignalCalc.getDistanceBySignal(signal, EmuData.DEFAULT_MAX_SIGNAL_FREQUENCY);
    }
}
