package eaglezr.checkers.system;

public class CheckersPiece {

	private CheckersPlayer owner;
	private boolean isPromoted;

	public CheckersPiece( CheckersPlayer owner, boolean isPromoted ) {
		this.owner = owner;
		this.isPromoted = isPromoted;
	}

	public CheckersPlayer getOwner() {
		return this.owner;
	}

	public boolean isPromoted() {
		return isPromoted;
	}

	public void promote() {
		this.isPromoted = true;
	}

	public void demote() {
		this.isPromoted = false;
	}
}
