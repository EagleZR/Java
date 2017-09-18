package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

import java.util.LinkedList;
import java.util.List;

public class PersonButtonScrollPane extends ScrollPane {

	private Pane buttonPane;
	private LinkedList<PersonButton> buttons;

	public PersonButtonScrollPane() {
		LoggingTool.print( "Constructing a new PersonButtonScrollPane." );
		this.setHbarPolicy( ScrollPane.ScrollBarPolicy.NEVER );
		this.setVbarPolicy( ScrollPane.ScrollBarPolicy.ALWAYS );
		this.setFitToWidth( true );
		buttonPane = new Pane();
		this.setContent( buttonPane );
		this.buttons = new LinkedList<>();
	}

	public void addButton( PersonButton button ) {
		LoggingTool.print( "PersonButtonScrollPane: Adding Button with text: " + button.getText() );
		button.prefWidthProperty().bind( buttonPane.widthProperty() );
		button.layoutXProperty().setValue( 0 );
		if ( buttons.size() == 0 ) {
			button.layoutYProperty().setValue( 0 );
		} else {
			Button previous = buttons.get( buttons.size() - 1 );
			button.layoutYProperty().bind( previous.layoutYProperty().add( previous.heightProperty() ) );
		}
		buttons.add( button );
		buttonPane.getChildren().add( button );
	}

	public void removeButton( PersonButton button ) {
		LoggingTool.print( "PersonButtonScrollPane: Removing Button with text: " + button.getText() );
		int index = buttons.indexOf( button );
		if ( buttons.size() == 1 ) {

		} else if ( index == 0 ) {
			Button next = buttons.get( 1 );
			next.layoutYProperty().unbind();
			next.layoutYProperty().setValue( 0 );
		} else {
			Button next = buttons.get( index + 1 );
			Button prev = buttons.get( index - 1 );
			next.layoutYProperty().bind( prev.layoutYProperty().add( prev.heightProperty() ) );
		}

		buttonPane.getChildren().remove( button );
		buttons.remove( button );
	}

	public List<PersonButton> getButtons() {
		return buttons;
	}

	public void addPerson( Person person ) {
		PersonButton button = new PersonButton( person );
		addButton( button );
	}

	public void removePerson( Person person ) {
		if ( containsPerson( person ) ) {
			removeButton( getPersonButton( person ) );
		}
	}

//	@Override public void updateTeamChange() {
//		LoggingTool.print( "PersonButtonScrollPane: Updating." );
//		// Check every member on team has button
//		for ( Person person : team.getMembers() ) {
//			if ( !containsPerson( person ) ) {
//				LoggingTool.print( "PersonButtonScrollPane: " + person.getName() + " was not in the list." );
//				addPerson( person );
//			}
//		}
//
//		// Check every button has member on team
//		for ( PersonButton button : buttons ) {
//			boolean isOnTeam = false;
//			for ( Person person : team.getMembers() ) {
//				if ( button.getPerson().equals( person ) ) {
//					isOnTeam = true;
//				}
//			}
//			if ( !isOnTeam ) {
//				LoggingTool.print( "PersonButtonScrollPane: Found a Button for person" + button.getText()
//						+ " who is no longer on the team." );
//				removeButton( button );
//			}
//		}
//	}

	public boolean containsPerson( Person person ) {
		for ( PersonButton button : buttons ) {
			if ( button.getPerson().equals( person ) ) {
				// Updates the name if it's changed.
				if ( !button.getText().equals( person.getName() ) ) {
					LoggingTool
							.print( "PersonButtonScrollPane: Updating the name displayed in the button for: " + person
									.getName() + "." );
					button.setText( person.getName() );
				}
				return true;
			}
		}
		return false;
	}

	public PersonButton getPersonButton( Person person ) {
		for ( PersonButton button : buttons ) {
			if ( button.getPerson().equals( person ) ) {
				return button;
			}
		}
		return null;
	}
}
