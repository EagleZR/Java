package eaglezr.checkers.system;

public class Board {

	private int width;
	private int height;

	protected Object[][] places;

	public Board( int width, int height ) {
		this.width = width;
		this.height = height;
		places = new Object[width][height];
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public Object[][] getPlaces() {
		return places;
	}

	public void placePieceAt( Object piece, int x, int y ) throws IndexOutOfBoundsException {
		if ( x >= this.getWidth() || y >= this.getHeight() || x < 0 || y < 0 ) {
			throw new IndexOutOfBoundsException();
		}
		places[x][y] = piece;
	}

	public Object getPieceAt( int x, int y ) throws IndexOutOfBoundsException {
		if ( x >= this.getWidth() || y >= this.getHeight() || x < 0 || y < 0 ) {
			throw new IndexOutOfBoundsException();
		}
		return places[x][y];
	}

}
