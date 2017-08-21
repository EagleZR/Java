package eaglezr.checkers.gui;

import eaglezr.checkers.system.CheckersBoard;
import eaglezr.checkers.system.CheckersPlayer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TEST_CheckersBoardPane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
		CheckersPlayer player1 = new CheckersPlayer( Color.DARKGOLDENROD );
		CheckersPlayer player2 = new CheckersPlayer( Color.DARKSLATEGRAY );
		CheckersBoard board = new CheckersBoard( player1, player2 );
		CheckersBoardPane boardPane = new CheckersBoardPane( board, Color.DARKBLUE, Color.GRAY );

		Scene scene = new Scene( boardPane, 500, 500 );
		boardPane.prefHeightProperty().bind( scene.heightProperty() );
		boardPane.prefWidthProperty().bind( scene.widthProperty() );
		primaryStage.setScene( scene );
//		primaryStage.setResizable( false );
		primaryStage.show();
		boardPane.drawBoard();
	}
}
