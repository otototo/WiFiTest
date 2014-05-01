/**
 * 
 */
package data.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import data.EmuData;

/**
 * @author paulina
 *
 */
public class Loader
{
	Properties properties;
	
	/**
     * 
     */
    public Loader()
    {
	    properties = new Properties();
    }
    
    public void load(File fileToLoad, EmuData emuData)
    {
    	InputStream input = null;
    	
    	try
        {
	        input = new FileInputStream(fileToLoad);	        
	        properties.load(input);	        
	        fillEmuData(emuData);	        
	        emuData.setJustLoaded(true, true);
        } 
    	catch (IOException e)
        {
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
    		properties.clear();
    	}
    }

	/**
	 * @param emuData
	 */
    private void fillEmuData(EmuData emuData)
    {
    	setDecreasePerCell(emuData);
    	setStationStrength(emuData);
    	setSize(emuData);	    
    }

	/**
	 * @param emuData
	 */
    private void setSize(EmuData emuData)
    {	    
	    String temp;
	    temp = properties.getProperty(
				SaveFormat.SIZE.getValue()
			   );
	    String values[] = temp.split("x");
	    
	    emuData.setGridColumnCount(Integer.parseInt(values[0]), false);
	    emuData.setGridRowCount(Integer.parseInt(values[1]), false);
    }

	/**
	 * @param emuData
	 */
    private void setStationStrength(EmuData emuData)
    {
	    emuData.setStationStrength(Integer.parseInt(
	    		properties.getProperty(
	    				SaveFormat.SIGNAL_STRENGTH.getValue()
	    				)
	    		), false);
    }

	/**
	 * @param emuData
	 */
    private void setDecreasePerCell(EmuData emuData)
    { 
    	emuData.setDecreasePerCell(Integer.parseInt(
    		properties.getProperty(
    				SaveFormat.DECREASE_PER_CELL.getValue()
    				)
    		), false);
    }
}
