package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;

// TODO Set an animation (?) so this updates regularly.
// TODO Make this so it displays in a separate window (On separate thread?)
public class DebugPane extends Pane {

	private static int numModules = 4;

	private Project project;
	private Label teamMembers;
	private Label workedHours;
	private Label risks;
	private Label requirements;

	public DebugPane( Project project ) {
		this.project = project;

		teamMembers = new Label();
		teamMembers.layoutXProperty().setValue( 0 );
		teamMembers.layoutYProperty().setValue( 0 );
		teamMembers.prefWidthProperty().bind( this.widthProperty().divide( numModules ) );
		teamMembers.prefHeightProperty().bind( this.heightProperty() );
		this.getChildren().add( teamMembers );

		workedHours = new Label();
		workedHours.layoutXProperty().bind( teamMembers.layoutXProperty().add( teamMembers.widthProperty() ) );
		workedHours.layoutYProperty().setValue( 0 );
		workedHours.prefWidthProperty().bind( this.widthProperty().divide( numModules ) );
		workedHours.prefHeightProperty().bind( this.heightProperty() );
		this.getChildren().add( workedHours );

		risks = new Label();
		risks.layoutXProperty().bind( workedHours.layoutXProperty().add( workedHours.widthProperty() ) );
		risks.layoutYProperty().setValue( 0 );
		risks.prefWidthProperty().bind( this.widthProperty().divide( numModules ) );
		risks.prefHeightProperty().bind( this.heightProperty() );
		this.getChildren().add( risks );

		requirements = new Label();
		requirements.layoutXProperty().bind( risks.layoutXProperty().add( risks.widthProperty() ) );
		requirements.layoutYProperty().setValue( 0 );
		requirements.prefWidthProperty().bind( this.widthProperty().divide( numModules ) );
		requirements.prefHeightProperty().bind( this.heightProperty() );
		this.getChildren().add( requirements );
	}

	private void update() {
		// Team Members' Names
		String names = "Team Members: ";
		for ( Person person : project.getTeam().getMembers() ) {
			names += "\n" + person.getName();
		}
		teamMembers.setText( names );

		// Worked Hours Totals
		String hours = "Worked Hours: ";
		hours += "\nTotal:\t";
		hours += project.getTeam().getProjectHourLog().getEffort( WorkedHourType.ANY );
		hours += "\nRequirements:\t";
		hours += project.getTeam().getProjectHourLog().getEffort( WorkedHourType.REQUIREMENTS_ANALYSIS );
		hours += "\nDesigning:\t";
		hours += project.getTeam().getProjectHourLog().getEffort( WorkedHourType.DESIGNING );
		hours += "\nCoding:\t";
		hours += project.getTeam().getProjectHourLog().getEffort( WorkedHourType.CODING );
		hours += "\nTesting:\t";
		hours += project.getTeam().getProjectHourLog().getEffort( WorkedHourType.TESTING );
		hours += "\nManagement:\t";
		hours += project.getTeam().getProjectHourLog().getEffort( WorkedHourType.PROJECT_MANAGEMENT );
		workedHours.setText( hours );

		// Risks
		String risks = "Risks: ";

		this.risks.setText( risks );

		// Requirements
		String requirements = "Requirements: ";

		this.requirements.setText( requirements );
	}
}
