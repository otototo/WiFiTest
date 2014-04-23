/**
 * 
 */
package data;

import helpers.EmuDataNotifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author paulina
 *
 */
public class EmuData
{
	public static final int DEFAULT_COLUMN_COUNT = 20;
	public static final int DEFAULT_ROW_COUNT = 20;
	public static final int DEFAULT_MAX_SIGNAL_STRENGTH = 50;
	public static final int DEFAULT_SIGNAL_DECREASE_PER_CELL = 5;
	public static final int DEFAULT_MAX_GRID_COUNT = 30;
	
	private int gridRowCount;
	private int gridColumnCount;
	private int decreasePerCell;
	private int stationStrength;
	
	private List<Device> mobileDevices;
	private List<Device> wiFiStationsReal;
	private List<Device> wiFiStationsCalculated;
	
	private EmuDataNotifier notifier;
	
	private Device selectedDevice;
	private boolean justLoaded = false;
	
	/**
     * 
     */
    public EmuData(EmuDataNotifier notifier)
    {
	    init();
	    setNotifier(notifier);
    }
	
	
	/**
	 * 
	 */
    private void init()
    {
    	mobileDevices = new ArrayList<Device>();
	    wiFiStationsCalculated = new ArrayList<Device>();
	    wiFiStationsReal = new ArrayList<Device>();	    

    	setGridColumnCount(DEFAULT_COLUMN_COUNT);
    	setGridRowCount(DEFAULT_ROW_COUNT);
    	setDecreasePerCell(DEFAULT_SIGNAL_DECREASE_PER_CELL);
    	setStationStrength(DEFAULT_MAX_SIGNAL_STRENGTH);
    }

    public void reinit()
    {
    	clear();

    	setGridColumnCount(DEFAULT_COLUMN_COUNT);
    	setGridRowCount(DEFAULT_ROW_COUNT);
    	setDecreasePerCell(DEFAULT_SIGNAL_DECREASE_PER_CELL);
    	setStationStrength(DEFAULT_MAX_SIGNAL_STRENGTH);    	
    }
    
	/**
	 * @return the gridRowCount
	 */
    public int getGridRowCount()
    {
	    return gridRowCount;
    }
	/**
	 * @param gridRowCount the gridRowCount to set
	 */
    public void setGridRowCount(int gridRowCount)
    {
		this.gridRowCount = gridRowCount;
    }
	/**
	 * @return the gridColumnCount
	 */
    public int getGridColumnCount()
    {
	    return gridColumnCount;
    }
	/**
	 * @param gridColumnCount the gridColumnCount to set
	 */
    public void setGridColumnCount(int gridColumnCount)
    {
		this.gridColumnCount = gridColumnCount;
    }
	/**
	 * @return the decreasePerCell
	 */
    public int getDecreasePerCell()
    {
	    return decreasePerCell;
    }
	/**
	 * @param decreasePerCell the decreasePerCell to set
	 */
    public void setDecreasePerCell(int decreasePerCell)
    {
	    this.decreasePerCell = decreasePerCell;
    }
	/**
	 * @return the stationStrength
	 */
    public int getStationStrength()
    {
	    return stationStrength;
    }
	/**
	 * @param stationStrength the stationStrength to set
	 */
    public void setStationStrength(int stationStrength)
    {
	    this.stationStrength = stationStrength;
    }
	/**
	 * @return the devices
	 */
    public List<Device> getMobileDevices()
    {
	    return mobileDevices;
    }
	/**
	 * @return the devices
	 */
    public Device getMobileDevice(int i)
    {
	    return mobileDevices.get(i);
    }
	/**
	 * @param mobileDevices the devices to set
	 */
    public void setMobileDevice(Device device, int i)
    {
	    mobileDevices.set(i, device);
    }
    /**
	 * @param mobileDevices the devices to add
	 */
    public void addMobileDevice(Device device)
    {
	    mobileDevices.add(device);
    } 



	/**
	 * @param device
	 * @return
	 */
    public int getMobileDeviceIndex(Device mobileDevice)
    {
    	int index = -1;
    	Device device;
    	for (int i = 0; i < mobileDevices.size(); i++)
    	{
    		device = mobileDevices.get(i);
    		if (device.equals(mobileDevice) && 
  				(DeviceType.MOBILE == mobileDevice.getDeviceType()))
			{
    			index = i;
    			break;
			}
    	}
    	return index;
    }


	/**
	 * @param index
	 */
    public void removeMobile(int index)
    {
	    mobileDevices.remove(index);
    }

	/**
	 * @return the wiFiStationsCalculated
	 */
    public List<Device> getWiFiStationsCalculated()
    {
	    return wiFiStationsCalculated;
    }
    
