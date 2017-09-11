package eaglezr.taskplanner.system;

import java.util.ArrayList;
import java.util.Collection;

public class TaskOutline extends ArrayList<Task> {

	ArrayList<Task> startTasks;
	ArrayList<Task> finalTasks;
	ArrayList<ArrayList> criticalPaths;

	boolean criticalPathsHasChanged = true;
	boolean phasesHasChanged = true;

	/**
	 * Creates an empty {@link TaskOutline}.
	 */
	public TaskOutline() {
		super();
		startTasks = new ArrayList<>();
		finalTasks = new ArrayList<>();
	}

	/**
	 * Checks to ensure that the outline contains the prerequisite and dependant {@link Task}s. If they are present, the
	 * provided task is added to the list. If not, the provided task is ignored.
	 *
	 * @param task The provided {@link Task} to be added. If the prerequisite and dependant tasks of the provided task
	 *             are not included in this outline, the provided task will be ignored.
	 * @return <p>{@code true} if the provided {@link Task} was successfully added.</p> <p>{@code false} if the provided
	 * task was not added.</p>
	 */
	public boolean add( Task task ) {
		// Checks for prerequisites
		for ( Task prereq : task.getPrerequisites() ) {
			if ( !this.contains( prereq ) ) {
				return false;
			}
		}
		// Checks for dependants
		for ( Task dependant : task.getDependencies() ) {
			if ( !this.contains( dependant ) ) {
				return false;
			}
		}

		// Adds the new task and refactors
		super.add( task );
		this.phasesHasChanged = true;
		this.criticalPathsHasChanged = true;
		refactor();
		return true;
	}

	/**
	 * Checks to ensure that the outline contains the prerequisite and dependant {@link Task}s. If they are present, the
	 * provided task is added to the list at the desired location. If not, the provided task is ignored. <p>NOTE: If the
	 * provided task is ignored, there will be no indication that it was ignored.</p>
	 *
	 * @param index The location in which the new {@link Task} is to be inserted.
	 * @param task  The provided {@link Task} to be added. If the prerequisite and dependant tasks of the provided task
	 *              are not included in this outline, the provided task will be ignored.
	 */
	public void add( int index, Task task ) {
		// Checks for prerequisites
		for ( Task prereq : task.getPrerequisites() ) {
			if ( !this.contains( prereq ) ) {
				return;
			}
		}
		// Checks for dependants
		for ( Task dependant : task.getDependencies() ) {
			if ( !this.contains( dependant ) ) {
				return;
			}
		}

		// Adds the new task and refactors
		super.add( index, task );
		refactor();
	}

	/**
	 * Checks to ensure that the outline contains the prerequisite and dependant {@link Task}s of each of the provided
	 * tasks. If they are present, the provided tasks are added to the list. If not, the provided tasks are ignored.
	 *
	 * @param tasks The provided {@link Task}s to be added. If the prerequisite and dependant tasks of each of the
	 *              provided tasks are not included in this outline, the provided tasks will be ignored.
	 * @return <p>{@code true} if the provided {@link Task}s were successfully added.</p> <p>{@code false} if the
	 * provided tasks were not added.</p>
	 */
	public boolean addAll( Collection<? extends Task> tasks ) {
		for ( Task task : tasks ) {
			// Checks for prerequisites
			for ( Task prereq : task.getPrerequisites() ) {
				if ( !this.contains( prereq ) && !tasks.contains( prereq ) ) {
					return false;
				}
			}
			// Checks for dependants
			for ( Task dependant : task.getDependencies() ) {
				if ( !this.contains( dependant ) && !tasks.contains( dependant ) ) {
					return false;
				}
			}
		}

		// Adds the new task and refactors
		super.addAll( tasks );
		refactor();
		return true;
	}

	/**
	 * Checks to ensure that the outline contains the prerequisite and dependant {@link Task}s of each of the provided
	 * tasks. If they are present, the provided tasks are inserted into the list at the provided index. If not, the
	 * provided tasks are ignored.
	 *
	 * @param index The index into which the provided {@link Task}s are to be added.
	 * @param tasks The provided {@link Task}s to be added. If the prerequisite and dependant tasks of each of the
	 *              provided tasks are not included in this outline, the provided tasks will be ignored.
	 * @return <p>{@code true} if the provided {@link Task}s were successfully added.</p> <p>{@code false} if the
	 * provided tasks were not added.</p>
	 */
	public boolean addAll( int index, Collection<? extends Task> tasks ) {
		for ( Task task : tasks ) {
			// Checks for prerequisites
			for ( Task prereq : task.getPrerequisites() ) {
				if ( !this.contains( prereq ) && !tasks.contains( prereq ) ) {
					return false;
				}
			}
			// Checks for dependants
			for ( Task dependant : task.getDependencies() ) {
				if ( !this.contains( dependant ) && !tasks.contains( dependant ) ) {
					return false;
				}
			}
		}

		// Adds the new task and refactors
		super.addAll( index, tasks );
		refactor();
		return true;
	}

	/**
	 * Re-determines the {@code startTasks} and {@code finalTasks} when new tasks are added or tasks are removed.
	 */
	private void refactor() {
		startTasks.clear();
		finalTasks.clear();
		for ( Task task : this ) {
			if ( task.isFinalTask() ) {
				finalTasks.add( task );
			}
			if ( task.isStartTask() ) {
				startTasks.add( task );
			}
		}
		for ( Task task : this ) {
			task.getTier();
			task.getEarlyStartTime();
			task.getEarlyFinishTime();
			task.getLateStartTime();
			task.getLateFinishTime();
		}
		if ( this.finalTasks.size() == 0 || this.startTasks.size() == 0 ) {
			audit();
		}
	}

