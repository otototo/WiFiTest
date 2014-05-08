import data.Device;
import data.DeviceType;
import data.EmuData;
import algorithm.WiFiPositionCalc;
import gui.MainFrame;
import helpers.WiFiCalcUpdate;

/**
 * 
 */

/**
 * @author Paul Mora
 *
 */
public class Main
{
	public static void main(String[] args)
    {
	    new MainFrame();
//		testMatrixSystemSolution();
//		testWifiPositionCalc();
    }

	/**
	 * 
	 */
    private static void testWifiPositionCalc()
    {
    	EmuData emuData = new EmuData(null);
    	
    	Device mobile1 = new Device(DeviceType.MOBILE, 5, -3);
    	emuData.addMobileDevice(mobile1, false);
    	
    	Device mobile2 = new Device(DeviceType.MOBILE, 2, 5);
    	emuData.addMobileDevice(mobile2, false);
    	
    	Device mobile3 = new Device(DeviceType.MOBILE, 1, 4);
    	emuData.addMobileDevice(mobile3, false);
    	
    	Device station = new Device(DeviceType.WIFI_STATION, 0, 0);
    	emuData.addWiFiStationReal(station, false);
    	
    	mobile1.addSignalStrength(station.getId(), 1);
    	mobile2.addSignalStrength(station.getId(), 19);
    	mobile3.addSignalStrength(station.getId(), 23);
    	
	    WiFiCalcUpdate calc = new WiFiCalcUpdate(emuData);
	    calc.calculateWiFiPostitions();
	    
	    for (Device calculated : emuData.getWiFiStationsCalculated())
	    {
	    	System.out.println("device:"+calculated.getId()+
	    			" ["+calculated.getX()+","+calculated.getY()+"]");
	    }
    }

	
}
