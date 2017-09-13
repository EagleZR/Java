package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class DescriptionPane extends FramedPane {

	private String description;
	private TextArea textArea;
	private Label statusLabel;

	public DescriptionPane() {
		description = "";
		setup();
	}

	public DescriptionPane( String description ) {
		this.description = description;
		setup();
	}

	private void setup() {
		// Label
		Label label = new Label( "Project Description: " );
		label.layoutXProperty().setValue( 10 );
		label.layoutYProperty().setValue( 10 );

		this.getChildren().add( label );

		// Text Area
		textArea = new TextArea( description );
		textArea.prefWidthProperty().bind( this.widthProperty().subtract( 20 ) );
		textArea.layoutXProperty().bind( label.layoutXProperty() );
		textArea.layoutYProperty().bind( label.layoutYProperty().add( label.heightProperty() ).add( 10 ) );

		textArea.wrapTextProperty().setValue( true );
		textArea.setOnKeyTyped( e -> {
			showStatusChanged();
		} );

		this.getChildren().add( textArea );

		// Update Button
		Button update = new Button( "Update" );
		update.setOnAction( e -> {
			this.description = textArea.getText();
			showStatusCurrent();
		} );

		update.layoutXProperty().bind( textArea.layoutXProperty().add( textArea.widthProperty() ).subtract( update.widthProperty() ) );
		update.layoutYProperty().bind( this.heightProperty().subtract( 10 ).subtract( update.heightProperty() ) );
		this.getChildren().add( update );

		// Reset Button
		Button reset = new Button( "Reset" );
		reset.setOnAction( e -> {
			textArea.setText( this.description );
			showStatusCurrent();
		} );

		reset.layoutXProperty().bind( update.layoutXProperty().subtract( reset.widthProperty().add( 10 ) ) );
		reset.layoutYProperty().bind( update.layoutYProperty() );
		this.getChildren().add( reset );

		textArea.prefHeightProperty().bind( update.layoutYProperty().subtract( textArea.layoutYProperty().add( 10 ) ) );

		// Status Label
		statusLabel = new Label("Status: Current" );
		statusLabel.layoutXProperty().bind( textArea.layoutXProperty() );
		statusLabel.layoutYProperty().bind( update.layoutYProperty() );

		this.getChildren().add( statusLabel );
	}

	private void showStatusChanged() {
		statusLabel.setText( "Status: Changed" );
	}

	private void showStatusCurrent() {
		statusLabel.setText( "Status: Current" );
	}

}
