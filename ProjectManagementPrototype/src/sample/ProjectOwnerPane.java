package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProjectOwnerPane extends FramedPane {

	private String manager;
	private Label label;
	private TextField textField;
	private Button button;

	public ProjectOwnerPane() {
		manager = "";
		setup();
	}

	public ProjectOwnerPane( String manager ) {
		this.manager = manager;
		setup();
	}

	private void setup() {
		// Draw Label
		label = new Label( "");
		label.prefWidthProperty()
				.bind( this.widthProperty().subtract( this.widthProperty().divide( 10 ).multiply( 2 ) ) );
		label.layoutXProperty().bind( this.widthProperty().divide( 10 ) );
		label.layoutYProperty().bind( this.heightProperty().divide( 20 ) );

		this.getChildren().add( label );

		// Draw Update Button
		button = new Button( "Update" );
		button.setOnAction( e -> {
			updateManager( this.textField.getText());
			update();
		} );
		this.getChildren().add( button );

		// Draw Text Field
		textField = new TextField();
		textField.prefWidthProperty().bind( label.prefWidthProperty().subtract( button.widthProperty() )
				.subtract( widthProperty().divide( 10 ) ) );
		textField.layoutXProperty().bind( label.layoutXProperty() );
		textField.layoutYProperty().bind( label.layoutYProperty().add( this.heightProperty().divide( 20 ) )
				.add( label.heightProperty() ) );

		this.getChildren().add( textField );

		button.layoutXProperty().bind( textField.layoutXProperty().add( textField.widthProperty() ).add( this.widthProperty().divide( 10 ) ) );
		button.layoutYProperty().bind( textField.layoutYProperty() );

		update();
	}

	private void update() {
		label.setText( "Project Manager: " + manager );
		textField.clear();
	}

	public void updateManager(String manager) {
		this.manager = manager;
	}
}
