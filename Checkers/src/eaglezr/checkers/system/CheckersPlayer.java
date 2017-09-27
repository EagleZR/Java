package eaglezr.checkers.system;

import javafx.scene.paint.Color;

public class CheckersPlayer {

	private Color color;
	private CheckersBoard.Orientation orientation;
	private CheckersPiece[] pieces;


	public CheckersPlayer(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public void setOrientation( CheckersBoard.Orientation orientation ) {
		this.orientation = orientation;
	}

	public CheckersBoard.Orientation getOrientation() {
		return orientation;
	}

	public void setPieces( CheckersPiece[] pieces ) {
		this.pieces = pieces;
	}

	public CheckersPiece[] getPieces() {
		return pieces;
	}

	public void setBoard(CheckersBoard board) {

	}
}
