package ksu.fall2017.swe4663.group1.projectmanagementsystem.effort;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import org.junit.Test;

import static org.junit.Assert.*;

public class TEST_Effort {

	@Test public void TEST_Accessors() throws InvalidEffortTypeException {
		Effort effort = new Effort( new Person( "Bob" ), 5, EffortType.CODING );
		assertTrue( effort.getPerson().getName().equals( "Bob" ) );
		assertTrue( effort.getDuration() == 5 );
		assertTrue( effort.getType() == EffortType.CODING );

		// LATER Use setters?
	}

	@Test (expected = InvalidEffortTypeException.class) public void TEST_EffortManagerException()
			throws InvalidEffortTypeException {
		Effort effort = new Effort( new Person( "Bob" ), 5, EffortType.PROJECT_MANAGEMENT );
	}

	@Test (expected = InvalidEffortTypeException.class) public void TEST_AnyException()
			throws InvalidEffortTypeException {
		Effort effort = new Effort( new Person( "Bob" ), 5, EffortType.ANY );
	}
}
