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
	HEIGHT("HEIGHT"),
	WIDTH("WIDTH"),
	
	/** Decrease of signal strength per cell*/
	DECREASE_PER_CELL("DECREASE_PER_CELL"),
	
	/** Positions of placed WiFi stations*/
	WIFI("WIFI"),
	
	/** Positions of calculated WiFi stations*/
	WIFI_CALC("WIFI_CACLCULATED"),
	
	/** Maximum signal strength of WiFi station*/
	SIGNAL_FREQUENCY("SIGNAL_FREQUENCY"),
	
	/** Positions of placed mobile devices*/
	MOBILE("MOBILE"),
	
	X("X"),
	Y("Y"), ID("ID"), SIGNAL_STRENGTHS("SIGNAL_STRENGTHS"), 
	SIGNAL_STRENGTH("SIGNAL_STRENGTH"), VALUE("VALUE"), COORD("COORD"), MOBILES("MOBILES"), WIFIS("WIFIS");
	
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
