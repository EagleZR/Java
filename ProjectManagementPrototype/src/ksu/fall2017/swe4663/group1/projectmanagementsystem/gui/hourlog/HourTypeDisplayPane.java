package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.ProjectHourLog;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;

public class HourTypeDisplayPane extends FramedPane {

	private Config config;
	private ProjectHourLog hourLog;
	private WorkedHourType hourType;
	private ProgressBar progressBar;

	public HourTypeDisplayPane( WorkedHourType hourType, Project project, Config config ) {
		this.config = config;
		this.hourLog = project.getTeam().getProjectHourLog();
		this.hourType = hourType;

		Label label = new Label( hourType.toString() );
		label.layoutXProperty().setValue( config.buffer );
		label.layoutYProperty().setValue( config.buffer );
		this.getChildren().add( label );

		progressBar = new ProgressBar();
		progressBar.layoutXProperty().bind( label.layoutXProperty() );
		progressBar.layoutYProperty().bind( label.layoutYProperty().add( label.heightProperty() ).add( config.buffer ) );
		progressBar.prefWidthProperty().bind( this.widthProperty() );
		progressBar.prefHeightProperty()
				.bind( this.heightProperty().subtract( config.buffer * 3 ).subtract( label.heightProperty() ) );
		this.getChildren().add( progressBar );
		update();
	}

	public void update() {
		progressBar.setProgress( hourLog.getEffort( WorkedHourType.ANY ) / hourLog.getEffort( this.hourType ) );
	}
}
