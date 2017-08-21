package eaglezr.checkers;

import eaglezr.checkers.system.CheckersBoard;
import eaglezr.checkers.system.CheckersPlayer;
import eaglezr.checkers.system.DuplicatePlayerException;
import eaglezr.checkers.system.PieceNotOnBoardException;
import javafx.scene.paint.Color;

public class TestMain {

	public static void main(String[] args) {
		CheckersPlayer player1 = new CheckersPlayer( Color.WHITE );
		CheckersPlayer player2 = new CheckersPlayer( Color.BLACK );

		CheckersBoard board = null;
		try {
			board = new CheckersBoard( player1, player2 );
		} catch ( DuplicatePlayerException e ) {
			System.out.println( e );
		}

		System.out.println(board.getPieceAt( 0, 2 ).getOwner().equals( player1 ));
		try {
			System.out.println(board.isValidMove( board.getPieceAt( 0, 2 ), 1, 3 ));
		} catch ( PieceNotOnBoardException e ) {
			e.printStackTrace();
		}
	}
}
