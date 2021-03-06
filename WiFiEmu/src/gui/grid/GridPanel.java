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
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
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
	
	private Vector<MDSPoint> mdsClassical;
	private Vector<MDSPoint> mdsStress;
	
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
	    mdsClassical = new Vector<MDSPoint>();
	    mdsStress = new Vector<MDSPoint>();
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
    protected void removeDevice(double x, double y)
    {
    	int index = selectedCell.x + 
    			(selectedCell.y * getCurrentColumnCount());
    	GridCell cell = getGridCell(index);
    	Device device = cell.getDevice();
    	if (device.getDeviceType() == DeviceType.WIFI_REAL)
    	{
    		if (getEmuData().getWiFiStationsReal().contains(device))
    			getEmuData().removeWiFiStation(device, true);
    	}
    	else if (device.getDeviceType() == DeviceType.MOBILE);
    	{
    		if (getEmuData().getMobileDevices().contains(device))
    			getEmuData().removeMobile(index, true);
        }
    	
    }
	/**
	 * @param device
	 */
    protected void addWiFiStation(double x, double y)
    {
    	Device device = addImageOntoCell(DeviceType.WIFI_REAL.getImage(), x, y);
    	if (device != null)
    	{
        	device.setDeviceType(DeviceType.WIFI_REAL);
        	getEmuData().addWiFiStationReal(device, true);
        	wifiSignalUpdate.update(device);
        	
//        	repaint();
    	}
    	else
    	{
    		int index = selectedCell.x + 
        			(selectedCell.y * getCurrentColumnCount());
        	GridCell cell = getGridCell(index);
        	Device wifi = cell.getDevice();
        	String frequencyChange = JOptionPane.showInputDialog("Enter new frequency");
        	try
            {
	            System.out.println("frequencyChange="+frequencyChange);
	            wifi.setSignalFrequency(Integer.parseInt(frequencyChange));
	        	wifiSignalUpdate.update(wifi);
            } catch (Exception e)
            {
	            System.err.println(e.getMessage());
            }
    	}
    }

    protected void addDeviceOntoCell(Device device)
    {
    	addImageOntoCell(device, device.getX(), device.getY());
    }
	/**
	 * @param device
	 */
    protected void addMobileDevice(double x, double y)
    {
    	Device device = addImageOntoCell(DeviceType.MOBILE.getImage(), x, y);
    	if (device != null)
    	{
        	device.setDeviceType(DeviceType.MOBILE);
        	getEmuData().addMobileDevice(device, true);	
        	wifiSignalUpdate.update(device);
        	
//        	repaint();
    	}
    	else
    	{
    		int index = selectedCell.x + 
        			(selectedCell.y * getCurrentColumnCount());
        	GridCell cell = getGridCell(index);
        	Device mobile = cell.getDevice();
        	String msg = "ID="+mobile.getId();
        	
        	Enumeration<Integer> it = mobile.getSignalStrengthTable().keys();
        	while (it.hasMoreElements())
        	{
        		int next = it.nextElement();
        		msg += "\n"+next+" = "+mobile.getSignalStrength(next);
        	}
        	
        	JOptionPane.showConfirmDialog(this, msg, "WifiSignals", JOptionPane.CLOSED_OPTION);
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
    public Device addImageOntoCell(Image image, double x, double y)
    {
    	Device device = null;
    	int index = selectedCell.x + 
    			(selectedCell.y * getCurrentColumnCount());
    	GridCell cell = getGridCell(index);
    	if ((cell != null) && (cell.getImage() == null))
    	{
    		cell.setImage(image);
    		device = new Device(DeviceType.MOBILE, 
        			selectedCell.x, selectedCell.y);
    		cell.setDevice(device);
    	}
    	return device;
    }
    
    public void addImageOntoCell(Device device, double x, double y)
    {
    	int index = (int) (x + (y * getCurrentColumnCount()));
    	GridCell cell = getGridCell(index);
    	if ((cell != null) && (cell.getImage() == null))
    	{
    		cell.setImage(device.getDeviceType().getImage());
    		cell.setDevice(device);
    	}
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
	   Graphics2D g2d = (Graphics2D) g.create();
	   g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
       drawGrid(g2d);  
       drawMDS(g2d);
       g2d.dispose();
    }
    
    /**
	 * 
	 */
    private void drawMDS(Graphics2D g2d)
    {
	    for (MDSPoint p : mdsStress)
	    {
	    	g2d.setColor(Color.MAGENTA);
	    	int w = 10, h = 10;
	    	g2d.fillRect(p.getPoint().x, p.getPoint().y, w, h);
	    	g2d.setColor(Color.BLUE);
        	g2d.drawString(p.getID()+"", p.getPoint().x+w, p.getPoint().y+h);
	    }
	    for (MDSPoint p : mdsClassical)
	    {
	    	g2d.setColor(Color.GREEN);
	    	int w = 10, h = 10;
	    	g2d.fillOval(p.getPoint().x+getWidth()/2, p.getPoint().y, w, h);
	    	g2d.setColor(Color.BLUE);
        	g2d.drawString(p.getID()+"", p.getPoint().x+getWidth()/2, p.getPoint().y+2*h);
	    }
    }
    
    private void drawGrid(Graphics2D g2d)
    {
        if (grid.isEmpty())
        {
            initGrid();
        }
        drawCells(g2d);  
    }

    /**
	 * @param g2d
	 */
    private void initGrid()
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
        	if (emuData.isView(GridViewType.Reality) 
//        			&& 
//        			(cell.getCellType() != GridCellType.WIFI_CALC)
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
       		if (id == ChangeIdentifier.ALL)
       		{
       			mdsClassical.clear();
       			mdsStress.clear();
       			
       			initGrid();
           		
           		for (Device device : emuData.getMobileDevices())
           		{
           			addDeviceOntoCell(device);
           		}
           		for (Device device : emuData.getWiFiStationsReal())
           		{
           			addDeviceOntoCell(device);
           		}
       		}
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
    	System.out.println("mouseReleased.realView:"+emuData.isView(GridViewType.Reality));
    	if (emuData.isView(GridViewType.Reality))
    	{
    		if (selectedCell != null)
    		{
            	/*Device device = new Device(DeviceType.WIFI_REAL, 
            			selectedCell.x, selectedCell.y);*/
                if (e.getButton() == MouseEvent.BUTTON1)
                {
                	addMobileDevice(selectedCell.x, selectedCell.y);
                }
                else if (e.getButton() == MouseEvent.BUTTON3)
                {
                	addWiFiStation(selectedCell.x, selectedCell.y);
                }
                else if (e.getButton() == MouseEvent.BUTTON2)
                {
                	removeDevice(selectedCell.x, selectedCell.y);            //does not work     	
                }
    		}
    	}
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
	/**
	 * @param all
	 * @param stress
	 */
    public void fillMDS(List<Device> all, double[][] stress, double[][] classical)
    {
    	double maxnx = stress[0][0], maxny = stress[1][0];
	    for (int i = 0; i < stress[0].length; i++)
	    {
	    	if (stress[0][i] < maxnx)
	    		maxnx = stress[0][i];
	    	if (stress[1][i] < maxny)
	    		maxny = stress[1][i];
	    }
	    maxnx = -maxnx;
	    maxny = -maxny;
	    
	    int x, y;
	    double cellWidth = grid.get(0).getWidth();
	    double cellHeight = grid.get(0).getHeight();
	    
	    mdsStress.clear();
	    for (int i = 0; i < stress[0].length; i++)
	    {
    	    x = (int) ((stress[0][i] + maxnx) * cellWidth);
    	    y = (int) ((stress[1][i] + maxny) * cellHeight);	
    	    mdsStress.add(new MDSPoint(x, y, all.get(i).getId()));
	    }
	    
	    maxnx = classical[0][0]; maxny = classical[1][0];
	    for (int i = 0; i < classical[0].length; i++)
	    {
	    	if (classical[0][i] < maxnx)
	    		maxnx = classical[0][i];
	    	if (classical[1][i] < maxny)
	    		maxny = classical[1][i];
	    }
	    maxnx = -maxnx;
	    maxny = -maxny;
	    
	    mdsClassical.clear();
	    for (int i = 0; i < classical[0].length; i++)
	    {
    	    x = (int) ((classical[0][i] + maxnx) * cellWidth);
    	    y = (int) ((classical[1][i] + maxny) * cellHeight);	
    	    mdsClassical.add(new MDSPoint(x, y, all.get(i).getId()));
	    }
	    repaint();
    }
 
}
