package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.ProjectManagementPane;

public class Main extends Application {

	public static void main( String[] args ) {
		launch( args );
	}

	@Override public void start( Stage primaryStage ) throws Exception {

		ProjectManagementPane pane = new ProjectManagementPane( primaryStage );

		////////////////////////////////////
		// Display Scene
		////////////////////////////////////
		primaryStage.setTitle( "Project Management System" );
		primaryStage.setScene( new Scene( pane, 500, 600 ) );
		primaryStage.setMinWidth( 450 );
		primaryStage.setMinHeight( 500 );
		primaryStage.show();
	}
}