	/**
	 * @return the wiFiStationCalculated
	 */
    public Device getWiFiStationCalculated(int i)
    {
	    return wiFiStationsCalculated.get(i);
    }
	/**
	 * @param wiFiStationCalculated the wiFiStationCalculated to set
	 */
    public void setWiFiStationCalculated(Device wiFiStationCalculated, int i)
    {
	    this.wiFiStationsCalculated.set(i, wiFiStationCalculated);
	}
    /**
	 * @param wiFiStationCalculated the wiFiStationCalculated to add
	 */
    public void addWiFiStationCalculated(Device wiFiStationCalculated)
    {
	    this.wiFiStationsCalculated.add(wiFiStationCalculated);
	}
	/**
	 * @return the wiFiStationsReal
	 */
    public List<Device> getWiFiStationsReal()
    {
	    return wiFiStationsReal;
    }
	/**
	 * @return the wiFiStationReal
	 */
    public Device getWiFiStationReal(int i)
    {
	    return wiFiStationsReal.get(i);
    }
	/**
	 * @param wiFiStationReal the wiFiStationReal to set
	 */
    public void setWiFiStationReal(Device wiFiStationReal, int i)
    {
	    this.wiFiStationsReal.set(i, wiFiStationReal);
	}	
	/**
	 * @param wiFiStationReal the wiFiStationReal to add
	 */
    public void addWiFiStationReal(Device wiFiStationReal)
    {
	    this.wiFiStationsReal.add(wiFiStationReal);
	}

    public int getWiFiStationRealIndex(Device station)
    {
    	int index = -1;
    	Device device;
    	for (int i = 0; i < wiFiStationsReal.size(); i++)
    	{
    		device = wiFiStationsReal.get(i);
    		if (device.equals(station) && 
  				(DeviceType.WIFI_STATION == station.getDeviceType()))
			{
    			index = i;
    			break;
			}
    	}
    	return index;
    }

    public int RemoveWiFiStation(Device station)
    {
    	int index = -1;
    	Device device;
    	for (int i = 0; i < wiFiStationsReal.size(); i++)
    	{
    		device = wiFiStationsReal.get(i);
    		if (device.equals(station) && 
  				(DeviceType.WIFI_STATION == station.getDeviceType()))
			{
    			index = i;
    			break;
			}
    	}
    	return index;
    }


	/**
	 * @return the notifier
	 */
    public EmuDataNotifier getNotifier()
    {
	    return notifier;
    }


	/**
	 * @param notifier the notifier to set
	 */
    public void setNotifier(EmuDataNotifier notifier)
    {
	    this.notifier = notifier;
	}

	/**
	 * 
	 */
    public void removeWiFiStation(int index)
    {
	    wiFiStationsCalculated.remove(index);
	    wiFiStationsReal.remove(index);
    }	
	
	public void clear()
	{
		wiFiStationsCalculated.clear();
		wiFiStationsReal.clear();
		mobileDevices.clear();
	}


	/**
	 * @return the justLoaded
	 */
    public boolean isJustLoaded()
    {
	    return justLoaded;
    }


	/**
	 * @param justLoaded the justLoaded to set
	 */
    public void setJustLoaded(boolean justLoaded, boolean realDataChange)
    {
	    this.justLoaded = justLoaded;
	    if (notifier != null)
	    	notifier.notifyListeners(realDataChange);
    }


	/**
	 * @param tempEmuData
	 */
    public void copySingularData(EmuData tempEmuData)
    {
	    this.setDecreasePerCell(tempEmuData.getDecreasePerCell());
	    this.setGridColumnCount(tempEmuData.getGridColumnCount());
	    this.setGridRowCount(tempEmuData.getGridRowCount());
	    this.setStationStrength(tempEmuData.getStationStrength());
    }


	/**
	 * @return
	 */
    public String[] getMobileDevicesNames()
    {
	    return getIdList(getMobileDevices(), "Mobile ");
    }


	/**
	 * @return
	 */
    public String[] getWiFiStationsRealNames()
    {
	    return getIdList(getWiFiStationsReal(), "WiFi ");
    }



	/**
	 * @param deviceList
	 * @return
	 */
    private String[] getIdList(List<Device> deviceList, String prefix)
    {
    	String[] names = new String[deviceList.size()];
	    for (int i = 0; i < deviceList.size(); i++)
	    {
	    	names[i] = prefix + deviceList.get(i).getId();
	    }
	    return names;
    }


	/**
	 * @return
	 */
    public int getMobileDevicesCount()
    {
	    return getMobileDevices().size();
    }


	/**
	 * @return
	 */
    public int getWiFiStationsRealCount()
    {
	    return getWiFiStationsReal().size();
    }

}
