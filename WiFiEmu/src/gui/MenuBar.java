/**
 * 
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import data.EmuData;
import data.management.SaveManager;

/**
 * @author paulina
 *
 */
public class MenuBar extends JMenuBar implements ActionListener
{
	JMenuItem newSimulation;
	JMenuItem save;
	JMenuItem load;
	JMenuItem preferences;
	JMenuItem exit;
	
	private EmuData emuData;
	private SaveManager saveManager;
	
	/**
	 * @param emuData 
     * 
     */
    public MenuBar(EmuData emuData)
    {
	    super();
	    JMenu menu = new JMenu("Options");
	    add(menu);
	    
//	    addNew(menu);
	    addSave(menu);
	    addLoad(menu);
//	    addPreferences(menu);
	    addExit(menu);
	    
	    setEmuData(emuData);
    	saveManager = new SaveManager(emuData);
    }

	/**
	 * @param menu
	 */
    private void addExit(JMenu menu)
    {	    
	    exit = addMenuItem(menu, "Exit", KeyEvent.VK_Q);    
    }

	/**
	 * @param menuItemName
	 * @param keyEvent
	 */
    private JMenuItem addMenuItem(JMenu menu, String menuItemName, int keyEvent)
    {
    	JMenuItem menuItem = new JMenuItem(menuItemName, keyEvent);
    	menuItem.setAccelerator(KeyStroke.getKeyStroke(keyEvent, ActionEvent.CTRL_MASK));
    	menuItem.addActionListener(this);
	    menu.add(menuItem);
	    return menuItem;
    }

	/**
	 * @param menu
	 */
    private void addPreferences(JMenu menu)
    {
	    preferences = addMenuItem(menu, "Preferences", KeyEvent.VK_P);  
    }

	/**
	 * @param menu
	 */
    private void addLoad(JMenu menu)
    {
	    load = addMenuItem(menu, "Load", KeyEvent.VK_O);  
    }

	/**
	 * @param menu
	 */
    private void addSave(JMenu menu)
    {
	    save = addMenuItem(menu, "Save", KeyEvent.VK_S);  
    }

	/**
	 * @param menu
	 */
    private void addNew(JMenu menu)
    {
	    newSimulation = addMenuItem(menu, "New", KeyEvent.VK_N);  
    }

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
    @Override
    public void actionPerformed(ActionEvent e)
    {
    	System.out.println("MenuBar action performed");
    	System.out.println("source:"+e.getSource());
	    if ((e.getSource() == save) || (e.getSource() == load))
	    {
	    	if (e.getSource() == save)
	    	{
	    		saveManager.saveFile(this);
	    	}
	    	else
	    	{
	    		saveManager.openFile(this);
	    	}
	    }
	    else if (e.getSource() == newSimulation)
	    {
	    	readPreferences(true);
	    	emuData.reinit();
	    }
	    else if(e.getSource() == preferences)
	    {
	    	readPreferences(false);
	    }
	    else if (e.getSource() == exit)
	    {
            int confirm = JOptionPane.showOptionDialog(this,
                    "Close Program?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
	    }
    }

	/**
	 * @param b
	 */
    private void readPreferences(boolean b)
    {
    	/*JTextField columnCount = new JTextField(EmuData.DEFAULT_COLUMN_COUNT);
    	JTextField rowCount = new JTextField(EmuData.DEFAULT_ROW_COUNT);
    	JTextField signalStrength = new JTextField(EmuData.DEFAULT_MAX_SIGNAL_STRENGTH);
    	JTextField decreaseRate = new JTextField(EmuData.DEFAULT_SIGNAL_DECREASE_PER_CELL);
    	
    	JButton ok = new JButton("OK");
    	JButton cancel = new JButton("Cancel");
    	
    	Object[] options = {cancel, ok, columnCount, rowCount, signalStrength, decreaseRate};
	    JOptionPane.showOptionDialog(this, "hullo", "title",
	    		JOptionPane.OK_CANCEL_OPTION, JOptionPane.DEFAULT_OPTION,
	    		null, options, null);*/
    	
    }

	/**
	 * @return the emuData
	 */
    public EmuData getEmuData()
    {
	    return emuData;
    }

	/**
	 * @param emuData the emuData to set
	 */
    public void setEmuData(EmuData emuData)
    {
	    this.emuData = emuData;
    }
    
}
