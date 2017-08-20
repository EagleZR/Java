package eaglezr.checkers.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TEST_CheckersPiecePane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
BorderPane borderPane = new BorderPane();
		borderPane.setCenter( new CheckersPiecePane( Color.BLUE, Color.RED, null ) );

		Button promoteButton = new Button( "Promote/Demote" );
		promoteButton.setOnAction( e -> {
			( (CheckersPiecePane) borderPane.getCenter() )
					.setPromoted( !( (CheckersPiecePane) borderPane.getCenter() ).getPromote() );
		} );

		Button redButton = new Button( "Color red" );
		redButton.setOnAction( e -> {
			borderPane.setCenter( new CheckersPiecePane( Color.BLUE, Color.RED,
					( (CheckersPiecePane) borderPane.getCenter() ).getPiece() ) );
		} );

		Button blackButton = new Button( "Color black" );
		blackButton.setOnAction( e -> {
			borderPane.setCenter( new CheckersPiecePane( Color.BLUE, Color.BLACK,
					( (CheckersPiecePane) borderPane.getCenter() ).getPiece() ) );
		} );

		HBox hBox = new HBox();
		hBox.getChildren().addAll( promoteButton, redButton, blackButton );

		borderPane.setBottom( hBox );

		Scene scene = new Scene( borderPane, 500, 500 );
		primaryStage.setScene( scene );
		primaryStage.show();
	}
}
