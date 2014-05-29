/**
 * 
 */
package data.management;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import data.Device;
import data.EmuData;

/**
 * @author paulina
 *
 */
public class Saver
{
	private static final String ROOT_ELEMENT = "ROOT";
//	Properties properties;
	Document xmlDoc;
	XMLFunctions xmlFun;
	/**
     * 
     */
    public Saver()
    {
//	    properties = new Properties();
    	xmlFun = new XMLFunctions();
    }
    
    public boolean save(File fileToSave, EmuData emuData)
    {
    	boolean ret = true;;
	    try
        {
	    	xmlDoc = xmlFun.createDocument();
	        readEmuData(emuData);
	        xmlFun.saveXml(xmlDoc, fileToSave.getAbsolutePath());
        }
	    catch (ParserConfigurationException | TransformerException e)
        {
	        e.printStackTrace();
	        ret = false;
        }
	    
	    return ret;
    	/*OutputStream os = null;
    	
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
    	}*/
    }

	/**
	 * @param emuData
	 */
    private void readEmuData(EmuData emuData)
    {
    	Node root = xmlDoc.getElementsByTagName("ROOT").item(0);
	    setGridSize(emuData, root);
//	    setDecreasePerCell(emuData);
	    setRealWiFiStations(emuData, root);
//	    setCalculatedWifiStations(emuData);
	    setMobileDevices(emuData, root);
    }

	/**
	 * @param emuData
	 * @param root 
	 */
    private void setMobileDevices(EmuData emuData, Node root)
    {
    	List<Device> mobiles = emuData.getMobileDevices();

    	Node mobilen, signalStrength, signalStrengths, coord;
    	Node mobilesn = xmlFun.addElementToNode(xmlDoc, root, SaveFormat.MOBILES.getValue());
    	for (Device mobile : mobiles)
    	{
    		mobilen = xmlFun.addElementToNode(xmlDoc, mobilesn, SaveFormat.MOBILE.getValue());
        	xmlFun.addElementWithTextNodeToNode(xmlDoc, mobilen, SaveFormat.ID.getValue(), mobile.getId()+"");
        	coord = xmlFun.addElementToNode(xmlDoc, mobilen, SaveFormat.COORD.getValue());
        	xmlFun.addElementWithTextNodeToNode(xmlDoc, coord, SaveFormat.X.getValue(), mobile.getX()+"");
        	xmlFun.addElementWithTextNodeToNode(xmlDoc, coord, SaveFormat.Y.getValue(), mobile.getY()+"");
        	
        	Iterator<Entry<Integer, Double>> it = mobile.getSignalStrengthTable().entrySet().iterator();

    		signalStrengths = xmlFun.addElementToNode(xmlDoc, mobilen, SaveFormat.SIGNAL_STRENGTHS.getValue());
        	while (it.hasNext()) 
        	{
        		Map.Entry<Integer, Double> entry = it.next();
        		signalStrength = xmlFun.addElementToNode(xmlDoc, signalStrengths, SaveFormat.SIGNAL_STRENGTH.getValue());
            	xmlFun.addElementWithTextNodeToNode(xmlDoc, signalStrength, SaveFormat.ID.getValue(), 
            			entry.getKey()+"");
            	xmlFun.addElementWithTextNodeToNode(xmlDoc, signalStrength, SaveFormat.VALUE.getValue(), 
            			entry.getValue()+"");
        	}
    	}
    	
//    	xmlFormat.ad
//	    properties.setProperty(SaveFormat.MOBILE.getValue(), value);
    }

	/**
	 * @param emuData
	 * @param root 
	 */
    private void setRealWiFiStations(EmuData emuData, Node root)
    {
    	List<Device> wifis = emuData.getWiFiStationsReal();
    	Node wifiReal, coord;
    	Node wifisReal = xmlFun.addElementToNode(xmlDoc, root, SaveFormat.WIFIS.getValue());
    	
    	for (Device wifi : wifis)
    	{
        	wifiReal = xmlFun.addElementToNode(xmlDoc, wifisReal, SaveFormat.WIFI.getValue());
        	xmlFun.addElementWithTextNodeToNode(xmlDoc, wifiReal, SaveFormat.ID.getValue(), wifi.getId()+"");
        	xmlFun.addElementWithTextNodeToNode(xmlDoc, wifiReal, SaveFormat.SIGNAL_FREQUENCY.getValue(), 
        			wifi.getSignalFrequency()+"");        	
        	coord = xmlFun.addElementToNode(xmlDoc, wifiReal, SaveFormat.COORD.getValue());
        	xmlFun.addElementWithTextNodeToNode(xmlDoc, coord, SaveFormat.X.getValue(), wifi.getX()+"");
        	xmlFun.addElementWithTextNodeToNode(xmlDoc, coord, SaveFormat.Y.getValue(), wifi.getY()+"");
    	}
//	    properties.setProperty(SaveFormat.WIFI_REAL.getValue(), value);	    
    }

	/**
	 * @param emuData
	 * @param root 
	 */
    private void setGridSize(EmuData emuData, Node root)
    {
    	Node size = xmlFun.addElementToNode(xmlDoc, root, SaveFormat.SIZE.getValue());
    	xmlFun.addElementWithTextNodeToNode(xmlDoc, size, SaveFormat.WIDTH.getValue(), emuData.getGridColumnCount()+"");
    	xmlFun.addElementWithTextNodeToNode(xmlDoc, size, SaveFormat.HEIGHT.getValue(), emuData.getGridRowCount()+"");
    	
	   /* properties.setProperty(SaveFormat.SIZE.getValue(), 
	    		emuData.getGridColumnCount()+"x"+emuData.getGridRowCount());
	    */
    }
}
