
package eaglezr.physics;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The {@link BigNumber} class is a device made for data values too large and
 * too precise for the traditional data types like {@link long} or {@link float}
 * .
 * <p>
 * The structure is such that the {@link KDig} holds the data value, and it's
 * position in the overall number. The {@link KDigList} holds each individual
 * {@link KDig}, and helps organize it. The {@link BigNumber} holds the single
 * {@link KDigList} and all of the methods that a {@link Number} derived class
 * would need.
 * <p>
 * This was developed to hold numbers of extreme value in a way that maintains
 * incredible precision. One example would be to hold the distance between the
 * sun and Alpha Centauri in millimeters. There is no practical reason to use
 * the {@link BigNumber} class in normal programs, as such precision is rarely
 * necessary. However, this was developed for those few times where it would be
 * useful.
 * <p>
 * The philosophy is that math can be performed on individual segments of a
 * large number if those segments are recombined correctly. An example of this
 * is long division, where the division is calculated using only one digit of
 * the divisor at a time. This principle is extended to the other math
 * operations.
 * 
 * @author Mark Zeagler
 */
public class BigNumber extends Number implements Comparable<BigNumber>, Cloneable {
	
	/**
	 * Auto generated Serial ID.
	 */
	private static final long serialVersionUID = 6416462081323427521L;
	/**
	 * This is the set size of the individual {@link KDig}s. This was originally
	 * set to 3, but this was determined to be a waste of memory. I've set them
	 * to 5 for now, since {@link Integer.MAX_VALUE} is 10 places. This may be
	 * lowered if data is lost.
	 */
	private static final int kDigSize = 5;
	/**
	 * This is the maximum value to be stored by the {@link KDig}. It is
	 * calculated from the {@link kDigSize} value.
	 */
	private static final int kDigSizeValue = (int) Math.pow( 10, kDigSize + 1 ) - 1;
	/**
	 * This will enable the occasional {@link System.out.println()} statements
	 * to check when something is going on.
	 * <p>
	 * NOTE: This is a development tool that will be removed from the final
	 * version, along with the print statements.
	 */
	private static final boolean debugging = true;
	
	/**
	 * This is a small storage device for use in the {@link BigNumber} class.
	 * <p>
	 * The structure is such that the {@link KDig} holds the data value, and
	 * it's position in the overall number. The {@link KDigList} holds each
	 * individual {@link KDig}, and helps organize it. The {@link BigNumber}
	 * holds the single {@link KDigList} and all of the methods that a
	 * {@link Number} derived class would need.
	 * <p>
	 * This was developed to hold numbers of extreme value in a way that
	 * maintains incredible precision. One example would be to hold the distance
	 * between the sun and Alpha Centauri in millimeters. There is no practical
	 * reason to use the {@link BigNumber} class in normal programs, as such
	 * precision is rarely necessary. However, this was developed for those few
	 * times where it would be useful.
	 * 
	 * @author Mark Zeagler
	 *
	 */
	protected class KDig implements Serializable {
		/**
		 * Auto generated Serial ID.
		 */
		private static final long serialVersionUID = 902581646911899938L;
		/**
		 * The integer value stored by this {@link KDig}. The size of this value
		 * is controlled by the {@link kDigSize} and {@link kDigSizeValue}
		 * values.
		 */
		protected int value = 0;
		// TODO reword Javadoc
		/**
		 * The position that the {@link KDig} has in the {@link KDigList}. This
		 * has been added as a precaution, since it's possible that 0s might not
		 * be included in the {@link KDigList}.
		 */
		protected short exponent = 0;
		/**
		 * The next {@link KDig} in the {@link KDigList}.
		 * {@link KDig.next.exponent} is smaller in value than the corresponding
		 * value in the current {@link KDig} in an ordered {@link KDigList}.
		 */
		protected KDig next;
		/**
		 * The preceding {@link KDig} in the {@link KDigList}.
		 * {@link KDig.previous.exponent} is greater in value than the
		 * corresponding value in the current {@link KDig} in an ordered
		 * {@link KDigList}.
		 */
		protected KDig previous;
		
