package eaglezr.checkers.system;

import org.junit.Test;

import static org.junit.Assert.*;

public class TEST_Board {

	@Test public void testConstructors() {
		Board board = new Board( 12, 12 );
		assertTrue( board.getHeight() == 12 );
		assertTrue( board.getWidth() == 12 );

		board = new Board( 15, 18 );
		assertTrue( board.getHeight() == 18 );
		assertTrue( board.getWidth() == 15 );
	}

	@Test public void testArray() {
		int width = 12;
		int height = 12;
		Board board = new Board( width, height );
		Object[][] testPieces = new Object[width][height];
		assertTrue( testPieces.length == board.getPlaces().length );
		assertTrue( testPieces[0].length == board.getPlaces()[0].length );
	}

	@Test public void testBoard() {
		Board board = new Board( 12, 12 );
		for ( int y = 0; y < board.getHeight(); y++ ) {
			for ( int x = 0; x < board.getWidth(); x++ ) {
				board.placePieceAt( ( x + 1 ) + ", " + ( y + 1 ), x, y );
			}
		}

		for ( int y = 0; y < board.getHeight(); y++ ) {
			for ( int x = 0; x < board.getWidth(); x++ ) {
				assertTrue( ( (String) board.getPieceAt( x, y ) ).equals( ( x + 1 ) + ", " + ( y + 1 ) ) );
			}
		}
	}
}
