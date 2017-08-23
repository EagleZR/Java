package eaglezr.checkers.system;

public class CheckersBoard extends Board {

	private CheckersPlayer player1;
	private CheckersPlayer player2;

	private CheckersPlayer movingPlayer;

	private JumpPlaceholder jumpChain;

	/**
	 * Constructs a board with the given players. <p>NOTE: The players should not be the same.</p>
	 *
	 * @param player1
	 * @param player2
	 * @throws IllegalArgumentException
	 */
	public CheckersBoard( CheckersPlayer player1, CheckersPlayer player2 ) throws DuplicatePlayerException {
		super( 8, 8 );
		if ( player1.equals( player2 ) ) {
			throw new DuplicatePlayerException( "player1 cannot equal player2" ); // Set  a person as a User. A user
			// can control both players, if that's wanted. But on the board level, the two players cannot be the same
		}
		this.player1 = player1;
		this.player2 = player2;

		setUp();
	}

	/**
	 * Clears the board and sets up a new game (Used for initial construction as well).
	 */
	public void setUp() {
		player1.setOrientation( Orientation.UP );
		player2.setOrientation( Orientation.DOWN );

		movingPlayer = player1;

		for ( int x = 0; x < getWidth(); x++ ) {
			for ( int y = 0; y < getHeight(); y++ ) {
				if ( x % 2 == y % 2 ) {
					if ( y < 3 ) {
						placePieceAt( new CheckersPiece( player1, false ), x, y );
					} else if ( y > 4 ) {
						placePieceAt( new CheckersPiece( player2, false ), x, y );
					} else {
						placePieceAt( null, x, y );
					}
				} else {
					placePieceAt( null, x, y );
				}
			}
		}

		player1.setPieces( getPlayer1Pieces() );
		player2.setPieces( getPlayer2Pieces() );
	}

	/**
	 * Gets the active pieces for player1.
	 *
	 * @return
	 */
	public CheckersPiece[] getPlayer1Pieces() {
		return getPlayerPieces( player1 );
	}

	/**
	 * Gets the active pieces for player2.
	 *
	 * @return
	 */
	public CheckersPiece[] getPlayer2Pieces() {
		return getPlayerPieces( player2 );
	}

	/**
	 * Gets the active pieces for the given player.
	 *
	 * @param player
	 * @return
	 */
	private CheckersPiece[] getPlayerPieces( CheckersPlayer player ) {
		// TODO It works... Find a better way to do this.
		// Maybe create a new array of eaglezr.checkers.system.CheckersPiece[getPlaces.length * getPlaces[0].length] and shrink it down?
		int count = 0;
		for ( int x = 0; x < getPlaces().length; x++ ) {
			for ( int y = 0; y < getPlaces()[0].length; y++ ) {
				if ( getPlaces()[x][y] != null && ( (CheckersPiece) getPlaces()[x][y] ).getOwner().equals( player ) ) {
					count++;
				}
			}
		}

		CheckersPiece[] pieces = new CheckersPiece[count];
		int i = 0;
		for ( int x = 0; x < getPlaces().length; x++ ) {
			for ( int y = 0; y < getPlaces()[0].length; y++ ) {
				if ( getPlaces()[x][y] != null && ( (CheckersPiece) getPlaces()[x][y] ).getOwner().equals( player ) ) {
					pieces[i] = (CheckersPiece) getPlaces()[x][y];
					i++;
				}
			}
		}
		return pieces;
	}

	/**
	 * Returns in in[]{x, y} the coordinates on the board of the given piece.
	 *
	 * @param piece
	 * @return
	 * @throws PieceNotOnBoardException
	 */
	public int[] getPieceCoord( CheckersPiece piece ) throws PieceNotOnBoardException {
		for ( int x = 0; x < getWidth(); x++ ) {
			for ( int y = 0; y < getHeight(); y++ ) {
				if ( places[x][y] != null && places[x][y].equals( piece ) ) {
					return new int[] { x, y };
				}
			}
		}
		throw new PieceNotOnBoardException( "The piece is not on the board" );
	}

	/**
	 * Returns the players on the board.
	 *
	 * @return
	 */
	public CheckersPlayer[] getPlayers() {
		return new CheckersPlayer[] { player1, player2 };
	}

