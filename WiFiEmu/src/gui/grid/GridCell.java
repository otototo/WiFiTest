/**
 * 
 */
package gui.grid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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
	private GridCellType cellType;
	private Image image;
	private Device device;

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

	/**
	 * @return the cellType
	 */
    public GridCellType getCellType()
    {
	    return cellType;
    }

	/**
	 * @param cellType the cellType to set
	 */
    public void setCellType(GridCellType cellType)
    {
	    this.cellType = cellType;
    }

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
    	g2d.drawRect(x, y, width, height);
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
		if (device.isSelected() && (device.getDeviceType() == DeviceType.WIFI_STATION))
		{
	    	g2d.setColor(Color.RED);
	    	//1-0
	    	//3-1
	    	//tik nelyginiams
	    	int coef = ((signalR - 1) > 0) ? ((signalR - 1) / 2) : 0;
	    	System.out.println("koef="+coef);
			g2d.fillOval(x - width * coef, y-height * coef, 
					width*signalR, height*signalR);
		}
		else if (device.isSelected() && (device.getDeviceType() == DeviceType.MOBILE))
		{
	    	g2d.setColor(Color.CYAN);
			g2d.fillRect(x, y, width, height);
		}
    	else
    	{
        	g2d.setColor(Color.BLACK);
    		drawEmpty(g2d);
    	}
    	g2d.setColor(Color.BLACK);
    	g2d.drawImage(getImage(), x+2, y+2, 
				width-2, height-2, null);
		getImage().flush();	    
    	
    	g2d.drawString(device.getId()+"", x, y+height);
		
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
}
