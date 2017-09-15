package ksu.fall2017.swe4663.group1.projectmanagementsystem.team;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.hourlog.WorkedHourType;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.hourlog.WorkedHours;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.hourlog.InvalidWorkedHourTypeException;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

public class Person implements Serializable {

	private static final long serialVersionUID = 904457401957708182L;
	/**
	 * To ensure that no 2 {@link Person} instances have the same ID, at least in the same program.
	 */
	private static LinkedList<Long> personIDs;
	protected final String name; // LATER Final?
	protected final long ID;
	protected Team team;
	protected boolean isManager;

	public Person( String name ) {
		this.name = name;

		if ( personIDs == null ) {
			personIDs = new LinkedList<>();
		}

		// Generate unique ID
		Random random = new Random();
		long ID;
		do {
			ID = random.nextLong();
		} while ( personIDs.contains( ID ) );
		this.ID = ID;
		personIDs.add( ID );
	}

	public String getName() {
		return this.name;
	}

	public boolean isManager() {
		return this.isManager;
	}

	protected void addToTeam( Team team ) {
		this.team = team;
	}

	public void reportEffort( int duration, WorkedHourType workedHourType )
			throws PersonNotOnTeamException, InvalidWorkedHourTypeException {
		if ( this.team == null ) {
			throw new PersonNotOnTeamException( this.name + " has not yet been added to a team" );
		}
		team.registerEffort( new WorkedHours( this, duration, workedHourType ) );
	}

	public boolean equals( Person other ) {
		return this.ID == other.ID;
	}

	public void promote() {
		this.isManager = true;
	}

	public void demote() {
		this.isManager = false;
	}
}