	/**
	 * Determines whose turn it is to move.
	 *
	 * @return
	 */
	public CheckersPlayer getMovingPlayer() {
		return movingPlayer;
	}

	/**
	 * Returns the piece at the given location.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	@Override public CheckersPiece getPieceAt( int x, int y ) {
		return (CheckersPiece) super.getPieceAt( x, y );
	}

	/**
	 * Gets the y-location that the piece will be promoted at.
	 *
	 * @param piece The piece to check on the y-location for promotion.
	 * @return
	 */
	public int getPromotionY( CheckersPiece piece ) {
		return ( piece.getOwner().getOrientation() == Orientation.UP ? 7 : 0 );
	}

	/**
	 * Attempts to move the given piece for the given player to the given location.
	 *
	 * @param player
	 * @param piece
	 * @param x
	 * @param y
	 * @throws WrongPlayerMoveException
	 * @throws InvalidMoveException
	 * @throws PieceNotOnBoardException
	 */
	public void move( CheckersPlayer player, CheckersPiece piece, int x, int y )
			throws WrongPlayerMoveException, InvalidMoveException, PieceNotOnBoardException {
		if ( !player.equals( movingPlayer ) || !player.equals( piece.getOwner() ) ) {
			throw new WrongPlayerMoveException( "It is not that player's turn to move" );
		} else if ( !isValidMove( piece, x, y ) ) {
			throw new InvalidMoveException( "This piece cannot move there" );
		}
		moveOverride( piece, x, y );
	}

	/**
	 * Moves the piece to the given location without checking for a valid move. <P>NOTE: Only use this when the validity
	 * of the move has already been checked!!!</P>
	 *
	 * @param piece
	 * @param x
	 * @param y
	 * @throws PieceNotOnBoardException
	 */
	protected void moveOverride( CheckersPiece piece, int x, int y ) throws PieceNotOnBoardException {
		for ( int xPos = 0; xPos < getWidth(); xPos++ ) {
			for ( int yPos = 0; yPos < getHeight(); yPos++ ) {
				if ( places[xPos][yPos] == piece ) {
					places[xPos][yPos] = null;
					yPos = getWidth();
					xPos = getHeight();
					places[x][y] = piece;
					movingPlayer = ( movingPlayer == player1 ? player2 : player1 );

				}
			}
		}
		throw new PieceNotOnBoardException( "Cannot find the piece" );
	}

	/**
	 * Determines if the proposed location would make for a valid move.
	 *
	 * @param piece
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isValidMove( CheckersPiece piece, int x, int y ) throws PieceNotOnBoardException {
		if ( x >= 0 && y >= 0 ) {
			if ( piece.isPromoted() ) {
				return Math.abs( getPieceCoord( piece )[0] - x ) == 1 && Math.abs( getPieceCoord( piece )[1] - y ) == 1
						&& getWidth() > x && getHeight() > y && getPlaces()[x][y] == null;
			} else {
				return Math.abs( getPieceCoord( piece )[0] - x ) == 1 && getPieceCoord( piece )[1] - y == (
						piece.getOwner().getOrientation() == Orientation.UP ? -1 : 1 ) && getWidth() > x
						&& getHeight() > y && getPlaces()[x][y] == null;
			}
		} else {
			return false;
		}
	}

	/**
	 * Builds the jump chain for a series of jumps.
	 *
	 * @param player
	 * @param piece1
	 * @param piece2
	 * @throws WrongPlayerMoveException
	 * @throws InvalidMoveException
	 * @throws PieceNotOnBoardException
	 */
	public void jump( CheckersPlayer player, CheckersPiece piece1, CheckersPiece piece2 )
			throws WrongPlayerMoveException, InvalidMoveException, PieceNotOnBoardException {
		if ( !player.equals( movingPlayer ) ) {
			throw new WrongPlayerMoveException( "It is not that player's turn to move" );
		} else if ( !isValidJump( piece1, piece2 ) ) {
			throw new InvalidMoveException( "This piece cannot jump there" );
		} else {
			int[] dest = getJumpDestination( piece1, piece2 );
			if ( jumpChain == null ) {
				jumpChain = new JumpPlaceholder( piece2, dest[0], dest[1] );
			} else {
				new JumpPlaceholder( getLastJump(), piece2, dest[0], dest[1] );
			}
		}
	}

