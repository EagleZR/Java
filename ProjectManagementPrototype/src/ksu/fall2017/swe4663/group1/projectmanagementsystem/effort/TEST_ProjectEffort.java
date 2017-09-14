package ksu.fall2017.swe4663.group1.projectmanagementsystem.effort;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Manager;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Programmer;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TEST_ProjectEffort {

	private ProjectEffort projectEffort;

	private Person p1;
	private Person p2;
	private Person p3;
	private Person p4;
	private Person p5;
	private Person p6;
	private Person p7;

	private Team team;

	@Before public void setup() {
		projectEffort = new ProjectEffort();

		p1 = new Programmer( "Bob" );
		p2 = new Programmer( "Red" );
		p3 = new Programmer( "Jim" );
		p4 = new Programmer( "Ate" );
		p5 = new Programmer( "Eight" );
		p6 = new Programmer( "Ayt" );
		p7 = new Manager( "Mr. Manager" );

		team = new Team( projectEffort, p1, p2, p3, p4, p5, p6, p7 );

		p1.reportEffort( 5, EffortType.DESIGNING );                // Designing: 5, 		p1: 5
		p2.reportEffort( 8, EffortType.DESIGNING );                // Designing: 13, 		p2: 8
		p3.reportEffort( 3, EffortType.TESTING );                    // Testing: 3,			p3: 3
		p4.reportEffort( 2, EffortType.DESIGNING );                // Designing: 15, 		p4: 2
		p5.reportEffort( 8, EffortType.REQUIREMENTS_ANALYSIS );    // Requirements: 8,	 	p5: 8
		p6.reportEffort( 5, EffortType.TESTING );                    // Testing: 8, 			p6: 8
		p7.reportEffort( 6, EffortType.CODING );                    // Coding: 6, 			p7: 6
		p1.reportEffort( 1, EffortType.DESIGNING );                // Designing: 16, 		p1: 6
		p2.reportEffort( 8, EffortType.CODING );                    // Coding: 14, 			p2: 16
		p3.reportEffort( 8, EffortType.REQUIREMENTS_ANALYSIS );    // Requirements: 16, 	p3: 11
		p4.reportEffort( 9, EffortType.CODING );                    // Coding: 23,			p4: 11
		p5.reportEffort( 10, EffortType.DESIGNING );                // Designing: 26, 		p5: 18
		p6.reportEffort( 5, EffortType.CODING );                    // Coding: 19, 			p6: 13
		p7.reportEffort( 4, EffortType.CODING );                    // Coding: 23, 			p7: 10
		p1.reportEffort( 3, EffortType.CODING );                    // Coding: 26, 			p1: 10
		p2.reportEffort( 7, EffortType.DESIGNING );                // Designing: 33,		p2: 23
		p3.reportEffort( 3, EffortType.CODING );                    // Coding: 29, 			p3: 14
		p4.reportEffort( 6, EffortType.CODING );                    // Coding 35,			p4: 17
		p5.reportEffort( 3, EffortType.TESTING );                    // Testing: 11			p5: 21
		p6.reportEffort( 7, EffortType.CODING );                    // Coding: 42			p6: 20
		p7.reportEffort( 8, EffortType.PROJECT_MANAGEMENT );        // Management: 8		p7: 18

		// Requirements: 16, Designing: 33, Coding: 42, Testing: 11, ProjectManagement: 8
		// p1: 10, p2: 23, p3: 14, p4: 17, p5: 21, p6: 20, p7: 18
	}

	@Test public void TEST_Accessors() {

	}

	@Test public void TEST_getEffort() {
		assertTrue( projectEffort.getEffort( EffortType.ANY ) == 110 );
		assertTrue( projectEffort.getEffort( EffortType.REQUIREMENTS_ANALYSIS ) == 16 );
		assertTrue( projectEffort.getEffort( EffortType.DESIGNING ) == 33 );
		assertTrue( projectEffort.getEffort( EffortType.CODING ) == 42 );
		assertTrue( projectEffort.getEffort( EffortType.TESTING ) == 11 );
		assertTrue( projectEffort.getEffort( EffortType.PROJECT_MANAGEMENT ) == 8 );

		assertTrue( projectEffort.getEffort(p1) == 10 );
		assertTrue( projectEffort.getEffort(p2) == 23 );
		assertTrue( projectEffort.getEffort(p3) == 14 );
		assertTrue( projectEffort.getEffort(p4) == 17 );
		assertTrue( projectEffort.getEffort(p5) == 21 );
		assertTrue( projectEffort.getEffort(p6) == 20 );
		assertTrue( projectEffort.getEffort(p7) == 18 );
	}

	@Test public void TEST_save() {

	}

	@Test public void TEST_load() {

	}

}
