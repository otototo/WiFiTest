/**
 * 
 */
package helpers;

import data.Device;
import data.DeviceType;
import data.EmuData;
import algorithm.SignalCalc;

/**
 * @author paulina
 *
 */
public class WiFiSignalUpdate
{
	private SignalCalc calculateSignal;
	private EmuData emuData;
	
	/**
     * 
     */
    public WiFiSignalUpdate(EmuData emuData)
    {
	    this.calculateSignal = new SignalCalc();
	    this.emuData = emuData;
    }
    

	
	/**
	 * @param device
	 */
    public void update(Device device)
    {
    	System.out.println("WiFiCalcUpdate.update()+");
    	System.out.println("device:"+device.getDeviceType());
	    if (device.getDeviceType() == DeviceType.MOBILE)
	    {
	    	updateMobiles(device);
	    }
	    else if (device.getDeviceType() == DeviceType.WIFI_STATION)
	    {
	    	updateStations(device);
	    }
//	    recalculateWiFiPostition();
	    System.out.println("WiFiCalcUpdate.update()-");
    }

	/**
	 * @param device
	 */
    private void updateStations(Device wifi)
    {
    	System.out.println("WiFiCalcUpdate.updateStations()+");
    	System.out.println("wifi="+wifi);
    	if ((wifi != null) && (emuData.getMobileDevicesCount() > 0))
    		calculateSignal.calculate(wifi, emuData.getMobileDevices());
    	System.out.println("WiFiCalcUpdate.updateStations()-");
    }


	/**
	 * @param device 
	 * 
	 */
    private void updateMobiles(Device mobile)
    {
    	System.out.println("WiFiCalcUpdate.updateMobiles()+");
    	System.out.println("mobile="+mobile);
    	if ((mobile != null) && (emuData.getWiFiStationsRealCount() > 0))
    		calculateSignal.calculate(emuData.getWiFiStationsReal(), mobile);
    	System.out.println("WiFiCalcUpdate.updateMobiles()-");
    }


	/**
	 * @return the calculateSignal
	 */
    public SignalCalc getCalculateSignal()
    {
	    return calculateSignal;
    }


	/**
	 * @param calculateSignal the calculateSignal to set
	 */
    public void setCalculateSignal(SignalCalc calculateSignal)
    {
	    this.calculateSignal = calculateSignal;
    }


}
