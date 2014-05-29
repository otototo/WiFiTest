/**
 * 
 */
package algorithm;

import java.util.ArrayList;
import java.util.Collection;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import mdsj.MDSJ;
import mdsj.StressMinimization;

/**
 * @author paulina
 *
 */
public class MDS
{
	private double[][] outputClassical;
	private double[][] outputStress;
	/**
     * 
     */
    public void scale(double[][] input, double[][] weigth)
    {
    	int n=input[0].length;
		
    	outputStress = MDSJ.classicalScaling(input, 2);
    	outputClassical = new double[outputStress.length][outputStress[0].length];
    	for (int i = 0; i < outputStress.length; i++)
    		for (int j = 0; j < outputStress[0].length; j++)
    		{
    			if (Double.isNaN(outputStress[i][j]))
    			{
    				outputStress[i][j] = 0;
    			}
    			outputClassical[i][j] = outputStress[i][j];     
    		}
    	
		StressMinimization sm = new StressMinimization(input, outputStress, weigth);
		sm.iterate(0, 0, 3);
				
		System.out.println("classical");
		for(int i=0; i<n; i++) {  // output all coordinates
		    System.out.printf("%.2f\t%.2f\n", outputClassical[0][i], outputClassical[1][i]);
		}
		System.out.println("\nstress");
		for(int i=0; i<n; i++) {  // output all coordinates
		    System.out.printf("%.2f\t%.2f\n", outputStress[0][i], outputStress[1][i]);
		}
		System.out.println();
		System.out.println();
//		return output;
    }
	/**
	 * @return the outputStress
	 */
    public double[][] getStressSol()
    {
	    return outputStress;
    }

	/**
	 * @return the outputStress
	 */
    public double[][] getClassicalSol()
    {
	    return outputClassical;
    }
}
