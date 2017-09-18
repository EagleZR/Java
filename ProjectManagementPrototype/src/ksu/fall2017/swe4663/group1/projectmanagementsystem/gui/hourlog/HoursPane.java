package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.layout.Pane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;

public class HoursPane extends Pane implements ProjectPane {

	private Config config;
	SelectPersonPane selectPersonPane;
	WorkedHoursSubmissionPane detailsPane;

	public HoursPane( Project project, Config config ) {
		LoggingTool.print( "Constructing new HoursPane." );
		// Select Person Pane
		LoggingTool.print( "HoursPane: Creating new SelectPersonPane." );
		selectPersonPane = new SelectPersonPane( project, config );
		selectPersonPane.layoutXProperty().setValue( 0 );
		selectPersonPane.layoutYProperty().setValue( 0 );
		selectPersonPane.prefWidthProperty().bind( this.widthProperty().divide( 3 ) );
		selectPersonPane.prefHeightProperty().bind( this.heightProperty() );
		this.getChildren().add( selectPersonPane );

		// Fill In Details Pane
		LoggingTool.print( "HoursPane: Creating new WorkedHoursSubmissionPane." );
		detailsPane = new WorkedHoursSubmissionPane( project, config );
		detailsPane.layoutXProperty()
				.bind( selectPersonPane.layoutXProperty().add( selectPersonPane.widthProperty() ) );
		detailsPane.layoutYProperty().bind( selectPersonPane.layoutYProperty() );
		detailsPane.prefWidthProperty().bind( this.widthProperty().divide( 3 ).multiply( 2 ) );
		detailsPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		detailsPane.maxHeightProperty().bind( this.heightProperty().divide( 2 ) );
		this.getChildren().add( detailsPane );

		// Display Hour Log Pane

		selectPersonPane.setSelectResponse( () -> {
			LoggingTool
					.print( "HoursPane: Relaying newly-selected person from SelectPersonPane to WorkedHoursSubmissionPane." );
			detailsPane.registerNewSelectedPerson( selectPersonPane.getSelectedPerson() );
		} );
	}

	public void loadNewProject( Project project ) {
		selectPersonPane.loadNewProject( project );
	}

}
