package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.ProjectHourLog;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;

public class HourTypeDisplayPane extends FramedPane {

	private ProjectHourLog hourLog;
	private WorkedHourType hourType;
	private ProgressBar progressBar;

	public HourTypeDisplayPane( WorkedHourType hourType, Project project ) {
		this.hourLog = project.getTeam().getProjectHourLog();
		this.hourType = hourType;

		Label label = new Label( hourType.getText() );
		label.layoutXProperty().setValue( 10 );
		label.layoutYProperty().setValue( 10 );
		this.getChildren().add( label );

		progressBar = new ProgressBar();
		progressBar.layoutXProperty().bind( label.layoutXProperty() );
		progressBar.layoutYProperty().bind( label.layoutYProperty().add( label.heightProperty() ).add( 10 ) );
		progressBar.prefWidthProperty().bind( this.widthProperty() );
		progressBar.prefHeightProperty()
				.bind( this.heightProperty().subtract( 30 ).subtract( label.heightProperty() ) );
		this.getChildren().add( progressBar );
		update();
	}

	public void update() {
		progressBar.setProgress( hourLog.getEffort( WorkedHourType.ANY ) / hourLog.getEffort( this.hourType ) );
	}
}