	/**
	 * Moves the moving piece to the final destination and removes all jumped pieces from the board. If the piece
	 * reaches the end of the board, it is promoted.
	 *
	 * @param player The player who is attempting to make the jump.
	 * @param piece  The jumping piece.
	 * @throws PieceNotOnBoardException
	 * @throws WrongPlayerMoveException
	 */
	public void completeJump( CheckersPlayer player, CheckersPiece piece )
			throws PieceNotOnBoardException, WrongPlayerMoveException {
		if ( !player.equals( movingPlayer ) ) {
			throw new WrongPlayerMoveException( "It is not that player's turn to move" );
		}
		JumpPlaceholder curr = jumpChain;
		do {
			jumpPiece( piece, curr.getJumpedPiece() );
			curr = curr.getNext();
		} while ( curr.hasNext() );
		jumpChain = null;
	}

	/**
	 * @param piece1
	 * @param piece2
	 * @throws PieceNotOnBoardException
	 */
	private void jumpPiece( CheckersPiece piece1, CheckersPiece piece2 ) throws PieceNotOnBoardException {
		for ( int x = 0; x < getWidth(); x++ ) {
			for ( int y = 0; y < getHeight(); y++ ) {
				if ( places[x][y] != null && getPieceAt( x, y ).equals( piece2 ) ) {
					places[x][y] = null;
					int[] dest = getJumpDestination( piece1, piece2 );
					moveOverride( piece1, dest[0], dest[1] );
					return;
				}
			}
		}
		throw new PieceNotOnBoardException( "The piece cannot be found on the board" );
	}

	/**
	 * Determines if a given piece is able to jump another piece. This is calculated from the jumping piece if there is
	 * not yet a chain of jumps, and from the last jump in the jump chain if a chain exists.
	 *
	 * @param piece1 The jumping piece.
	 * @param piece2 The jumped piece.
	 * @return True if the jumping piece is able to jump the jumped piece. False if the jumping piece is unable to
	 * complete the jump.
	 * @throws PieceNotOnBoardException
	 */
	public boolean isValidJump( CheckersPiece piece1, CheckersPiece piece2 ) throws PieceNotOnBoardException {
		int[] dest = getJumpDestination( piece1, piece2 );
		if ( jumpChain == null ) {
			if ( piece1.isPromoted() ) {
				return !piece1.getOwner().equals( piece2.getOwner() )
						&& Math.abs( getPieceCoord( piece1 )[0] - getPieceCoord( piece2 )[0] ) == 1
						&& Math.abs( getPieceCoord( piece1 )[1] - getPieceCoord( piece2 )[1] ) == 1
						&& getWidth() > dest[0] && getHeight() > dest[1] && getPlaces()[dest[0]][dest[1]] == null;
			} else {
				return !piece1.getOwner().equals( piece2.getOwner() )
						&& Math.abs( getPieceCoord( piece1 )[0] - getPieceCoord( piece2 )[0] ) == 1
						&& getPieceCoord( piece1 )[1] - getPieceCoord( piece2 )[1] == (
						piece1.getOwner().getOrientation() == Orientation.UP ? 1 : -1 ) && getWidth() > dest[0]
						&& getHeight() > dest[1] && getPlaces()[dest[0]][dest[1]] == null;
			}
		} else {
			JumpPlaceholder lastJump = getLastJump();
			if ( jumpChainPromotion( piece1 ) ) {
				return !piece1.getOwner().equals( piece2.getOwner() )
						&& Math.abs( lastJump.getX() - getPieceCoord( piece2 )[0] ) == 1
						&& Math.abs( lastJump.getY() - getPieceCoord( piece2 )[1] ) == 1 && getWidth() > dest[0]
						&& getHeight() > dest[1] && getPlaces()[dest[0]][dest[1]] == null;
			} else {
				return !piece1.getOwner().equals( piece2.getOwner() )
						&& Math.abs( lastJump.getX() - getPieceCoord( piece2 )[0] ) == 1
						&& lastJump.getY() - getPieceCoord( piece2 )[1] == (
						piece1.getOwner().getOrientation() == Orientation.UP ? 1 : -1 ) && getWidth() > dest[0]
						&& getHeight() > dest[1] && getPlaces()[dest[0]][dest[1]] == null;
			}
		}
	}

