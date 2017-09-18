package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import eaglezr.javafx.stages.PopupStage;
import eaglezr.support.logs.LoggingTool;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.PersonButton;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.PersonButtonScrollPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.TeamPresenter;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

class TeamMembersPane extends FramedPane implements TeamPresenter {

	private Stage primaryStage;
	private Project project;
	private PersonButtonScrollPane scrollPane;

	TeamMembersPane( Stage primaryStage, Project project ) {
		LoggingTool.print( "Constructing a new TeamMembersPane." );
		this.primaryStage = primaryStage;
		this.project = project;
		this.project.getTeam().addToDistro( this );
		setup();
		for ( Person person : project.getTeam().getMembers() ) {
			addPerson( person );
		}
	}

	private void setup() {
		// Label
		LoggingTool.print( "TeamMembersPane: Creating title label in TeamMembersPane." );
		Label label = new Label( "Team Members (Click to edit): " );
		label.layoutXProperty().setValue( 10 );
		label.layoutYProperty().setValue( 10 );
		this.getChildren().add( label );

		// ScrollPane
		LoggingTool.print( "TeamMembersPane: Creating PersonButtonScrollPane in TeamMembersPane." );
		scrollPane = new PersonButtonScrollPane();
		scrollPane.layoutXProperty().bind( label.layoutXProperty() );
		scrollPane.layoutYProperty().bind( label.layoutYProperty().add( 5 ).add( label.heightProperty() ) );
		scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( 20 ) );
		this.getChildren().add( scrollPane );

		// Add Button
		LoggingTool.print( "TeamMembersPane: Creating the \"Add Member\" Button in the TeamMembersPane." );
		Button addButton = new Button( "+ Add Member +" );
		addButton.layoutXProperty().bind( scrollPane.layoutXProperty() );
		addButton.layoutYProperty().bind( scrollPane.layoutYProperty().add( scrollPane.heightProperty() ).add( 10 ) );
		addButton.setOnAction( e -> {
			addPerson();
		} );
		this.getChildren().add( addButton );

		scrollPane.prefHeightProperty().bind( this.heightProperty().subtract( 35 ).subtract( label.heightProperty() )
				.subtract( addButton.heightProperty() ) );
	}

	private void addPerson() {
		Pane pane = new Pane();
		Scene scene = new Scene( pane, 280, 70 );
		PopupStage popupStage = new PopupStage( scene, primaryStage );

		// Label
		Label label = new Label( "Member's Name: " );
		label.layoutXProperty().setValue( 10 );
		label.layoutYProperty().setValue( 10 );
		pane.getChildren().add( label );

		// Text Field
		TextField textField = new TextField();
		textField.layoutXProperty().bind( label.layoutXProperty().add( label.widthProperty() ).add( 5 ) );
		textField.layoutYProperty().bind( label.layoutYProperty() );
		pane.getChildren().add( textField );

		// Button
		Button button = new Button( "Add" );
		button.layoutXProperty()
				.bind( pane.widthProperty().divide( 2 ).subtract( button.widthProperty().divide( 2 ) ) );
		button.layoutYProperty().bind( label.layoutYProperty().add( 10 ).add( label.heightProperty() ) );
		button.setOnAction( e -> {
			if ( !textField.getText().equals( "" ) ) {
				// LATER Check how to send message to Manager Pane
				LoggingTool.print( "TeamMembersPane: Creating a new Person named " + textField.getText()
						+ " in the TeamMembersPane." );
				Person newMember = new Person( textField.getText() );
				project.getTeam().addToTeam( newMember );
				addPerson( newMember );
				popupStage.close();
			}
		} );

		pane.prefWidthProperty().bind( textField.layoutXProperty().add( textField.widthProperty() ).add( 10 ) );
		pane.getChildren().add( button );

		popupStage.show();
	}

	@Override public void addPerson( Person person ) {
		LoggingTool.print( "TeamMembersPane: Adding " + person.getName() + " to the TeamMembersPane." );
		PersonButton newMemberButton = new PersonButton( person );
		scrollPane.addButton( newMemberButton );
		newMemberButton.setOnAction( i -> {
			editMember( newMemberButton );
		} );
	}

	private void editMember( PersonButton personButton ) {
		LoggingTool
				.print( "TeamMembersPane: Editing " + personButton.getPerson().getName() + " in the TeamMembersPane." );
		Pane pane = new Pane();
		Scene scene = new Scene( pane, 280, 70 );
		PopupStage popupStage = new PopupStage( scene, primaryStage );

		// Label
		Label label = new Label( "Member's Name: " );
		label.layoutXProperty().setValue( 10 );
		label.layoutYProperty().setValue( 10 );
		pane.getChildren().add( label );

		// Text Field
		TextField textField = new TextField();
		textField.setText( personButton.getText() );
		textField.layoutXProperty().bind( label.layoutXProperty().add( label.widthProperty() ).add( 5 ) );
		textField.layoutYProperty().bind( label.layoutYProperty() );
		pane.getChildren().add( textField );

		// Edit Button
		Button edit = new Button( "Edit" );
		edit.layoutXProperty().bind( pane.widthProperty().divide( 2 ).subtract( edit.widthProperty() ).subtract( 5 ) );
		edit.layoutYProperty().bind( label.layoutYProperty().add( 10 ).add( label.heightProperty() ) );
		edit.setOnAction( e -> {
			if ( !textField.getText().equals( "" ) && !textField.getText().equals( edit.getText() ) ) {
				LoggingTool
						.print( "TeamMembersPane: Changing the name of " + personButton.getPerson().getName() + " to "
								+ textField.getText() + "in the TeamMembersPane." );
				personButton.changeName( textField.getText() );
			}
			popupStage.close();
		} );

		// Delete Button
		Button delete = new Button( "Delete" );
		delete.layoutXProperty().bind( pane.widthProperty().divide( 2 ).add( delete.widthProperty() ).add( 5 ) );
		delete.layoutYProperty().bind( label.layoutYProperty().add( 10 ).add( label.heightProperty() ) );
		delete.setOnAction( e -> {
			LoggingTool.print( "TeamMembersPane: Deleting " + personButton.getPerson().getName()
					+ " from the TeamMembersPane." );
			removePerson( personButton.getPerson() );
			scrollPane.removeButton( personButton );
			popupStage.close();
		} );

		pane.prefWidthProperty().bind( textField.layoutXProperty().add( textField.widthProperty() ).add( 10 ) );
		pane.getChildren().add( edit );
		pane.getChildren().add( delete );

		popupStage.show();
	}

	@Override public void removePerson( Person person ) {
		this.project.getTeam().removeFromTeam( person );
	}

	@Override public void updateTeamChange() {

	}
}
