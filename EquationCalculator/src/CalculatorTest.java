import eaglezr.support.logs.LoggingTool;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

	@Test( expected = NumberFormatException.class ) public void testNumbers() {
		assertTrue( Calculator.calculate( "5.0" ) == 5.0 );
		assertTrue( Calculator.calculate( "-5.0" ) == -5.0 );
		Calculator.calculate( "5.0.0" );
	}

	@Test public void testAdd1() {
		LoggingTool.print( "\n*********************\ntestAdd1\n*********************" );
		assertTrue( Calculator.calculate( "5 + 5" ) == 10.0 );
		assertTrue( Calculator.calculate( "-5 + 5" ) == 0 );
		assertTrue( Calculator.calculate( "-5 + -5" ) == -10 );

		assertTrue( Calculator.calculate( "5.0 + 5.0" ) == 10.0 );
		assertTrue( Calculator.calculate( "-5.0 + 5.0" ) == 0 );
		assertTrue( Calculator.calculate( "-5.0 + -5.0" ) == -10 );
	}

	@Test public void testSubtract1() {
		LoggingTool.print( "\n*********************\ntestSubtract1\n*********************" );
		assertTrue( Calculator.calculate( "5 - 5" ) == 0 );
		assertTrue( Calculator.calculate( "5 - -5" ) == 10 );
		assertTrue( Calculator.calculate( "-5 - 5" ) == -10 );

		assertTrue( Calculator.calculate( "5.0 - 5.0" ) == 0 );
		assertTrue( Calculator.calculate( "5.0 - -5.0" ) == 10 );
		assertTrue( Calculator.calculate( "-5.0 - 5.0" ) == -10 );
	}

	@Test public void testParentheses1() {
		LoggingTool.print( "\n*********************\ntestParentheses1\n*********************" );
		assertTrue( Calculator.calculate( "5 + (5)" ) == 10 );
		assertTrue( Calculator.calculate( "5 - (-5)" ) == 10 );
		assertTrue( Calculator.calculate( "-5 + (5)" ) == 0 );

		assertTrue( Calculator.calculate( "5 + (5 - 5)" ) == 5 );
		assertTrue( Calculator.calculate( "5 - (-5 + 5)" ) == 5 );
		assertTrue( Calculator.calculate( "(-5 - 5) - 5" ) == -15 );
	}

	@Test public void testExponents1() {
		LoggingTool.print( "\n*********************\ntestParentheses1\n*********************" );
		assertTrue( Calculator.calculate( "2 ^ 2" ) == 4.0 );
		assertTrue( Calculator.calculate( "(2) ^ 2" ) == 4.0 );
		assertTrue( Calculator.calculate( "3 + 2 ^ 2" ) == 7.0 );
		assertTrue( Calculator.calculate( "(3 + 2) ^ 2" ) == 25.0 );
	}

}
