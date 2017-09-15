package ksu.fall2017.swe4663.group1.projectmanagementsystem.team;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.effort.Effort;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.effort.ProjectEffort;

import java.io.*;
import java.util.LinkedList;

/**
 * A representation of a project team. The team consists of {@link Person} instances, some of which are flagged as a
 * manager. The team also comes with an associated {@link ProjectEffort} that records the submitted {@link Effort}s
 * submitted by each of the {@link Person}s on this team.
 */
public class Team implements Serializable {

	private static final long serialVersionUID = -105595693545291325L;
	private static String saveDirectory = "saves/";
	private LinkedList<Person> teamMembers;
	private ProjectEffort projectEffort;

	public Team( Person... teamMembers ) {
		this.teamMembers = new LinkedList<>();
		addToTeam( teamMembers );
	}

	public static void save( Team team, String fileName ) throws IOException {
		File file = new File( saveDirectory + fileName );
		file.createNewFile();
		ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( file ) );
		out.writeObject( team );
	}

	public static Team load( String fileName ) throws IOException, ClassNotFoundException {
		File file = new File( saveDirectory + fileName ); // LATER FileNotFoundException ?
		if ( !file.exists() ) {
			throw new FileNotFoundException( "The File, " + fileName + " could not be located." );
		}
		ObjectInputStream in = new ObjectInputStream( new FileInputStream( file ) );
		return (Team) in.readObject();
	}

	/**
	 * Returns the {@link ProjectEffort} associated with this team.
	 *
	 * @return The {@link ProjectEffort} that belongs to this team.
	 */
	public ProjectEffort getProjectEffort() {
		if ( projectEffort == null ) {
			projectEffort = new ProjectEffort();
		}
		return projectEffort;
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
	 * Takes a submitted {@link Effort} from one of this team's {@link Person} members.
	 *
	 * @param effort The newly-submitted {@link Effort} from one of this team's members.
	 * @throws PersonNotOnTeamException If the {@link Person} who completed the {@link Effort} is not on this team.
	 */
	void registerEffort( Effort effort ) throws PersonNotOnTeamException {
		if ( !teamMembers.contains( effort.getPerson() ) ) {
			throw new PersonNotOnTeamException( effort.getPerson() + " is not on this team." );
		}
		getProjectEffort().registerEffort( effort );
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
}
