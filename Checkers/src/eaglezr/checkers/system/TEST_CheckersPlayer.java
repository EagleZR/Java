package eaglezr.checkers.system;

import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class TEST_CheckersPlayer {

	/**
	 * Tests to makes sure that the player is constructed appropriately.
	 */
	@Test public void testConstructors() {
		CheckersPlayer player = new CheckersPlayer( Color.WHITE );
		assertTrue( player.getColor().equals( Color.WHITE ) );
	}

	/**
	 * Tests the eaglezr.checkers.system.CheckersPlayer.equals(eaglezr.checkers.system.CheckersPlayer) method just to be sure it works. It's inherited from Object, so
	 * just wanna be sure...
	 */
	@Test public void testEquals() {
		CheckersPlayer player = new CheckersPlayer( Color.WHITE );
		assertTrue( player.getColor().equals( Color.WHITE ) );

		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );

		assertTrue( player.equals( player ) );
		assertFalse( player.equals( player2 ) );

		assertTrue( player2.equals( player2 ) );
		assertFalse( player2.equals( player ) );
	}

	/**
	 * Tests the methods necessary for the board setup process. Specifically, tests the setting of the Orientation and
	 * the assignment of the pieces.
	 */
	@Test public void testBoardSetupMethods() {
		// Check Orientation
		CheckersPlayer player = new CheckersPlayer( Color.WHITE );
		player.setOrientation( CheckersBoard.Orientation.UP );
		assertTrue( player.getOrientation() == CheckersBoard.Orientation.UP );

		// Check give Pieces
		CheckersPiece[] pieces = new CheckersPiece[20];
		for ( int i = 0; i < pieces.length; i++ ) {
			pieces[i] = new CheckersPiece( player, false );
		}

		player.setPieces( pieces );
		for ( int i = 0; i < pieces.length; i++ ) {
			assertTrue( pieces[i].equals( player.getPieces()[i] ) );
		}
	}

	/**
	 * Tests the functionality required for gameplay. Specifically, tests the player's ability to move on their turn
	 */
	@Test public void testPlayerMoves() {
		CheckersPlayer player1 = new CheckersPlayer( Color.RED );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );
		try {
			CheckersBoard board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			fail();
		}

		for(int x = 0; x < 20; x++ ) {

		}
	}
}
