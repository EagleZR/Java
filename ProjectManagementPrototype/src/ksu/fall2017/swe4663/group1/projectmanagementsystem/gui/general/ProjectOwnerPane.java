package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import eaglezr.javafx.stages.PopupStage;
import eaglezr.support.logs.LoggingTool;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.PersonButton;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.PersonButtonScrollPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.TeamPresenter;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.PersonNotOnTeamException;

public class ProjectOwnerPane extends FramedPane implements TeamPresenter {

	private Project project;
	private Person manager;
	private Label label;
	private PersonButtonScrollPane scrollPane;
	private Stage primaryStage;

	public ProjectOwnerPane( Project project, Stage primaryStage ) throws PersonNotOnTeamException {
		LoggingTool.print( "Creating new ProjectOwnerPane." );
		this.project = project;
		this.project.getTeam().addToDistro( this );
		this.primaryStage = primaryStage;
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
		for ( Person person : project.getTeam().getMembers() ) {
			addPerson( person );
		}
	}

	private void setup() {
		// Draw Label
		LoggingTool.print( "ProjectOwnerPane: Creating title label in ProjectOwnerPane." );
		label = new Label( "" );
		label.prefWidthProperty().bind( this.widthProperty().subtract( 20 ) );
		label.layoutXProperty().setValue( 10 );
		label.layoutYProperty().setValue( 10 );
		this.getChildren().add( label );

		// Draw ScrollPane of Person Buttons
		LoggingTool.print( "ProjectOwnerPane: Creating PersonButtonScrollPane in ProjectOwnerPane." );
		scrollPane = new PersonButtonScrollPane();
		scrollPane.layoutXProperty().bind( label.layoutXProperty() );
		scrollPane.layoutYProperty().bind( label.layoutYProperty().add( 5 ).add( label.heightProperty() ) );
		scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( 20 ) );
		scrollPane.prefHeightProperty().bind( this.heightProperty().subtract( 25 ).subtract( label.heightProperty() ) );
		this.getChildren().add( scrollPane );

		update();
	}

	private void update() {
		LoggingTool.print( "ProjectOwnerPane: Updating ProjectOwnerPane." );
		try {
			manager = project.getTeam().getManager();
		} catch ( PersonNotOnTeamException e ) {
			LoggingTool.print( "ProjectOwnerPane: Unable to update manager." );
			// LATER Use status label instead?
			//			ErrorPopupSystem.displayMessage( "Unable to update manager." ); // Probably not a good idea....
		}
		for ( PersonButton button : scrollPane.getButtons() ) {
			button.setDefaultButton( button.getPerson().isManager() );
		}
		label.setText( "Project Manager: " + manager.getName() );
	}

	@Override public void addPerson( Person person ) {
		LoggingTool.print( "ProjectOwnerPane: Adding person: " + person.getName() + "." );
		PersonButton personButton = new PersonButton( person );
		personButton.setOnAction( e -> {
			Pane pane = new Pane();
			Scene scene = new Scene( pane, 280, 70 );
			PopupStage popupStage = new PopupStage( scene, primaryStage );

			Label label = new Label( "Set " + person.getName() + " as the manager?" );
			label.layoutXProperty().bind( pane.widthProperty().divide( 2 ).subtract( label.widthProperty().divide( 2 ) ) );
			label.layoutYProperty().setValue( 10 );
			pane.getChildren().add( label );

			// Edit Button
			Button edit = new Button( "Yes" );
			edit.layoutXProperty().bind( pane.widthProperty().divide( 2 ).subtract( edit.widthProperty() ).subtract( 5 ) );
			edit.layoutYProperty().bind( label.layoutYProperty().add( 10 ).add( label.heightProperty() ) );
			edit.setOnAction( a -> {
				project.getTeam().demote( manager );
				project.getTeam().promote( person );
				this.manager = person;
				popupStage.close();
			} );

			// Delete Button
			Button delete = new Button( "No" );
			delete.layoutXProperty().bind( pane.widthProperty().divide( 2 ).add( delete.widthProperty() ).add( 5 ) );
			delete.layoutYProperty().bind( label.layoutYProperty().add( 10 ).add( label.heightProperty() ) );
			delete.setOnAction( a -> {
				popupStage.close();
			} );

//			pane.prefWidthProperty().bind( textField.layoutXProperty().add( textField.widthProperty() ).add( 10 ) );
			pane.getChildren().add( edit );
			pane.getChildren().add( delete );

			popupStage.show();
		} );
		scrollPane.addButton( personButton );
	}

	@Override public void removePerson( Person person ) {
		LoggingTool.print( "ProjectOwnerPane: Removing person: " + person.getName() + "." );
		scrollPane.removePerson( person );
	}

	@Override public void updateTeamChange() {
		LoggingTool.print( "ProjectOwnerPane: Updating people." );
		// Check every member on team has button
		for ( Person person : project.getTeam().getMembers() ) {
			if ( !scrollPane.containsPerson( person ) ) {
				addPerson( person );
			}
		}

		// Check every button has member on team
		for ( PersonButton button : scrollPane.getButtons() ) {
			boolean isOnTeam = false;
			for ( Person person : project.getTeam().getMembers() ) {
				if ( button.getPerson().equals( person ) ) {
					isOnTeam = true;
				}
			}
			if ( !isOnTeam ) {
				LoggingTool.print( "PersonButtonScrollPane: Found a Button for person" + button.getText()
						+ " who is no longer on the team." );
				scrollPane.removeButton( button );
			}
		}

		update();

	}
}
