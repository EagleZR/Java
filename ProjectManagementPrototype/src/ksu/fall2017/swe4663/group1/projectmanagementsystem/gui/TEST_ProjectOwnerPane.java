package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TEST_ProjectOwnerPane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
		ProjectOwnerPane pane = new ProjectOwnerPane( "Test" );
		Scene scene = new Scene( pane );
		primaryStage.setScene( scene );
		primaryStage.show();
	}
}