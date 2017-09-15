package ksu.fall2017.swe4663.group1.projectmanagementsystem.hourlog;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class ProjectHourLog implements Serializable {

	private static final long serialVersionUID = -7863698898951761080L;
	private LinkedList<WorkedHours> workedHours;

	public ProjectHourLog( WorkedHours... workedHours ) {
		this.workedHours = new LinkedList<>( Arrays.asList( workedHours ) );
	}

	public int getEffort( WorkedHourType workedHourType ) {
		int count = 0;
		for ( WorkedHours workedHours : this.workedHours ) {
			if ( workedHours.getType() == workedHourType || workedHourType == WorkedHourType.ANY ) {
				count += workedHours.getDuration();
			}
		}
		return count;
	}

	public int getEffort( Person person ) {
		int count = 0;
		for ( WorkedHours workedHours : this.workedHours ) {
			if ( workedHours.getPerson().equals( person ) ) {
				count += workedHours.getDuration();
			}
		}
		return count;
	}

	public void registerEffort( WorkedHours newWorkedHours ) {
		this.workedHours.add( newWorkedHours );
	}

	@Override public boolean equals( Object other ) {
		ProjectHourLog otherHourLog;
		if ( other.getClass().equals( ProjectHourLog.class ) ) {
			otherHourLog = (ProjectHourLog) other;
		} else {
			return false;
		}
		if ( this.workedHours.size() != otherHourLog.workedHours.size() ) {
			return false;
		}
		for ( int i = 0; i < this.workedHours.size(); i++ ) {
			if ( !this.workedHours.get( i ).equals( otherHourLog.workedHours.get( i ) ) ) {
				return false;
			}
		}
		return true;
	}
}
