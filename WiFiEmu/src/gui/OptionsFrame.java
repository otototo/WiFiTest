/**
 * 
 */
package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author paulina
 *
 */
public class OptionsFrame extends JFrame
{
	/**
     * 
     */
    public OptionsFrame()
    {
    	super("Preferences");
	    JPanel panel = new JPanel();
	    this.add(panel);
	    
	    
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
    }
}
