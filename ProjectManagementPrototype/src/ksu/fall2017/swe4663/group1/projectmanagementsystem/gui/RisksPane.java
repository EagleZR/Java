package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import javafx.scene.control.Label;

public class RisksPane extends FramedPane {

	public RisksPane() {
		Label label = new Label( "Risks Pane" );
		label.layoutXProperty().bind( this.widthProperty().divide( 2 ).subtract( label.widthProperty().divide( 2 ) ) );
		label.layoutYProperty()
				.bind( this.heightProperty().divide( 2 ).subtract( label.heightProperty().divide( 2 ) ) );
		this.getChildren().addAll( label );
	}
}
