package gui.grid;

import helpers.ChangeIdentifier;
import helpers.EmuDataListener;
import helpers.WiFiSignalUpdate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import data.Device;
import data.DeviceType;
import data.EmuData;

/**
 * TODO: delete device;
 * TODO: calculate device's position
 * */

public class GridPanel 
	extends JPanel 
	implements MouseListener, EmuDataListener, ActionListener
{
	
	
	private static final int CELL_TAKEN = -1;
	private static final int SUCCESS = 0;
	
	private EmuData emuData;
	private List<GridCell> grid;
	private Point selectedCell = null;
	private MouseAdapter mouseHandler = null;
	private WiFiSignalUpdate wifiSignalUpdate;
	
	private int currentColumnCount;
	private int currentRowCount;
	
	/**
     * 
     */
    public GridPanel(EmuData emuData)
    {
	    super();
	    initEmuData(emuData);
	    wifiSignalUpdate = new WiFiSignalUpdate(emuData);
//	    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	    setBackground(Color.WHITE);
    }
	/**
	 * 
	 */
    private void initEmuData(EmuData emuData)
    {
    	setEmuData(emuData);
	    setGrid(getEmuData().getGridColumnCount(), getEmuData().getGridRowCount());
	    
    }
	/**
	 * @param columnCount
	 * @param rowCount
	 */
    private void setGrid(int columnCount, int rowCount)
    {
    	setCurrentColumnCount(columnCount);
    	setCurrentRowCount(rowCount);
    	grid = new ArrayList<GridCell>(columnCount*rowCount);
    	
    	if (mouseHandler == null)
    		initMouseHandler();        	
    }
    
    /**
	 * 
	 */
    private void initMouseHandler()
    {
    	mouseHandler = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) 
            {
                selectedCell = calculatePoint(e);
                repaint();
            }
    	};
        addMouseMotionListener(mouseHandler);
        addMouseListener(this);
    }
	/**
	 * @param device
	 */
    protected void removeDevice(Device device)
    {
    	device.setDeviceType(DeviceType.WIFI_STATION);
    	int index = getEmuData().getWiFiStationRealIndex(device);
    	if (index != -1)
    	{
        	getEmuData().removeWiFiStation(index, true);
    	}
    	else
    	{
        	device.setDeviceType(DeviceType.MOBILE);
    		index = getEmuData().getMobileDeviceIndex(device);
    		if (index != -1)
    		{
            	getEmuData().removeMobile(index, true);
    		}
        }
    	
    }
	/**
	 * @param device
	 */
    protected void addWiFiStation(Device device)
    {
    	if (addImageOntoCell(GridCellType.WIFI_REAL.getImage(), device) != CELL_TAKEN)
    	{
        	device.setDeviceType(DeviceType.WIFI_STATION);
        	getEmuData().addWiFiStationReal(device, true);
        	wifiSignalUpdate.update(device);
        	
//        	repaint();
    	}
    }
	/**
	 * @param device
	 */
    protected void addMobileDevice(Device device)
    {
    	if (addImageOntoCell(GridCellType.PERSON.getImage(), device) != CELL_TAKEN)
    	{
        	device.setDeviceType(DeviceType.MOBILE);
        	getEmuData().addMobileDevice(device, true);	
        	wifiSignalUpdate.update(device);
        	
//        	repaint();
    	}
    }
    public void addPrediction(Device device, int x, int y)
    {
    	int index = x + (y * getCurrentColumnCount());
    	GridCell cell = getGridCell(index);
		cell.setPrediction(device);
    }
    
	/**
	 * @param string
	 * @param device - device with wich to link
	 */
    public int addImageOntoCell(Image image, Device device)
    {
    	int ret = CELL_TAKEN;
    	int index = selectedCell.x + 
    			(selectedCell.y * getCurrentColumnCount());
    	GridCell cell = getGridCell(index);
    	if ((cell != null) && (cell.getImage() == null))
    	{
    		cell.setImage(image);
    		cell.setDevice(device);
    		ret = SUCCESS;
    	}
    	return ret;
    }
	/**
	 * @return
	 */
    protected Point calculatePoint(MouseEvent e)
    {
    	int width = getWidth();
        int height = getHeight();

        int cellWidth = width / getCurrentColumnCount();
        int cellHeight = height / getCurrentRowCount();

        int column = e.getX() / cellWidth;
        int row = e.getY() / cellHeight;

        return new Point(column, row);
    }
    
	@Override
    protected void paintComponent(Graphics g) 
	{    			
       super.paintComponent(g);
       drawGrid(g);  
    }
    
    /**
	 * http://stackoverflow.com/questions/15421708/how-to-draw-grid-using-swing-class-java-and-detect-mouse-position-when-click-and
	 */
    private void drawGrid(Graphics g)
    {
	    Graphics2D g2d = (Graphics2D) g.create();

        if (grid.isEmpty())
        {
            initGrid(g2d);
        }
        drawCells(g2d);         
        g2d.dispose();
    }

    /**
	 * @param g2d
	 */
    private void initGrid(Graphics2D g2d)
    {
        int width = getWidth();
        int height = getHeight();

     	int columnCount = getCurrentColumnCount();
     	int rowCount = getCurrentRowCount();
     	
        int cellWidth = width / columnCount;
        int cellHeight = height / rowCount;
        
        int currX = 0, currY = 0;
        
    	for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                GridCell cell = new GridCell(
                        currX, currY,
                        cellWidth,
                        cellHeight);
                grid.add(cell);
                currX += cellWidth;
            }            
        	currX = 0;
    		currY += cellHeight;            
        }
    }
	/**
	 * @param g2d
	 */
    private void drawCells(Graphics2D g2d)
    {
        g2d.setColor(Color.BLACK);
        for (GridCell cell : grid) 
        {
        	if (emuData.isView(GridViewType.Reality) && 
        			(cell.getCellType() != GridCellType.WIFI_CALC)
        		)
        	{
           	 	cell.draw(g2d);
        	}
        	else if (emuData.isView(GridViewType.Calculated))
        	{
           	 	cell.draw(g2d);
        	}
        	else
        	{
        		cell.drawEmpty(g2d);
        	}
        }
    }

	@Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }
    
	/**
	 * @param index
	 * @return
	 */
    private GridCell getGridCell(int index)
    {
    	if (grid.isEmpty())
    	{
    		System.err.println("Grid is empty");
    		return null;
    	}
    	GridCell ret = grid.get(0);
    	if ((index > 0) && (index < grid.size()))
    			ret = grid.get(index);
	    return ret;
    }
	@Override
    public void invalidate() {
//        grid.clear();
        super.invalidate();
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

	/**
	 * @return the selectedCell
	 */
    public Point getSelectedCell()
    {
	    return selectedCell;
    }
	/**
	 * @param selectedCell the selectedCell to set
	 */
    public void setSelectedCell(Point selectedCell)
    {
	    this.selectedCell = selectedCell;
    }
	/* (non-Javadoc)
	 * @see listeners.EmuDataListener#onEmuDataChange()
	 * TODO: change so if new selected grid is created a new. Changing size mid-work is pointless
	 */
    @Override
    public void onEmuDataChange(boolean isRealDataChange, ChangeIdentifier id)
    {
    	if ((id == ChangeIdentifier.SIZE) || (id == ChangeIdentifier.ALL))
    	{
        	int newColCount = getEmuData().getGridColumnCount();
        	int newRowCount = getEmuData().getGridRowCount();
       		setGrid(newColCount, newRowCount);
    	}
    	else if (id == ChangeIdentifier.WIFIC)
    	{
    		
    	}	
    		
   		repaint();
    }
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
    @Override
    public void mouseClicked(MouseEvent e)
    {
    	System.out.println("mouseClicked.realView:"+emuData.isView(GridViewType.Reality));
    	if (emuData.isView(GridViewType.Reality))
    	{
        	Device device = new Device(DeviceType.WIFI_STATION, 
        			selectedCell.x, selectedCell.y);
            if (e.getButton() == MouseEvent.BUTTON1)
            {
            	addMobileDevice(device);
            }
            else if (e.getButton() == MouseEvent.BUTTON3)
            {
            	addWiFiStation(device);
            }
            else if (e.getButton() == MouseEvent.BUTTON2)
            {
            	removeDevice(device);            //does not work     	
            }
    	}
    }
    
    
    
    
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
    @Override
    public void mouseEntered(MouseEvent e)
    {
	    // TODO Auto-generated method stub
	    
    }
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
    @Override
    public void mouseExited(MouseEvent e)
    {
	    // TODO Auto-generated method stub
	    
    }
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
    @Override
    public void mousePressed(MouseEvent e)
    {
	    // TODO Auto-generated method stub
	    
    }
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
    @Override
    public void mouseReleased(MouseEvent e)
    {
	    // TODO Auto-generated method stub
	    
    }
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
	    // TODO Auto-generated method stub
	    
    }
	/**
	 * @return the currentColumnCount
	 */
    public int getCurrentColumnCount()
    {
	    return currentColumnCount;
    }
	/**
	 * @param currentColumnCount the currentColumnCount to set
	 */
    public void setCurrentColumnCount(int currentColumnCount)
    {
	    this.currentColumnCount = currentColumnCount;
    }
	/**
	 * @return the currentRowCount
	 */
    public int getCurrentRowCount()
    {
	    return currentRowCount;
    }
	/**
	 * @param currentRowCount the currentRowCount to set
	 */
    public void setCurrentRowCount(int currentRowCount)
    {
	    this.currentRowCount = currentRowCount;
    }
}
