/**
 * 
 */
package helpers;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author paulina
 *
 */
public class SpinnerListener implements ChangeListener
{
	private int value;
	

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
    @Override
    public void stateChanged(ChangeEvent e)
    {
	    ;
    }


	/**
	 * @return the value
	 */
    public int getValue()
    {
	    return value;
    }


	/**
	 * @param value the value to set
	 */
    public void setValue(int value)
    {
	    this.value = value;
    }

}
