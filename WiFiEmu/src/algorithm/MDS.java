/**
 * 
 */
package algorithm;

import java.util.List;

import mdsj.MDSJ;
import data.Device;
import data.EmuData;

/**
 * @author paulina
 *
 */
public class MDS
{
	/**
     * 
     */
    private double[][] scale(double[][] input)
    {
    	int n=input[0].length;    // number of data objects
		double[][] output=MDSJ.classicalScaling(input); // apply MDS
		for(int i=0; i<n; i++) {  // output all coordinates
		    System.out.println(output[0][i]+" "+output[1][i]);
		}
		return output;
    }
    
    public double[][] scale(EmuData emuData)
    {
    	List<Device> wifis = emuData.getWiFiStationsCalculated();
    	List<Device> mobiles = emuData.getMobileDevices();
    	
    	double[][] input = new double[wifis.size()][mobiles.size()]; 
    	
    	for (int i = 0; i < wifis.size(); i++)
	    {	    	
	    	for (int j = 0; j < mobiles.size(); j++)
	    	{
	    		input[i][j] = mobiles.get(j).getSignalStrength(wifis.get(i).getId());
	    	}
	    }
    	return scale(input);
    }
}
