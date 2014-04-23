import lmath.data.Complex;
import algorithm.WiFiPositionCalc;
import gui.MainFrame;

/**
 * 
 */

/**
 * @author Paul Mora
 *
 */
public class Main
{
	public static void main(String[] args)
    {
	    new MainFrame();
//		testMatrixSystemSolution();
    }

	/**
	 * 
	 */
    private static void testMatrixSystemSolution()
    {
    	//x=2, y=3
		double[][] coeff = 
		{
				{5, -3},
				{2, 5}
		};
		double[] freem = 
			{
				1,
				19
			};
		WiFiPositionCalc calc = new WiFiPositionCalc();
		calc.calculate(coeff, freem);
		System.out.println("result:");
		for (Complex d : calc.getResult().getData())
		{
			System.out.print(d.getReal()+" ");
		}
    }
}
