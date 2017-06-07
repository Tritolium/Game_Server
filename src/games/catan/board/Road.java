package games.catan.board;

import games.catan.Player;

public class Road {

	private int id;
	private Player owner;
	private Crossroad[] crossroads;

	public Road(int id) {
		this.id = id;
		owner = null;
		crossroads = new Crossroad[2];
	}

	public void addCrossroad(Crossroad cross){
		for(int i = 0; i < 2; i++){
			if(crossroads[i] == null){
				crossroads[i] = cross;
				break;
			}
		}
	}
}
