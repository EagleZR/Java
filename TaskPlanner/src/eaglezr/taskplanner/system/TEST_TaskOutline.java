package eaglezr.taskplanner.system;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TEST_TaskOutline {

	@Test public void TEST_Accessors() {
		// Setup
		TaskOutline outline = new TaskOutline();

		Task task0 = new Task( "A", "Task 0", "Do something", 5 );
		Task task1 = new Task( "B", "Task 1", "Do something 1", 7, task0 );
		Task task2 = new Task( "C", "Task 2", "Do something 2", 3, task0 );
		Task task3 = new Task( "D", "Task 3", "Do something 3", 5, task1, task2 );

		assertTrue( outline.addAll( Arrays.asList( task0, task1, task2, task3 ) ) );

		ArrayList<Task> startTasks = outline.getStartTasks();
		ArrayList<Task> finalTasks = outline.getFinalTasks();

		// Test initial setup
		assertTrue( startTasks.size() == 1 );
		assertTrue( finalTasks.size() == 1 );

		assertTrue( startTasks.contains( task0 ) );
		assertTrue( finalTasks.contains( task3 ) );

		// Test adding new final task
		Task task4 = new Task( "E", "Task 4", "Do Something 4", 2, task3 );
		assertTrue( outline.add( task4 ) );

		finalTasks = outline.getFinalTasks();

		assertTrue( finalTasks.size() == 1 );
		assertFalse( finalTasks.contains( task3 ) );
		assertTrue( finalTasks.contains( task4 ) );
	}

	/**
	 * Instead of an exception, this now tests if an invalid add attempt returns true or false (true signifying
	 * successful addition.
	 */
	@Test public void TEST_add_Exception() {
		// Setup
		TaskOutline outline = new TaskOutline();

		Task task0 = new Task( "A", "Task 0", "Do something", 5 );
		Task task1 = new Task( "B", "Task 1", "Do something 1", 7, task0 );

		assertFalse( outline.add( task1 ) );
	}

	/**
	 * Instead of an exception, this now tests if an invalid add attempt returns true or false (true signifying
	 * successful addition.
	 */
	@Test public void TEST_addAll_Exception() {
		// Setup
		TaskOutline outline = new TaskOutline();

		Task task0 = new Task( "A", "Task 0", "Do something", 5 );
		Task task1 = new Task( "B", "Task 1", "Do something 1", 7, task0 );
		Task task2 = new Task( "C", "Task 2", "Do something 2", 3, task0 );
		Task task3 = new Task( "D", "Task 3", "Do something 3", 5, task1, task2 );

		assertFalse( outline.addAll( Arrays.asList( task1, task2, task3 ) ) );
	}

	/**
	 * Tests the generation of the Critical Paths
	 */
	@Test public void TEST_CriticalPathGeneration() {
		// SETUP
		TaskOutline outline = new TaskOutline();

		Task task0 = new Task( "A", "Task 0", "Do something", 5 );
		Task task1 = new Task( "B", "Task 1", "Do something 1", 7, task0 );
		Task task2 = new Task( "C", "Task 2", "Do something 2", 3, task0 );
		Task task3 = new Task( "D", "Task 3", "Do something 3", 5, task1, task2 );

		assertTrue( outline.addAll( Arrays.asList( task0, task1, task2, task3 ) ) );

		// Store Critical Paths
		ArrayList<ArrayList> criticalPaths = outline.getCriticalPaths();
		System.out.println( "Number of Critical Paths: " + criticalPaths.size() );
		assertTrue( criticalPaths.size() == 1 );

		// Test Critical Paths
		ArrayList<Task> criticalPath0 = criticalPaths.get( 0 );
		assertTrue( criticalPath0.contains( task0 ) );
		assertTrue( criticalPath0.contains( task1 ) );
		assertTrue( criticalPath0.contains( task3 ) );
		assertFalse( criticalPath0.contains( task2 ) );

		// Print Critical Path Nodes
		System.out.print( "Critical Path: " );
		for ( Task task : criticalPath0 ) {
			System.out.print( task.getName() + ", " );
		}
		System.out.println();
		assertTrue( criticalPath0.get( 2 ).equals( task0 ) );
		assertTrue( criticalPath0.get( 1 ).equals( task1 ) );
		assertTrue( criticalPath0.get( 0 ).equals( task3 ) );

	}

//	/**
//	 * Tests that the structureArray are correctly generated, and that the tasks are distributed to the correct structureArray
//	 */
//	@Test public void TEST_generatePhases() {
//		fail( "Unimplemented" );
//		// TODO 2. In TaskOutline.refactor(), divide tasks into structureArray based on the number of prerequisites from the start tasks.
//		// TODO 2a. The number of structureArray will be determined by the number of tasks in the critical path with the most tasks.
//		// TODO 2b. May need to take into some consideration if a path has far fewer tasks than other tasks.
//		// TODO 2c. For example, a short path may have Path(n-1) set to Phase (n-1) if Path(n) is shared by other paths so that Path(0) or Path(1) can draw a line across to Path(n-1) without intersecting any other tasks.
//		// SETUP
//		TaskOutline outline = new TaskOutline();
//
//		Task task0 = new Task( "A", "Task 0", "Do something", 5 );
//		Task task1 = new Task( "B", "Task 1", "Do something 1", 7, task0 );
//		Task task2 = new Task( "C", "Task 2", "Do something 2", 3, task0 );
//		Task task3 = new Task( "D", "Task 3", "Do something 3", 5, task1, task2 );
//
//		assertTrue( outline.addAll( Arrays.asList( task0, task1, task2, task3 ) ) );
//
//		// Test Phases length
//		Task[][] phases = outline.getStructure();
//		assertTrue( phases.length == 3 );
//		assertTrue( phases[0].length == 2 );
//
//		assertTrue( phases[0][0].equals( task0 ) );
//		assertTrue( phases[0][1] == null );
//
//		assertTrue( phases[1][0].equals( task1 ) );
//		assertTrue( phases[1][1].equals( task2 ) );
//
//		assertTrue( phases[2][0].equals( task3 ) );
//		assertTrue( phases[2][1] == null );
//	}
}
