package ksu.fall2017.swe4663.group1.projectmanagementsystem.effort;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

import java.io.Serializable;

public class Effort implements Serializable {

	private static final long serialVersionUID = 1777547981787649391L;
	private Person person;
	private int duration;
	private EffortType effortType;

	public Effort( Person person, int duration, EffortType effortType ) throws InvalidEffortTypeException {
		if ( effortType == EffortType.ANY ) {
			throw new InvalidEffortTypeException( "Effort of type EffortType.ANY cannot be submitted." );
		}
		if ( !person.isManager() && effortType == EffortType.PROJECT_MANAGEMENT ) {
			throw new InvalidEffortTypeException( "A person of class " + person.getClass().getName()
					+ " cannot submit effort of type PROJECT_MANAGEMENT." );
		}
		this.person = person;
		this.duration = duration;
		this.effortType = effortType;
	}

	public Person getPerson() {
		return this.person;
	}

	public int getDuration() {
		return this.duration;
	}

	public EffortType getType() {
		return effortType;
	}

	public boolean equals( Effort other ) {
		return this.person.equals( other.person ) && this.duration == other.duration
				&& this.effortType == other.effortType;
	}
}
