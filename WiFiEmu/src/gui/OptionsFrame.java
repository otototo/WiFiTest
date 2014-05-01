/**
 * 
 */
package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import data.EmuData;

/**
 * @author paulina
 *
 */
public class OptionsFrame extends JFrame implements DocumentListener, ActionListener
{	
	private static final JLabel columnLabel = new JLabel("Column count");
	private static final JLabel rowLabel = new JLabel("Row count");
	private static final JLabel decreaseLabel = new JLabel("Enter signal deprecation per cell");
	private static final JLabel maxStrengthLabel = new JLabel("Enter signal strength");
	private static final JLabel emptyLabel = new JLabel("");
	
	private JTextField columnCountText = new JTextField(EmuData.DEFAULT_COLUMN_COUNT+"");
	private JTextField rowCountText = new JTextField(EmuData.DEFAULT_ROW_COUNT+"");
	private JTextField decreaseText = new JTextField(EmuData.DEFAULT_SIGNAL_DECREASE_PER_CELL+"");
	private JTextField maxStrengthText = new JTextField(EmuData.DEFAULT_MAX_SIGNAL_STRENGTH+"");

	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	
	private EmuData emuData;
	private EmuData tempEmuData;
	
	/**
     * 
     */
    public OptionsFrame(EmuData emuData)
    {
    	super("Preferences");
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(5,1));
	    
	    setTempEmuData(emuData);
	    setEmuData(emuData);
	    
	    panel.add(columnLabel);
	    panel.add(columnCountText);
	    columnCountText.getDocument().addDocumentListener(this);
	    
	    panel.add(rowLabel);	    
	    panel.add(rowCountText);
	    rowCountText.getDocument().addDocumentListener(this);
	    
	    panel.add(decreaseLabel);
	    panel.add(decreaseText);
	    decreaseText.getDocument().addDocumentListener(this);
//	    add(emptyLabel);
	    
	    panel.add(maxStrengthLabel);
	    maxStrengthText.getDocument().addDocumentListener(this);
	    panel.add(maxStrengthText);
	    
	    okButton.addActionListener(this);
	    panel.add(okButton);
	    
	    cancelButton.addActionListener(this);
	    panel.add(cancelButton);
	    
	    panel.setBorder(new EmptyBorder(5,5,5,5));
	    
	    this.add(panel);
	    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	    setVisible(false);
	    pack();
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
    		okButton.setEnabled(false);	
    	}
    	else if (!okButton.isEnabled())
    	{
    		okButton.setEnabled(true);
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
	    if (e.getSource() == okButton)
	    {
	    	emuData.reinit();
	    	emuData.copySingularData(tempEmuData);
	    	emuData.setJustLoaded(true, true);
	    	this.setVisible(false);
	    }
	    else if (e.getSource() == cancelButton)
	    {
	    	this.setVisible(false);
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
