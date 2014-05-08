/**
 * 
 */
package helpers;

import java.util.ArrayList;
import java.util.List;

import algorithm.SignalCalc;
import algorithm.WiFiPositionCalc;
import data.Device;
import data.DeviceType;
import data.EmuData;

/**
 * @author paulina
 *
 */
public class WiFiCalcUpdate
{
	private SignalCalc calculateSignal;
	private WiFiPositionCalc calculateWiFiPosition;
	private EmuData emuData;
	
	/**
     * 
     */
    public WiFiCalcUpdate(EmuData emuData)
    {
	    this.setCalculateSignal(new SignalCalc());
	    this.setCalculateWiFiPosition(new WiFiPositionCalc());	
	    this.setEmuData(emuData);
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
	 * 
	 */
    public void calculateWiFiPostitions()
    {
	    System.out.println("WiFiCalcUpdate.calculateWiFiPostitions()+");
	    
	    List<Device> wifis = getEmuData().getWiFiStationsReal();
	    List<Device> mobiles = new ArrayList<Device>(); 
	    List<Device> allMobiles = emuData.getMobileDevices(); 
	   
	    int iterationCount = 0;
	    double[] accumulate = {0,0};
	    
	    
	    System.out.println("WiFiCalcUpdate.calculateWiFiPostitions()-");
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


	/**
	 * @return the calculateWiFiPosition
	 */
    public WiFiPositionCalc getCalculateWiFiPosition()
    {
	    return calculateWiFiPosition;
    }


	/**
	 * @param calculateWiFiPosition the calculateWiFiPosition to set
	 */
    public void setCalculateWiFiPosition(WiFiPositionCalc calculateWiFiPosition)
    {
	    this.calculateWiFiPosition = calculateWiFiPosition;
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

}
