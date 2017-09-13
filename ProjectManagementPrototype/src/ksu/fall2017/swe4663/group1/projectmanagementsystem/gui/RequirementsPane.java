package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import javafx.scene.control.Label;

public class RequirementsPane extends FramedPane {

	public RequirementsPane() {
		Label label = new Label( "Requirements Pane" );
		label.layoutXProperty().bind( this.widthProperty().divide( 2 ).subtract( label.widthProperty().divide( 2 ) ) );
		label.layoutYProperty()
				.bind( this.heightProperty().divide( 2 ).subtract( label.heightProperty().divide( 2 ) ) );
		this.getChildren().addAll( label );
	}
}
