package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import eaglezr.support.errorsystem.ErrorPopupSystem;
import eaglezr.support.logs.LoggingTool;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.ProjectManagementPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

	File configFile;
	Config config;

	public static void main( String[] args ) {
		launch( args );
	}

	@Override public void start( Stage primaryStage ) throws Exception {
		////////////////////////////////////
		// Setup
		////////////////////////////////////
		LoggingTool.getLogger().setDefault( LoggingTool.generateLogPrinter( "projectmanager" ) );
		// TODO Modify LoggingTool so I can retrieve the file it outputs to, then redirect System.out to the file
		LoggingTool.print( "Main: Initializing Project Manager." );
		loadSettings();
		Project project;
		try {
			project = initializeProject();
		} catch ( Exception e ) {
			LoggingTool.print( "Main: Previous save not found. Creating new project." );
			project = new Project();
		}
		LoggingTool.print( "Main: Initializing ErrorPopupSystem." );
		ErrorPopupSystem.setDefaultStage( primaryStage );

		////////////////////////////////////
		// Display Scene
		////////////////////////////////////
		LoggingTool.print( "Main: Creating ProjectManagementPane." );
		ProjectManagementPane pane = new ProjectManagementPane( primaryStage, config, project );
		primaryStage.setTitle( "Project Management System" );
		primaryStage.setScene( new Scene( pane, config.windowWidth, config.windowHeight ) );
		primaryStage.setMinWidth( 450 );
		primaryStage.setMinHeight( 500 );
		LoggingTool.print( "Main: Displaying Project Management System window." );
		primaryStage.show();
	}

	/**
	 * Load settings from the config file
	 */
	private void loadSettings() throws IOException {
		LoggingTool.print( "Main: Loading settings." );
		try {
			LoggingTool.print( "Main: Configuration file found. Reading configuration." );
			this.configFile = new File( "data/config.ini" );
			this.config = new Config( this.configFile );
		} catch ( FileNotFoundException e ) {
			LoggingTool.print( "Main: Configuration file not found. Initializing new configuration." );
			initializeNewConfigFile();
		}
	}

	/**
	 * Initializes a new config file with default settings.
	 *
	 * @throws IOException
	 */
	private void initializeNewConfigFile() throws IOException {
		File newConfigFile = new File( "data/config.ini" );
		LoggingTool.print( "Main: Creating new configuration file at " + newConfigFile.getAbsolutePath() + "." );
		newConfigFile.createNewFile();
		LoggingTool.print( "Main: Setting default configuration." );
		// LATER Set this up after I figure out what default settings I want
	}

	/**
	 * Creates a new {@link Team} if possible.
	 *
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Project initializeProject() throws IOException, ClassNotFoundException {
		LoggingTool.print( "Main: Loading previous save." );
		return Project.load( config.previousSave );
	}

	@Override public void stop() {
		LoggingTool.print( "Program is exiting." );
		LoggingTool.getLogger().close();
		Platform.exit();
	}
}
