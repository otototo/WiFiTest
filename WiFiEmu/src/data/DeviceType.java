/**
 * 
 */
package data;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author paulina
 * TODO: associate with IMAGE
 */
public enum DeviceType
{
	WIFI_REAL(Color.WHITE, "res/wifi.png"), WIFI_CALC(Color.RED, "res/wifi2.png"), MOBILE(Color.WHITE, "res/person.png");
	
	private Color color;
	private Image image = null;
	
	private DeviceType(Color color, String imagePath)
	{
		this.setColor(color);
		try
        {
			if (imagePath != null)
				this.setImage(ImageIO.read(new File(imagePath)));
        } catch (IOException e)
        {
	        e.printStackTrace();
	        this.setImage(null);
        }
	}

	/**
	 * @return the color
	 */
    public Color getColor()
    {
	    return color;
    }

	/**
	 * @param color the color to set
	 */
    public void setColor(Color color)
    {
	    this.color = color;
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
}
