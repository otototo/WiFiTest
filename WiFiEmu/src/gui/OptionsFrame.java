/**
 * 
 */
package gui;

import java.awt.Dimension;

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
	    
	    
	    
	    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	    setVisible(false);
	    setMinimumSize(new Dimension(400, 100));
    }
}
