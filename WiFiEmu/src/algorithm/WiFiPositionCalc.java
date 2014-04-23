/**
 * 
 */
package algorithm;

import java.util.List;

import lmath.data.Matrix;
import lmath.data.Series;
import lmath.data.impl.MatrixImpl;
import lmath.data.impl.SeriesImpl;
import lmath.equation.SolverFactory;
import data.Device;
import data.EmuData;



/**
 * @author paulina
 *
 */
public class WiFiPositionCalc
{
	private static final int COORDINATE_COUNT = 2;
	
	private EmuData emuData;
//	private SimpleMatrix matrix;
	private Matrix coefficients;
	private Series freemembers;

	private Series result;
	/**
     * 
     */
    public WiFiPositionCalc(EmuData emuData, int wifiId)
    {
	    initCoeficients(emuData.getMobileDevicesCount(),
	    		emuData.getMobileDevices());
	    initFreeMembers(emuData.getMobileDevicesCount(), 
	    		wifiId, emuData.getMobileDevices());
    }
    public WiFiPositionCalc()
    {
	    //empty
    }
	/**
	 * @param wifiId 
	 * @param mobileDevices
	 */
    private void initFreeMembers(int mobileDevicesCount,
    		int wifiId, List<Device> mobileDevices)
    {
	    double[] freemembersd = new double[mobileDevicesCount];
	    for (int i = 0; i < mobileDevicesCount; i++)
	    {
	    	freemembersd[i] = mobileDevices.get(i).getSignalStrength(wifiId);
	    }
	    this.freemembers = new SeriesImpl(freemembersd);
    }
	/**
	 * @param mobileDevicesCount
	 * @param mobileDevices
	 */
    private void initCoeficients(int mobileDevicesCount,
            List<Device> mobileDevices)
    {
    	double[][] coefficientsd = new double[mobileDevicesCount][COORDINATE_COUNT];
    	for (int i = 0; i < mobileDevicesCount; i++)
    	{
    		coefficientsd[i][0] = mobileDevices.get(0).getX();
    		coefficientsd[i][1] = mobileDevices.get(0).getY();
    	}
    	this.coefficients = new MatrixImpl(coefficientsd);
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
	 * @param list 
	 * using class field values
	 * @return 
	 */
    public Series calculate()
    {
	    return solveLinearEquation(this.coefficients, this.freemembers);
    }
	/**
	 * using provided values
	 * provided values overwrite old ones
	 */
    public Series calculate(EmuData emuData, int wifiId)
    {
	    initCoeficients(emuData.getMobileDevicesCount(),
	    		emuData.getMobileDevices());
	    initFreeMembers(emuData.getMobileDevicesCount(), 
	    		wifiId, emuData.getMobileDevices());
	    calculate();
	    return getResult();
    }

	/**
	 * using provided values.
	 * provided values overwrite old ones
	 */
    public Series calculate(double[][] coeff, double[] freem)
    {
	    this.coefficients = new MatrixImpl(coeff);
	    System.out.println("coef:"+this.coefficients.getData());
	    this.freemembers = new SeriesImpl(freem);
	    System.out.println("coef:"+this.freemembers.getData());
	    calculate();
	    return getResult();
    }
    
    private Series solveLinearEquation(Matrix coefficients, Series freeMembers)
    {
    	this.result = SolverFactory.KRAMER.solve(coefficients, freeMembers);
    	return getResult();
    }
    
    public Series getResult()
    {
    	return this.result;
    }
}
