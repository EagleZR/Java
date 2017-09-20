package ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog;

public enum WorkedHourType {

	ANY( "" ), REQUIREMENTS_ANALYSIS( "Requirements Analysis" ), DESIGNING( "Designing" ), CODING( "Coding" ), TESTING(
			"Testing" ), PROJECT_MANAGEMENT( "Project Management" );

	String text;

	WorkedHourType( String text ) {
		this.text = text;
	}

	@Override public String toString() {
		return this.text;
	}
}