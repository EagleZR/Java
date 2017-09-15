package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.PersonNotOnTeamException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team;

public class GeneralPane extends Pane {
	
	public GeneralPane(Stage primaryStage) {
		// Initialize system
		Team team = new Team();

		// Description Pane
		DescriptionPane descriptionPane = new DescriptionPane(
				"Here would be the description of the program. Edit it if you want, but it won't save after you close it." );
		descriptionPane.prefWidthProperty().bind( this.widthProperty().divide( 2 ) );
		descriptionPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		descriptionPane.layoutXProperty().setValue( 0 );
		descriptionPane.layoutYProperty().setValue( 0 );
		this.getChildren().add( descriptionPane );

		// Team Members Pane
		TeamMembersPane teamMembersPane = new TeamMembersPane( primaryStage, team );
		teamMembersPane.prefWidthProperty().bind( this.widthProperty().divide( 2 ) );
		teamMembersPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		teamMembersPane.layoutXProperty().bind( descriptionPane.layoutXProperty() );
		teamMembersPane.layoutYProperty().bind( descriptionPane.layoutYProperty().add( descriptionPane.heightProperty() ) );
//		teamMembersPane.updateStartMembers();
		this.getChildren().add( teamMembersPane );

		// Project Owner Pane
		ProjectOwnerPane projectOwnerPane = null;
		try {
			projectOwnerPane = new ProjectOwnerPane( team );
		} catch ( PersonNotOnTeamException e ) {
			e.printStackTrace();
		}
		projectOwnerPane.prefWidthProperty().bind( this.widthProperty().divide( 2 ) );
		projectOwnerPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		projectOwnerPane.layoutXProperty().bind( descriptionPane.layoutXProperty().add( descriptionPane.widthProperty() ) );
		projectOwnerPane.layoutYProperty().bind( descriptionPane.layoutYProperty() );
		this.getChildren().add( projectOwnerPane );

		// Risks Pane
		RisksPane risksPane = new RisksPane();
		risksPane.prefWidthProperty().bind( this.widthProperty().divide( 2 ) );
		risksPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		risksPane.layoutXProperty().bind( descriptionPane.layoutXProperty().add( descriptionPane.widthProperty() ) );
		risksPane.layoutYProperty().bind( descriptionPane.layoutYProperty().add( descriptionPane.heightProperty() ) );
		this.getChildren().addAll( risksPane );
	}
}
