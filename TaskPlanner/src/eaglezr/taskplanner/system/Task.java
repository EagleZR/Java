package eaglezr.taskplanner.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task {

	private final String id;
	private final String name;
	private String description;
	private int duration;

	private ArrayList<Task> prerequisites = new ArrayList<>();
	private ArrayList<Task> dependencies = new ArrayList<>();

	private int tier;

	private int earlyStartTime;
	private int earlyFinishTime;
	private int lateStartTime;
	private int lateFinishTime;

	private boolean tierHasChanged = true;
	private boolean ES_HasChanged = true;
	private boolean EF_HasChanged = true;
	private boolean LS_HasChanged = true;
	private boolean LF_HasChanged = true;

	public Task( String id, String name, String description, int duration ) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.duration = duration;
		setPrerequisites( new ArrayList<>() );
	}

	public Task( String id, String name, String description, int duration, final Task... prerequisites ) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.duration = duration;
		setPrerequisites( prerequisites );
	}

	public Task( String id, String name, String description, int duration, final List<Task> prerequisites ) {
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

	public void setDescription( String description ) {
		reportChange();
		this.description = description;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration( int duration ) {
		reportChange();
		this.duration = duration;
	}

	public ArrayList<Task> getPrerequisites() {
		if ( prerequisites == null ) {
			return null;
		}
		return (ArrayList<Task>) prerequisites.clone();
	}

	public void setPrerequisites( Task... prerequisites ) {
		setPrerequisites( Arrays.asList( prerequisites ) );
	}

	public void setPrerequisites( List<Task> prerequisites ) {
		reportChange();
		// 1. Remove dependency from old prerequisites
		for ( Task prerequisite : this.prerequisites ) {
			prerequisite.reportIndependence( this );
		}

		// 2. Set new prerequisites
		this.prerequisites.clear();
		if ( prerequisites != null ) {
			this.prerequisites.addAll( prerequisites );
		}

		// 3. Add dependency to new prerequisites
		for ( Task prerequisite : this.prerequisites ) {
			prerequisite.reportDependency( this );
		}
	}

	public int getEarlyStartTime() {
		if ( this.ES_HasChanged ) {
			if ( prerequisites.size() == 0 ) {
				this.earlyStartTime = 0;
			} else if ( prerequisites.size() == 1 ) {
				this.earlyStartTime = prerequisites.get( 0 ).getEarlyFinishTime();
			} else {
				int latestPrereqFinishTime = prerequisites.get( 0 ).getEarlyFinishTime();
				for ( Task currPrereq : prerequisites ) {
					int currPrereqFinishTime = currPrereq.getEarlyFinishTime();
					if ( currPrereqFinishTime > latestPrereqFinishTime ) {
						latestPrereqFinishTime = currPrereqFinishTime;
					}
				}
				this.earlyStartTime = latestPrereqFinishTime;
			}
			this.ES_HasChanged = false;
		}
		return this.earlyStartTime;
	}

	public int getEarlyFinishTime() {
		if ( this.EF_HasChanged ) {
			this.earlyFinishTime = this.getEarlyStartTime() + duration;
			this.EF_HasChanged = false;
		}
		return this.earlyFinishTime;
	}

	protected void reportDependency( Task task ) {
		reportChange();
		dependencies.add( task );
	}

	protected void reportIndependence( Task task ) {
		reportChange();
		if ( dependencies.contains( task ) ) {
			dependencies.remove( task );
		}
	}

	public ArrayList<Task> getDependencies() {
		if ( this.dependencies == null ) {
			return null;
		}
		return (ArrayList<Task>) this.dependencies.clone();
	}

	public int getLateStartTime() {
		if ( LS_HasChanged ) {
			this.lateStartTime = getLateFinishTime() - this.duration;
			this.LS_HasChanged = false;
		}
		return this.lateStartTime;
	}

	public int getLateFinishTime() {
		if ( LF_HasChanged ) {
			if ( this.dependencies.size() == 0 ) {
				this.lateFinishTime = this.getEarlyFinishTime();
			} else if ( this.dependencies.size() == 1 ) {
				this.lateFinishTime = this.dependencies.get( 0 ).getLateStartTime();
			} else {
				int latestPrereqFinishTime = dependencies.get( 0 ).getEarlyFinishTime();
				for ( Task currDependant : dependencies ) {
					int currDependStartTime = currDependant.getLateStartTime();
					if ( currDependStartTime < latestPrereqFinishTime ) {
						latestPrereqFinishTime = currDependStartTime;
					}
				}

				this.lateFinishTime = latestPrereqFinishTime;
			}
			this.LF_HasChanged = false;
		}
		return this.lateFinishTime;
	}

	public int getSlackTime() {
		return getLateFinishTime() - getEarlyFinishTime();
	}

	public boolean isStartTask() {
		return this.prerequisites.size() == 0;
	}

	public boolean isFinalTask() {
		return this.dependencies.size() == 0;
	}

	private void reportChange() {
		tierHasChanged = true;
		ES_HasChanged = true;
		EF_HasChanged = true;
		LS_HasChanged = true;
		LF_HasChanged = true;
	}

	public int getTier() {
		if ( tierHasChanged ) {
			if ( this.isStartTask() ) {
				this.tier = 0;
			} else {
				int maxDepnTier = 0;
				for ( Task task : prerequisites ) {
					if (task.getTier() > maxDepnTier) {
						maxDepnTier = task.tier;
					}
				}
				this.tier = maxDepnTier + 1;
			}
			tierHasChanged = false;
		}
		return tier;
	}
}
