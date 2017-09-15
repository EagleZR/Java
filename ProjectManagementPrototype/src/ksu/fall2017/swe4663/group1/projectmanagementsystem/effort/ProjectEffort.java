package ksu.fall2017.swe4663.group1.projectmanagementsystem.effort;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class ProjectEffort implements Serializable {

	private static final long serialVersionUID = -7863698898951761080L;
	private LinkedList<Effort> efforts;

	public ProjectEffort( Effort... efforts ) {
		this.efforts = new LinkedList<>( Arrays.asList( efforts ) );
	}

	public int getEffort( EffortType effortType ) {
		int count = 0;
		for ( Effort effort : efforts ) {
			if ( effort.getType() == effortType || effortType == EffortType.ANY ) {
				count += effort.getDuration();
			}
		}
		return count;
	}

	public int getEffort( Person person ) {
		int count = 0;
		for ( Effort effort : efforts ) {
			if ( effort.getPerson().equals( person ) ) {
				count += effort.getDuration();
			}
		}
		return count;
	}

	public void registerEffort( Effort newEffort ) {
		this.efforts.add( newEffort );
	}

	public boolean equals( ProjectEffort otherEffort ) {
		if (this.efforts.size() != otherEffort.efforts.size()) {
			return false;
		}
		for (int i = 0; i < this.efforts.size(); i++ ) {
			if (!this.efforts.get( i ).equals( otherEffort.efforts.get( i ) )) {
				return false;
			}
		}
		return true;
	}
}
