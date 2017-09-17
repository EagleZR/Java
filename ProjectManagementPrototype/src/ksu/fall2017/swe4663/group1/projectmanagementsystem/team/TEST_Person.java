package ksu.fall2017.swe4663.group1.projectmanagementsystem.team;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;
import org.junit.Test;

import static org.junit.Assert.*;

public class TEST_Person {
	@Test public void getName() throws Exception {
		Person programmer = new Person( "Bob" );
		assertEquals( "Bob", programmer.getName() );
	}

	@Test public void addToTeam() throws Exception {
		Person programmer = new Person( "Bob" );
		Team team = new Team( programmer );
		assertEquals( team, programmer.team );
	}

	@Test public void reportEffort() throws Exception {
		Person programmer = new Person( "Bob" );
		Team team = new Team( programmer );
		programmer.reportHours( 5, WorkedHourType.CODING );

		assertEquals( 5, team.getProjectHourLog().getEffort( programmer ) );

		programmer.reportHours( 8, WorkedHourType.DESIGNING );

		assertEquals( 5, team.getProjectHourLog().getEffort( WorkedHourType.CODING ) );
		assertEquals( 8, team.getProjectHourLog().getEffort( WorkedHourType.DESIGNING ) );
		assertEquals( 13, team.getProjectHourLog().getEffort( programmer ) );

		Person programmer1 = new Person( "Dexter" );
		team.addToTeam( programmer1 );
		programmer1.reportHours( 2, WorkedHourType.CODING );

		assertEquals( 7, team.getProjectHourLog().getEffort( WorkedHourType.CODING ) );
		assertEquals( 8, team.getProjectHourLog().getEffort( WorkedHourType.DESIGNING ) );
		assertEquals( 13, team.getProjectHourLog().getEffort( programmer ) );
		assertEquals( 2, team.getProjectHourLog().getEffort( programmer1 ) );
	}

	@Test public void equals() throws Exception {
		Person person1 = new Person( "Bob" );
		Person person2 = new Person( "Bob" );

		assertEquals( person1, person1 );
		assertEquals( person2, person2 );

		assertNotEquals( person1, person2 );
	}

	@Test public void promote() throws Exception {
		Person person = new Person( "Bob" );
		person.promote();
		assertTrue( person.isManager() );

	}

	@Test public void demote() throws Exception {
		Person person = new Person( "Bob" );
		person.promote();
		person.demote();
		assertFalse( person.isManager() );
	}

}
