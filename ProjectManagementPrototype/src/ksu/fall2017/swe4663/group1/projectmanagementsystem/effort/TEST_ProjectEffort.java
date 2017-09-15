package ksu.fall2017.swe4663.group1.projectmanagementsystem.effort;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

public class TEST_ProjectEffort {

	private ProjectEffort projectEffort;

	private Person p1;
	private Person p2;
	private Person p3;
	private Person p4;
	private Person p5;
	private Person p6;
	private Person p7;

	@Before public void setup() throws InvalidEffortTypeException, PersonNotOnTeamException {
		p1 = new Person( "Bob" );
		p2 = new Person( "Red" );
		p3 = new Person( "Jim" );
		p4 = new Person( "Ate" );
		p5 = new Person( "Eight" );
		p6 = new Person( "Ayt" );
		p7 = new Person( "Mr. Manager" );

		Team team = new Team( p1, p2, p3, p4, p5, p6, p7 );
		team.promote( p7 );

		projectEffort = team.getProjectEffort();

		p1.reportEffort( 5, EffortType.DESIGNING );                // Designing: 5, 		p1: 5
		p2.reportEffort( 8, EffortType.DESIGNING );                // Designing: 13, 		p2: 8
		p3.reportEffort( 3, EffortType.TESTING );                  // Testing: 3,			p3: 3
		p4.reportEffort( 2, EffortType.DESIGNING );                // Designing: 15, 		p4: 2
		p5.reportEffort( 8, EffortType.REQUIREMENTS_ANALYSIS );    // Requirements: 8,	 	p5: 8
		p6.reportEffort( 5, EffortType.TESTING );                  // Testing: 8, 			p6: 8
		p7.reportEffort( 6, EffortType.CODING );                   // Coding: 6, 			p7: 6
		p1.reportEffort( 1, EffortType.DESIGNING );                // Designing: 16, 		p1: 6
		p2.reportEffort( 8, EffortType.CODING );                   // Coding: 14, 			p2: 16
		p3.reportEffort( 8, EffortType.REQUIREMENTS_ANALYSIS );    // Requirements: 16, 	p3: 11
		p4.reportEffort( 9, EffortType.CODING );                   // Coding: 23,			p4: 11
		p5.reportEffort( 10, EffortType.DESIGNING );               // Designing: 26, 		p5: 18
		p6.reportEffort( 5, EffortType.CODING );                   // Coding: 28, 			p6: 13
		p7.reportEffort( 4, EffortType.CODING );                   // Coding: 32, 			p7: 10
		p1.reportEffort( 3, EffortType.CODING );                   // Coding: 35, 			p1: 9
		p2.reportEffort( 7, EffortType.DESIGNING );                // Designing: 33,		p2: 23
		p3.reportEffort( 3, EffortType.CODING );                   // Coding: 38, 			p3: 14
		p4.reportEffort( 6, EffortType.CODING );                   // Coding: 44,			p4: 17
		p5.reportEffort( 3, EffortType.TESTING );                  // Testing: 11,			p5: 21
		p6.reportEffort( 7, EffortType.CODING );                   // Coding: 51,			p6: 20
		p7.reportEffort( 8, EffortType.PROJECT_MANAGEMENT );       // Management: 8			p7: 18

		// Requirements: 16, Designing: 33, Coding: 42, Testing: 11, ProjectManagement: 8
		// p1: 10, p2: 23, p3: 14, p4: 17, p5: 21, p6: 20, p7: 18
	}

	@Test public void TEST_getEffortOfType() {
		assertEquals( 119, projectEffort.getEffort( EffortType.ANY ) );
		assertEquals( 16, projectEffort.getEffort( EffortType.REQUIREMENTS_ANALYSIS ) );
		assertEquals( 33, projectEffort.getEffort( EffortType.DESIGNING ) );
		assertEquals( 51, projectEffort.getEffort( EffortType.CODING ) );
		assertEquals( 11, projectEffort.getEffort( EffortType.TESTING ) );
		assertEquals( 8, projectEffort.getEffort( EffortType.PROJECT_MANAGEMENT ) );
	}

	@Test public void TEST_getEffortOfPerson() {
		assertEquals( 9, projectEffort.getEffort( p1 ) );
		assertEquals( 23, projectEffort.getEffort( p2 ) );
		assertEquals( 14, projectEffort.getEffort( p3 ) );
		assertEquals( 17, projectEffort.getEffort( p4 ) );
		assertEquals( 21, projectEffort.getEffort( p5 ) );
		assertEquals( 17, projectEffort.getEffort( p6 ) );
		assertEquals( 18, projectEffort.getEffort( p7 ) );
	}

	@Test public void TEST_equals() {
		assertEquals( projectEffort, projectEffort );

		assertNotSame( new Team().getProjectEffort(), new Team().getProjectEffort() );
	}

}
