/**
 * 
 */
package data;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author Paul Mora
 *
 */
public class Device
{
	private DeviceType deviceType;
	private ArrayList<Double> x;
	private ArrayList<Double> y;
	private int id;
	private int signalFrequency;
	private Hashtable<Integer, Double> signalStrengthTable;

	private boolean isSelected = false;
	private boolean onLeft = true;
	
	
	private static int count = 0;
	/**
	 * @param y 
	 * @param x 
     * 
     */

    public Device(DeviceType deviceType, double x, double y)
    {
	   this(deviceType, EmuData.DEFAULT_MAX_SIGNAL_FREQUENCY, x, y);
    }
    
    public Device(DeviceType deviceType, double x, double y, int id)
    {
	    this(deviceType, EmuData.DEFAULT_MAX_SIGNAL_FREQUENCY, x, y, id);
    }
    
    public Device(DeviceType deviceType, int frequency, double x, double y)
    {
	    this.setDeviceType(deviceType);
	    signalStrengthTable = new Hashtable<>();
	    this.x = new ArrayList<Double>();
	    this.y = new ArrayList<Double>();
	    
	    addX(x);
	    addY(y);
	    setId(++count);
	    setSignalFrequency(frequency);
    }
    
    public Device(DeviceType deviceType, int frequency, double x, double y, int id)
    {
	    this.setDeviceType(deviceType);
	    signalStrengthTable = new Hashtable<>();
	    
	    this.x = new ArrayList<Double>();
	    this.y = new ArrayList<Double>();
	    addX(x);
	    addY(y);
	    setId(id);
	    count++;
	    setSignalFrequency(frequency);
    }

	/**
	 * @return the deviceType
	 */
    public DeviceType getDeviceType()
    {
	    return deviceType;
    }

	/**
	 * @param deviceType the deviceType to set
	 */
    public void setDeviceType(DeviceType deviceType)
    {
	    this.deviceType = deviceType;
    }
	/**
	 * @return the x
	 */
    public double getX(int i)
    {
	    return x.get(i);
    }

	/**
	 * @return the first x
	 */
    public double getX()
    {
	    return x.get(0);
    }

	/**
	 * @param x the x to set
	 */
    public void setX(double x)
    {
    	this.x.clear();
	    addX(x);
    }

	/**
	 * @param x the x to set
	 */
    public void setX(int i, double x)
    {
	    this.x.set(i, x);
    }

	/**
	 * @param x2
	 */
    public void addX(double x)
    {
	    this.x.add(x);
    }

	/**
	 * @return the y
	 */
    public double getY()
    {
	    return this.y.get(0);
    }

	/**
	 * @return the y
	 */
    public double getY(int i)
    {
	    return this.y.get(i);
    }
    
    public void clearX()
    {
    	this.x.clear();
    }
    
    public void clearY()
    {
    	this.y.clear();
    }

	/**
	 * @param y the y to set
	 */
    public void setY(double y)
    {
    	this.y.clear();
	    addY(y);
    }


	/**
	 * @param y the y to set
	 */
    public void setY(int i, double y)
    {
	    this.y.set(i, y);
    }
    /**
	 * @param y2
	 */
    public void addY(double y)
    {
	    this.y.add(y);
    }

	/** equal coordinates regardles of device type*/
    public boolean equals(Device device)
    {
        if ((getX() == device.getX()) && (getY() == device.getY()))
        	return true;
        else
        	return false;
    }

	/**
	 * @return the id
	 */
    public int getId()
    {
	    return id;
    }

	/**
	 * @param id the id to set
	 */
    public void setId(int id)
    {
	    this.id = id;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
    	String string = getX()+"x"+getY()+"x"+getId()+"x"+getSignalFrequency();
    	
        return string;
    }
/*
	*//**
	 * @return the deviceCount
	 *//*
    public static int getDeviceCount()
    {
	    return deviceCount;
    }

	*//**
	 * @param deviceCount the deviceCount to set
	 *//*
    public static void setDeviceCount(int deviceCount)
    {
	    Device.deviceCount = deviceCount;
    }
    
    public static void incDeviceCount()
    {
    	deviceCount++;
    }
    
    public static void decDeviceCount()
    {
    	deviceCount--;
    }*/

	/**
	 * @return the signalStrength
	 */
    public Double getSignalStrength(int wifiId)
    {
	    return signalStrengthTable.get(wifiId);
    }

	/**
	 * @param signalStrength the signalStrength to set
	 */
    public void addSignalStrength(int wifiId, double signalStrength)
    {
	    this.signalStrengthTable.put(wifiId, signalStrength);
    }

	/**
	 * @return the signalStrengthTable
	 */
    public Hashtable<Integer, Double> getSignalStrengthTable()
    {
	    return signalStrengthTable;
    }

	/**
	 * @param signalStrengthTable the signalStrengthTable to set
	 */
    public void setSignalStrengthTable(Hashtable<Integer, Double> signalStrengthTable)
    {
	    this.signalStrengthTable = signalStrengthTable;
    }

	/**
	 * @return the isSelected
	 */
    public boolean isSelected()
    {
	    return isSelected;
    }

	/**
	 * @param isSelected the isSelected to set
	 */
    public void setSelected(boolean isSelected)
    {
	    this.isSelected = isSelected;
    }

	/**
	 * @return the onLeft
	 */
    public boolean isOnLeft()
    {
	    return onLeft;
    }

	/**
	 * @param onLeft the onLeft to set
	 */
    public void setOnLeft(boolean onLeft)
    {
	    this.onLeft = onLeft;
    }
    
    public static void resetCount()
    {
    	count = 0;
    }

	/**
	 * @return the signalFrequency
	 */
    public int getSignalFrequency()
    {
	    return signalFrequency;
    }

	/**
	 * @param signalFrequency the signalFrequency to set
	 */
    public void setSignalFrequency(int signalFrequency)
    {
	    this.signalFrequency = signalFrequency;
    }
}
