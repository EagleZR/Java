import eaglezr.support.logs.LoggingTool;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

	@Test( expected = NumberFormatException.class ) public void testNumbers() {
		assertTrue( Calculator.calculate( "5.0" ) == 5.0 );
		assertTrue( Calculator.calculate( "-5.0" ) == -5.0 );
		Calculator.calculate( "5.0.0" );
	}

	@Test public void testAdd() {
		LoggingTool.print( "\n*********************\ntestAdd\n*********************" );
		assertTrue( Calculator.calculate( "5 + 5" ) == 10.0 );
		assertTrue( Calculator.calculate( "-5 + 5" ) == 0 );
		assertTrue( Calculator.calculate( "-5 + -5" ) == -10 );

		assertTrue( Calculator.calculate( "5.0 + 5.0" ) == 10.0 );
		assertTrue( Calculator.calculate( "-5.0 + 5.0" ) == 0 );
		assertTrue( Calculator.calculate( "-5.0 + -5.0" ) == -10 );
	}

	@Test public void testSubtract() {
		LoggingTool.print( "\n*********************\ntestSubtract\n*********************" );
		assertTrue( Calculator.calculate( "5 - 5" ) == 0 );
		assertTrue( Calculator.calculate( "5 - -5" ) == 10 );
		assertTrue( Calculator.calculate( "-5 - 5" ) == -10 );

		assertTrue( Calculator.calculate( "5.0 - 5.0" ) == 0 );
		assertTrue( Calculator.calculate( "5.0 - -5.0" ) == 10 );
		assertTrue( Calculator.calculate( "-5.0 - 5.0" ) == -10 );
	}

	@Test public void testParentheses() {
		LoggingTool.print( "\n*********************\ntestParentheses\n*********************" );
		assertTrue( Calculator.calculate( "5 + (5)" ) == 10 );
		assertTrue( Calculator.calculate( "5 - (-5)" ) == 10 );
		assertTrue( Calculator.calculate( "-5 + (5)" ) == 0 );

		assertTrue( Calculator.calculate( "5 + (5 - 5)" ) == 5 );
		assertTrue( Calculator.calculate( "5 - (-5 + 5)" ) == 5 );
		assertTrue( Calculator.calculate( "(-5 - 5) - 5" ) == -15 );
	}

	@Test public void testExponents() {
		LoggingTool.print( "\n*********************\ntestExponents\n*********************" );
		assertTrue( Calculator.calculate( "2 ^ 2" ) == 4.0 );
		assertTrue( Calculator.calculate( "(2) ^ 2" ) == 4.0 );
		assertTrue( Calculator.calculate( "3 + 2 ^ 2" ) == 7.0 );
		assertTrue( Calculator.calculate( "(3 + 2) ^ 2" ) == 25.0 );
	}

	@Test public void testMultiply() {
		LoggingTool.print( "\n*********************\ntestMultiply\n*********************" );
		assertTrue( Calculator.calculate( "2 * 2" ) == 4.0 );
		assertTrue( Calculator.calculate( "2 * -2" ) == -4.0 );
		assertTrue( Calculator.calculate( "3 + 2 * 2" ) == 7.0 );
		assertTrue( Calculator.calculate( "(3 + 2) * 2" ) == 10.0 );
	}

	@Test public void testDivide() {
		LoggingTool.print( "\n*********************\ntestDivide\n*********************" );
		assertTrue( Calculator.calculate( "2 / 2" ) == 1.0 );
		assertTrue( Calculator.calculate( "2 / -2" ) == -1.0 );
		assertTrue( Calculator.calculate( "3 + 2 / 2" ) == 4.0 );
		assertTrue( Calculator.calculate( "(3 + 2) / 2" ) == 2.5 );
	}

	@Test public void testOrderOfOperations(){
		LoggingTool.print( "\n*********************\ntestOrderOfOperations\n*********************" );
		assertTrue( Calculator.calculate( "2 / 2 + 3 - 4 * 10 ( 10 - 9 * 7 ) ^ 4 - 2" ) == -315619238 );
	}

}