		protected int sendUp = 0;
		protected double sendDown = 0.0;
		
		protected KDig() {
			
		}
		
		protected KDig( int value, short exponent ) {
			this.value = value;
			this.exponent = exponent;
		}
		
		/**
		 * Processes the current KDig to make sure that it is not >=1,000.
		 * Places values >=1,000 in sendUp and values <1 in sendDown
		 */
		protected void preprocess() {
			double tempValue = this.value;
			if ( debugging ) {
				System.out.println( "Begin processing KDig: " + this.exponent );
			}
			
			// 1st pulls the KDig.sendUp and KDig.sendDown from this.next and
			// this.previous and adds
			// to this.tempValue
			if ( this.next != null ) {
				tempValue += ( this.next.sendDown * kDigSizeValue );
				this.next.sendDown = 0;
			}
			if ( this.previous != null ) {
				tempValue += ( this.previous.sendUp / kDigSizeValue );
				this.previous.sendUp = 0;
			}
			if ( debugging ) {
				System.out.println( "A tempValue has been set at " + tempValue );
			}
			
			// Takes everything that is greater than 1,000 and sends to
			// this.sendUp
			double temp = tempValue % kDigSizeValue;
			this.sendUp = (int) ( tempValue - temp );
			// Takes everything less than 1 and sends to this.sendDown
			double newTemp = (int) temp;
			this.sendDown = temp - newTemp;
			if ( debugging ) {
				System.out.println( "End processing KDig: " + this.exponent + "\n" );
			}
		}
		
		protected void sendUps() {
			// Takes appropriate actions for KDigs that will be affected.
			// If there is no KDig, but there needs to be, a new one is created.
			if ( sendUp != 0 && this.next.equals( null ) ) { // Is this right?
				this.next = new KDig( sendUp, ++this.exponent );
				if ( debugging ) {
					System.out.println(
							"There is no \"this.next\", so a new KDig was created with the value: " + this.sendUp );
				}
				this.next.isModded = true;
			} else if ( sendUp != 0 && !this.next.equals( null ) ) {
				this.next.isModded = true;
				if ( debugging ) {
					System.out.println( this.sendUp + " is being sent up to the next KDig." );
				}
			}
		}
		
		protected void sendDowns() {
			if ( sendDown != 0 && this.previous.equals( null ) ) {
				this.previous = new KDig( 0, --this.exponent );
				// TODO find out how to fix KDigList.first && KDigList.last
				this.previous.isModded = true;
				if ( debugging ) {
					System.out.println( "There is no \"this.previous\", so a new KDig was created with the value: "
							+ this.sendDown );
				}
			} else if ( sendDown != 0 && !this.previous.equals( null ) ) {
				this.previous.isModded = true;
				if ( debugging ) {
					System.out.println( this.sendDown + " is being sent down to the next KDig." );
				}
			}
		}
		
		/*
		 * public double[] process() { double temp = 0; if (this.previous !=
		 * null) { temp = this.previous.value * 1000; } temp += this.tempValue;
		 * double newCurrVal = (int) temp % 1000; int newPrevVal = (int) (temp -
		 * newCurrVal); newPrevVal = newPrevVal / 1000; this.value = (int)
		 * newCurrVal; this.tempValue = this.value; double newNextVal =
		 * this.value - newCurrVal; newNextVal = newNextVal * 1000;
		 * 
		 * double[] returnArray = { 0, 0 }; if (this.previous != null) {
		 * this.previous.value = newPrevVal; } else { returnArray[0] =
		 * newPrevVal; }
		 * 
		 * if (this.next != null) { this.next.tempValue += newNextVal; } else {
		 * returnArray[1] = newNextVal; }
		 * 
		 * return returnArray; }
		 */
		
		protected KDig copy() {
			return new KDig( this.value, this.exponent );
		}
		
