import eaglezr.support.logs.LoggingTool;
import sun.rmi.runtime.Log;

public class Calculator {

	public static double calculate( String equation ) {
		double ans = internalCalculate( equation );
		LoggingTool.print( equation + " = " + ans + "\n" );
		return ans;
	}

	private static double internalCalculate( String equation ) {
		LoggingTool.print( "Evaluating Formula: \"" + equation + "\"." );
		// Using PEMDAS (i.e. Parentheses, Exponents, Multiplication, Division, Addition, Subtraction

		for ( int i = 0; i < equation.length(); i++ ) {
			/////////////////
			// Parentheses
			/////////////////
			if ( equation.charAt( i ) == '(' ) {
				LoggingTool.print( "Found a '(' at index: " + i + "." );
				for ( int u = i; u < equation.length(); u++ ) {
					if ( equation.charAt( u ) == ')' ) {
						LoggingTool.print( "Found a ')' at index: " + u );
						return internalCalculate(
								equation.substring( 0, i ) + internalCalculate( equation.substring( i + 1, u ) )
										+ equation.substring( u + 1 ) );

					}
				}
				throw new NumberFormatException( "Parentheses error" );
			}

			/////////////////
			// Exponents
			/////////////////
			if ( equation.charAt( i ) == '^' ) {
				LoggingTool.print( "Found a '^' at index " + i + "." );
				// Find the start of the prior number
				for ( int u = i - 2; u >= 0; u-- ) {
					double priorNumber;
					if ( equation.charAt( u ) == ' ' || u == 0 ) {
						if ( isNumber( equation.substring( u, i ) ) ) {
							priorNumber = Double.parseDouble( equation.substring( u, i ) );
							// Find the end of the following number
							for ( int x = i; x < equation.length(); x++ ) {
								double followingNumber;
								if ( equation.charAt( x ) == ' ' || x == equation.length() - 1 ) {
									if ( isNumber( equation.substring( i + 1, x ) ) ) {
										followingNumber = Double.parseDouble( equation.substring( i + 1, x ) );
										LoggingTool.print( "Evaluating \"" + priorNumber + " ^ " + followingNumber
												+ "\"." );
										return Math.pow( priorNumber, followingNumber );
									} else {
										throw new NumberFormatException( "Exponent Error 0" );
									}
								}
							}
						} else {
							throw new NumberFormatException( "Exponent error 1" );
						}
					}
				}
				throw new NumberFormatException( "Exponent error 2" );
			}
			/////////////////
			// Multiplication
			/////////////////

			/////////////////
			// Division
			/////////////////

			/////////////////
			// Addition
			/////////////////
			if ( equation.charAt( i ) == '+' ) {
				LoggingTool.print( "Found a '+' at index " + i + "." );
				return add( equation.substring( 0, i ), equation.substring( i + 1 ) );
			}

			/////////////////
			// Subtraction
			/////////////////
			if ( equation.charAt( i ) == '-' && equation.length() > i + 1 && equation.charAt( i + 1 ) == ' ' ) {
				LoggingTool.print( "Found a '-' at index " + i + "." );
				return subtract( equation.substring( 0, i ), equation.substring( i + 1 ) );
			}
		}

		/////////////////
		// Not a formula
		/////////////////
		if (

				isNumber( equation ) )

		{
			LoggingTool.print( "\"" + equation
					+ "\" was not evaluated by any of the PEMDAS loops, and was turned into a number." );
			return Double.parseDouble( equation );
		} else

		{
			LoggingTool.print( "\"" + equation
					+ "\" was not evaluated by any of the PEMDAS loops, and was attempted to be turned into a number, but it was not a number." );
			throw new NumberFormatException( "End of PEMDAS error" );
		}

	}

	/**
	 * Determines if the passed string can be parsed into a number.
	 *
	 * @param s
	 * @return
	 */
	private static boolean isNumber( String s ) {
		LoggingTool.print( "Determining if \"" + s + "\" is a number." );
		s = s.trim();

		if ( s.equals( "" ) ) {
			LoggingTool.print( "\"\" is not a number." );
			return false;
		}

		int startAt = 0;

		if ( s.charAt( 0 ) == '-' ) {
			startAt = 1;
		}

		int decimalCount = 0;

		for ( int i = startAt; i < s.length(); i++ ) {
			if ( s.charAt( i ) == '.' ) {
				decimalCount++;
				if ( decimalCount > 1 ) {
					LoggingTool.print( "\"" + s + "\" is not a number." );
					return false;
				}
			} else if ( ( s.charAt( i ) > '9' || s.charAt( i ) < '0' ) ) {
				LoggingTool.print( "\"" + s + "\" is not a number." );
				return false;
			}
		}
		LoggingTool.print( "\"" + s + "\" is a number." );
		return true;
	}

	/**
	 * Adds the two strings together.
	 *
	 * @param left
	 * @param right
	 * @return
	 */
	private static double add( String left, String right ) {
		left = left.trim();
		right = right.trim();

		LoggingTool.print( "Adding \"" + left + "\" and \"" + right + "\"." );

		boolean leftIsNumber = isNumber( left );
		boolean rightIsNumber = isNumber( right );

		if ( !leftIsNumber && !rightIsNumber ) {
			LoggingTool.print( "Neither \"" + left + "\" nor \"" + right
					+ "\" are numbers, and are being sent for further calculation before being added." );
			return add( internalCalculate( left ) + "", internalCalculate( right ) + "" );
		} else if ( !leftIsNumber ) {
			LoggingTool.print( "\"" + left
					+ "\" is not a number, and is being sent for further calculation before being added to \"" + right
					+ "\"." );
			return add( internalCalculate( left ) + "", right );
		} else if ( !rightIsNumber ) {
			LoggingTool.print( "\"" + right
					+ "\" is not a number, and is being sent for further calculation before being added to \"" + left
					+ "\"." );
			return add( left, internalCalculate( right ) + "" );
		} else {
			LoggingTool.print( "\"" + left + "\" and \"" + right + "\" are both numbers, and will be added." );
			return Double.parseDouble( left ) + Double.parseDouble( right );
		}
	}

	/**
	 * Subtracts the right string from the left string.
	 *
	 * @param left
	 * @param right
	 * @return
	 */
	private static double subtract( String left, String right ) {
		left = left.trim();
		right = right.trim();

		LoggingTool.print( "Subtracting \"" + right + "\" from \"" + left + "\"." );

		boolean leftIsNumber = isNumber( left );
		boolean rightIsNumber = isNumber( right );

		if ( !leftIsNumber && !rightIsNumber ) {
			LoggingTool.print( "Neither \"" + left + "\" nor \"" + right
					+ "\" are numbers, and are being sent for further calculation before being subtracted." );
			return subtract( internalCalculate( left ) + "", internalCalculate( right ) + "" );
		} else if ( !leftIsNumber ) {
			LoggingTool.print( "\"" + left
					+ "\" is not a number, and is being sent for further calculation before being subtracted by \""
					+ right + "\"." );
			return subtract( internalCalculate( left ) + "", right );
		} else if ( !rightIsNumber ) {
			LoggingTool.print( "\"" + right
					+ "\" is not a number, and is being sent for further calculation before being subtracted from \""
					+ left + "\"." );
			return subtract( left, internalCalculate( right ) + "" );
		} else {
			LoggingTool.print( "\"" + left + "\" and \"" + right + "\" are both numbers, and will be subtracted." );
			return Double.parseDouble( left ) - Double.parseDouble( right );
		}
	}
}
