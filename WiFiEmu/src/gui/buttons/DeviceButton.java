/**
 * 
 */
package gui.buttons;

import javax.swing.JButton;

/**
 * @author Paul Mora
 *
 */
public class DeviceButton extends JButton
{
	/**
     * 
     */
    public DeviceButton()
    {
	    super("Person");
	    /*Image img = null; 
	    try
        {
	       img = ImageIO.read(new File("res/person.jpg"));
        } catch (IOException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	    setIcon(new ImageIcon(img));*/
	    setEnabled(true);
        setSize(20, 20);
	    setToolTipText("A person to put on map");
    }
}
