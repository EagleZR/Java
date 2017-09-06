package eaglezr.checkers.gui;

import eaglezr.checkers.system.CheckersBoard;
import eaglezr.checkers.system.CheckersPiece;
import eaglezr.checkers.system.PieceNotOnBoardException;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CheckersBoardPane extends BoardPane {

	private CheckersBoard board;

	public CheckersBoardPane( CheckersBoard board, Color primaryColor, Color secondaryColor ) {
		super( board, primaryColor, secondaryColor );
		this.board = board;
		drawPieces();
	}

	@Override public void drawBoard() {
		super.drawBoard();
		if ( board != null ) {
			drawPieces();
		}
	}

	private void drawPieces() {
		GridPane piecesPane = new GridPane();
		for ( int x = 0; x < board.getWidth(); x++ ) {
			for ( int y = 0; y < board.getHeight(); y++ ) {
				if ( board.getPieceAt( x, y ) != null ) {
					// Make piece
					CheckersPiecePane piecePane = new CheckersPiecePane( primaryColor,
							board.getPieceAt( x, y ).getOwner().getColor(), board.getPieceAt( x, y ) );

					// Rotate if player2
					if ( board.getPieceAt( x, y ).getOwner().equals( board.getPlayers()[0] ) ) {
						piecePane.setRotate( 180 );
					}

					// Size piece
					piecePane.prefWidthProperty().bind( widthProperty().divide( board.getWidth() ) );
					piecePane.prefHeightProperty().bind( heightProperty().divide( board.getHeight() ) );

					piecePane.setOnMouseEntered( e -> highlightAvailableMoves( piecePane.getPiece() ) );

					piecePane.setOnMouseExited( e -> drawBoard() );

					// Move piece
					//					piecePane.setTranslateX( getWidth() / board.getWidth() * x );
					//					piecePane.setTranslateY( getHeight() / board.getHeight() * y );

					// Place piece
					piecesPane.add( piecePane, x, y );
				} else {
					Pane pane = new Pane();
					piecesPane.add( pane, x, y );
					pane.prefWidthProperty().bind( widthProperty().divide( board.getWidth() ) );
					pane.prefHeightProperty().bind( heightProperty().divide( board.getHeight() ) );
				}
			}
		}
		piecesPane.prefWidthProperty().bind( widthProperty() );
		piecesPane.prefHeightProperty().bind( heightProperty() );
		piecesPane.setHgap( 0 );
		piecesPane.setVgap( 0 );
		getChildren().add( piecesPane );
	}

	private void highlightAvailableMoves( CheckersPiece piece ) {
		for ( int x = 0; x < board.getWidth(); x++ ) {
			for ( int y = 0; y < board.getHeight(); y++ ) {
				try {
					if ( board.isValidMove( piece, x, y ) ) {
						tiles[x][y] = new Rectangle( 100, 100, Color.GREEN );

						drawRectangle( tiles[x][y], x, y );
					}
				} catch ( PieceNotOnBoardException e ) {
					// TODO Figure out how to handle
					e.printStackTrace();
				}
			}
		}
	}
}
