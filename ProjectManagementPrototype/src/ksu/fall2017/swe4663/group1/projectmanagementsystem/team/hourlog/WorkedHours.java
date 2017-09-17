package ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

import java.io.Serializable;

public class WorkedHours implements Serializable {

	private static final long serialVersionUID = 1777547981787649391L;
	private Person person;
	private int duration;
	private WorkedHourType workedHourType;

	public WorkedHours( Person person, int duration, WorkedHourType workedHourType )
			throws InvalidWorkedHourTypeException {
		if ( workedHourType == WorkedHourType.ANY ) {
			throw new InvalidWorkedHourTypeException( "WorkedHours of type WorkedHourType.ANY cannot be submitted." );
		}
		if ( !person.isManager() && workedHourType == WorkedHourType.PROJECT_MANAGEMENT ) {
			throw new InvalidWorkedHourTypeException( "A person of class " + person.getClass().getName()
					+ " cannot submit hourlog of type PROJECT_MANAGEMENT." );
		}
		this.person = person;
		this.duration = duration;
		this.workedHourType = workedHourType;
	}

	public Person getPerson() {
		return this.person;
	}

	public int getDuration() {
		return this.duration;
	}

	public WorkedHourType getType() {
		return workedHourType;
	}

	public boolean equals( Object other ) {
		return other.getClass().equals( WorkedHours.class ) && this.person.equals( ( (WorkedHours) other ).person )
				&& this.duration == ( (WorkedHours) other ).duration
				&& this.workedHourType == ( (WorkedHours) other ).workedHourType;
	}
}
