/**
 * 
 */
package algorithm;

import mdsj.MDSJ;

/**
 * @author paulina
 *
 */
public class MDS
{
	/**
     * 
     */
    public double[][] scale(double[][] input)
    {
    	int n=input[0].length;    // number of data objects
		double[][] output=MDSJ.classicalScaling(input); // apply MDS
		for(int i=0; i<n; i++) {  // output all coordinates
		    System.out.println(output[0][i]+" "+output[1][i]);
		}
		return output;
    }
   
}