		public String toString() {
			String returnString = "";
			if ( this.exponent == -1 ) {
				returnString += ".";
			}
			if ( this.previous != null ) {
				if ( Math.abs( this.value ) < 100 ) {
					returnString += "0";
				}
				if ( Math.abs( this.value ) < 10 ) {
					returnString += "0";
				}
			}
			return returnString += Math.abs( this.value );
		}
		
		protected int getValue() {
			process();
			return this.value;
		}
		
	}
	
	/**
	 * This is a storage and organization structure used to arrange {@link KDig}
	 * objects in the {@link BigNumber} class.
	 * <p>
	 * The structure is such that the {@link KDig} holds the data value, and
	 * it's position in the overall number. The {@link KDigList} holds each
	 * individual {@link KDig}, and helps organize it. The {@link BigNumber}
	 * holds the single {@link KDigList} and all of the methods that a
	 * {@link Number} derived class would need.
	 * <p>
	 * This was developed to hold numbers of extreme value in a way that
	 * maintains incredible precision. One example would be to hold the distance
	 * between the sun and Alpha Centauri in millimeters. There is no practical
	 * reason to use the {@link BigNumber} class in normal programs, as such
	 * precision is rarely necessary. However, this was developed for those few
	 * times where it would be useful.
	 * 
	 * @author Mark Zeagler
	 *
	 */
	protected class KDigList implements Serializable {
		
		private static final long serialVersionUID = -2151937743871241439L;
		
		protected KDig onesPlace;
		protected KDig first;
		protected KDig last;
		protected ArrayList<KDig> isModded = new ArrayList<KDig>();
		// public boolean isPositive = true;
		
		protected KDigList() {
			
		}
		
		protected KDigList( KDigList list ) {
			this.onesPlace = list.onesPlace;
			this.first = list.first;
			this.last = list.last;
		}
		
		/**
		 * Adds a KDig onto the end.
		 * 
		 * @param newKDig
		 *            - The KDig to be added.
		 */
		protected void add( KDig newKDig ) {
			if ( isEmpty() ) {
				this.first = newKDig;
				this.last = newKDig;
			} else {
				this.last.next = newKDig;
				newKDig.previous = this.last;
				this.last = newKDig;
			}
		}
		
		/**
		 * Removes the indicated KDig from the list and repairs the hole.
		 * 
		 * @param oldKDig
		 *            - The KDig to be removed.
		 */
		protected void remove( KDig oldKDig ) {
			KDig nextKDig = oldKDig.next;
			KDig prevKDig = oldKDig.previous;
			if ( nextKDig != null ) {
				nextKDig.previous = prevKDig;
			} else {
				this.last = prevKDig;
			}
			if ( prevKDig != null ) {
				prevKDig.next = nextKDig;
			} else {
				this.first = nextKDig;
			}
		}
		
		/**
		 * Places the KDigList in order by exponent.
		 */
		protected void order() {
			// If already in order, will return with no action to save time
			if ( !checkOrder( this.first ) ) {
				KDig newFirst = findHighest();
				remove( newFirst );
				order( newFirst );
				this.first = newFirst;
			}
		}
		
		// Creates and returns a new string of KDigs in order by exponents
		private void order( KDig currKDig ) {
			if ( this.first != null ) {
				KDig next = findHighest();
				remove( next );
				currKDig.next = next;
				next.previous = currKDig;
				order( next );
			}
		}
		
		// Inserts a KDig between 2 other KDigs. Assumes the 2 other KDigs are
		// in sequence
		private void insert( KDig newKDig, KDig prevKDig ) {
			KDig nextKDig = prevKDig.next;
			if ( nextKDig != null ) {
				nextKDig.previous = newKDig;
				newKDig.next = nextKDig;
				prevKDig.next = newKDig;
				newKDig.previous = prevKDig;
			} else {
				add( newKDig );
			}
		}
		
		private void insertBefore( KDig newKDig, KDig nextKDig ) {
			newKDig.next = nextKDig;
			nextKDig.previous = newKDig;
			if ( first == nextKDig ) {
				first = newKDig;
			}
		}
		
