package eaglezr.checkers.system;

import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class TEST_CheckersBoard {

	@Test public void testConstructors() {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );

		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			e.printStackTrace();
			fail();
		}
		assertTrue( board.getHeight() == 8 );
		assertTrue( board.getWidth() == 8 );
		assertFalse( board.getPlayers()[0].equals( board.getPlayers()[1] ) );

		assertTrue( ( board.getPlayers()[0].equals( player1 ) && board.getPlayers()[1].equals( player2 ) ) || (
				board.getPlayers()[0].equals( player2 ) && board.getPlayers()[1].equals( player1 ) ) );
	}

	/**
	 * Tests that an {@link IllegalArgumentException} is thrown when a board is made where {@code
	 * player1.equals(player2)}.
	 */
	@Test( expected = DuplicatePlayerException.class ) public void testConstructorsException()
			throws DuplicatePlayerException {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );

		CheckersBoard board = new CheckersBoard( player1, player1 );
		assertFalse( board.getPlayers()[0].equals( board.getPlayers()[1] ) );
	}

	/**
	 * Tests that the board is correctly set up with the pieces in the correct place and belonging to the appropriate
	 * player. Also, the player needs the correct orientation.
	 */
	@Test public void testSetup() {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );

		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			e.printStackTrace();
			fail();
		}

		for ( int x = 0; x < board.getWidth(); x++ ) {
			for ( int y = 0; y < board.getHeight(); y++ ) {
				if ( x % 2 == y % 2 && ( y < 3 || y > 4 ) ) {
					assertFalse( ( board.getPieceAt( x, y ) ).isPromoted() );
					if ( y < 3 ) {
						assertTrue( ( board.getPieceAt( x, y ) ).getOwner().equals( player1 ) );
					} else if ( y > 4 ) {
						assertTrue( ( board.getPieceAt( x, y ) ).getOwner().equals( player2 ) );
					}
				} else {
					assertTrue( board.getPieceAt( x, y ) == null );
				}
			}
		}

		compareArrays( player1.getPieces(), board.getPlayer1Pieces() );
		compareArrays( player2.getPieces(), board.getPlayer2Pieces() );

		assertTrue( player1.getOrientation().equals( CheckersBoard.Orientation.UP ) );
		assertTrue( player2.getOrientation().equals( CheckersBoard.Orientation.DOWN ) );
	}

	private void compareArrays( CheckersPiece[] array1, CheckersPiece[] array2 ) {
		if ( array1 == null || array2 == null || array1.length != array2.length ) {
			fail();
		} else {
			for ( int i = 0; i < array1.length; i++ ) {
				assertTrue( array1[i].equals( array2[i] ) );
			}
		}
	}

	/**
	 * Tests the board.isValidMove method without actually completing the move.
	 */
	@Test public void testValidMoveMethods(){
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );

		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			e.printStackTrace();
		}
		// Test a piece from the lower starting position.
		try {
			assertTrue( board.isValidMove( board.getPieceAt( 0, 2 ), 1, 3 ) );
		} catch ( PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}

		// Test a piece from the upper starting position.
		try {
			assertTrue( board.isValidMove( board.getPieceAt( 1, 5 ), 0, 4 ) );
		} catch ( PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}

		// Move a piece to the middle and check backwards, unpromoted movement
		try {
			CheckersPiece piece =  board.getPieceAt( 1, 5 );
			board.moveOverride( piece, 1, 3 );
			assertFalse( board.isValidMove( piece, 0, 2 ) );
		} catch ( PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}

		// Promote the moved piece and check backwards movement
		try {
			CheckersPiece piece =  board.getPieceAt( 1, 5 );
			piece.promote();
			assertTrue( board.isValidMove( piece, 0, 2 ) );
		} catch ( PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Tests the board.isValidJump method without actually completing the jump.
	 */
	@Test public void testValidJumpMethods(){

	}

	/**
	 * Tests that multiple jumps in a chain will be recognized as valid without actually completing the jump.
	 */
	@Test public void testJumpChainConstruction(){

	}

	/**
	 * Tests that the current player is set to move at the appropriate time.
	 */
	@Test public void testPlayerRotation() {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );

		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			e.printStackTrace();
			fail();
		}
		assertTrue( board.getMovingPlayer().equals( player1 ) );

		try {
			board.move( player1, board.getPieceAt( 0, 2 ), 1, 3 );
		} catch ( WrongPlayerMoveException | PieceNotOnBoardException | InvalidMoveException e ) {
			e.printStackTrace();
			fail();
		}
		assertTrue( ( board.getMovingPlayer().equals( player2 ) ) );

		try {
			board.move( player2, board.getPieceAt( 7, 5 ), 6, 4 );
		} catch ( WrongPlayerMoveException | InvalidMoveException | PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}
		assertTrue( board.getMovingPlayer().equals( player1 ) );
	}

	/**
	 * Tests that an exception is thrown when the wrong player attempts to move. This one tests the initial condition to
	 * make sure only Player 1 can move.
	 *
	 * @throws WrongPlayerMoveException
	 */
	@Test( expected = WrongPlayerMoveException.class ) public void testWrongPlayerMove()
			throws WrongPlayerMoveException {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );

		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			e.printStackTrace();
			fail();
		}
		try {
			board.move( player2, board.getPieceAt( 0, 2 ), 1, 3 );
		} catch ( InvalidMoveException | PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Tests that an exception is thrown when the wrong player attempts to move. This one has player1 move, then makes
	 * sure it cannot move again.
	 *
	 * @throws WrongPlayerMoveException
	 */
	@Test( expected = WrongPlayerMoveException.class ) public void testWrongPlayerMove2()
			throws WrongPlayerMoveException {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );

		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			e.printStackTrace();
			fail();
		}
		try {
			board.move( player1, board.getPieceAt( 0, 2 ), 1, 3 );
		} catch ( InvalidMoveException | PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}
		assertTrue( ( board.getMovingPlayer().equals( player2 ) ) );
		try {
			board.move( player1, board.getPieceAt( 0, 2 ), 1, 3 );
		} catch ( InvalidMoveException | PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test moving forward diagonally, moving a promoted piece backwards diagonally, test jumping over enemy piece
	 */
	@Test public void testValidMovesExecuted() {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );
		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			e.printStackTrace();
			fail();
		}

		// Player1 move 1
		try {
			board.move( player1, board.getPieceAt( 0, 2 ), 1, 3 );
		} catch ( WrongPlayerMoveException | InvalidMoveException | PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}

		// Player2 move 1
		try {
			board.move( player2, board.getPieceAt( 7, 5 ), 6, 4 );
		} catch ( WrongPlayerMoveException | InvalidMoveException | PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}

		board.getPieceAt( 1, 3 ).promote();

		// Player1 move 2
		try {
			board.move( player1, board.getPieceAt( 1, 3 ), 0, 2 );
		} catch ( WrongPlayerMoveException | PieceNotOnBoardException | InvalidMoveException e ) {
			e.printStackTrace();
			fail();
		}

		// Player2 move 2
		try {
			board.move( player2, board.getPieceAt( 1, 5 ), 2, 4 );
		} catch ( WrongPlayerMoveException | PieceNotOnBoardException | InvalidMoveException e ) {
			e.printStackTrace();
			fail();
		}

		// Player1 move 3
		try {
			board.move( player1, board.getPieceAt( 0, 2 ), 1, 3 );
		} catch ( WrongPlayerMoveException | PieceNotOnBoardException | InvalidMoveException e ) {
			e.printStackTrace();
			fail();
		}

		// Player2 move 3
		try {
			board.jump( player2, board.getPieceAt( 2, 4 ), board.getPieceAt( 1, 3 ) );
			board.completeJump(player2, board.getPieceAt( 2, 4 )); // For chaining jumps
			// TODO Test chaining jumps
		} catch ( WrongPlayerMoveException | PieceNotOnBoardException | InvalidMoveException e ) {
			e.printStackTrace();
			fail();
		}

	}

	/**
	 * Test moving forward
	 */
	@Test( expected = InvalidMoveException.class ) public void testInvalidMove1() throws InvalidMoveException {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );
		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			e.printStackTrace();
			fail();
		}

		// Player1 move 1
		try {
			board.move( player1, board.getPieceAt( 0, 2 ), 0, 3 );
		} catch ( WrongPlayerMoveException | PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test moving backwards
	 */
	@Test( expected = InvalidMoveException.class ) public void testInvalidMove2() throws InvalidMoveException {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );
		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			e.printStackTrace();
			fail();
		}

		// Player1 move 1
		try {
			board.move( player1, board.getPieceAt( 0, 2 ), 0, 1 );
		} catch ( WrongPlayerMoveException | PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test moving left
	 */
	@Test( expected = InvalidMoveException.class ) public void testInvalidMove3() throws InvalidMoveException {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );
		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			e.printStackTrace();
			fail();
		}

		// Player1 move 1
		try {
			board.move( player1, board.getPieceAt( 2, 2 ), 1, 2 );
		} catch ( WrongPlayerMoveException | PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test moving right
	 */
	@Test( expected = InvalidMoveException.class ) public void testInvalidMove4() throws InvalidMoveException {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );
		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			e.printStackTrace();
			fail();
		}

		// Player1 move 1
		try {
			board.move( player1, board.getPieceAt( 2, 2 ), 3, 2 );
		} catch ( WrongPlayerMoveException | PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test moving to occupied space
	 */
	@Test( expected = InvalidMoveException.class ) public void testInvalidMove5() throws InvalidMoveException {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );
		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			e.printStackTrace();
			fail();
		}

		board.getPieceAt( 2, 2 ).promote();
		// Player1 move 1
		try {
			board.move( player1, board.getPieceAt( 2, 2 ), 1, 1 );
		} catch ( WrongPlayerMoveException | PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test moving unpromoted piece backwards, diagonally
	 */
	@Test( expected = InvalidMoveException.class ) public void testInvalidMove6() throws InvalidMoveException {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );
		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			e.printStackTrace();
			fail();
		}

		// Player1 move 1
		try {
			board.move( player1, board.getPieceAt( 2, 2 ), 1, 1 );
		} catch ( WrongPlayerMoveException | PieceNotOnBoardException e ) {
			e.printStackTrace();
			fail();
		}
	}

}