	/**
	 * Determines the destination of a potential jump given the jumping piece and the jumped piece. If a jumping chain
	 * exists, this destination will be calculated from the last jump instead of the position of the jumping piece.
	 *
	 * @param piece1 The jumping piece.
	 * @param piece2 The jumped piece.
	 * @return The int[]{x, y} board coordinates of where the jumping piece will land after jumping the jumped piece.
	 * @throws PieceNotOnBoardException
	 */
	public int[] getJumpDestination( CheckersPiece piece1, CheckersPiece piece2 ) throws PieceNotOnBoardException {
		if ( jumpChain == null ) {
			return new int[] {
					getPieceCoord( piece1 )[0] + ( 2 * getPieceCoord( piece1 )[0] - getPieceCoord( piece2 )[0] ),
					getPieceCoord( piece1 )[1] + ( 2 * getPieceCoord( piece1 )[1] - getPieceCoord( piece2 )[1] ) };
		} else {
			JumpPlaceholder lastJump = getLastJump();
			return new int[] { lastJump.getX() + ( 2 * lastJump.getX() - getPieceCoord( piece2 )[0] ),
					lastJump.getY() + ( 2 * lastJump.getY() - getPieceCoord( piece2 )[1] ) };
		}
	}

	/**
	 * Starts the recursive search for the last jump in the jump chain.
	 *
	 * @return The last jump in the jump chain.
	 */
	private JumpPlaceholder getLastJump() {
		return getLastJump( jumpChain );
	}

	/**
	 * Recursively searches for the last jump in a jump chain.
	 *
	 * @param curr
	 * @return
	 */
	private JumpPlaceholder getLastJump( JumpPlaceholder curr ) {
		if ( curr.hasNext() ) {
			return getLastJump( curr.getNext() );
		} else {
			return curr;
		}
	}

	/**
	 * Checks if the piece will be promoted if it goes through the jump chain.
	 *
	 * @param piece
	 * @return
	 */
	private boolean jumpChainPromotion( CheckersPiece piece ) {
		return jumpChainGoesToY( getPromotionY( piece ) );
	}

	/**
	 * Checks to see if the jump chain will go to a specified y-location.
	 *
	 * @param y
	 * @return
	 */
	private boolean jumpChainGoesToY( int y ) {
		return jumpChainGoesToY( y, jumpChain );
	}

	/**
	 * Checks to see if the jump chain will go to a specified y-location.
	 *
	 * @param y
	 * @param curr
	 * @return
	 */
	private boolean jumpChainGoesToY( int y, JumpPlaceholder curr ) {
		if ( curr.getY() == y ) {
			return true;
		} else if ( curr.hasNext() ) {
			return jumpChainGoesToY( y, curr.getNext() );
		} else {
			return false;
		}
	}

	/**
	 * An orientation device to help with determining which direction a player is moving in.
	 */
	public enum Orientation {
		UP, DOWN
	}

	/**
	 * Creates a placeholder for a chain of jumps.
	 */
	private class JumpPlaceholder {
		private JumpPlaceholder prevPlaceholder;
		private JumpPlaceholder nextPlaceholder;
		private CheckersPiece jumpedPiece;
		private int x;
		private int y;

		private JumpPlaceholder( CheckersPiece jumpedPiece, int x, int y ) {
			this.jumpedPiece = jumpedPiece;
			this.x = x;
			this.y = y;
		}

		private JumpPlaceholder( JumpPlaceholder prevPlaceholder, CheckersPiece jumpedPiece, int x, int y ) {
			this.prevPlaceholder = prevPlaceholder;
			prevPlaceholder.nextPlaceholder = this;
		}

		private boolean hasNext() {
			if ( this.nextPlaceholder == null ) {
				return false;
			}
			return true;
		}

		private JumpPlaceholder getNext() {
			return this.nextPlaceholder;
		}

		private JumpPlaceholder getPrvious() {
			return this.prevPlaceholder;
		}

		private CheckersPiece getJumpedPiece() {
			return this.jumpedPiece;
		}

		private int getX() {
			return this.x;
		}

		private int getY() {
			return this.y;
		}
	}
}
