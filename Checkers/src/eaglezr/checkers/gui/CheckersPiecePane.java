package eaglezr.checkers.gui;

import eaglezr.checkers.system.CheckersPiece;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CheckersPiecePane extends StackPane {

	private Color tileColor;
	private Color playerColor;
	private CheckersPiece piece;
	private Color centerColor = Color.WHITE;

	public CheckersPiecePane( Color tileColor, Color playerColor, CheckersPiece piece ) {
		this.tileColor = tileColor;
		this.playerColor = playerColor;
		this.piece = piece;
		drawPiece();
	}

	public void drawPiece() {
		Circle outsideCircle = new Circle( 50, playerColor );
		setAlignment( outsideCircle, Pos.CENTER );
		outsideCircle.radiusProperty().bind( widthProperty().multiply( 0.4 ) );

		Circle innerCircle = new Circle( 40, centerColor );
		setAlignment( innerCircle, Pos.CENTER );
		innerCircle.radiusProperty().bind( widthProperty().multiply( 0.3 ) );

		getChildren().add( outsideCircle );
		getChildren().add( innerCircle );

		if ( piece.isPromoted() ) {
			Image crownImage = new Image( "eaglezr/checkers/resources/crown.png" );
			ImageView crown = new ImageView( crownImage );
			setAlignment( crown, Pos.CENTER );
			crown.fitWidthProperty().bind( widthProperty().divide( 2 ) );
			crown.setPreserveRatio( true );
			crown.setSmooth( true );
			getChildren().add( crown );
		}
	}

	public void setPromoted( boolean isPromoted ) {
		if (isPromoted) {
			piece.promote();
		} else {
			piece.demote();
		}
		drawPiece();
	}

	public boolean getPromote() {
		return this.piece.isPromoted();
	}

	public CheckersPiece getPiece () {
		return this.piece;
	}
}
