/**
 * 
 */
package gui.grid;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author paulina
 *
 */
public class GridCell extends Rectangle
{
	private GridCellType cellType;
	private Image image;
	

	/**
	 * @param i
	 * @param j
	 * @param cellWidth
	 * @param cellHeight
	 */
    public GridCell(int i, int j, int cellWidth, int cellHeight)
    {
    	super(i, j, cellWidth, cellHeight);
    }

	/**
	 * @return the cellType
	 */
    public GridCellType getCellType()
    {
	    return cellType;
    }

	/**
	 * @param cellType the cellType to set
	 */
    public void setCellType(GridCellType cellType)
    {
	    this.cellType = cellType;
    }

	/**
	 * @return the image
	 */
    public Image getImage()
    {
	    return image;
    }

	/**
	 * @param image the image to set
	 */
    public void setImage(Image image)
    {
	    this.image = image;
    }

	/**
	 * @param imagePath
	 */
    public void setImage(String imagePath)
    {
	    try
        {
	       setImage(ImageIO.read(new File(imagePath)));
	        
        } catch (IOException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
    }
}
