package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Label;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;

public class RequirementsPane extends FramedPane {

	public RequirementsPane() {
		LoggingTool.print( "Constructing new RequirementsPane." );
		Label label = new Label( "Requirements Pane" );
		label.layoutXProperty().bind( this.widthProperty().divide( 2 ).subtract( label.widthProperty().divide( 2 ) ) );
		label.layoutYProperty()
				.bind( this.heightProperty().divide( 2 ).subtract( label.heightProperty().divide( 2 ) ) );
		this.getChildren().addAll( label );
	}
}
