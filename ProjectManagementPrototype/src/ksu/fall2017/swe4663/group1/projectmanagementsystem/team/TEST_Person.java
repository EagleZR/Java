package ksu.fall2017.swe4663.group1.projectmanagementsystem.team;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.effort.EffortType;
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
		programmer.reportEffort( 5, EffortType.CODING );

		assertEquals( 5, team.getProjectEffort().getEffort( programmer ) );

		programmer.reportEffort( 8, EffortType.DESIGNING );

		assertEquals( 5, team.getProjectEffort().getEffort( EffortType.CODING ) );
		assertEquals( 8, team.getProjectEffort().getEffort( EffortType.DESIGNING ) );
		assertEquals( 13, team.getProjectEffort().getEffort( programmer ) );

		Person programmer1 = new Person( "Dexter" );
		team.addToTeam( programmer1 );
		programmer1.reportEffort( 2, EffortType.CODING );

		assertEquals( 7, team.getProjectEffort().getEffort( EffortType.CODING ) );
		assertEquals( 8, team.getProjectEffort().getEffort( EffortType.DESIGNING ) );
		assertEquals( 13, team.getProjectEffort().getEffort( programmer ) );
		assertEquals( 2, team.getProjectEffort().getEffort( programmer1 ) );
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
