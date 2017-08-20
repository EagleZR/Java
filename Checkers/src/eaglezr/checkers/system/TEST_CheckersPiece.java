package eaglezr.checkers.system;

import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class TEST_CheckersPiece {

	@Test public void testConstructors() {
		CheckersPlayer player = new CheckersPlayer( Color.WHITE);
		CheckersPiece piece = new CheckersPiece( player, false );
		assertTrue( piece.getOwner().equals( player ));
		assertTrue( piece.isPromoted() == false );
	}

	@Test public void testPromote() {
		CheckersPlayer player = new CheckersPlayer(Color.WHITE);
		CheckersPiece piece = new CheckersPiece( player, false );
		assertFalse( piece.isPromoted() );
		piece.promote();
		assertTrue( piece.isPromoted() );
		piece.demote();
		assertFalse( piece.isPromoted() );
	}
}
