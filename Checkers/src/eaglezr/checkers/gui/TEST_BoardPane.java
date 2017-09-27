package eaglezr.checkers.gui;

import eaglezr.checkers.system.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.text.DecimalFormat;

/**
 * Builds a BoardPane at the default 8x8 and rebuilds it based on the new (square) dimensions inputted by the user.
 */
public class TEST_BoardPane extends Application {

	@Override public void start( Stage primaryStage ) throws Exception {
		Board board = new Board( 8, 8 );
		BoardPane boardPane = new BoardPane( board, Color.DARKBLUE, Color.GRAY );

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter( boardPane );

		Slider slider = new Slider( 2, 25, board.getWidth() );
		slider.setMajorTickUnit( 1.0 );
		slider.setMinorTickCount( 0 );
		slider.setSnapToTicks( true );
		slider.setShowTickMarks( true );
		slider.setShowTickLabels( true );
		Button button = new Button( "Commit" );
		button.setOnAction( e -> {
			Board board1 = new Board( (int) slider.getValue(), (int) slider.getValue() );
			boardPane.setBoard( board1 );
		} );
		Label label = new Label( new DecimalFormat( "#" ).format( slider.getValue() ) );
		//		label.textProperty().bind( slider.labelFormatterProperty() );
		slider.valueProperty()
				.addListener( e -> label.setText( new DecimalFormat( "#" ).format( slider.getValue() ) ) );

		BorderPane controlPanel = new BorderPane();
		controlPanel.setCenter( slider );
		controlPanel.setRight( button );
		controlPanel.setLeft( label );

		borderPane.setBottom( controlPanel );

		Scene scene = new Scene( borderPane, 400, 400 );
		primaryStage.setScene( scene );
		primaryStage.show();
	}

}
