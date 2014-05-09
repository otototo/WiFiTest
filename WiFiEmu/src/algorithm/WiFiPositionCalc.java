/**
 * 
 */
package algorithm;

/**
 * @author paulina
 *
 */
public class WiFiPositionCalc
{
	private double xi1, yi1, xi2, yi2;
	private double sx, sy;
	
	/**
     * 
     */
    public WiFiPositionCalc()
    {
    	xi1 = -1;
    	yi1 = -1;
    	xi2 = -1;
    	yi2 = -1;
    	
    	sx = -1;
    	sy = -1;
    }
	
	private int circleIntersect(double x0, double y0, double r0, double x1, double y1, double r1)
	{
	    /* This function checks for the intersection of two circles.
	    If one circle is wholly contained within the other a -1 is returned
	    If there is no intersection of the two circles a 0 is returned
	    If the circles intersect a 1 is returned and
	    the coordinates are placed in xi1, yi1, xi2, yi2*/
	 
	    // dx and dy are the vertical And horizontal distances between
	    // the circle centers.
		double dx = x1 - x0;
		double dy = y1 - y0;
	 
	    // Determine the straight-Line distance between the centers.
	    double d = Math.sqrt((dy*dy) + (dx*dx));
	 
	 
	    //Check for solvability.
	    if (d > (r0 + r1))
	    {
	        // no solution. circles do Not intersect
	        return 0;
	    }
	 
	    if (d < Math.abs(r0 - r1)) 
	    {
	    	// no solution. one circle is contained in the other
	        return -1;
	    }
	 
	    // 'point 2' is the point where the Line through the circle
	    // intersection points crosses the Line between the circle
	    // centers.
	 
	    // Determine the distance from point 0 To point 2.
	    double a = ((r0*r0) - (r1*r1) + (d*d)) / (2.0 * d);
	 
	    // Determine the coordinates of point 2.
	    double x2 = x0 + (dx * a/d);
	    double y2 = y0 + (dy * a/d);
	 
	    // Determine the distance from point 2 To either of the
	    // intersection points.
	    double h = Math.sqrt((r0*r0) - (a*a));
	 
	    // Now determine the offsets of the intersection points from
	    // point 2.
	    double rx = (0-dy) * (h/d);
	    double ry = dx * (h/d);
	 
	    // Determine the absolute intersection points.
	    xi1 = (float)(x2 + rx);
	    xi2 = (float)(x2 - rx);
	    yi1 = (float)(y2 + ry);
	    yi2 = (float)(y2 - ry);
	 
	    return 1;	
	}
	
	/**
	 * @return [[x1, y1], [x2, y2]] - two intersections
	 * */
	public double[][] getDoubleIntersection()
	{
		return new double[][]{{xi1, yi1}, {xi2, yi2}};
	}
	
	/**
	 * @return [sx, sy] - final solution
	 * */
	public double[] getIntersection()
	{
		return new double[]{sx, sy};
	}
	
	
	private boolean belongsToCircle(double x, double y, double r)
	{
		boolean ret = (Math.sqrt(x) + Math.sqrt(y) == Math.sqrt(r));		
		return ret;
	}

	/**
	 * @param coord
	 * @param strength
	 * 
	 * @returns -2 -> not enough data. -1 no intersections. 0 - 2 intersections. 1 - 1 intersection.
	 */
    public int calculate(double[][] coord, double[] strength)
    {
    	if (strength.length < 2)
    		return -2;
    	
    	int ret = -1;
    	double x1, y1, x2, y2, r1, r2;
    	double x3, y3, r3;
    	
    	double[] intersection = new double[2];
    	
	    for (int i = 0; i < strength.length -1; i++)
	    {
	    	x1 = coord[i][0];
	    	y1 = coord[i][1];
	    	r1 = strength[i];
	    	
	    	x2 = coord[i+1][0];
	    	y2 = coord[i+1][1];
	    	r2 = strength[i+1];
	    	
	    	if (circleIntersect(x1, y1, r1, x2, y2, r2) == 1)
	    	{    	    	
    	    	for (int j = i+2 ; j < strength.length; j++)
    	    	{
    	    		x3 = coord[j][0];
    	    		y3 = coord[j][1];
    	    		r3 = strength[j];

    		    	if (((x3 == xi1) && (y3 == yi1)) ||
    		    			((x3 == xi2) && (y2 == yi2)))
    		    	{
    		    		sx = x3;
    		    		sy = y3;
    		    		return 1;
    		    	}
    	    	}
	    	}
	    }
	    
	    return (xi1 != -1) ? -1 : -2;
    }
}
