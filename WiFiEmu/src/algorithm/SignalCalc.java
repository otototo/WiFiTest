/**
 * 
 */
package algorithm;

import java.util.List;
import java.util.List;

import data.Device;
import data.EmuData;

/**
 * @author paulina
 *
 */
public class SignalCalc
{
	/**
     * 
     */
    public SignalCalc()
    {
	    // TODO Auto-generated constructor stub
    }
    

    /**
     * 
     */
    public void calculate(List<Device> wifis, Device mobile)
    {
    	System.out.println("SignalCalc.calculate()+");
    	int signalStrength;
	    for (Device wifi : wifis)
	    {
    		signalStrength = calculateDecreasedSignal(mobile,wifi); 
    		mobile.addSignalStrength(wifi.getId(), signalStrength);
    		System.out.println("SignalCalc.calculate():"+wifi.getId()+"="+signalStrength);
    	}
	    System.out.println("SignalCalc.calculate()-");
    }
	  

    /**
     * 
     */
    public void calculate(Device wifi, List<Device> mobiles)
    {
    	System.out.println("SignalCalc.calculate()+");
    	int signalStrength;
    	for (Device mobile : mobiles)
    	{
    		signalStrength = calculateDecreasedSignal(mobile,wifi); 
    		mobile.addSignalStrength(wifi.getId(), signalStrength);
    		System.out.println("SignalCalc.calculate():"+wifi.getId()+"="+signalStrength);
    	}
    	System.out.println("SignalCalc.calculate()-");
    }
	  
    
    /**
     * 
     */
    public void calculate(List<Device> wifis, List<Device> mobiles)
    {
    	int signalStrength;
	    for (Device wifi : wifis)
	    {
	    	for (Device mobile : mobiles)
	    	{
	    		signalStrength = calculateDecreasedSignal(mobile,wifi); 
	    		mobile.addSignalStrength(wifi.getId(), signalStrength);
	    	}
	    }
    }

	/**
	 * @param wifi 
	 * @param mobile 
	 * @return signalStrength
	 */
    private int calculateDecreasedSignal(Device mobile, Device wifi)
    {
    	int signalStrength = 
    		EmuData.DEFAULT_MAX_SIGNAL_STRENGTH -
    		getDistance(mobile.getX(), mobile.getY(), wifi.getX(), wifi.getY()) * 
    		EmuData.DEFAULT_SIGNAL_DECREASE_PER_CELL;
    	return (signalStrength > 0)? signalStrength : 0;
    }

	/**
	 * @return
	 */
    private int getDistance(int x1, int y1, int x2, int y2)
    {
	    return (int) Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
    }
}
