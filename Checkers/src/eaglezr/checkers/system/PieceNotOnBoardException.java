package eaglezr.checkers.system;

public class PieceNotOnBoardException extends Exception {
	public PieceNotOnBoardException( String s ) {
		super( s );
	}
}
