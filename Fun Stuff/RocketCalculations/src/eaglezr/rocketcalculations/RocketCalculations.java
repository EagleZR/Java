package eaglezr.rocketcalculations;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Mark Zeagler
 *         <p>
 *         This is a fun program I'm making to learn a little more about
 *         rocketry and to make myself a little tool for KSP and my own hobbies.
 */

public class RocketCalculations {
	
	public static void main( String[] args ) {
		int selection = 0;
		Scanner input = new Scanner( System.in );
		
		do {
			try {
				// @formatter:off
				System.out.print( "Please select an application to run.\n"
				+ "1. Delta-v calculator\n"
				+ "2. Thrust calculator\n"
				// + "3. Optimal Payload Calculation\n"
				// + "4. Gravity calculator"
				+ "8. Exit\n" // lol, room to grow
				+ ": " );
				// @formatter:on
				selection = input.nextInt();
				if ( selection == 1 ) {
					deltaVInterface( input );
				} else if ( selection == 2 ) {
					thrustInterface( input );
				} else if ( selection == 3 ) {
					boosterPayloadMassInterface( input );
				} else if ( selection == 4 ) {
					// TODO add method call
				}
				else if ( selection == 8 ) {
					System.out.println( "Exiting program..." );
				} else {
					System.err.println( "An invalid value has been entered. Please try again." );
				}
			} catch ( InputMismatchException e ) {
				System.err.println( "An invalid value has been entered. Please start over." );
			}
		} while ( selection != 4 );
	}
	
	// 1. Delta-v Calculator
	/**
	 * Accepts user inputs and interacts with the deltaVCalculator method to
	 * perform the actual calculation.
	 * 
	 * @param input
	 *            - The scanner input is passed around to minimize resources.
	 */
	private static void deltaVInterface( Scanner input ) {
		double startMass;
		double endMass;
		double isp;
		double exhaustVelocity;
		double deltaV;
		
		System.out.println( "Please enter the starting (full) mass in kg: " );
		startMass = input.nextDouble();
		System.out.println( "Please enter the final (empty) mass in kg: " );
		endMass = input.nextDouble();
		System.out.println( "Please enter the specific impulse of the engine in seconds: " );
		isp = input.nextDouble();
		exhaustVelocity = exhaustVelocityCalculator( isp );
		deltaV = deltaVCalculator( startMass, endMass, exhaustVelocity );
		System.out.printf( "The total delta-v of the rocket is %.3f\n\n", deltaV );
	}
	
	/**
	 * Calculates the total delta-v using the given exhaust velocity and the
	 * starting and final masses (difference in mass of the propellant used).
	 * This assumes a static specific impulse.
	 * 
	 * @param startMass
	 *            - The "full" mass of the rocket, including the mass to be
	 *            propelled and the full mass of the propellant.
	 * @param endMass
	 *            - The "empty" mass of the rocket. Only includes the propelled
	 *            mass of the rocket (including empty fuel tanks), but does not
	 *            include the mass of the expelled propellant.
	 * @param exhaustVelocity
	 *            - The velocity of the propellant exiting the combustion
	 *            chamber.
	 * @return - The total delta-v in m/s of the rocket.
	 */
	private static double deltaVCalculator( double startMass, double endMass, double exhaustVelocity ) {
		return exhaustVelocity * Math.log( startMass / endMass );
	}
	
	/**
	 * I honestly thought this was a more difficult calculation... It's
	 * literally just the specific impulse * earth's gravity acceleration...
	 * <p>
	 * Probably handy to keep around, though.
	 * 
	 * @param isp
	 *            - The specific impulse of the engine.
	 * @return - The calculated effective exhaust velocity of the engine.
	 */
	private static double exhaustVelocityCalculator( double isp ) {
		double earthsGravity = 9.081;
		return isp * earthsGravity;
	}
	
	// 2. Thrust calculator
	private static void thrustInterface( Scanner input ) {
		double thrust;
		double massFlowRate;
		double isp;
		double effectiveExhaustVelocity;
		
		System.out.println( "Please enter the mass flow rate of the engine: " );
		massFlowRate = input.nextDouble();
		System.out.println( "Please enter the specific impulse of the engine: " );
		isp = input.nextDouble();
		effectiveExhaustVelocity = exhaustVelocityCalculator( isp );
		thrust = thrustCalculator( massFlowRate, effectiveExhaustVelocity );
		
		System.out.printf( "The thrust of the engine is %.2f\n\n", thrust );
	}
	
	private static double thrustCalculator( double massFlowRate, double effectiveExhaustVelocity ) {
		return massFlowRate * effectiveExhaustVelocity;
	}
	
	// 3. Booster payload calculator
	private static void boosterPayloadMassInterface( Scanner input ) {
		double twr;
		double thrust;
		
		System.out.println( "Please input the desired Thrust-to-Weight ratio: " );
		twr = input.nextDouble();
	}

	// 4. Gravity calculator 
	
}
