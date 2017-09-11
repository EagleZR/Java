package eaglezr.taskplanner.system;

/**
 * Public exception for catching the inner exceptions thrown by this package's classes.
  */
public abstract class TaskPlannerException extends Exception {
	// Forcing there to be a string due to anonymous inherited classes
	public TaskPlannerException( String s ) {
		super( s );
	}
}
