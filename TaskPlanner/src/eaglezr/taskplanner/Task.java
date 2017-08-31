package eaglezr.taskplanner;

import java.util.ArrayList;

public class Task {

	private final String id;
	private final String name;
	private String description;
	private int duration;
	private ArrayList<Task> prerequisites;
	private ArrayList<Task> dependencies;

	public Task( String id, String name, String description, int duration, final Task... prerequisites ) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.duration = duration;
		setPrerequisites( prerequisites );
	}

	public String getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public int getDuration() {
		return this.duration;
	}

	public ArrayList<Task> getPrerequisites() {
		if ( prerequisites == null ) {
			return null;
		}
		return (ArrayList<Task>) prerequisites.clone();
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public void setDuration( int duration ) {
		this.duration = duration;
	}

	public void setPrerequisites( Task... prerequisites ) {
		// 1. Remove dependency from old prerequisites
		if ( this.dependencies != null && this.dependencies.size() > 0 ) {
			for ( Task dependency : this.dependencies ) {
				dependency.reportIndependence( this );
			}
		}

		// 2. Set new prerequisites
		if ( prerequisites == null || prerequisites.length == 0 || prerequisites[0] == null ) {
			this.prerequisites = null;
		} else {
			this.prerequisites.clear();
			this.prerequisites.addAll( prerequisites );

			// 3. Add dependency to new prerequisites
			for ( Task prerequisite : prerequisites ) {
				prerequisite.reportDependency( this );
			}
		}
	}

	public int getEarlyStartTime() {
		if ( prerequisites == null ) {
			return 0;
		} else if ( prerequisites.length == 1 ) {
			return prerequisites[0].getEarlyFinishTime();
		} else {
			int latestPrereqFinishTime = prerequisites[0].getEarlyFinishTime();
			for ( int i = 1; i < prerequisites.length; i++ ) {
				int currPrereqFinishTime = prerequisites[i].getEarlyFinishTime();
				if ( currPrereqFinishTime > latestPrereqFinishTime ) {
					latestPrereqFinishTime = currPrereqFinishTime;
				}
			}
			return latestPrereqFinishTime;
		}
	}

	public int getEarlyFinishTime() {
		return getEarlyStartTime() + this.duration;
	}

	protected void reportDependency( Task task ) {
		if ( this.dependencies == null ) {
			this.dependencies = new Task[] { task };
		} else {
			Task[] newDependencies = new Task[dependencies.length + 1];
			System.arraycopy( dependencies, 0, newDependencies, 0, dependencies.length );
			newDependencies[newDependencies.length - 1] = task;
			this.dependencies = newDependencies;
		}
	}

	protected void reportIndependence( Task task ) {
		if ( this.dependencies == null || this.dependencies.length == 0 ) {

		} else if ( this.dependencies.length == 1 && this.dependencies[0].equals( task ) ) {
			this.dependencies = null;
		} else {
			Task[] newDependencies = new Task[dependencies.length - 1];
			for ( int i = 0, u = 0; i < this.dependencies.length && u < newDependencies.length; i++, u++ ) {
				if ( dependencies[i].equals( task ) ) {
					u--;
				} else {
					newDependencies[u] = dependencies[i];
				}
			}
			this.dependencies = newDependencies;
		}
	}

	public Task[] getDependencies() {
		if ( this.dependencies == null ) {
			return null;
		}
		return this.dependencies.clone();
	}
}
