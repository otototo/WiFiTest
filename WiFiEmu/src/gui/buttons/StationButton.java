/**
 * 
 */
package gui.buttons;

import javax.swing.JButton;

/**
 * @author Paul Mora
 *
 */
public class StationButton extends JButton 
{
	public StationButton()
    {
    	super("WiFi station");
      /*  Image img = null; 
        try
        {
 	       img = ImageIO.read(new File("res/wifi.png"));
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setIcon(new ImageIcon(img));*/
        setEnabled(true);
        setSize(20, 20);
        setToolTipText("A device to put on map");
    }
}