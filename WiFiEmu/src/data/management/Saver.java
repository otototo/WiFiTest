/**
 * 
 */
package data.management;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import data.Device;
import data.EmuData;

/**
 * @author paulina
 *
 */
public class Saver
{
	Properties properties;
	/**
     * 
     */
    public Saver()
    {
	    properties = new Properties();
    }
    
    public void save(File fileToSave, EmuData emuData)
    {
    	OutputStream os = null;
    	
    	try
        {
	        os = new FileOutputStream(fileToSave);
	        
	        readEmuData(emuData);
	        properties.store(os, "Save File used for WiFiEmu. Property of PJINC");
	        
        } 
    	catch (IOException e)
        {
	        e.printStackTrace();
        }
    	finally
    	{
    		try
            {
	            os.close();
            } catch (IOException e)
            {
	            e.printStackTrace();
            }
    		properties.clear();
    	}
    }

	/**
	 * @param emuData
	 */
    private void readEmuData(EmuData emuData)
    {
	    setGridSize(emuData);
	    setDecreasePerCell(emuData);
	    setRealWiFiStations(emuData);
	    setCalculatedWifiStations(emuData);
	    setSignalStrength(emuData);
	    setMobileDevices(emuData);
    }

	/**
	 * @param emuData
	 */
    private void setCalculatedWifiStations(EmuData emuData)
    {

    	String value = getDevicesString(emuData.getWiFiStationsCalculated());
	    properties.setProperty(SaveFormat.WIFI_CALC.getValue(), value);
    }

	/**
	 * @param emuData
	 */
    private void setMobileDevices(EmuData emuData)
    {
    	String value = getDevicesString(emuData.getMobileDevices());
	    properties.setProperty(SaveFormat.MOBILE.getValue(), value);
    }

	/**
	 * @param emuData
	 */
    private void setRealWiFiStations(EmuData emuData)
    {
    	String value = getDevicesString(emuData.getWiFiStationsReal());
	    properties.setProperty(SaveFormat.WIFI_REAL.getValue(), value);
	    
    }
    
    private String getDevicesString(List<Device> devices)
    {
    	String ret = "{";
    	
    	for (Device device : devices)
    	{
    		ret += device.toString() + ",";
    	}
    	
    	if (ret.endsWith(",")) ret = ret.substring(0, ret.length()-1);
    	ret += "}";
    	return ret;
    }

	/**
	 * @param emuData
	 */
    private void setGridSize(EmuData emuData)
    {
	    properties.setProperty(SaveFormat.SIZE.getValue(), 
	    		emuData.getGridColumnCount()+"x"+emuData.getGridRowCount());
	    
    }

	/**
	 * @param emuData
	 */
    private void setSignalStrength(EmuData emuData)
    {
	    properties.setProperty(SaveFormat.SIGNAL_STRENGTH.getValue(), 
	    		emuData.getStationStrength()+"");
	    
    }

	/**
	 * @param emuData
	 */
    private void setDecreasePerCell(EmuData emuData)
    {
	    properties.setProperty(SaveFormat.DECREASE_PER_CELL.getValue(), 
	    		emuData.getDecreasePerCell()+"");
    }
}
