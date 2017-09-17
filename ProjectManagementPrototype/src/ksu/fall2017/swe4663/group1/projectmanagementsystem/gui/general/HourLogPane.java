package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Label;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;

public class HourLogPane extends FramedPane {

	public HourLogPane() {
		LoggingTool.print( "Constructing new HourLogPane." );
		Label label = new Label( "WorkedHours Monitoring and Tracking Pane" );
		label.layoutXProperty().bind( this.widthProperty().divide( 2 ).subtract( label.widthProperty().divide( 2 ) ) );
		label.layoutYProperty()
				.bind( this.heightProperty().divide( 2 ).subtract( label.heightProperty().divide( 2 ) ) );
		this.getChildren().addAll( label );
	}
}
