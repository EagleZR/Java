package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main( String[] args ) {
		launch( args );
	}

	@Override public void start( Stage primaryStage ) throws Exception {

		BorderPane borderPane = new BorderPane();

		/////////////////
		// Menu
		/////////////////
		Menu file = new Menu( "File" );
		Menu options = new Menu( "Options" );
		Menu help = new Menu( "Help" );
		MenuBar menuBar = new MenuBar( file, options, help );
		borderPane.setTop( menuBar );

		/////////////////
		// Content Pane
		/////////////////
		Pane contentPane = new Pane();
		borderPane.setCenter( contentPane );

		// Description Pane
		DescriptionPane descriptionPane = new DescriptionPane(
				"Here would be the description of the program. Edit it if you want, but it won't save after you close it." );
		descriptionPane.prefWidthProperty().bind( contentPane.widthProperty().divide( 2 ) );
		descriptionPane.prefHeightProperty().bind( contentPane.heightProperty().divide( 2 ) );
		descriptionPane.layoutXProperty().setValue( 0 );
		descriptionPane.layoutYProperty().setValue( 0 );
		contentPane.getChildren().add( descriptionPane );

		// Team Members Pane
		TeamMembersPane teamMembersPane = new TeamMembersPane( primaryStage );
		teamMembersPane.prefWidthProperty().bind( contentPane.widthProperty().divide( 2 ) );
		teamMembersPane.prefHeightProperty().bind( contentPane.heightProperty().divide( 2 ) );
		teamMembersPane.layoutXProperty().bind( descriptionPane.layoutXProperty() );
		teamMembersPane.layoutYProperty().bind( descriptionPane.layoutYProperty().add( descriptionPane.heightProperty() ) );
		contentPane.getChildren().add( teamMembersPane );

		// Project Owner Pane
		ProjectOwnerPane projectOwnerPane = new ProjectOwnerPane( "Sharmell" );
		projectOwnerPane.prefWidthProperty().bind( contentPane.widthProperty().divide( 2 ) );
		projectOwnerPane.prefHeightProperty().bind( contentPane.heightProperty().divide( 2 ) );
		projectOwnerPane.layoutXProperty().bind( descriptionPane.layoutXProperty().add( descriptionPane.widthProperty() ) );
		projectOwnerPane.layoutYProperty().bind( descriptionPane.layoutYProperty() );
		contentPane.getChildren().add( projectOwnerPane );

		//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		primaryStage.setTitle( "Project Management System" );
		primaryStage.setScene( new Scene( borderPane, 500, 600 ) );
		primaryStage.show();
	}
}
