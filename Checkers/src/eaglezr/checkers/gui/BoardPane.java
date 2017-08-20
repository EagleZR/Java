package eaglezr.checkers.gui;

import eaglezr.checkers.system.Board;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardPane extends Pane {

	Rectangle[][] tiles;
	Color primaryColor;
	Color secondaryColor;

	public BoardPane( Board board, Color primaryColor, Color secondaryColor ) {
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		setBoard( board );
	}

	public void setBoard( Board board ) {
		tiles = new Rectangle[board.getWidth()][board.getHeight()];
		drawBoard();
	}

	public void drawBoard() {
		for ( int x = 0; x < tiles.length; x++ ) {
			for ( int y = 0; y < tiles[0].length; y++ ) {
				// Create & color tile
				tiles[x][y] = new Rectangle( 100, 100, ( ( x % 2 ) == ( y % 2 ) ? primaryColor : secondaryColor ) );
				drawRectangle( tiles[x][y], x, y );
			}
		}
	}

	void drawRectangle(Rectangle rect, int x, int y) {
		// Set dimensions of tile
		rect.widthProperty().bind( widthProperty().divide( tiles.length ) );
		rect.heightProperty().bind( heightProperty().divide( tiles[0].length ) );

		// Place tile in appropriate spot
		rect.xProperty().bind( widthProperty().divide( tiles.length ).multiply( x ) );
		rect.yProperty().bind( heightProperty().divide( tiles[0].length ).multiply( y ) );

		// Adds tile to pane
		getChildren().add( rect );
	}
}