		/**
		 * Checks to see if there are any KDigs in the KDig list
		 * 
		 * @return true if the KDigList is empty.
		 */
		protected boolean isEmpty() {
			return this.first == null;
		}
		
		// Helper method to return the KDig with the highest exponent
		private KDig findHighest() {
			return findHighest( first, first );
		}
		
		// Returns the KDig with the highest exponent
		private KDig findHighest( KDig topKDig, KDig currKDig ) {
			if ( currKDig != null ) {
				// Assuming that there will never be duplicate exponents...
				if ( currKDig.exponent > topKDig.exponent ) {
					return findHighest( currKDig, currKDig.next );
				} else {
					return findHighest( topKDig, currKDig.next );
				}
			} else {
				return topKDig;
			}
		}
		
		// Checks to see if the KDigs are in order by exponent already.
		private boolean checkOrder( KDig currKDig ) {
			if ( currKDig.exponent == 1 ) {
				this.onesPlace = currKDig;
			}
			if ( currKDig.next != null ) {
				if ( currKDig.exponent > currKDig.next.exponent ) {
					return checkOrder( currKDig.next );
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
		
		/**
		 * Adds 0s for formatting. Do not run this if the list has not been
		 * ordered.
		 */
		protected void addZeros() {
			// order();
			// if (this.first != null) {
			// if (first.exponent > 0) {
			// addZerosPositive(first);
			// } else {
			// addZerosNegative(first);
			// addZerosPositive(first);
			// }
			// }
		}
		
		// Inserts Zeros between greater exponents and lesser exponents, and
		// greater exponents and 0.
		private void addZerosPositive( KDig currKDig ) {
			if ( currKDig.next != null && currKDig.next.exponent > 0 ) {
				if ( currKDig.exponent - currKDig.next.exponent > 1 ) {
					insert( new KDig( 0, currKDig.exponent - 1 ), currKDig );
				}
				addZerosPositive( currKDig.next );
			} else if ( currKDig.exponent > 1 ) {
				insert( new KDig( 0, currKDig.exponent - 1 ), currKDig );
			}
		}
		
		// Inserts Zeros when this.first.exponent for situations when there are
		// no positive exponents
		private void addZerosNegative( KDig currKDig ) {
			if ( currKDig.exponent < -1 ) {
				insertBefore( new KDig( 0, currKDig.exponent + 1 ), currKDig );
				addZerosNegative( currKDig.previous );
			}
		}
		
		/**
		 * Returns the KDig with the specified exponent.
		 * 
		 * @param exponent
		 *            The exponent of the KDig to be retrieved.
		 * @return The KDig with the specified exponent. If there is no KDig, it
		 *         will create an empty KDig with null values.
		 */
		protected KDig get( int exponent ) {
			order();
			if ( exponent > this.first.exponent || exponent < this.last.exponent ) {
				return null;
			} else {
				return find( exponent, this.first );
			}
		}
		
		// Returns the KDig with the specified exponent.
		private KDig find( int exponent, KDig currKDig ) { // TODO test this
			int u = currKDig.exponent - exponent;
			for ( int i = 0; i < u; i++ ) {
				currKDig = currKDig.next;
			}
			return currKDig;
		}
		
		/**
		 * Turns the local listing of KDigs into a String.
		 */
		public String toString() {
			order();
			String returnString = "";
			if ( this.first.value < 0 ) {
				returnString += "-";
			}
			if ( this.first.exponent == -1 ) {
				returnString += "0";
			}
			returnString += toString( this.first );
			return returnString;
		}
		
		// Recursively adds each KDig to the string
		private String toString( KDig currKDig ) {
			if ( currKDig != null ) {
				return currKDig.toString() + toString( currKDig.next );
			} else {
				return "";
			}
		}
		
		protected KDigList copy() {
			KDigList copy = new KDigList();
			copy.first = this.first.copy();
			return copy( first, copy );
		}
		
		private KDigList copy( KDig curr, KDigList copy ) {
			if ( curr.equals( this.last ) ) {
				return copy;
			} else {
				copy.add( curr.next.copy() );
				return copy( curr.next, copy );
			}
		}
		
		// TODO This is a complete CF. In order for one KDig to process another
		// KDig
		// without causing a stack overflow, the process method must end after
		// running, instead of automatically calling the next process method
		protected void process() {
			System.out.println( "The KDigList is processing..." );
			while ( checkModded() ) {
				this.isModded.remove( 0 ).process();
			}
		}
		
		protected boolean checkModded() {
			KDig curr = this.first;
			while ( curr != last ) {
				if ( curr.isModded && !this.isModded.contains( curr ) ) {
					this.isModded.add( curr );
				}
				curr = curr.next;
			}
			return !this.isModded.isEmpty();
		}
		
	}
	
	// ArrayList<KDig> number = new ArrayList<KDig>();
	private KDigList number = new KDigList();
	
	/**
	 * 
	 */
	public BigNumber() {
		
	}
	
	/**
	 * Creates a BigNumber from the given integer. Useful for turning smaller
	 * integers into BigNumbers, especially for use in equations.
	 * 
	 * @param number
	 *            The value to be turned into a BigNumber.
	 */
	public BigNumber( int number ) {
		this( Integer.toString( number ) + ".0" );
	}
	
	/**
	 * Creates a BigNumber from the given String. This is useful when the number
	 * value is too large to be stored as a primitive data type.
	 * 
	 * @param number
	 *            The value to be turned into a BigNumber. Can only use '0-9',
	 *            '-'(negative), ','(comma), '^'(up carrot for square roots),
	 *            '*'(asterisk for multiplication), and '.'(decimal).
	 */
	public BigNumber( String number ) {
		// System.out.println(number);
		boolean isNegative = false;
		if ( number.charAt( 0 ) == '-' ) {
			isNegative = true;
			number = number.substring( 1 );
		}
		
		int decimal = number.indexOf( '.' );
		String left = number.substring( 0, decimal );
		ArrayList<Integer> leftArray = new ArrayList<Integer>();
		String right = number.substring( decimal + 1 );
		ArrayList<Integer> rightArray = new ArrayList<Integer>();
		
		KDigList numberBuild = new KDigList();
		
		// LEFT
		// Extract from String into ArrayList
		while ( !left.isEmpty() ) {
			int index = 0;
			String tempString = "";
			while ( index < kDigSize && !left.isEmpty() ) {
				tempString += left.charAt( left.length() - 1 );
				left = left.substring( 0, left.length() - 1 );
				index++ ;
			}
			
			String addString = "";
			while ( !tempString.isEmpty() ) {
				addString += tempString.charAt( tempString.length() - 1 );
				tempString = tempString.substring( 0, tempString.length() - 1 );
			}
			
			leftArray.add( Integer.parseInt( addString ) );
		}
		
		// Extract from ArrayList to KDigList
		while ( !leftArray.isEmpty() ) {
			KDig newKDig = new KDig( leftArray.get( leftArray.size() - 1 ), (short) ( leftArray.size() - 1 ) );
			numberBuild.add( newKDig );
			leftArray.remove( leftArray.size() - 1 );
		}
		
		// RIGHT
		// Extract from String to ArrayList
		while ( !right.isEmpty() ) {
			int stop = 3;
			int index = 0;
			String addString = "";
			while ( index < stop && !right.isEmpty() ) {
				addString += right.charAt( 0 );
				right = right.substring( 1 );
				index++ ;
			}
			
			int remainder = stop - addString.length();
			for ( int i = 0; i < remainder; i++ ) {
				addString += "0";
			}
			
			rightArray.add( Integer.parseInt( addString ) );
		}
		
		// Extract from ArrayList to KDigList
		int size = -1;
		while ( !rightArray.isEmpty() ) {
			KDig newKDig = new KDig( rightArray.get( 0 ), (short) size );
			numberBuild.add( newKDig );
			size-- ;
			rightArray.remove( 0 );
		}
		
		// I forgot to do this above, and this is easier than re-writing above
		if ( isNegative ) {
			KDig currKDig = numberBuild.first;
			while ( currKDig != null ) {
				currKDig.value = currKDig.value * -1;
				currKDig = currKDig.next;
			}
		}
		
		this.number = numberBuild;
		numberBuild.process();
	}
	
	/**
	 * Creates a BigNumber from a given double. Useful for turning smaller
	 * numbers with decimals into BigNumbers.
	 * 
	 * @param number
	 *            The value to be turned into a BigNumber.
	 */
	public BigNumber( double number ) {
		this( Double.toString( number ) );
	}
	
	/**
	 * Creates a BigNumber from KDigList. Usually only used when a BigNumber is
	 * creating another due to a math equation.
	 * 
	 * @param listing
	 *            The listing supplied that the BigNumber will be created from.
	 */
	protected BigNumber( KDigList listing ) {
		this.number = listing.copy();
	}
	
	public BigNumber( BigNumber number ) {
		this.number = number.number.copy();
	}
	
	// TODO This method cleans up the KDigs after math. It makes sure each
	// KDig's value is < 100.
	private void process() {
		this.number.process();
	}
	
	/*
	 * // Helper method public BigNumber squareRoot() { int exponent =
	 * this.number.first.exponent; ArrayList<Integer> brown =
	 * toArrayList(this.number); ArrayList<Integer> purple = new
	 * ArrayList<Integer>(); int orange = 0; int blue = 0; return new
	 * BigNumber(toKDigList(squareRoot(brown, purple, orange, blue), exponent));
	 * // TODO // find // a // way to // implement // this // method. }
	 * 
	 * // Loops through until no more brown private ArrayList<Integer>
	 * squareRoot(ArrayList<Integer> brown, ArrayList<Integer> purple, int
	 * orange, int blue) { if (brown.isEmpty()) { return purple; } // Pull down
	 * brown into blue blue = blue * 10 + brown.remove(0);
	 * 
	 * // Add to orange the int multiple purple that makes green just under //
	 * blue int green = 0; for (int i = 1; i < 100; i++) { green = orange + i;
	 * green = green * i; if (green > blue) { purple.add(i - 1); orange = orange
	 * + i - 1; i = 101; } }
	 * 
	 * // Multiply orange and purple to get green green = orange *
	 * purple.get(purple.size() - 1);
	 * 
	 * // Subtract green from blue blue = blue - green;
	 * 
	 * // Loop return squareRoot(brown, purple, orange * 10, blue); }
	 */
	
	/*
	 * private ArrayList<Integer> toArrayList(KDigList list) {
	 * ArrayList<Integer> arrayList = new ArrayList<Integer>(); String number =
	 * list.toString(); int breakpoint = number.indexOf("."); String left = "";
	 * String right = ""; if (breakpoint == -1) { // No decimal left = number; }
	 * else { // Decimal exists left = number.substring(0, breakpoint); right =
	 * number.substring(breakpoint + 1); } // TODO Finish turning String into
	 * ArrayList of 2-dig integers while (!left.isEmpty()) { if (left.length() %
	 * 2 == 1) { int num = Integer.parseInt(number.substring(0, 0)); number =
	 * number.substring(0, 0); arrayList.add(num); } else { int num =
	 * Integer.parseInt(number.substring(0, 1)); number = number.substring(0,
	 * 1); arrayList.add(num); } } }
	 * 
	 * private KDigList toKDigList(ArrayList<Integer> number, int exponent) { //
	 * divide the exoponent by 2, and that will be the first KDig's // exponent,
	 * then go down by 1 for ever following KDig }
	 */
	
	/**
	 * Adds the supplied BigNumber to the local BigNumber and returns a new
	 * BigNumber.
	 * 
	 * @param addend
	 *            The BigNumber to be added to the local BigNumber.
	 * @return The result as a BigNumber.
	 */
	public BigNumber add( BigNumber addend ) {
		BigNumber result = new BigNumber();
		int startExponent = 0;
		if ( this.number.first.exponent >= addend.number.first.exponent ) {
			startExponent = this.number.first.exponent;
		} else {
			startExponent = addend.number.first.exponent;
		}
		KDigList newList = add( this.number, addend.number, new KDigList(), startExponent );
		// System.out.println(newList.first);
		result = new BigNumber( newList );
		result.process();
		return result;
	}
	
	// Adds each KDig in the two KDigLists to each other and returns a new
	// KDigList.
	private KDigList add( KDigList left, KDigList right, KDigList newNumber, short currExponent ) {
		// System.out.println("hi");
		if ( left.get( currExponent ) == null && right.get( currExponent ) == null ) {
			// System.out.println("bye");
			return newNumber;
		} else if ( left.get( currExponent ) == null ) {
			// System.out.println("0 + " + right + " = " + right);
			newNumber.add( right.get( currExponent ).copy() );
		} else if ( right.get( currExponent ) == null ) {
			// System.out.println("0 + " + left + " = " + left);
			newNumber.add( left.get( currExponent ).copy() );
		} else {
			// System.out.println(left + " + " + right + " = " +
			// right.get(currExponent) + left.get(currExponent));
			newNumber.add( new KDig( right.get( currExponent ).value + left.get( currExponent ).value, currExponent ) );
		}
		return add( left, right, newNumber, --currExponent );
	}
	
	/**
	 * Subtracts the supplied BigNumber from the local BigNumber and returns a
	 * new BigNumber.
	 * 
	 * @param subtractor
	 *            The BigNumber to be subtracted from the local BigNumber.
	 * @return The result as a BigNumber.
	 */
	public BigNumber subtract( BigNumber subtractor ) {
		BigNumber result = new BigNumber();
		short startExponent = 0;
		if ( this.number.first.exponent >= subtractor.number.first.exponent ) {
			startExponent = this.number.first.exponent;
		} else {
			startExponent = subtractor.number.first.exponent;
		}
		result = new BigNumber( subtract( this.number, subtractor.number, new KDigList(), startExponent ) );
		result.process();
		return result;
	}
	
	private KDigList subtract( KDigList top, KDigList bottom, KDigList newNumber, short currExponent ) {
		if ( top.get( currExponent ) == null && bottom.get( currExponent ) == null ) { // (null
																						// -
																						// null)
			return newNumber;
		} else if ( bottom.get( currExponent ) == null ) { // (top - null)
			newNumber.add( top.get( currExponent ).copy() );
		} else if ( top.get( currExponent ) == null ) { // (null - bottom)
			newNumber.add( bottom.get( currExponent ).copy() );
			newNumber.last.value = newNumber.last.value * -1;
		} else { // (top - bottom)
			newNumber.add( new KDig( top.get( currExponent ).value - bottom.get( currExponent ).value, currExponent ) );
		}
		return subtract( top, bottom, newNumber, --currExponent );
	}
	
	public BigNumber multiply( BigNumber other ) {
		KDig curr = this.number.first;
		KDigList sum = new KDigList();
		
		while ( curr != this.number.last ) {
			sum.add( multiply( curr, other.number ) );
			curr = curr.next;
		}
		// System.out.println(sum.get(0).value);
		sum.process();
		// System.out.println(sum.get(0).value);
		return new BigNumber( sum );
	}
	
	private KDig multiply( KDig curr, KDigList number ) {
		KDig sum = new KDig( 0, curr.exponent );
		KDig numCurr = number.first;
		while ( numCurr != number.last ) {
			int correctVal = numCurr.value * 10 ^ numCurr.exponent;
			// System.out.println(curr.value + " * " + correctVal);
			int temp = ( curr.value * correctVal );
			// System.out.println(temp);
			sum.value = sum.value + temp;
			numCurr = numCurr.next;
		}
		return sum;
	}
	
	public String toString() {
		return this.number.toString();
	}
	
	public BigNumber copy() {
		return new BigNumber( this.number.copy() );
	}
	
	@Override
	public double doubleValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public float floatValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public long longValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int compareTo( BigNumber arg0 ) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public BigNumber clone() {
		return new BigNumber(this);
	}
	
}
