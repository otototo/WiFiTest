/**
 * 
 */
package data;

import gui.grid.GridViewType;
import helpers.ChangeIdentifier;
import helpers.EmuDataNotifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author paulina
 *
 */
public class EmuData
{
	public static final int DEFAULT_COLUMN_COUNT = 25;
	public static final int DEFAULT_ROW_COUNT = 25;
	public static final int DEFAULT_MAX_SIGNAL_STRENGTH = 0;
	//TODO perrasyt i funkcija, kuri skaiciuoja stipri pagal metru skaiciu
	// 1 langelis = 1 metras
	public static final int DEFAULT_SIGNAL_DECREASE_PER_CELL = 1;
	public static final int DEFAULT_MAX_GRID_COUNT = 100;
	public static final int DEFAULT_MAX_SIGNAL_FREQUENCY = 2400;
	
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
	
	private GridViewType realView = GridViewType.Reality; //if false calculated position are shown 
	
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

    	setGridColumnCount(DEFAULT_COLUMN_COUNT, false);
    	setGridRowCount(DEFAULT_ROW_COUNT, false);
    	setDecreasePerCell(DEFAULT_SIGNAL_DECREASE_PER_CELL, false);
    	setStationStrength(DEFAULT_MAX_SIGNAL_STRENGTH, false);
    }

    public void reinit()
    {
    	clear();

    	setGridColumnCount(DEFAULT_COLUMN_COUNT, false);
    	setGridRowCount(DEFAULT_ROW_COUNT, false);
    	setDecreasePerCell(DEFAULT_SIGNAL_DECREASE_PER_CELL, false);
    	setStationStrength(DEFAULT_MAX_SIGNAL_STRENGTH, false);    	
    	resetCount();
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
    public void setGridRowCount(int gridRowCount, boolean notify)
    {
		this.gridRowCount = gridRowCount;
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.SIZE);
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
    public void setGridColumnCount(int gridColumnCount, boolean notify)
    {
		this.gridColumnCount = gridColumnCount;
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.SIZE);
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
    public void setDecreasePerCell(int decreasePerCell, boolean notify)
    {
	    this.decreasePerCell = decreasePerCell;
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.DECREASE);
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
    public void setStationStrength(int stationStrength, boolean notify)
    {
	    this.stationStrength = stationStrength;
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.STRENGTH);
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
    public void setMobileDevice(Device device, int i, boolean notify)
    {
	    mobileDevices.set(i, device);
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.MOBILE);
    }
    /**
	 * @param mobileDevices the devices to add
	 */
    public void addMobileDevice(Device device, boolean notify)
    {
	    mobileDevices.add(device);
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.MOBILE);
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
    public void removeMobile(int index, boolean notify)
    {
	    mobileDevices.remove(index);
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.MOBILE);
    }
    
    public void removeMobile(Device mobile, boolean notify)
    {
    	getMobileDevices().remove(mobile);
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.MOBILE);
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
    public void setWiFiStationCalculated(Device wiFiStationCalculated, int i, boolean notify)
    {
	    this.wiFiStationsCalculated.set(i, wiFiStationCalculated);
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.WIFIC);
	}
    /**
	 * @param wiFiStationCalculated the wiFiStationCalculated to add
	 */
    public void addWiFiStationCalculated(Device wiFiStationCalculated, boolean notify)
    {
	    this.wiFiStationsCalculated.add(wiFiStationCalculated);
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.WIFIC);
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
    public void setWiFiStationReal(Device wiFiStationReal, int i, boolean notify)
    {
	    this.wiFiStationsReal.set(i, wiFiStationReal);
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.WIFIR);
	}	
	/**
	 * @param wiFiStationReal the wiFiStationReal to add
	 */
    public void addWiFiStationReal(Device wiFiStationReal, boolean notify)
    {
	    this.wiFiStationsReal.add(wiFiStationReal);
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.WIFIR);
	}

    public int getWiFiStationRealIndex(Device station)
    {
    	int index = -1;
    	Device device;
    	for (int i = 0; i < wiFiStationsReal.size(); i++)
    	{
    		device = wiFiStationsReal.get(i);
    		if (device.equals(station) && 
  				(DeviceType.WIFI_REAL == station.getDeviceType()))
			{
    			index = i;
    			break;
			}
    	}
    	return index;
    }

    public void removeWiFiStation(Device station, boolean notify)
    {
    	getWiFiStationsReal().remove(station);
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.MOBILE);
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
    public void removeWiFiStation(int index, boolean notify)
    {
	    wiFiStationsCalculated.remove(index);
	    wiFiStationsReal.remove(index);
    	if (notify)
    		notifier.notifyListeners(true, ChangeIdentifier.MOBILE);
    }	
	
	public void clear()
	{
		wiFiStationsCalculated.clear();
		wiFiStationsReal.clear();
		mobileDevices.clear();
		notifier.notifyListeners(true, ChangeIdentifier.ALL);
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
	    	notifier.notifyListeners(realDataChange, ChangeIdentifier.ALL);
    }


	/**
	 * @param tempEmuData
	 */
    public void copySingularData(EmuData tempEmuData)
    {
	    this.setDecreasePerCell(tempEmuData.getDecreasePerCell(), false);
	    this.setGridColumnCount(tempEmuData.getGridColumnCount(), false);
	    this.setGridRowCount(tempEmuData.getGridRowCount(), false);
	    this.setStationStrength(tempEmuData.getStationStrength(), false);
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


	/**
	 * @return the realView
	 */
    public boolean isView(GridViewType view)
    {
	    return realView == view;
    }


	/**
	 * @param realView the realView to set
	 */
    public void setRealView(GridViewType realView)
    {
    	System.out.println("realView="+realView);
	    this.realView = realView;
	    if (notifier != null)
	    	notifier.notifyListeners(false, ChangeIdentifier.VIEW);
    }


	/**
	 * @param prevSelectedIndex
	 * @param b
	 */
    public void setWiFiSRIsSelected(int index, boolean b)
    {
		getWiFiStationReal(index).setSelected(false);	
	    if (notifier != null)
	    	notifier.notifyListeners(true, ChangeIdentifier.SELECTED);    
    }


    public boolean getWiFiSRIsSelected(int index)
    {
		return getWiFiStationReal(index).isSelected();	 
    }
    
    public void resetCount()
    {
    	Device.resetCount();	
    }
}
