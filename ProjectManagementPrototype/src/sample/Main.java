package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override public void start( Stage primaryStage ) throws Exception {

		Menu file = new Menu( "File" );
		Menu options = new Menu( "Options" );
		Menu help = new Menu( "Help" );

		MenuBar menuBar = new MenuBar( file, options, help );

		BorderPane borderPane = new BorderPane();
		borderPane.setTop( menuBar );

		//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		primaryStage.setTitle( "Project Management System" );
		primaryStage.setScene( new Scene( borderPane, 500, 600 ) );
		primaryStage.show();
	}

	public static void main( String[] args ) {
		launch( args );
	}
}
