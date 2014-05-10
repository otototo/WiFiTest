/**
 * 
 */
package gui.grid;

import java.awt.Point;

/**
 * @author paulina
 *
 */
public class MDSPoint
{
    /**
     * 
     */
	
	private Point point;
	private int ID;
	
	/**
     * 
     */
    public MDSPoint(int x, int y, int id)
    {
	    this(new Point(x, y), id);
    }
	
    public MDSPoint(Point point, int id)
    {
    	this.point = point;
    	this.ID = id;
    }

	/**
	 * @return the point
	 */
    public Point getPoint()
    {
	    return point;
    }

	/**
	 * @param point the point to set
	 */
    public void setPoint(Point point)
    {
	    this.point = point;
    }

	/**
	 * @return the iD
	 */
    public int getID()
    {
	    return ID;
    }

	/**
	 * @param iD the iD to set
	 */
    public void setID(int iD)
    {
	    ID = iD;
    }
}
