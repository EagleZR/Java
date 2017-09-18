package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import eaglezr.support.logs.LoggingTool;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;

public class WorkedHoursSubmissionPane extends FramedPane {

	private Person selectedPerson;
	private Project project;
	private Label personName;
	private Label personID;
	private Label personManager;
	private Label hourType;
	// https://stackoverflow.com/questions/27801119/populating-javafx-combobox-or-choicebox-from-enum
	private ComboBox<WorkedHourType> selectHourType;
	private Label duration;
	private TextField inputDuration;
	private Button submitButton;

	public WorkedHoursSubmissionPane( Project project ) {
		LoggingTool.print( "Constructing new WorkedHoursSubmissionPane." );
		this.project = project;

		personName = new Label( "Person's name: " );
		personID = new Label( "Person's ID: " );
		personManager = new Label( "Person is a manager?: " );
		hourType = new Label( "Please select the type of worked hours: " );
		selectHourType = new ComboBox<>( FXCollections.observableArrayList( WorkedHourType.values() ) );
		duration = new Label( "Please select the duration of the worked hours:" );
		inputDuration = new TextField();
		submitButton = new Button( "Submit" );

		setup();
	}

	private void setup() {
		// Scroll Pane
		LoggingTool.print( "WorkedHoursSubmissionPane: Creating ScrollPane." );
		ScrollPane scrollPane = new ScrollPane();
		this.getChildren().add( scrollPane );
		scrollPane.setVbarPolicy( ScrollPane.ScrollBarPolicy.ALWAYS );
		scrollPane.setHbarPolicy( ScrollPane.ScrollBarPolicy.NEVER );
		scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( 2 ) );
		scrollPane.prefHeightProperty()
				.bind( this.heightProperty().subtract( 20 ).subtract( submitButton.heightProperty() ) );
		scrollPane.layoutXProperty().setValue( 1 );
		scrollPane.layoutYProperty().setValue( 1 );
		scrollPane.setFitToWidth( true );

		// Content pane
		LoggingTool.print( "WorkedHoursSubmissionPane: Creating content Pane." );
		Pane form = new Pane();
		scrollPane.setContent( form );

		// Person Name Label
		personName.layoutXProperty().setValue( 10 );
		personName.layoutYProperty().setValue( 10 );
		personName.wrapTextProperty().setValue( true );
		form.getChildren().add( personName );

		// Person ID Label
		personID.layoutXProperty().bind( personName.layoutXProperty() );
		personID.layoutYProperty().bind( personName.layoutYProperty().add( personName.heightProperty() ).add( 10 ) );
		personID.wrapTextProperty().setValue( true );
		form.getChildren().add( personID );

		// Person Manager Label
		personManager.layoutXProperty().bind( personName.layoutXProperty() );
		personManager.layoutYProperty().bind( personID.layoutYProperty().add( personID.heightProperty() ).add( 10 ) );
		personManager.wrapTextProperty().setValue( true );
		form.getChildren().add( personManager );

		// Hour Type Label
		hourType.layoutXProperty().bind( personName.layoutXProperty() );
		hourType.layoutYProperty()
				.bind( personManager.layoutYProperty().add( personManager.heightProperty() ).add( 10 ) );
		hourType.wrapTextProperty().setValue( true );
		form.getChildren().add( hourType );

		// Select Hour Type ComboBox
		selectHourType.layoutXProperty().bind( hourType.layoutXProperty() );
		selectHourType.layoutYProperty().bind( hourType.layoutYProperty().add( hourType.heightProperty() ).add( 10 ) );
		form.getChildren().add( selectHourType );

		// Duration Label
		duration.layoutXProperty().bind( selectHourType.layoutXProperty() );
		duration.layoutYProperty()
				.bind( selectHourType.layoutYProperty().add( selectHourType.heightProperty() ).add( 10 ) );
		duration.wrapTextProperty().setValue( true );
		form.getChildren().add( duration );

		// Input Duration TextField
		inputDuration.layoutXProperty().bind( duration.layoutXProperty() );
		inputDuration.layoutYProperty().bind( duration.layoutYProperty().add( duration.heightProperty() ).add( 10 ) );
		form.getChildren().add( inputDuration );

		// Submit Button
		submitButton.layoutXProperty().bind( scrollPane.layoutXProperty().add( 10 ) );
		submitButton.layoutYProperty()
				.bind( scrollPane.layoutYProperty().add( scrollPane.heightProperty() ).add( 10 ) );
		submitButton.prefWidthProperty().bind( this.widthProperty().subtract( 20 ) );
		submitButton.setOnAction( e -> {

		} );
		this.getChildren().add( submitButton );

		update();
	}

	private void update() {
		// Person Name
		personName.setText( "Person's name: " + ( selectedPerson == null ? "" : selectedPerson.getName() ) );

		// Person ID
		personID.setText( "Person's ID: " + ( selectedPerson == null ? "" : selectedPerson.getID() ) );

		// Person Manager
		personManager.setText( "Person is a manager? " + ( selectedPerson == null ?
				"" :
				( selectedPerson.isManager() ? "Yes" : "No" ) ) );
	}

	public void registerNewSelectedPerson( Person person ) {
		LoggingTool.print( "WorkedHoursSubmissionPane: Registering newly-selected Person: " + ( selectedPerson == null ?
				"" :
				selectedPerson.getName() ) );
		this.selectedPerson = person;
		update();
	}
}
