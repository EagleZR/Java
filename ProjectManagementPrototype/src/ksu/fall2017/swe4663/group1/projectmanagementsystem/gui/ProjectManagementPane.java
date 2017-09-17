package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import eaglezr.support.errorsystem.ErrorPopupSystem;
import eaglezr.support.logs.LoggingTool;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.GeneralPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.HourLogPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements.RequirementsPane;

import java.io.File;
import java.io.IOException;

public class ProjectManagementPane extends BorderPane {

	File saveFile;
	Project project;

	public ProjectManagementPane( Stage primaryStage, Config config, Project project ) {
		LoggingTool.print( "Constructing new ProjectManagementPane." );
		this.saveFile = config.previousSave;
		this.project = project;

		// Initialize primary panes
		BorderPane contentPane = new BorderPane();
		this.setCenter( contentPane );

		this.setTop( getMenuBar() );

		////////////////////////////////////
		// Initialize content panes
		////////////////////////////////////
		LoggingTool.print( "ProjectManagementPane: Creating GeneralPane in ProjectManagementPane." );
		GeneralPane generalPane = new GeneralPane( primaryStage, project );
		LoggingTool.print( "ProjectManagementPane: Creating RequirementsPane in ProjectManagementPane." );
		RequirementsPane requirementsPane = new RequirementsPane();
		LoggingTool.print( "ProjectManagementPane: Creating HourLogPane in ProjectManagementPane." );
		HourLogPane hourLogPane = new HourLogPane();
		LoggingTool.print( "ProjectManagementPane: Setting GeneralPane as content in ProjectManagementPane." );
		contentPane.setCenter( generalPane );
		generalPane.prefWidthProperty().bind( contentPane.widthProperty() );

		////////////////////////////////////
		// Initialize tabs
		////////////////////////////////////
		LoggingTool.print( "ProjectManagementPane: Creating tabs in ProjectManagementPane." );
		Pane tabsPane = new Pane();
		contentPane.setTop( tabsPane );

		// General Pane
		LoggingTool.print( "ProjectManagementPane: Creating Button tab for GeneralPane in ProjectManagementPane." );
		Button generalPaneButton = new Button( "General" );
		generalPaneButton.setDefaultButton( true );
		generalPaneButton.prefWidthProperty().bind( tabsPane.widthProperty().divide( 3 ) );
		generalPaneButton.layoutXProperty().setValue( 0 );
		generalPaneButton.layoutYProperty().setValue( 0 );

		// Requirements
		LoggingTool.print( "ProjectManagementPane: Creating Button tab for RequirementsPane in ProjectManagementPane." );
		Button requirements = new Button( "Requirements" );
		requirements.prefWidthProperty().bind( generalPaneButton.widthProperty() );
		requirements.layoutXProperty()
				.bind( generalPaneButton.layoutXProperty().add( generalPaneButton.widthProperty() ) );
		requirements.layoutYProperty().bind( generalPaneButton.layoutYProperty() );

		// Hours Expended
		LoggingTool.print( "ProjectManagementPane: Creating Button tab for HourLogPane in ProjectManagementPane." );
		Button hoursLog = new Button( "Hour Log" );
		hoursLog.prefWidthProperty().bind( generalPaneButton.widthProperty() );
		hoursLog.layoutXProperty().bind( requirements.layoutXProperty().add( requirements.widthProperty() ) );
		hoursLog.layoutYProperty().bind( generalPaneButton.layoutYProperty() );

		// Actions
		generalPaneButton.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Setting GeneralPane as content in ProjectManagementPane." );
			contentPane.setCenter( generalPane );
			generalPaneButton.setDefaultButton( true );
			requirements.setDefaultButton( false );
			hoursLog.setDefaultButton( false );

		} );
		requirements.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Setting RequirementsPane as content in ProjectManagementPane." );
			contentPane.setCenter( requirementsPane );
			generalPaneButton.setDefaultButton( false );
			requirements.setDefaultButton( true );
			hoursLog.setDefaultButton( false );
		} );
		hoursLog.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Setting HourLogPane as content in ProjectManagementPane." );
			contentPane.setCenter( hourLogPane );
			generalPaneButton.setDefaultButton( false );
			requirements.setDefaultButton( false );
			hoursLog.setDefaultButton( true );
		} );

		tabsPane.getChildren().addAll( generalPaneButton, requirements, hoursLog );
	}

	private MenuBar getMenuBar() {
		LoggingTool.print( "ProjectManagementPane: Creating MenuBar." );
		MenuBar menuBar = new MenuBar();

		// File
		Menu file = new Menu( "File" );
		MenuItem newProject = new MenuItem( "New" );
		newProject.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: New Project Created." );
			this.project = new Project(); // TODO Push to dependent panes?
		} );
		MenuItem save = new MenuItem( "Save" );
		MenuItem saveAs = new MenuItem( "Save As" );
		save.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Save button pressed." );
			if ( saveFile == null ) {
				saveAs.fire();
			} else {
				LoggingTool.print( "ProjectManagementPane: Saving to " + saveFile.getAbsolutePath() + "." );
				try {
					Project.save( this.project, this.saveFile );
				} catch ( IOException e1 ) {
					ErrorPopupSystem.displayMessage( "The file could not be saved." );
				}
			}
		} );
		saveAs.setOnAction( e -> {
			// TODO SaveAs the file
			LoggingTool.print( "ProjectManagementPane: Save As button pressed." );
		} );
		MenuItem load = new MenuItem( "Load" );
		load.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Load button pressed." );
			// TODO Load a file
		} );
		MenuItem exit = new MenuItem( "Exit" );
		exit.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Exit button pressed." );
			// TODO Exit stuff?
			Platform.exit();
		} );
		file.getItems().addAll( save, saveAs, load, exit );

		// Options
		Menu options = new Menu( "Options" );
		MenuItem settings = new MenuItem( "Settings" );
		settings.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Settings button pressed." );
			// TODO Display settings stage
		} );
		options.getItems().addAll( settings );

		// Help
		Menu help = new Menu( "Help" );
		MenuItem helpItem = new MenuItem( "Help" );
		helpItem.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Help button pressed." );
			// TODO Display a help stage
		} );
		MenuItem about = new MenuItem( "About" );
		about.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: About button pressed." );
			// TODO Display the about stage
		} );
		help.getItems().addAll( helpItem, about );

		menuBar.getMenus().addAll( file, options, help );

		return menuBar;
	}
}
