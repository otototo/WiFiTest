/**
 * 
 */
package data.management;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import data.EmuData;

/**
 * @author paulina
 *
 */
public class SaveManager
{
	private JFileChooser fileChooser;
	private EmuData emuData;
	
	private static final String FILE_END = ".WiFiEmu";
	
	/**
	 * @param emuData 
     * 
     */		
    public SaveManager(EmuData emuData)
    {
    	fileChooser = new JFileChooser();
    	FileFilter filter = new FileFilter()
		{
			
			@Override
			public String getDescription()
			{
				return "WiFi Emulators properties file.";
			}
			
			@Override
			public boolean accept(File f)
			{
				return f.getName().endsWith(FILE_END) || f.isDirectory();
			}
		};
    	fileChooser.setFileFilter(filter);
    	setEmuData(emuData);
    }
    
    public void openFile(Component component)
    {
    	int returnVal = fileChooser.showOpenDialog(component);
    	if (returnVal == JFileChooser.APPROVE_OPTION)
    	{
    		File fileToOpen = fileChooser.getSelectedFile();
    		fileChooser.setCurrentDirectory(fileChooser.getSelectedFile().getParentFile());
    		System.out.println("cuurentDir="+fileChooser.getCurrentDirectory());
    		Loader loader = new Loader();
    		loader.load(fileToOpen, getEmuData());
    	}
    }
    
    public void saveFile(Component component)
    {
    	int returnVal = fileChooser.showSaveDialog(component);
    	if (returnVal == JFileChooser.APPROVE_OPTION)
    	{
    		File fileToSave = fileChooser.getSelectedFile();
    		fileChooser.setCurrentDirectory(fileChooser.getSelectedFile().getParentFile());
    		System.out.println("cuurentDir="+fileChooser.getCurrentDirectory());
    		if (!fileToSave.getName().endsWith(FILE_END))
    		{
    			fileToSave = new File(fileToSave+FILE_END);
    		}
    		Saver saver = new Saver();
    		saver.save(fileToSave, getEmuData());
    	}
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
