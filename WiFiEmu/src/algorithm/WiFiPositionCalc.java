/**
 * 
 */
package algorithm;

import java.util.List;

import helpers.EmuDataListener;
import data.Device;
import data.EmuData;

/**
 * @author paulina
 *
 */
public class WiFiPositionCalc implements EmuDataListener
{
	private EmuData emuData;
	/**
     * 
     */
    public WiFiPositionCalc(EmuData emuData)
    {
	    
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
    public void onEmuDataChange(boolean realDataChange)
    {
	    if (realDataChange)
	    {
	    	calculate(getEmuData().getMobileDevices());
	    }
	    
    }
	/**
	 * @param list 
	 * 
	 */
    private void calculate(List<Device> list)
    {
	    // TODO Auto-generated method stub
	    
    }
}
