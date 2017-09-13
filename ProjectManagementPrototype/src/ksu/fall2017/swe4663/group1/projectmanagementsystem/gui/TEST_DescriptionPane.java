package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TEST_DescriptionPane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
		DescriptionPane descriptionPane = new DescriptionPane(
				"Hello. This is the description of our project. If you like what you see, or have any suggestions for changes, please let us know. Thank you." );

		Scene scene = new Scene( descriptionPane );
		primaryStage.setScene( scene );
		primaryStage.show();

	}
}
