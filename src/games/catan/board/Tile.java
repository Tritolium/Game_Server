package games.catan.board;

public class Tile {

	private int id;
	private String resource;
	private int dice;
	private double value = 0;
	private int orientation;
	private Crossroad[] crossroads;

	public Tile(String resource, int dice, int orientation) {
		this.resource = resource;
		this.dice = dice;
		this.orientation = orientation;

		crossroads = new Crossroad[6];
	}

	public String getResource() {
		return resource;
	}
	
	public int getDice(){
		return dice;
	}
	
	public double getValue(){
		return value;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addCrossroad(Crossroad cross) {
		for (int i = 0; i < 6; i++) {
			if (crossroads[i] == null) {
				crossroads[i] = cross;
				break;
			}
		}
		cross.addTile(this);
	}

	public void calcValue() {
		if (this.resource.equals("wood") || this.resource.equals("sheep") || this.resource.equals("wheat")) {
			this.value = 0.25 * diceValue();
		} else if (this.resource.equals("ore") || this.resource.equals("clay")) {
			this.value = 0.33 * diceValue();
		} else {
			this.value = 0.0;
		}
	}

	public double diceValue() {
		if (this.dice == 2 || this.dice == 12)
			return 1;
		else if (this.dice == 3 || this.dice == 11)
			return 2;
		if (this.dice == 4 || this.dice == 10)
			return 3;
		if (this.dice == 5 || this.dice == 9)
			return 4;
		if (this.dice == 6 || this.dice == 8)
			return 5;
		else
			return 0.0;
	}
}
