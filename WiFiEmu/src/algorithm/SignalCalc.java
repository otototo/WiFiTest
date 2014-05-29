/**
 * 
 */
package algorithm;

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
    public void calculate(List<Device> wifis, Device mobile)
    {
    	System.out.println("SignalCalc.calculate()+");
    	double signalStrength;
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
    	double signalStrength;
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
    	double signalStrength;
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
    private double calculateDecreasedSignal(Device mobile, Device wifi)
    {
    	double signalStrength = getSignalByDistance(wifi.getSignalFrequency(), 
    			Distance.getDistance(mobile.getX(), mobile.getY(), wifi.getX(), wifi.getY()));
    	return signalStrength;
    }
    
    public static double getSignalByDistance(double freqInMHz, double distanceInMeters) 
    {
        double fpsl = 20 * Math.log10(distanceInMeters) + 20 * Math.log10(freqInMHz) - 27.55;
        return -fpsl;
    }
    
    public static double getDistanceBySignal(double signalLevelInDb, double freqInMHz) 
    {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) - signalLevelInDb) / 20.0;
        return Math.pow(10.0, exp);
    }
}
