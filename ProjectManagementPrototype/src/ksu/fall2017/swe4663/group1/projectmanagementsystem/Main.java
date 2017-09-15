package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import eaglezr.support.errorsystem.ErrorPopupSystem;
import javafx.application.Application;
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
		// Setup
		loadSettings();
		Team team = initializeTeam();
		ErrorPopupSystem.setDefaultStage( primaryStage );

		ProjectManagementPane pane = new ProjectManagementPane( primaryStage, config, team );

		////////////////////////////////////
		// Display Scene
		////////////////////////////////////
		primaryStage.setTitle( "Project Management System" );
		primaryStage.setScene( new Scene( pane, config.windowWidth, config.windowHeight ) );
		primaryStage.setMinWidth( 450 );
		primaryStage.setMinHeight( 500 );
		primaryStage.show();
	}

	/**
	 * Load settings from the config file
	 */
	private void loadSettings() throws IOException {
		try {
			this.configFile = new File( "data/config.ini" );
			this.config = new Config( this.configFile );
		} catch ( FileNotFoundException e ) {
			initializeNewConfigFile();
		}
	}

	/**
	 * Initializes a new config file with default settings.
	 *
	 * @throws IOException
	 */
	private void initializeNewConfigFile() throws IOException {
		// LATER Set this up after I figure out what default settings I want
		File newConfigFile = new File( "data/config.ini" );
		newConfigFile.createNewFile();
	}

	/**
	 * Creates a new {@link Team} if possible.
	 *
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Team initializeTeam() throws IOException, ClassNotFoundException {
		return Team.load( config.previousSave );
	}
}
