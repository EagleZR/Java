package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TEST_TeamMembersPane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
		TeamMembersPane teamMembersPane = new TeamMembersPane(primaryStage);
		Scene scene = new Scene( teamMembersPane );
		primaryStage.setScene( scene );
		primaryStage.show();
	}
}
