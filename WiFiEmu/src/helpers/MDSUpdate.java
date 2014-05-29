/**
 * 
 */
package helpers;

import gui.grid.GridPanel;

import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import algorithm.Distance;
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
	private static final double DEFAULT_NE_SIGNAL = 4;
	private static final double DEFAULT_LITTLE_STRESS = 0;
	private static final double DEFAULT_BIG_STRESS = 4;
	
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
    	double[][] weigths = new double[all.size()][all.size()];
    	
    	for (int i = 0; i < input.length; i++)
    	{
    		for (int j = 0; j < input[i].length; j++)
    		{
    			input[i][j] = DEFAULT_NE_SIGNAL;
    			weigths[i][j] = DEFAULT_LITTLE_STRESS;
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
	    			weigths[i][j] = DEFAULT_BIG_STRESS;
	    		}
	    		else
	    		{
	    			temp = all.get(j).getSignalStrength(all.get(i).getId());
		    		if (temp != null)
		    		{
		    			input[i][j] = signalToDistance(temp, all.get(i).getSignalFrequency());
		    			input[j][i] = signalToDistance(temp, all.get(i).getSignalFrequency());
		    			weigths[i][j] = DEFAULT_BIG_STRESS;
		    			weigths[j][i] = DEFAULT_BIG_STRESS;
		    		}
	    		}
	    	}
	    }    	
    	for (int i = 0; i < input.length; i++)
    	{
    		for (int j = 0; j < input[i].length; j++)
    		{
    			System.out.printf("%.2f\t", input[i][j]);
    		}
    		System.out.println();
    	}
    	System.out.println();
    	mds.scale(input, weigths);
    	gridPanel.fillMDS(all, mds.getStressSol(), mds.getClassicalSol());
    	System.out.println("------------compare classical--------------");
   /* 	double[][] clas = {
    			{0.72,-1.65},{1.65,0.72},{-1.65,-0.72},{-0.72,1.65},{0.72,-1.65},{-1.65,-0.72},{-0.72,1.65},{1.65,0.72}
    			};
    	compare(clas, all);*/
    	compare(mds.getClassicalSol(), all);
    	System.out.println("------------compare stress--------------");
    /*	double[][] stres = {
    			{0.32,-3.62},{3.07,1.62},{-2.96,-2},{-0.47,3.38},{0.41,-0.79},{-0.78,-0.2},{-0.39,0.55},{0.77,-0.02}
    			};
    	compare(stres, all);*/
    	compare(mds.getStressSol(), all);
    	System.out.println("-------------------------------------");
    
    	System.out.println("MDSUpdate.update()-");
    }

	/**
	 * @param all 
	 * @param ds 
	 * 
	 */
    private void compare(double[][] ds, List<Device> all)
    {
    	double distReal, distMds;
		double[][] newmat = new double[all.size()][all.size()];
	    double average = 0;
    	
	    for (int i = 0; i < ds.length; i++)
	    {
	    	for (int j = 0; j < ds[0].length; j++)
	    	{
	    		distReal = Distance.getDistance(all.get(i).getX(), all.get(i).getY(), 
	    				all.get(j).getX(), all.get(j).getY());
//			    System.out.println("distReal="+distReal);
	    		distMds = Distance.getDistance(ds[i][0], ds[i][1], 
	    				ds[j][0], ds[j][1]);
//			    System.out.println("distMds="+distMds);
			    if (distReal != 0)
			    {
    				newmat[i][j] = (Math.abs(distReal - distMds) / distReal) * 100;
//    			    System.out.println("diff="+newmat[i][j] );
//    				average += newmat[i][j] / (all.size() * all.size());
			    }
	    	}
	    }
//	    System.out.println("average="+average);
	    for (int i = 0; i < newmat.length; i++)
	    {
	    	for (int j = 0; j < newmat[0].length; j++)
	    	{
	    		System.out.printf("%.2f\t", newmat[i][j]);
	    	}
	    	System.out.println();
	    }
    }

	/**
	 * @param signal
	 * @return
	 */
    private double signalToDistance(Double signal, int frequency)
    {
//	    return SignalCalc.getDistanceBySignal(signal, EmuData.DEFAULT_MAX_SIGNAL_FREQUENCY);
	    return SignalCalc.getDistanceBySignal(signal, frequency);
    }
}
