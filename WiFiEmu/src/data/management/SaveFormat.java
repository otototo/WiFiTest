/**
 * 
 */
package data.management;

/**
 * @author paulina
 *
 */
public enum SaveFormat
{	
	/** Height and Width of grid*/
	SIZE("SIZE"), 
	
	/** Decrease of signal strength per cell*/
	DECREASE_PER_CELL("DECR"),
	
	/** Positions of placed WiFi stations*/
	WIFI_REAL("WIFIR"),
	
	/** Positions of calculated WiFi stations*/
	WIFI_CALC("WIFIC"),
	
	/** Maximum signal strength of WiFi station*/
	SIGNAL_STRENGTH("MAX"),
	
	/** Positions of placed mobile devices*/
	MOBILE("MOBILE");
	
	private String value;
//	private String description;
	
	SaveFormat(String value/*, String description*/)
	{
		this.setValue(value);
/*		this.setDescription(description);*/
	}

	/**
	 * @return the value
	 */
    public String getValue()
    {
	    return value;
    }

	/**
	 * @param value the value to set
	 */
    public void setValue(String value)
    {
	    this.value = value;
    }

	/**
	 * @return the description
	 */
/*s*/

	/**
	 * @param description the description to set
	 */
/*    public void setDescription(String description)
    {
	    this.description = description;
    }*/
}
