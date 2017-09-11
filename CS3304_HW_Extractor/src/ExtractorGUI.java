import eaglezr.javafx.stages.PopupStage;
import eaglezr.support.logs.LoggingTool;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class ExtractorGUI extends Application {

	private File chosenDirectory;

	@Override public void start( Stage primaryStage ) throws Exception {
		LoggingTool.getLogger().setDefault( LoggingTool.generateLogPrinter( "ExtractorGUI" ) );
		int width = 400;
		int height = 100;
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setInitialDirectory( new File( System.getProperty( "user.dir" ) ) );
		chooser.setTitle( "Choose the folder to extract." );

		Button directoryChooserButton = new Button( "..." );

		TextField textField = new TextField();
		textField.setText( chooser.getInitialDirectory().getAbsolutePath() );
		textField.setMinWidth( 350 );
		textField.setMinWidth( 350 );
		textField.setEditable( false );

		directoryChooserButton.setLayoutX( 370 );
		directoryChooserButton.setLayoutY( 10 );

		textField.setLayoutX( 10 );
		textField.setLayoutY( 10 );

		directoryChooserButton.setOnAction( e -> {
			Stage stage2 = new Stage();

			chosenDirectory = chooser.showDialog( stage2 );

			textField.setText( chosenDirectory.getAbsolutePath() );

			stage2.show();
		} );

		Button extractButton = new Button( "Extract" );
		extractButton.setOnAction( e -> {
			if ( chosenDirectory == null ) {
				showPopupMessage( "Please select the directory to extract from.", primaryStage );
			} else {
				DirectoryExtractor extractor = new DirectoryExtractor( chosenDirectory );
				extractor.extract();
				showPopupMessage( "The extractor is finished.", primaryStage );
			}
		} );

		extractButton.setLayoutX( width - 50 );
		extractButton.setLayoutY( height - 20 );

		Pane pane = new Pane();
		pane.getChildren().addAll( directoryChooserButton, textField, extractButton );

		Scene scene = new Scene( pane, width, height );
		primaryStage.setScene( scene );
		primaryStage.setResizable( false );
		primaryStage.setTitle( "CS3304 HW Extractor" );
		primaryStage.show();
	}

	/**
	 * Displays a pop-up message using the given string and primary stage.
	 * @param message The message to be displayed.
	 * @param primaryStage The stage over which to display the message.
	 */
	private void showPopupMessage( String message, Stage primaryStage ) {
		Label label = new Label( message );
		BorderPane pane = new BorderPane();
		pane.setCenter( label );
		Scene scene = new Scene( pane );
		PopupStage popup = new PopupStage( scene, primaryStage );

		Button closeButton = new Button( "Ok" );
		BorderPane pane2 = new BorderPane();
		pane2.setCenter( closeButton );
		pane.setBottom( pane2 );
		closeButton.setOnAction( a -> popup.close() );
		popup.show();
	}
}
