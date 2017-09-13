package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main( String[] args ) {
		launch( args );
	}

	@Override public void start( Stage primaryStage ) throws Exception {
		// Initialize primary panes
		BorderPane scenePane = new BorderPane();
		BorderPane contentPane = new BorderPane();
		scenePane.setCenter( contentPane );

		////////////////////////////////////
		// Initialize menu
		////////////////////////////////////
		MenuBar menuBar = new MenuBar(  );
		scenePane.setTop( menuBar );

		// File
		Menu file = new Menu( "File" );
		MenuItem exit = new MenuItem( "Exit" );
		exit.setOnAction( e -> {
			// TODO Exit stuff?
			Platform.exit();
		});
		file.getItems().addAll( exit );

		// Options
		Menu options = new Menu( "Options" );
		MenuItem settings = new MenuItem( "Settings" );
		settings.setOnAction( e -> {
			// TODO Display settings stage
		} );
		options.getItems().addAll( settings );

		// Help
		Menu help = new Menu( "Help" );
		MenuItem helpItem = new MenuItem( "Help" );
		helpItem.setOnAction( e -> {
			// TODO Display a help stage
		} );
		MenuItem about = new MenuItem( "About" );
		about.setOnAction( e -> {
			// TODO Display the about stage
		} );
		help.getItems().addAll( helpItem, about );

		menuBar.getMenus().addAll( file, options, help );

		////////////////////////////////////
		// Initialize content panes
		////////////////////////////////////
		GeneralPane generalPane = new GeneralPane( primaryStage );
		RequirementsPane requirementsPane = new RequirementsPane();
		EffortPane effortPane = new EffortPane();
		contentPane.setCenter( generalPane );
		generalPane.prefWidthProperty().bind(contentPane.widthProperty());

		////////////////////////////////////
		// Initialize tabs
		////////////////////////////////////
		Pane tabsPane = new Pane();
		contentPane.setTop( tabsPane );

		// General Pane
		Button generalPaneButton = new Button( "General" );
		generalPaneButton.setDefaultButton( true );
		generalPaneButton.prefWidthProperty().bind( tabsPane.widthProperty().divide( 3 ) );
		generalPaneButton.layoutXProperty().setValue( 0 );
		generalPaneButton.layoutYProperty().setValue( 0 );

		// Requirements
		Button requirements = new Button( "Requirements" );
		requirements.prefWidthProperty().bind( generalPaneButton.widthProperty() );
		requirements.layoutXProperty().bind( generalPaneButton.layoutXProperty().add( generalPaneButton.widthProperty() ) );
		requirements.layoutYProperty().bind( generalPaneButton.layoutYProperty() );

		// Hours Expended
		Button hoursExpended = new Button( "Hours Expended" );
		hoursExpended.prefWidthProperty().bind( generalPaneButton.widthProperty() );
		hoursExpended.layoutXProperty().bind( requirements.layoutXProperty().add( requirements.widthProperty() ) );
		hoursExpended.layoutYProperty().bind( generalPaneButton.layoutYProperty() );

		// Actions
		generalPaneButton.setOnAction( e -> {
			contentPane.setCenter( generalPane );
			generalPaneButton.setDefaultButton( true );
			requirements.setDefaultButton( false );
			hoursExpended.setDefaultButton( false );

		} );
		requirements.setOnAction( e -> {
			contentPane.setCenter( requirementsPane );
			generalPaneButton.setDefaultButton( false );
			requirements.setDefaultButton( true );
			hoursExpended.setDefaultButton( false );
		} );
		hoursExpended.setOnAction( e -> {
			contentPane.setCenter( effortPane );
			generalPaneButton.setDefaultButton( false );
			requirements.setDefaultButton( false );
			hoursExpended.setDefaultButton( true );
		} );

		tabsPane.getChildren().addAll( generalPaneButton, requirements, hoursExpended );

		////////////////////////////////////
		// Display Scene
		////////////////////////////////////
		primaryStage.setTitle( "Project Management System" );
		primaryStage.setScene( new Scene( scenePane, 500, 600 ) );
		primaryStage.setMinWidth( 450 );
		primaryStage.setMinHeight( 500 );
		primaryStage.show();
	}
}
