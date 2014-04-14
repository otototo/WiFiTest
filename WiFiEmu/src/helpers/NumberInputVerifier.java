/**
 * 
 */
package helpers;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * @author paulina
 *
 */
public class NumberInputVerifier extends InputVerifier
{

	/* (non-Javadoc)
	 * @see javax.swing.InputVerifier#verify(javax.swing.JComponent)
	 */
    @Override
    public boolean verify(JComponent component)
    {
	    JTextField textfield = (JTextField) component;
	    //check if all are numbers
	    
	    return false;
    }

}
