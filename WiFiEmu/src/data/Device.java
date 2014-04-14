/**
 * 
 */
package data;

import java.util.Hashtable;

/**
 * @author Paul Mora
 *
 */
public class Device
{
	private DeviceType deviceType;
	private int x;
	private int y;
	private int id;
	private Hashtable<Integer, Integer> signalStrengthTable;

	
	private static int count = 0;
	/**
	 * @param y 
	 * @param x 
     * 
     */
    public Device(DeviceType deviceType, int x, int y)
    {
	    this.setDeviceType(deviceType);
	    signalStrengthTable = new Hashtable<>();
	    setX(x);
	    setY(y);
	    setId(++count);
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
    public int getX()
    {
	    return x;
    }

	/**
	 * @param x the x to set
	 */
    public void setX(int x)
    {
	    this.x = x;
    }

	/**
	 * @return the y
	 */
    public int getY()
    {
	    return y;
    }

	/**
	 * @param y the y to set
	 */
    public void setY(int y)
    {
	    this.y = y;
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
        // TODO Auto-generated method stub
        return getX()+"x"+getY()+"x"+getId();
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
    public int getSignalStrength(int wifiId)
    {
	    return signalStrengthTable.get(wifiId);
    }

	/**
	 * @param signalStrength the signalStrength to set
	 */
    public void addSignalStrength(int wifiId, int signalStrength)
    {
	    this.signalStrengthTable.put(wifiId, signalStrength);
    }

	/**
	 * @return the signalStrengthTable
	 */
    public Hashtable<Integer, Integer> getSignalStrengthTable()
    {
	    return signalStrengthTable;
    }

	/**
	 * @param signalStrengthTable the signalStrengthTable to set
	 */
    public void setSignalStrengthTable(Hashtable<Integer, Integer> signalStrengthTable)
    {
	    this.signalStrengthTable = signalStrengthTable;
    }
}
