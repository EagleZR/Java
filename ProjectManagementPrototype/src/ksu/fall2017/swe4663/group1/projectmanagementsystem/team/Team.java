package ksu.fall2017.swe4663.group1.projectmanagementsystem.team;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.hourlog.WorkedHours;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.hourlog.ProjectHourLog;

import java.io.*;
import java.util.LinkedList;

/**
 * A representation of a project team. The team consists of {@link Person} instances, some of which are flagged as a
 * manager. The team also comes with an associated {@link ProjectHourLog} that records the submitted {@link
 * WorkedHours}s submitted by each of the {@link Person}s on this team.
 */
public class Team implements Serializable {

	private static final long serialVersionUID = -105595693545291325L;
	private static String saveDirectory = "saves/";
	private LinkedList<Person> teamMembers;
	private ProjectHourLog projectHourLog;

	public Team( Person... teamMembers ) {
		this.teamMembers = new LinkedList<>();
		addToTeam( teamMembers );
	}

	public static void save( Team team, String fileName ) throws IOException {
		File file = new File( saveDirectory + fileName );
		save( team, file );
	}

	public static void save( Team team, File saveFile ) throws IOException {
		if ( !saveFile.exists() ) {
			saveFile.createNewFile();
		}
		ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( saveFile ) );
		out.writeObject( team );
	}

	public static Team load( String fileName ) throws IOException, ClassNotFoundException {
		File file = new File( saveDirectory + fileName ); // LATER FileNotFoundException ?
		return load( file );
	}

	public static Team load( File loadFile ) throws IOException, ClassNotFoundException {
		if ( !loadFile.exists() ) {
			throw new FileNotFoundException( "The File, " + loadFile.getName() + " could not be located." );
		}
		ObjectInputStream in = new ObjectInputStream( new FileInputStream( loadFile ) );
		return (Team) in.readObject();
	}

	/**
	 * Returns the {@link ProjectHourLog} associated with this team.
	 *
	 * @return The {@link ProjectHourLog} that belongs to this team.
	 */
	public ProjectHourLog getProjectHourLog() {
		if ( projectHourLog == null ) {
			projectHourLog = new ProjectHourLog();
		}
		return projectHourLog;
	}

	public void addToTeam( Person... teamMembers ) {
		for ( Person person : teamMembers ) {
			this.teamMembers.add( person );
			person.addToTeam( this );
		}
	}

	public void removeFromTeam( Person person ) {

	}

	/**
	 * Takes a submitted {@link WorkedHours} from one of this team's {@link Person} members.
	 *
	 * @param workedHours The newly-submitted {@link WorkedHours} from one of this team's members.
	 * @throws PersonNotOnTeamException If the {@link Person} who completed the {@link WorkedHours} is not on this
	 *                                  team.
	 */
	void registerEffort( WorkedHours workedHours ) throws PersonNotOnTeamException {
		if ( !teamMembers.contains( workedHours.getPerson() ) ) {
			throw new PersonNotOnTeamException( workedHours.getPerson() + " is not on this team." );
		}
		getProjectHourLog().registerEffort( workedHours );
	}

	/**
	 * Retrieves the list of {@link Person} members of this team.
	 *
	 * @return All of the {@link Person} members on this team.
	 */
	public LinkedList<Person> getMembers() {
		return teamMembers;
	}

	public Person getManager() throws PersonNotOnTeamException {
		for ( Person person : teamMembers ) {
			if ( person.isManager() ) {
				return person;
			}
		}
		throw new PersonNotOnTeamException( "There is no manager on this team." );
	}

	public LinkedList<Person> getManagers() {
		LinkedList<Person> managers = new LinkedList<>();
		for ( Person person : teamMembers ) {
			if ( person.isManager() ) {
				managers.add( person );
			}
		}
		return managers;
	}

	public void promote( Person person ) { // LATER Overly complicated? Why not just use an isManager boolean flag?
		person.promote();
	}

	public void demote( Person person ) {
		person.demote();
	}

	@Override public boolean equals( Object other ) {
		Team team;
		if ( other.getClass().equals( Team.class ) ) {
			team = (Team) other;
		} else {
			return false;
		}
		if ( teamMembers.size() != team.teamMembers.size() ) {
			return false;
		} else {
			for ( int i = 0; i < teamMembers.size(); i++ ) {
				if ( !teamMembers.contains( team.teamMembers.get( i ) ) ) {
					return false;
				}
			}
		}
		return this.getProjectHourLog().equals( team.getProjectHourLog() );
	}
}