	/**
	 * Performs an audit to ensure that all prerequisite and dependant tasks of the tasks in this outline are also in
	 * this outline.
	 *
	 * @return An {@link ArrayList}<{@link Task}> of all prerequisites and dependants not included in this outline.
	 */
	public ArrayList<Task> audit() {
		ArrayList<Task> missingTasks = new ArrayList<>();
		for ( Task task : this ) {
			for ( Task prereq : task.getPrerequisites() ) {
				auditDown( prereq, missingTasks );
			}
			for ( Task dependant : task.getDependencies() ) {
				auditUp( dependant, missingTasks );
			}
		}
		return missingTasks;
	}

	// Audits down through dependants
	private void auditUp( Task dependant, ArrayList<Task> missingTasks ) {
		if ( !this.contains( dependant ) ) {
			missingTasks.add( dependant );
		}
		for ( Task task : dependant.getDependencies() ) {
			auditUp( task, missingTasks );
		}
	}

	// Audits up through prerequisites
	private void auditDown( Task prereq, ArrayList<Task> missingTasks ) {
		if ( !this.contains( prereq ) ) {
			missingTasks.add( prereq );
		}
		for ( Task task : prereq.getPrerequisites() ) {
			auditUp( task, missingTasks );
		}
	}

	/**
	 * Retrieves the {@link Task}s that have no prerequisites, signifying that they can be started as soon as the
	 * project has begun.
	 *
	 * @return An {@code ArrayList<Task>} of all {@link Task}s contained in this outline that do not have prerequisites.
	 */
	public ArrayList<Task> getStartTasks() {
		return (ArrayList<Task>) this.startTasks.clone();
	}

	/**
	 * Retrieves the {@link Task}s that have no dependants, signifying that their completion can be delayed to match the
	 * completion of the critical path without risking delay of overall completion.
	 *
	 * @return An {@code ArrayList<Task>} of all {@link Task}s contained in this outline that do not have dependants.
	 */
	public ArrayList<Task> getFinalTasks() {
		return (ArrayList<Task>) finalTasks.clone();
	}

	/**
	 * <p>Returns an {@link ArrayList} of all critical paths. Each critical path is stored in another ArrayList, with
	 * the final task at location 0, and each preceding task in the subsequent ArrayList locations, with the initial
	 * task located at {@code ArrayList.size() - 1}. </p> <p>NOTE: Critical tasks are tasks with no slack time. There is
	 * the possibility of multiple critical paths existing in the same project. However, if any task is delayed in the
	 * project beyond its slack time, there is the potential of the number and make-up of critical paths changing.</p>
	 *
	 * @return An {@link ArrayList}{@code <ArrayList>} of critical paths. The first critical path can be found in {@code
	 * getCriticalPath().get(0)}. The number of critical paths can be found with {@code getCriticalPath().size()}. The
	 * way to access the final task of the first critical path is {@code getCriticalPath().get(0).get(0)}.
	 */
	public ArrayList<ArrayList> getCriticalPaths() {
		if ( criticalPathsHasChanged ) {
			int maxEarlyFinishTime = 0;
			ArrayList<Task> maxEarlyFinishTimeTasks = new ArrayList<>();

			for ( Task task : finalTasks ) {
				if ( task.getEarlyFinishTime() == maxEarlyFinishTime ) {
					maxEarlyFinishTimeTasks.add( task );
				} else if ( task.getEarlyFinishTime() > maxEarlyFinishTime ) {
					maxEarlyFinishTime = task.getEarlyFinishTime();
					maxEarlyFinishTimeTasks.clear();
					maxEarlyFinishTimeTasks.add( task );
				}
			}

			ArrayList<ArrayList> criticalPaths = new ArrayList<>();

			for ( Task task : maxEarlyFinishTimeTasks ) {
				ArrayList<Task> criticalPath = new ArrayList<>();
				getCriticalPaths( task, criticalPath, criticalPaths );
			}
			criticalPathsHasChanged = false;
			this.criticalPaths = criticalPaths;
		}

		return this.criticalPaths;
	}

	// Recursive private method for the public getCriticalPaths
	private void getCriticalPaths( Task currTask, ArrayList<Task> currPath, ArrayList<ArrayList> allPaths ) {
		// If currTask is on the critical path
		if ( currTask.getSlackTime() == 0 ) {
			currPath.add( currTask );

			// If currTask is a starter task
			if ( currTask.isStartTask() ) {
				allPaths.add( currPath );
				return;
			}

			// Cycle through prereqs
			for ( Task task : currTask.getPrerequisites() ) {
				getCriticalPaths( task, currPath, allPaths );
			}
		}
	}

//	public void generateStructure() {
//		if ( phasesHasChanged ) {
//			// Find longest path
//			int longestPath = 0;
//			for ( Task task : finalTasks ) {
//				if ( task.getTier() > longestPath ) {
//					longestPath = task.getTier();
//				}
//			}
//
//			longestPath++;
//
//			// Find widest tier
//			int[] tiers = new int[longestPath];
//			for ( Task task : this ) {
//				tiers[task.getTier()]++;
//			}
//			int widestTier = 0;
//			for ( int currTier : tiers ) {
//				if ( currTier > widestTier ) {
//					widestTier = currTier;
//				}
//			}
//
//			// Populate structureArray
//			Task[][] phases = new Task[widestTier][longestPath];
//			for ( Task task : startTasks ) {
//
//			}
//		}
//	}
}
