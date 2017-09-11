package eaglezr.taskplanner.system;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class TEST_Task {

	Task task0;
	Task task1;
	Task task2;
	Task task3;

	@Before public void setUp() {
		task0 = new Task( "A", "Task 0", "Do something", 5 );
		task1 = new Task( "B", "Task 1", "Do something 1", 7, task0 );
		task2 = new Task( "C", "Task 2", "Do something 2", 3, task0 );
		task3 = new Task( "D", "Task 3", "Do something 3", 5, task1, task2 );
	}

	@Test public void testGetters() {
		// 1. Test ID
		assertTrue( task0.getID().equals( "A" ) );
		assertTrue( task1.getID().equals( "B" ) );
		assertTrue( task2.getID().equals( "C" ) );
		assertTrue( task3.getID().equals( "D" ) );

		// 2. Test Name
		assertTrue( task0.getName().equals( "Task 0" ) );
		assertTrue( task1.getName().equals( "Task 1" ) );
		assertTrue( task2.getName().equals( "Task 2" ) );
		assertTrue( task3.getName().equals( "Task 3" ) );

		// 3. Test Description
		assertTrue( task0.getDescription().equals( "Do something" ) );
		assertTrue( task1.getDescription().equals( "Do something 1" ) );
		assertTrue( task2.getDescription().equals( "Do something 2" ) );
		assertTrue( task3.getDescription().equals( "Do something 3" ) );

		// 4. Test Duration
		assertTrue( task0.getDuration() == 5 );
		assertTrue( task1.getDuration() == 7 );
		assertTrue( task2.getDuration() == 3 );
		assertTrue( task3.getDuration() == 5 );

		// 5. Test Prerequisites
		assertTrue( task0.getPrerequisites().size() == 0 );
		assertTrue( task1.getPrerequisites().size() == 1 && task1.getPrerequisites().contains( task0 ) );
		assertTrue( task2.getPrerequisites().size() == 1 && task2.getPrerequisites().contains( task0 ) );
		assertTrue( task3.getPrerequisites().size() == 2 && task3.getPrerequisites().contains( task1 ) && task3
				.getPrerequisites().contains( task2 ) );
	}

	@Test public void testSetters() {
		// 1. Change Description
		task0.setDescription( "Something else" );
		task1.setDescription( "Something else 1" );
		task2.setDescription( "Something else 2" );
		task3.setDescription( "Something else 3" );

		// 2. Change Duration
		task0.setDuration( 3 );
		task1.setDuration( 9 );
		task2.setDuration( 4 );
		task3.setDuration( 1 );

		// 3. Change Prerequisites
		task0.setPrerequisites( task1 );
		task1.setPrerequisites( task3 );
		task2.setPrerequisites( task0, task1, task3 );
		task3.setPrerequisites( new ArrayList<>() );

		// 4. Test Description
		assertTrue( task0.getDescription().equals( "Something else" ) );
		assertTrue( task1.getDescription().equals( "Something else 1" ) );
		assertTrue( task2.getDescription().equals( "Something else 2" ) );
		assertTrue( task3.getDescription().equals( "Something else 3" ) );

		// 5. Test Duration
		assertTrue( task0.getDuration() == 3 );
		assertTrue( task1.getDuration() == 9 );
		assertTrue( task2.getDuration() == 4 );
		assertTrue( task3.getDuration() == 1 );

		// 6. Test Prerequisites
		assertTrue( task0.getPrerequisites().size() == 1 && task0.getPrerequisites().contains( task1 ) );
		assertTrue( task1.getPrerequisites().size() == 1 && task1.getPrerequisites().contains( task3 ) );
		assertTrue( task2.getPrerequisites().size() == 3 && task2.getPrerequisites().contains( task0 ) && task2
				.getPrerequisites().contains( task1 ) && task2.getPrerequisites().contains( task3 ) );
		assertTrue( task3.getPrerequisites().size() == 0 );
	}

	@Test public void TEST_getEarlyStartTime() {
		assertTrue( task0.getEarlyStartTime() == 0 );
		assertTrue( task1.getEarlyStartTime() == 5 );
		assertTrue( task2.getEarlyStartTime() == 5 );
		assertTrue( task3.getEarlyStartTime() == 12 );
	}

	@Test public void TEST_getEarlyFinishTime() {
		assertTrue( task0.getEarlyFinishTime() == 5 );
		assertTrue( task1.getEarlyFinishTime() == 12 );
		assertTrue( task2.getEarlyFinishTime() == 8 );
		assertTrue( task3.getEarlyFinishTime() == 17 );
	}

	@Test public void TEST_getDependencies() {
		// 1. Test initial dependencies
		assertTrue( task0.getDependencies().size() == 2 && task0.getDependencies().contains( task1 ) && task0
				.getDependencies().contains( task2 ) );
		assertTrue( task1.getDependencies().size() == 1 && task1.getDependencies().contains( task3 ) );
		assertTrue( task2.getDependencies().size() == 1 && task1.getDependencies().contains( task3 ) );
		assertTrue( task3.getDependencies().size() == 0 );

		// 2. Change Prereqs
		task0.setPrerequisites( task1 );
		task1.setPrerequisites( task3 );
		task2.setPrerequisites( task0, task1, task3 );
		task3.setPrerequisites( new ArrayList<>() );

		// 3. Test new Dependencies
		assertTrue( task0.getDependencies().size() == 1 && task0.getDependencies().contains( task2 ) );
		assertTrue( task1.getDependencies().size() == 2 && task1.getDependencies().contains( task0 ) && task1
				.getDependencies().contains( task2 ) );
		assertTrue( task2.getDependencies().size() == 0 );
		assertTrue( task3.getDependencies().size() == 2 && task3.getDependencies().contains( task1 ) && task3
				.getDependencies().contains( task2 ) );
	}

	@Test public void TEST_getLateStartTime() {
		assertTrue( task0.getLateStartTime() == 0 );
		assertTrue( task1.getLateStartTime() == 5 );
		assertTrue( task2.getLateStartTime() == 9 );
		assertTrue( task3.getLateStartTime() == 12 );
	}

	@Test public void TEST_getLateFinishTime() {
		assertTrue( task0.getLateFinishTime() == 5 );
		assertTrue( task1.getLateFinishTime() == 12 );
		assertTrue( task2.getLateFinishTime() == 12 );
		assertTrue( task3.getLateFinishTime() == 17 );
	}

	@Test public void TEST_getSlackTime() {
		assertTrue( task0.getSlackTime() == 0 );
		assertTrue( task1.getSlackTime() == 0 );
		assertTrue( task2.getSlackTime() == 4 );
		assertTrue( task3.getSlackTime() == 0 );
	}

	@Test public void TEST_isStartTask() {
		assertTrue( task0.isStartTask() );
		assertTrue( !task1.isStartTask() );
		assertTrue( !task2.isStartTask() );
		assertTrue( !task3.isStartTask() );
	}

	@Test public void TEST_isFinishTask() {
		assertTrue( !task0.isFinalTask() );
		assertTrue( !task1.isFinalTask() );
		assertTrue( !task2.isFinalTask() );
		assertTrue( task3.isFinalTask() );
	}
}
