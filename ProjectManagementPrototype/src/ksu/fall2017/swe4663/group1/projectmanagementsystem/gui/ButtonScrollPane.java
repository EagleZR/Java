package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;

public class ButtonScrollPane extends ScrollPane {

	Pane buttonPane;
	LinkedList<Button> buttons;
	Button lastButton = null;

	public ButtonScrollPane() {
		LoggingTool.print( "Constructing a new ButtonScrollPane." );
		this.setHbarPolicy( ScrollPane.ScrollBarPolicy.NEVER );
		this.setVbarPolicy( ScrollPane.ScrollBarPolicy.ALWAYS );
		this.setFitToHeight( true );
		this.setFitToWidth( true );
		buttonPane = new Pane();
		this.setContent( buttonPane );
		this.buttons = new LinkedList<>();
	}

	public void addButton( Button button ) {
		LoggingTool.print( "ButtonScrollPane: Adding Button with text: " + button.getText() );
		button.prefWidthProperty().bind( buttonPane.widthProperty() );
		button.layoutXProperty().setValue( 0 );
		if ( buttons.size() == 0 ) {
			button.layoutYProperty().setValue( 0 );
		} else {
			Button previous = buttons.get( buttons.size() - 1 );
			button.layoutYProperty().bind( previous.layoutYProperty().add( previous.heightProperty() ) );
		}
		if ( lastButton != null && !button.equals( lastButton ) ) {
			lastButton.layoutYProperty().bind( button.layoutYProperty().add( button.heightProperty() ) );
		}
		buttons.add( button );
		buttonPane.getChildren().add( button );
	}

	public void removeButton( Button button ) {
		LoggingTool.print( "ButtonScrollPane: Removing Button with text: " + button.getText() );
		if ( button.equals( lastButton ) ) {
			lastButton = null;
		}
		int index = buttons.indexOf( button );
		if ( buttons.size() == 1 ) {
			if ( lastButton != null ) {
				lastButton.layoutYProperty().unbind();
				lastButton.layoutYProperty().setValue( 0 );
			}
		} else if ( index == 0 ) {
			Button next = buttons.get( 1 );
			next.layoutYProperty().unbind();
			next.layoutYProperty().setValue( 0 );
		} else if ( index == buttons.size() - 1 ) {
			if ( lastButton != null ) {
				Button prev = buttons.get( index - 1 );
				lastButton.layoutYProperty().bind( prev.layoutYProperty().add( prev.heightProperty() ) );
			}
		} else {
			Button next = buttons.get( index + 1 );
			Button prev = buttons.get( index - 1 );
			next.layoutYProperty().bind( prev.layoutYProperty().add( prev.heightProperty() ) );
		}

		buttonPane.getChildren().remove( button );
		buttons.remove( button );
	}

	public void setLastButton( Button button ) {
		LoggingTool.print( "ButtonScrollPane: Setting as LastButton the Button with text: " + button.getText() );
		if ( lastButton != null ) {
			buttonPane.getChildren().remove( lastButton );
		}
		this.lastButton = button;
		if ( !buttons.contains( button ) ) {
			addButton( button );
		}
		buttons.remove( lastButton );
	}

	public List<Button> getButtons() {
		return buttons;
	}
}
