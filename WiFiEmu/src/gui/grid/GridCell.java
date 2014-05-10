/**
 * 
 */
package gui.grid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import data.Device;
import data.DeviceType;

/**
 * @author paulina
 *
 */
public class GridCell extends Rectangle
{
	private static final int signalR = 5;
//	private GridCellType cellType;
	private Image image;
	private Device device;
	private Device prediction;
	private Device mds;

	/**
	 * @param i
	 * @param j
	 * @param cellWidth
	 * @param cellHeight
	 */
    public GridCell(int i, int j, int cellWidth, int cellHeight)
    {
    	super(i, j, cellWidth, cellHeight);
    }
/*
	*//**
	 * @return the cellType
	 *//*
    public GridCellType getCellType()
    {
	    return cellType;
    }

	*//**
	 * @param cellType the cellType to set
	 *//*
    public void setCellType(GridCellType cellType)
    {
	    this.cellType = cellType;
    }
*/
	/**
	 * @return the image
	 */
    public Image getImage()
    {
	    return image;
    }

	/**
	 * @param image the image to set
	 */
    public void setImage(Image image)
    {
	    this.image = image;
    }

	/**
	 * @param imagePath
	 */
    public void setImage(String imagePath)
    {
	    try
        {
	       setImage(ImageIO.read(new File(imagePath)));
	        
        } catch (IOException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
    }
    
    /**
     * 
     */
    public void drawEmpty(Graphics2D g2d)
    {
		g2d.setColor(Color.BLACK);
    	g2d.drawRect(x, y, width, height);
    	if (prediction != null)
    	{
    		g2d.setColor(Color.MAGENTA);
    		int length = g2d.getFontMetrics().charsWidth((prediction.getId()+"").toCharArray(), 0, (prediction.getId()+"").length());
        	g2d.drawString(prediction.getId()+"", x+width-length, y+height);
    	}
    }
    public void draw(Graphics2D g2d)
    {
    	if (getImage() != null)
    		drawImage(g2d);
    	else
    		drawEmpty(g2d);
    }
    private void drawImage(Graphics2D g2d)
    {    	
    	if (mds != null)
    	{
    		System.out.println("GridCell.drawImage() mds+");
        	g2d.setColor(Color.BLACK);
        	g2d.drawImage(mds.getDeviceType().getImage(), x+2, y+2, 
    				width-2, height-2, null);    	
    		getImage().flush();	    
        	
    		g2d.setColor(Color.MAGENTA);
    		int length = g2d.getFontMetrics().charsWidth((mds.getId()+"").toCharArray(), 0, (mds.getId()+"").length());
        	g2d.drawString(mds.getId()+"", x+width-length, y+height);
        	System.out.println("GridCell.drawImage() mds-");
    	}
    	else
    	{
        	g2d.setColor(Color.BLACK);
    		drawEmpty(g2d);
        	g2d.drawImage(getImage(), x+2, y+2, 
    				width-2, height-2, null);    	
    		getImage().flush();	    
    		
    		if (getDevice().getDeviceType() == DeviceType.MOBILE)
    		{
    			
    			for (java.lang.Double val : device.getSignalStrengthTable().values())
    			{
    				Shape theCircle = new Ellipse2D.Double(x - width * val + width/2, y - height * val + height/2, 2.0 * width* val, 2.0 * height * val);
    			    g2d.draw(theCircle);
    			}
    		}
        	
    		g2d.setColor(Color.MAGENTA);
        	g2d.drawString(device.getId()+"", x, y+height);
        	if (prediction != null)
        	{
        		int length = g2d.getFontMetrics().charsWidth((prediction.getId()+"").toCharArray(), 0, (prediction.getId()+"").length());
            	g2d.drawString(prediction.getId()+"", x+width-length, y+height);
        	}
    	}
    }

	/**
	 * @return the device
	 */
    public Device getDevice()
    {
	    return device;
    }

	/**
	 * @param device the device to set
	 */
    public void setDevice(Device device)
    {
	    this.device = device;
    }

	/**
	 * @param device2
	 */
    public void setPrediction(Device device)
    {
	    this.prediction = device;
    }
    public void setMDS(Device mds)
    {
	    this.mds = mds;
    }
	/**
	 * @return
	 */
    public Device getMDS()
    {
	    // TODO Auto-generated method stub
	    return this.mds;
    }
}
