/**
 * 
 */
package helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author paulina
 *
 */
public class EmuDataNotifier
{
	private List<EmuDataListener> listeners;
	
	/**
     * 
     */
    public EmuDataNotifier()
    {
	   listeners = new ArrayList<EmuDataListener>();
    }
    
    /**
     * 
     */
    public void addListener(EmuDataListener listener)
    {
	    this.listeners.add(listener);
    }
	
    public void notifyListeners(boolean realDataChange, ChangeIdentifier id)
    {
    	for (EmuDataListener listener : listeners)
    	{
    		listener.onEmuDataChange(realDataChange, id);
    	}
    }

    
}
