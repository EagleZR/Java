package eaglezr.taskplanner.gui;

import eaglezr.taskplanner.system.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TaskPane extends Pane {

	public final static Color START_TASK_COLOR = Color.ORANGE;
	public final static Color FINAL_TASK_COLOR = Color.GREEN;
	public final static Color CRITICAL_TASK_COLOR = Color.RED;
	public final static Color NORMAL_TASK_COLOR = Color.BLUE;

	private Task task;
	private Color color;

	public TaskPane( Task task ) {
		this.task = task;

		if ( this.task.getSlackTime() == 0 ) {
			setColor( CRITICAL_TASK_COLOR );
		} else if ( this.task.isFinalTask() ) {
			setColor( FINAL_TASK_COLOR );
		} else if ( this.task.isStartTask() ) {
			setColor( START_TASK_COLOR );
		} else {
			setColor( NORMAL_TASK_COLOR );
		}
	}

	public void setColor( Color color ) {
		this.color = color;
		draw();
	}

	private void draw() {
		Circle circle = new Circle( 50, this.color );
		circle.radiusProperty().bind( widthProperty().divide( 2 ) );
		circle.centerYProperty().bind( heightProperty().divide( 2 ) );
		circle.centerXProperty().bind( widthProperty().divide( 2 ) );
		getChildren().add( circle );

		Label taskID = new Label( getLabelID() );
		taskID.setTextFill( Color.WHITE );
		taskID.layoutXProperty().bind( circle.centerXProperty().subtract( taskID.widthProperty().divide( 2 ) ) );
		taskID.layoutYProperty().bind( circle.centerYProperty().subtract( circle.radiusProperty() ) );
		taskID.scaleXProperty().bind( circle.radiusProperty().divide( 20 ) );
		taskID.scaleYProperty().bind( taskID.scaleXProperty() );
		getChildren().add( taskID );

		Label taskDuration = new Label( "" + task.getDuration() );
		taskDuration.setTextFill( Color.WHITE );
		taskDuration.layoutXProperty().bind( circle.centerXProperty().subtract( taskDuration.widthProperty().divide( 2 ) ) );
		taskDuration.layoutYProperty().bind( circle.centerYProperty().add( circle.radiusProperty() ) );
		taskDuration.scaleXProperty().bind( circle.radiusProperty().divide( 20 ) );
		taskDuration.scaleYProperty().bind( taskDuration.scaleXProperty() );
		getChildren().add( taskDuration );

		// For Debugging
		/*
		// Horizontal center line
		Line debugLine1 = new Line();
		debugLine1.startXProperty().bind( circle.centerXProperty().subtract( circle.radiusProperty() ) );
		debugLine1.endXProperty().bind( circle.centerXProperty().add( circle.radiusProperty() ) );
		debugLine1.startYProperty().bind( circle.centerYProperty() );
		debugLine1.endYProperty().bind( circle.centerYProperty() );
		getChildren().add( debugLine1 );

		// Vertical center line
		Line debugLine2 = new Line();
		debugLine2.startXProperty().bind( circle.centerXProperty() );
		debugLine2.endXProperty().bind( circle.centerXProperty() );
		debugLine2.startYProperty().bind( circle.centerYProperty().subtract( circle.radiusProperty() ) );
		debugLine2.endYProperty().bind( circle.centerYProperty().add( circle.radiusProperty() ) );
		getChildren().add( debugLine2 );

		// Upper radius box
		Line debugLine3 = new Line();
		debugLine3.startXProperty().bind( circle.centerXProperty().subtract( circle.radiusProperty() ) );
		debugLine3.startYProperty().bind( circle.centerYProperty() );
		debugLine3.endXProperty().bind( debugLine3.startXProperty() );
		debugLine3.endYProperty().bind( circle.centerYProperty().subtract( circle.radiusProperty() ) );
		getChildren().add( debugLine3 );

		Line debugLine4 = new Line();
		debugLine4.startXProperty().bind( circle.centerXProperty().subtract( circle.radiusProperty() ) );
		debugLine4.startYProperty().bind( circle.centerYProperty().subtract( circle.radiusProperty() ) );
		debugLine4.endXProperty().bind( circle.centerXProperty().add( circle.radiusProperty() ) );
		debugLine4.endYProperty().bind( debugLine4.startYProperty() );
		getChildren().add( debugLine4 );

		Line debugLine5 = new Line();
		debugLine5.startXProperty().bind( circle.centerXProperty().subtract( circle.radiusProperty() ) );
		debugLine5.startYProperty().bind( circle.centerYProperty() );
		debugLine5.endXProperty().bind( debugLine5.startXProperty() );
		debugLine5.endYProperty().bind( circle.centerYProperty().subtract( circle.radiusProperty() ) );
		getChildren().add( debugLine5 );

		// Upper label box
		Line debugLine6 = new Line();
		debugLine6.startXProperty().bind( circle.centerXProperty().subtract( taskID.scaleXProperty().divide( 2 ) ) );
		debugLine6.startYProperty().bind( circle.centerYProperty().subtract( circle.radiusProperty().subtract( taskID.scaleYProperty() ).divide( 2 ) ) );
		debugLine6.endXProperty().bind( circle.centerXProperty().add( taskID.widthProperty().divide( 2 ) ) );
		debugLine6.endYProperty().bind( debugLine6.startYProperty() );
		getChildren().add( debugLine6 );

		Line debugLine7 = new Line();
		debugLine7.startXProperty().bind( circle.centerXProperty().subtract( taskID.scaleXProperty().divide( 2 ) ) );
		debugLine7.startYProperty().bind( circle.centerYProperty().subtract( circle.radiusProperty().subtract( taskID.scaleYProperty() ).divide( 2 ) ) );
		debugLine7.endXProperty().bind( debugLine7.startXProperty() );
		debugLine7.endYProperty().bind( circle.centerYProperty().subtract( circle.radiusProperty().subtract( taskID.scaleYProperty() ).divide( 2 ) ).add( taskID.scaleYProperty() ) );
		getChildren().add( debugLine7 );

		Line debugLine8 = new Line();
		debugLine8.startXProperty().bind( circle.centerXProperty().add( taskID.scaleXProperty().divide( 2 ) ) );
		debugLine8.startYProperty().bind( circle.centerYProperty().subtract( circle.radiusProperty().subtract( taskID.scaleYProperty() ).divide( 2 ) ) );
		debugLine8.endXProperty().bind( debugLine8.startXProperty() );
		debugLine8.endYProperty().bind( circle.centerYProperty().subtract( circle.radiusProperty().subtract( taskID.scaleYProperty() ).divide( 2 ) ).add( taskID.scaleYProperty() ) );
		getChildren().add( debugLine8 );

		Line debugLine9 = new Line();
		debugLine9.startXProperty().bind( circle.centerXProperty().subtract( taskID.scaleXProperty().divide( 2 ) ) );
		debugLine9.startYProperty().bind( circle.centerYProperty().subtract( circle.radiusProperty().subtract( taskID.scaleYProperty() ).divide( 2 ) ).add( taskID.scaleYProperty() ) );
		debugLine9.endXProperty().bind( circle.centerXProperty().add( taskID.scaleXProperty().divide( 2 ) ) );
		debugLine9.endYProperty().bind( debugLine9.startYProperty() );
		getChildren().add( debugLine9 );
		*/
	}

	// LATER Set this to shorten the string
	private String getLabelID() {
		return task.getID();
	}

}
