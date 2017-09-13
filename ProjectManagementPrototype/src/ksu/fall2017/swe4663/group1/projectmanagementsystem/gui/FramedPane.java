package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class FramedPane extends Pane {

	public FramedPane() {
		// Draw Frame
		Line topLine = new Line( 1, 1, this.getWidth(), 1 );
		Line rightLine = new Line( this.getWidth(), 1, this.getWidth(), this.getHeight() );
		Line leftLine = new Line( 1, 1, 1, this.getHeight() );
		Line bottomLine = new Line( 1, this.getHeight(), this.getWidth(), this.getHeight() );

		topLine.endXProperty().bind( this.widthProperty().subtract( 1 ) );

		rightLine.startXProperty().bind( this.widthProperty().subtract( 1 ) );
		rightLine.endXProperty().bind( rightLine.startXProperty() );
		rightLine.endYProperty().bind( this.heightProperty().subtract( 1 ) );

		leftLine.endYProperty().bind( this.heightProperty().subtract( 1 ) );

		bottomLine.startYProperty().bind( this.heightProperty().subtract( 1 ) );
		bottomLine.endXProperty().bind( this.widthProperty().subtract( 1 ) );
		bottomLine.endYProperty().bind( bottomLine.startYProperty() );

		this.getChildren().addAll( topLine, rightLine, leftLine, bottomLine );
	}
}
