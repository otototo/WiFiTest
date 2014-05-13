/**
 * 
 */
package helpers;

import gui.grid.GridPanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algorithm.MDS;
import algorithm.SignalCalc;
import data.Device;
import data.EmuData;

/**
 * @author paulina
 *
 */
public class MDSUpdate
{
	/** default value for non-existant signal*/
	private static final double DEFAULT_NE_SIGNAL = 5;
	private EmuData emuData;
	private MDS mds;
	private GridPanel gridPanel;

	/**
	 * @param gridPanel 
     * 
     */
    public MDSUpdate(EmuData emuData, GridPanel gridPanel)
    {
	    this.emuData = emuData;
	    this.gridPanel = gridPanel;
	    this.mds = new MDS();
    }
    
    /**
     * 
     */
    public void update()
    {
    	System.out.println("MDSUpdate.update()+");
    	List<Device> wifis = emuData.getWiFiStationsReal();
    	List<Device> mobiles = emuData.getMobileDevices();
    	
    	List<Device> all = new ArrayList<Device>();
    	all.addAll(mobiles);
    	all.addAll(wifis);    	
    	
    	double[][] input = new double[all.size()][all.size()];
    	for (int i = 0; i < input.length; i++)
    	{
    		for (int j = 0; j < input[i].length; j++)
    		{
    			input[i][j] = DEFAULT_NE_SIGNAL;
    		}
    	}
    	
    	Double temp;
    	
    	for (int i = 0; i < all.size(); i++)
	    {	    	
	    	for (int j = 0; j < all.size(); j++)
	    	{
	    		if (i == j)
	    		{
	    			input[i][j] = 0;
	    		}
	    		else
	    		{
	    			temp = all.get(j).getSignalStrength(all.get(i).getId());
		    		if (temp != null)
		    		{
		    			input[i][j] = signalToDistance(temp);
		    			input[j][i] = signalToDistance(temp);
		    		}
	    		}
	    	}
	    }    	
    	for (int i = 0; i < input.length; i++)
    	{
    		for (int j = 0; j < input[i].length; j++)
    		{
    			System.out.print(" "+input[i][j]);
    		}
    		System.out.println();
    	}
    	System.out.println();
    	
    	gridPanel.fillMDS(all, mds.scale(input));
    	System.out.println("MDSUpdate.update()-");
    }

	/**
	 * @param signal
	 * @return
	 */
    private double signalToDistance(Double signal)
    {
	    return SignalCalc.getDistanceBySignal(signal, EmuData.DEFAULT_MAX_SIGNAL_FREQUENCY);
    }
}
