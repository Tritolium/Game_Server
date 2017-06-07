package games.catan.board;

import games.catan.Player;

public class Crossroad {

	private int id;
	private int build;
	private int value;
	private Player owner;
	private Road[] roads;
	private Tile[] tiles;

	public Crossroad(){
		build = 0;
		value = 0;
		owner = null;
		roads = new Road[3];
		tiles = new Tile[3];
	}
	
	public Tile[] getTiles(){
		return tiles;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void addRoad(Road road){
		for(int i = 0; i < 3; i++){
			if(roads[i] == null){
				roads[i] = road;
				break;
			}
		}
		road.addCrossroad(this);
	}
	
	public void addTile(Tile tile){
		for(int i = 0; i < 3; i++){
			if(tiles[i] == null){
				tiles[i] = tile;
				break;
			}
		}
	}
	
	public void addValue(double val){
		value += val;
	}
}
