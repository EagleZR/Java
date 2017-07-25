
package eaglezr.physics;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Mark
 *
 */
public class BigNumberTest {
	
	@Test
	public void testConstructorsBasic() {
		int intNumber = 1234;
		BigNumber number = new BigNumber( intNumber );
		assertEquals( number.toString(), "1234.000" );
		
		double doubleNumber = 1234.0;
		number = new BigNumber( doubleNumber );
		assertEquals( number.toString(), "1234.000" );
		
		String stringNumber = "12345.0";
		number = new BigNumber( stringNumber );
		assertEquals( number.toString(), "12345.000" );
	}
	
	@Test
	public void testConstructorsIntermediate() {
		int intNumber = Integer.MAX_VALUE;
		BigNumber number = new BigNumber( intNumber );
		assertEquals( number.toString(), Integer.MAX_VALUE + ".000" );
		
		intNumber = Integer.MIN_VALUE;
		number = new BigNumber( intNumber );
		assertEquals( number.toString(), Integer.MIN_VALUE + ".000" );
		
		double doubleNumber = Double.MAX_VALUE;
		number = new BigNumber( doubleNumber );
		assertEquals( number.toString(), Double.MAX_VALUE + "00" );
		
		doubleNumber = Double.MIN_VALUE;
		number = new BigNumber( doubleNumber );
		assertEquals( number.toString(), Double.MIN_VALUE + "00" );
		
		float floatNumber = Float.MAX_VALUE;
		number = new BigNumber( floatNumber );
		assertEquals( number.toString(), Float.MAX_VALUE + "00" );
		
		floatNumber = Float.MIN_VALUE;
		number = new BigNumber( floatNumber );
		assertEquals( number.toString(), Float.MIN_VALUE + "00" );
	}
}
