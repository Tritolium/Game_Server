package games.catan.board;

public class Tile {

	private String resource;
	private int dice;
	private int orientation;
	private Crossroad[] crossroads;

	public Tile(String resource, int dice, int orientation){
		this.resource = resource;
		this.dice = dice;
		this.orientation = orientation;
		
		crossroads = new Crossroad[6];
	}
}
