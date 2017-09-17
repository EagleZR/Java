package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Label;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.ButtonScrollPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.PersonButton;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.PersonNotOnTeamException;

public class ProjectOwnerPane extends FramedPane {

	private Project project;
	private Person manager;
	private Label label;
	private ButtonScrollPane scrollPane;

	public ProjectOwnerPane( Project project ) throws PersonNotOnTeamException {
		LoggingTool.print( "Creating new ProjectOwnerPane." );
		this.project = project;
		try {
			LoggingTool
					.print( "Setting " + project.getTeam().getManager() + " as the manager in the ProjectOwnerPane." );
			this.manager = project.getTeam().getManager();
			setup();
		} catch ( PersonNotOnTeamException e ) {
			LoggingTool
					.print( "No manager was found. Setting an empty person as the manager in the ProjectOwnerPane." );
			this.manager = new Person( "" );
			this.manager.promote();
			setup();
		}
	}

	private void setup() {
		// Draw Label
		LoggingTool.print( "ProjectOwnerPane: Creating title label in ProjectOwnerPane." );
		label = new Label( "" );
		label.prefWidthProperty()
				.bind( this.widthProperty().subtract( 20 ) );
		label.layoutXProperty().setValue( 10 );
		label.layoutYProperty().setValue( 10 );
		this.getChildren().add( label );

		// Draw ScrollPane of Person Buttons
		LoggingTool.print( "ProjectOwnerPane: Creating ButtonScrollPane in ProjectOwnerPane." );
		scrollPane = new ButtonScrollPane();
		scrollPane.layoutXProperty().bind( label.layoutXProperty() );
		scrollPane.layoutYProperty().bind( label.layoutYProperty().add( 5 ).add( label.heightProperty() ) );
		scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( 20 ) );
		scrollPane.prefHeightProperty().bind( this.heightProperty().subtract( 25 ).subtract( label.heightProperty() ) );
		this.getChildren().add( scrollPane );

		update();
	}

	private void update() {
		LoggingTool.print( "ProjectOwnerPane: Updating ProjectOwnerPane." );
		label.setText( "Project Manager: " + manager.getName() );
		// Make sure each team member is displayed
		for ( Person person : this.project.getTeam().getMembers() ) {

		}
		// Make sure no extras are displayed
	}

	private void addButton( Person person ) {
		LoggingTool.print( "ProjectOwnerPane: Adding Person named " + person.getName()
				+ " to the ButtonScrollPane in the ProjectOwnerPane." );
	}

	private void removeButton( PersonButton button ) {
		LoggingTool.print( "ProjectOwnerPane: Removing Person named " + button.getPerson().getName() + " from the ProjectOwnerPane." );
	}

	//	public void updateManager( Person manager ) {
	//		this.manager = manager;
	//	}
}
