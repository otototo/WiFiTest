package gui.panels;

import gui.buttons.DeviceButton;
import gui.buttons.StationButton;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import data.EmuData;

public class TopPanel extends JPanel implements DocumentListener, ActionListener
{
	private StationButton stationButton = new StationButton();
	private DeviceButton deviceButton = new DeviceButton();
	
	private static final JLabel sizeLabel = new JLabel("Enter column count x row count");
	private static final JLabel decreaseLabel = new JLabel("Enter signal deprecation per cell");
	private static final JLabel maxStrengthLabel = new JLabel("Enter signal strength");
	private static final JLabel emptyLabel = new JLabel("");
	
	private JTextField columnCountText = new JTextField(EmuData.DEFAULT_COLUMN_COUNT+"");
	private JTextField rowCountText = new JTextField(EmuData.DEFAULT_ROW_COUNT+"");
	private JTextField decreaseText = new JTextField(EmuData.DEFAULT_SIGNAL_DECREASE_PER_CELL+"");
	private JTextField maxStrengthText = new JTextField(EmuData.DEFAULT_MAX_SIGNAL_STRENGTH+"");

	private JButton change = new JButton("Change");
	
	private EmuData emuData;
	private EmuData tempEmuData;
	/**
	 * @param emuData 
     * 
     */
    public TopPanel(EmuData emuData)
    {
	    super();
	    setTempEmuData(emuData);
	    setEmuData(emuData);
	    setLayout(new GridLayout(3, 3));
//	    add(deviceButton);
//	    add(stationButton);
	    
//	    setSize(20,20);
	    
	    add(sizeLabel);
	    add(columnCountText);
	    columnCountText.getDocument().addDocumentListener(this);	    
	    add(rowCountText);
	    rowCountText.getDocument().addDocumentListener(this);
	    
	    add(decreaseLabel);
	    add(decreaseText);
	    decreaseText.getDocument().addDocumentListener(this);
	    add(emptyLabel);
	    
	    add(maxStrengthLabel);
	    maxStrengthText.getDocument().addDocumentListener(this);
	    add(maxStrengthText);
	    change.addActionListener(this);
	    add(change);
	    
//	    setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	    setBorder(BorderFactory.createEtchedBorder());
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
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
    @Override
    public void changedUpdate(DocumentEvent e)
    {
	    fillDataFromUpdate(e);
    }
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
    @Override
    public void insertUpdate(DocumentEvent e)
    {
    	try
    	{
        	fillDataFromUpdate(e);
    	} 
    	catch (Exception pe)
    	{
    		JOptionPane.showMessageDialog(this, 
    				"Must be a number.", 
    				"Critical error", JOptionPane.ERROR_MESSAGE);
    		pe.printStackTrace();
    	}
    }
	/**
	 * @param e
	 */
    private void fillDataFromUpdate(DocumentEvent e)
    {
    	if (e.getDocument().equals(columnCountText.getDocument()))
	    {
	    	int columnCount = getInt(columnCountText);
	    	if (columnCount != -1)
	    		getTempEmuData().setGridColumnCount(columnCount, false);
	    }
    	else if (e.getDocument().equals(rowCountText.getDocument()))
	    {
	    	int rowCount = getInt(rowCountText);
	    	getTempEmuData().setGridRowCount(rowCount, false);
	    }
	    else if (e.getDocument().equals(maxStrengthText.getDocument()))
	    {
	    	int strength = getInt(maxStrengthText);
	    	getTempEmuData().setStationStrength(strength, false);
	    }
	    else if (e.getDocument().equals(decreaseText.getDocument()))
	    {
	    	int decrease = getInt(decreaseText);
	    	getTempEmuData().setDecreasePerCell(decrease, false);
	    }
    	
    	if ((getTempEmuData().getGridColumnCount() > EmuData.DEFAULT_MAX_GRID_COUNT) ||
    			(getTempEmuData().getGridRowCount() > EmuData.DEFAULT_MAX_GRID_COUNT))
    	{
    		change.setEnabled(false);	
    	}
    	else if (!change.isEnabled())
    	{
    		change.setEnabled(true);
    	}
    }
	/**
	 * @param columnCountText2
	 * @return
	 */
    private int getInt(JTextField textField)
    {
    	String text = textField.getText();
    	if (text.isEmpty() || (text == null))
    		return -1;
    	else 
    		return Integer.parseInt(text.trim());
    }
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
    @Override
    public void removeUpdate(DocumentEvent e)
    {
	    fillDataFromUpdate(e);
    }
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
    @Override
    public void actionPerformed(ActionEvent e)
    {
	    if (e.getSource() == change)
	    {
	    	emuData.copySingularData(tempEmuData);
	    	emuData.setJustLoaded(true, false);
	    }
    }
	/**
	 * @return the tempEmuData
	 */
    public EmuData getTempEmuData()
    {
	    return tempEmuData;
    }
	/**
	 * @param tempEmuData the tempEmuData to set
	 */
    public void setTempEmuData(EmuData tempEmuData)
    {
	    this.tempEmuData = tempEmuData;
    }
}
