/**
 * 
 */
package data.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.impl.dtd.models.CMLeaf;

import data.Device;
import data.DeviceType;
import data.EmuData;

/**
 * @author paulina
 *
 */
public class Loader
{
//	Properties properties;
	Document xmlDoc;
	XMLFunctions xmlFun;
	/**
     * 
     */
    public Loader()
    {
    	xmlFun = new XMLFunctions();
//	    properties = new Properties();
    }
    
    public void load(File fileToLoad, EmuData emuData)
    {
    	InputStream input = null;
    	
    	try
        {
	        input = new FileInputStream(fileToLoad);	
	        
	        xmlDoc = xmlFun.getParsedDocument(fileToLoad);
//	        properties.load(input);	        
	        emuData.clear();
	        fillEmuData(emuData);	        
	        emuData.setJustLoaded(true, true);
        } 
    	catch (IOException e)
        {
	        e.printStackTrace();
        } catch (ParserConfigurationException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (SAXException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
    	finally
    	{
    		try
            {
	            input.close();
            } 
    		catch (IOException e)
            {
	            e.printStackTrace();
            }
    	}
    }

	/**
	 * @param emuData
	 */
    private void fillEmuData(EmuData emuData)
    {
//    	setDecreasePerCell(emuData);
    	emuData.resetCount();
    	setWifiStations(emuData);
    	setMobiles(emuData);
    	setSize(emuData);
    	emuData.setJustLoaded(true, true);
    }


	/**
	 * @param emuData
	 */
    private void setMobiles(EmuData emuData)
    {
    	Node mobiles = xmlFun.getNodeByTag(xmlDoc.getDocumentElement(), SaveFormat.MOBILES.getValue(), 0);
    	
    	Device device;
    	double x, y, signalStrength;
    	int id, wifiId;
    	DeviceType deviceType = DeviceType.MOBILE;
    	Node coord, strengths;
    	
    	for (Node mobile = mobiles.getFirstChild(); 
    			mobile != null; 
    			mobile = mobile.getNextSibling())
    	{
    		coord = xmlFun.getNodeByTag(mobile, SaveFormat.COORD.getValue(), 0);
    		x = Double.parseDouble(xmlFun.getStringValueByTag(coord, SaveFormat.X.getValue(), 0));
    		y = Double.parseDouble(xmlFun.getStringValueByTag(coord, SaveFormat.Y.getValue(), 0));
    		id = xmlFun.getIntValue(mobile, SaveFormat.ID.getValue(), 0);
    		
    		device = new Device(deviceType, x, y, id);
    		emuData.addMobileDevice(device, false);
    		
    		strengths = xmlFun.getNodeByTag(mobile, SaveFormat.SIGNAL_STRENGTHS.getValue(), 0);
    		for (Node strength = strengths.getFirstChild(); 
    				strength != null; 
    				strength = strength.getNextSibling())
    		{
    			wifiId = xmlFun.getIntValue(strength, SaveFormat.ID.getValue(), 0);
    			signalStrength = xmlFun.getIntValue(strength, SaveFormat.VALUE.getValue(), 0);
    			device.addSignalStrength(wifiId, signalStrength);
    		}
    	}
    }

	/**
	 * @param emuData
	 */
    private void setSize(EmuData emuData)
    {	    
	    Node size = xmlFun.getNodeByTag(xmlDoc.getDocumentElement(), SaveFormat.SIZE.getValue(), 0);
	    
	    int temp = xmlFun.getIntValue(size, SaveFormat.WIDTH.getValue(), 0);
	    emuData.setGridColumnCount(temp, false);
	    
	    temp = xmlFun.getIntValue(size, SaveFormat.HEIGHT.getValue(), 0);	    
	    emuData.setGridRowCount(temp, false);
    }

	/**
	 * @param emuData
	 */
    private void setWifiStations(EmuData emuData)
    {
    	Node wifiStations = xmlFun.getNodeByTag(xmlDoc.getDocumentElement(), SaveFormat.WIFIS.getValue(), 0);
    	
    	Device wifi;
    	double x, y;
    	int id, frequency;
    	DeviceType deviceType = DeviceType.WIFI_REAL;
    	Node coord;
    	
    	for (Node wifiStation = wifiStations.getFirstChild(); 
    			wifiStation != null; 
    			wifiStation = wifiStation.getNextSibling())
    	{
    		frequency = xmlFun.getIntValue(wifiStation, SaveFormat.SIGNAL_FREQUENCY.getValue(), 0);
    		coord = xmlFun.getNodeByTag(wifiStation, SaveFormat.COORD.getValue(), 0);
    		x = Double.parseDouble(xmlFun.getStringValueByTag(coord, SaveFormat.X.getValue(), 0));
    		y = Double.parseDouble(xmlFun.getStringValueByTag(coord, SaveFormat.Y.getValue(), 0));
    		id = xmlFun.getIntValue(wifiStation, SaveFormat.ID.getValue(), 0);
    		
    		wifi = new Device(deviceType, frequency, x, y, id);
    		emuData.addWiFiStationReal(wifi, false);
    	}
    }
}
