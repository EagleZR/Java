package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team;

public class TEST_TeamMembersPane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
		TeamMembersPane teamMembersPane = new TeamMembersPane(primaryStage, new Team());
		Scene scene = new Scene( teamMembersPane );
		primaryStage.setScene( scene );
		primaryStage.show();
	}
}
