package eaglezr.taskplanner.gui;

import eaglezr.taskplanner.system.Task;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TEST_TaskPane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
		Task task = new Task( "A", "Task 1", "Do something", 15 );
		TaskPane taskPane = new TaskPane( task );

		Scene scene = new Scene( taskPane );
		primaryStage.setScene( scene );
		primaryStage.show();
	}
}
