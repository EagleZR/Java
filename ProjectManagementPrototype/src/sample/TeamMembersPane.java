package sample;

import eaglezr.javafx.stages.PopupStage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamMembersPane extends FramedPane {

	private ArrayList<Button> teamMembers = new ArrayList<>();
	private Pane membersPane;
	private Button addButton;
	private Stage primaryStage;

	public TeamMembersPane( Stage primaryStage, String... teamMember ) {
		this( primaryStage, Arrays.asList( teamMember ) );
	}

	public TeamMembersPane( Stage primaryStage, List teamMembers ) {
		this.primaryStage = primaryStage;
		teamMembers.addAll( teamMembers );
		setup();
	}

	private void setup() {
		// Label
		Label label = new Label( "Team Members (Click to edit): " );
		label.layoutXProperty().setValue( 10 );
		label.layoutYProperty().setValue( 10 );
		this.getChildren().add( label );

		// ScrollPane
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.layoutXProperty().bind( label.layoutXProperty() );
		scrollPane.layoutYProperty().bind( label.layoutYProperty().add( 5 ).add( label.heightProperty() ) );
		scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( 20 ) );
		scrollPane.prefHeightProperty().bind( this.heightProperty().subtract( 25 ).subtract( label.heightProperty() ) );
		scrollPane.setHbarPolicy( ScrollPane.ScrollBarPolicy.NEVER );
		scrollPane.setVbarPolicy( ScrollPane.ScrollBarPolicy.ALWAYS );
		scrollPane.setFitToHeight( true );
		scrollPane.setFitToWidth( true );
		this.getChildren().add( scrollPane );

		// Members Pane
		membersPane = new Pane();
		scrollPane.setContent( membersPane );

		Rectangle rectangle = new Rectangle( membersPane.getWidth(), membersPane.getHeight(), Color.RED );
		rectangle.widthProperty().bind( membersPane.widthProperty() );
		rectangle.heightProperty().bind( membersPane.heightProperty() );

		// Add Button
		addButton = new Button( "+ Add Member +" );
		addButton.prefWidthProperty().bind( membersPane.widthProperty() );
		addButton.layoutXProperty().setValue( 0 );
		addButton.layoutYProperty().setValue( 0 );
		addButton.setOnAction( e -> {
			addMember();
		} );
		membersPane.getChildren().add( addButton );
	}

	private void addMember() {
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
				Button newMemberButton = new Button( textField.getText() );
				if ( teamMembers.size() != 0 ) {
					Button prevMember = teamMembers.get( teamMembers.size() - 1 );
					newMemberButton.layoutXProperty().bind( prevMember.layoutXProperty() );
					newMemberButton.layoutYProperty()
							.bind( prevMember.layoutYProperty().add( prevMember.heightProperty() ) );
				} else {
					newMemberButton.layoutXProperty().setValue( 0 );
					newMemberButton.layoutYProperty().setValue( 0 );
				}
				newMemberButton.prefWidthProperty().bind( membersPane.widthProperty() );
				addButton.layoutXProperty().bind( newMemberButton.layoutXProperty() );
				addButton.layoutYProperty()
						.bind( newMemberButton.layoutYProperty().add( newMemberButton.heightProperty() ) );
				membersPane.getChildren().add( newMemberButton );
				newMemberButton.setOnAction( i -> {
					editMember( newMemberButton );
				} );
				popupStage.close();
			}
		} );

		pane.prefWidthProperty().bind( textField.layoutXProperty().add( textField.widthProperty() ).add( 10 ) );
		pane.getChildren().add( button );

		popupStage.show();
	}

	private void editMember( Button member ) {
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

		// Edit Button
		Button edit = new Button( "Edit" );
		edit.layoutXProperty().bind( pane.widthProperty().divide( 2 ).subtract( edit.widthProperty() ).subtract( 5 ) );
		edit.layoutYProperty().bind( label.layoutYProperty().add( 10 ).add( label.heightProperty() ) );
		edit.setOnAction( e -> {
			if ( !textField.getText().equals( "" ) && !textField.getText().equals( edit.getText() ) ) {
				member.setText( textField.getText() );
				popupStage.close();
			}
		} );

		// Delete Button
		Button delete = new Button( "Delete" );
		delete.layoutXProperty().bind( pane.widthProperty().divide( 2 ).add( delete.widthProperty() ).add( 5 ) );
		delete.layoutYProperty().bind( label.layoutYProperty().add( 10 ).add( label.heightProperty() ) );
		delete.setOnAction( e -> {
			// FIXME Debug this
			int index = teamMembers.indexOf( member );
			if ( teamMembers.size() == 1 ) {
				addButton.layoutXProperty().setValue( 0 );
				addButton.layoutYProperty().setValue( 0 );
			} else if ( index + 1 == teamMembers.size() ) {
				Button prevMember = teamMembers.get( index - 1 );
				addButton.layoutXProperty().bind( prevMember.layoutXProperty() );
				addButton.layoutYProperty()
						.bind( prevMember.layoutYProperty().add( 10 ).add( prevMember.heightProperty() ) );
			} else {
				Button prevMember = teamMembers.get( index - 1 );
				Button nextMember = teamMembers.get( index + 1 );
				nextMember.layoutXProperty().bind( prevMember.layoutXProperty() );
				nextMember.layoutYProperty()
						.bind( prevMember.layoutYProperty().add( 10 ).add( prevMember.heightProperty() ) );
			}
			membersPane.getChildren().remove( member );
			teamMembers.remove( member );
			popupStage.close();
		} );

		pane.prefWidthProperty().bind( textField.layoutXProperty().add( textField.widthProperty() ).add( 10 ) );
		pane.getChildren().add( edit );
		pane.getChildren().add( delete );

		popupStage.show();
	}
}
