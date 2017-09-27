import eaglezr.support.logs.LoggingTool;

public class Calculator {

	public static double calculate( String equation ) {
		double ans = internalCalculate( process( equation ) );
		LoggingTool.print( equation + " = " + ans + "\n" );
		return ans;
	}

	private static String process( String equation ) {
		LoggingTool.print( "Processing equation: \"" + equation + "\"." );
		String processedEquation = equation;
		for ( int i = 0; i < processedEquation.length(); i++ ) {
			if ( processedEquation.charAt( i ) == '(' ) {
				LoggingTool.print( "Found a '(' at index " + i + ". Will search for preceding operations token." );
				for ( int u = i - 1; u > 0; u-- ) {
					if ( processedEquation.charAt( u ) != ' ' ) {
						char charToken = processedEquation.charAt( u );
						if ( charToken != '+' && charToken != '-' && charToken != '*' && charToken != '/'
								&& charToken != '^' ) {
							LoggingTool.print( "Preceding operator not found. Inserting a '*'." );
							processedEquation =
									processedEquation.substring( 0, u + 1 ) + " * " + processedEquation.substring( i );
						} else {
							LoggingTool.print( "Preceding operator found." );
						}
						u = -1;
					}
				}
			}
		}
		LoggingTool.print( "Returning processed equation: \"" + processedEquation + "\"." );
		return processedEquation;
	}

	private static double internalCalculate( String equation ) {
		LoggingTool.print( "Evaluating Formula: \"" + equation + "\"." );
		// Using PEMDAS (i.e. Parentheses, Exponents, Multiplication, Division, Addition, Subtraction

		/////////////////
		// Parentheses
		/////////////////
		for ( int i = 0; i < equation.length(); i++ ) {
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
		}

		/////////////////
		// Exponents
		/////////////////
		for ( int i = 0; i < equation.length(); i++ ) {
			if ( equation.charAt( i ) == '^' ) {
				LoggingTool.print( "Found a '^' at index " + i + "." );
				// Find the start of the prior number
				double priorNumber = Double.parseDouble( equation.substring( 0, i ) );
				double followingNumber = Double.parseDouble( equation.substring( i + 1 ) );

				return internalCalculate( equation.substring( 0, i - Double.toString( priorNumber ).length() ) + Math
						.pow( priorNumber, followingNumber ) + equation
						.substring( i + Double.toString( followingNumber ).length() ) );
			}
		}

		/////////////////
		// Multiplication
		/////////////////
		for ( int i = 0; i < equation.length(); i++ ) {
			if ( equation.charAt( i ) == '*' ) {
				LoggingTool.print( "Found a '*' at index " + i + "." );
				return multiply( equation.substring( 0, i ), equation.substring( i + 1 ) );
			}
		}

		/////////////////
		// Division
		/////////////////
		for ( int i = 0; i < equation.length(); i++ ) {
			if ( equation.charAt( i ) == '/' && equation.length() > i + 1 && equation.charAt( i + 1 ) == ' ' ) {
				LoggingTool.print( "Found a '/' at index " + i + "." );
				return divide( equation.substring( 0, i ), equation.substring( i + 1 ) );
			}
		}

		/////////////////
		// Addition
		/////////////////
		for ( int i = 0; i < equation.length(); i++ ) {
			if ( equation.charAt( i ) == '+' ) {
				LoggingTool.print( "Found a '+' at index " + i + "." );
				return add( equation.substring( 0, i ), equation.substring( i + 1 ) );
			}
		}

		/////////////////
		// Subtraction
		/////////////////
		for ( int i = 0; i < equation.length(); i++ ) {
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

	private static String findNextToken( String s ) {
		s = s.trim();
		LoggingTool.print( "Looking for the next token in \"" + s + "\"." );
		return findToken( s, 1 );
	}

	private static String findPriorToken( String s ) {
		s = s.trim();
		LoggingTool.print( "Looking for the prior token in \"" + s + "\"." );
		return findToken( s, -1 );
	}

	private static String findToken( String s, int direction ) {
		int startToken;
		int endToken = -1;
		if ( direction == 1 ) {
			startToken = 0;
		} else if ( direction == -1 ) {
			startToken = s.length() - 1;
		} else {
			throw new IndexOutOfBoundsException( "The findToken method was passed an invalid direction: " + direction );
		}

		for ( int i = startToken; i >= 0 && i < s.length(); i += direction ) {
			if ( s.charAt( i ) == ' ' || i == 0 || i == s.length() - 1 ) {
				endToken = i;
				i = -1;
			}
		}

		if ( endToken == -1 ) {
			throw new IndexOutOfBoundsException( "The token could not be found." );
		}

		return s.substring( Math.min( startToken, endToken ), Math.max( startToken, endToken ) );
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

	private static double multiply( String left, String right ) {
		left = left.trim();
		right = right.trim();

		LoggingTool.print( "Multiplying \"" + left + "\" and \"" + right + "\"." );

		boolean leftIsNumber = isNumber( left );
		boolean rightIsNumber = isNumber( right );

		if ( !leftIsNumber && !rightIsNumber ) {
			LoggingTool.print( "Neither \"" + left + "\" nor \"" + right
					+ "\" are numbers, and are being sent for further calculation before being multiplied." );
			return multiply( internalCalculate( left ) + "", internalCalculate( right ) + "" );
		} else if ( !leftIsNumber ) {
			LoggingTool.print( "\"" + left
					+ "\" is not a number, and is being sent for further calculation before being multiplied to \""
					+ right + "\"." );
			return multiply( internalCalculate( left ) + "", right );
		} else if ( !rightIsNumber ) {
			LoggingTool.print( "\"" + right
					+ "\" is not a number, and is being sent for further calculation before being multiplied to \""
					+ left + "\"." );
			return multiply( left, internalCalculate( right ) + "" );
		} else {
			LoggingTool.print( "\"" + left + "\" and \"" + right + "\" are both numbers, and will be multiplied." );
			return Double.parseDouble( left ) * Double.parseDouble( right );
		}
	}

	private static double divide( String left, String right ) {
		left = left.trim();
		right = right.trim();

		LoggingTool.print( "Dividing \"" + right + "\" from \"" + left + "\"." );

		boolean leftIsNumber = isNumber( left );
		boolean rightIsNumber = isNumber( right );

		if ( !leftIsNumber && !rightIsNumber ) {
			LoggingTool.print( "Neither \"" + left + "\" nor \"" + right
					+ "\" are numbers, and are being sent for further calculation before being divided." );
			return divide( internalCalculate( left ) + "", internalCalculate( right ) + "" );
		} else if ( !leftIsNumber ) {
			LoggingTool.print( "\"" + left
					+ "\" is not a number, and is being sent for further calculation before being divided by \"" + right
					+ "\"." );
			return divide( internalCalculate( left ) + "", right );
		} else if ( !rightIsNumber ) {
			LoggingTool.print( "\"" + right
					+ "\" is not a number, and is being sent for further calculation before being divided from \""
					+ left + "\"." );
			return divide( left, internalCalculate( right ) + "" );
		} else {
			LoggingTool.print( "\"" + left + "\" and \"" + right + "\" are both numbers, and will be divided." );
			return Double.parseDouble( left ) / Double.parseDouble( right );
		}
	}
}
