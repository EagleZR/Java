package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.PersonNotOnTeamException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team;

public class ProjectOwnerPane extends FramedPane {

	private Person manager;
	private Label label;
	private Button button;

	public ProjectOwnerPane( Team team ) throws PersonNotOnTeamException {
		try {
			this.manager = team.getManager();
			setup();
		} catch ( PersonNotOnTeamException e ) {
			this.manager = new Person( "" );
			setup();
			throw e;
		}
	}

	private void setup() {
		// Draw Label
		label = new Label( "" );
		label.prefWidthProperty()
				.bind( this.widthProperty().subtract( this.widthProperty().divide( 10 ).multiply( 2 ) ) );
		label.layoutXProperty().bind( this.widthProperty().divide( 10 ) );
		label.layoutYProperty().bind( this.heightProperty().divide( 20 ) );

		this.getChildren().add( label );

		// TODO Draw ScrollPane of Person Buttons

		// Draw Update Button
		button = new Button( "Update" );
		button.setOnAction( e -> {
//			updateManager( this.textField.getText() );
			update();
		} );
		this.getChildren().add( button );

		// TODO Draw Add New Manager Button

//		button.layoutXProperty().bind( textField.layoutXProperty().add( textField.widthProperty() )
//				.add( this.widthProperty().divide( 10 ) ) );
//		button.layoutYProperty().bind( textField.layoutYProperty() );

		update();
	}

	private void update() {
		label.setText( "Project Manager: " + manager.getName() );
	}

	public void updateManager( Person manager ) {
		this.manager = manager;
	}
}
