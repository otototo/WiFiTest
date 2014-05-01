/**
 * 
 */
package helpers;


/**
 * @author paulina
 *
 */
public interface EmuDataListener
{	
    public abstract void onEmuDataChange(boolean realDataChange, ChangeIdentifier id);	
}
