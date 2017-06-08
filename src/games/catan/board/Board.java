package games.catan.board;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import dataManagement.Data;
import games.catan.Catan;

public class Board {

	/**
	 * The set of possible dice-numbers. The set is arranged in an array,
	 * because there is a given order
	 */
	private final int[] diceNumbers = { 5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11 };

	/**
	 * Index to iterate over the diceNumbers-array
	 */
	private int diceNumCounter = 0;

	/**
	 * The game logic
	 */
	private Catan catan;

	/**
	 * The tiles on the board
	 */
	private Tile[] tiles;

	/**
	 * The crossroads on the board
	 */
	private Crossroad[] crossroads;

	/**
	 * The roads on the board
	 */
	private Road[] roads;

	/**
	 * The tile where the thief is placed
	 */
	private Tile thief;

	/**
	 * The state of the board
	 */
	private boolean initialized;

	/**
	 * Initial count of desert tiles
	 */
	private int desertTile = 1;

	/**
	 * Initial count of wood tiles
	 */
	private int woodTile = 4;

	/**
	 * Initial count of sheep tiles
	 */
	private int sheepTile = 4;

	/**
	 * Initial count of wheat tiles
	 */
	private int wheatTile = 4;

	/**
	 * Initial count of clay tiles
	 */
	private int clayTile = 3;

	/**
	 * Initial count of ore tiles
	 */
	private int oreTile = 3;

	/**
	 * Initial count of harbor tiles
	 */
	private int harbor = 4;

	/**
	 * Initial count of sheep-harbor tiles
	 */
	private int sheepHarbor = 1;

	/**
	 * Initial count of wood-harbor tiles
	 */
	private int woodHarbor = 1;

	/**
	 * Initial count of wheat-harbor tiles
	 */
	private int wheatHarbor = 1;

	/**
	 * Initial count of clay-harbor tiles
	 */
	private int clayHarbor = 1;

	/**
	 * Initial count of ore-harbor tiles
	 */
	private int oreHarbor = 1;

	/**
	 * Constructor of the board
	 * 
	 * @param catan:
	 *            the game logic
	 */
	public Board(Catan catan) {
		tiles = new Tile[37];
		crossroads = new Crossroad[54];
		roads = new Road[72];
		this.catan = catan;
	}

	/**
	 * Initializes the board
	 * 
	 * @return true, if successful
	 */
	public boolean init() {

		if (initialized)
			return false;

		Tile[] temp = new Tile[37];
		String resource;

		int dice, i, j, id, id_1, id_2, id_3;

		/*
		 * Generate the Tiles in wrong order. In the board-game, the order of
		 * the dice-numbers starts in the middle of the board
		 */
		for (i = 0; i < 19; i++) {
			resource = getResource();
			if (!resource.equals("desert"))
				dice = getDiceNumber();
			else
				dice = 0;
			temp[i] = new Tile(resource, dice, 0);
		}

		/*
		 * Sort the tiles into the right order
		 */
		tiles[0] = temp[2];
		tiles[1] = temp[1];
		tiles[2] = temp[0];
		tiles[3] = temp[3];
		tiles[4] = temp[13];
		tiles[5] = temp[12];
		tiles[6] = temp[11];
		tiles[7] = temp[4];
		tiles[8] = temp[14];
		tiles[9] = temp[18];
		tiles[10] = temp[17];
		tiles[11] = temp[10];
		tiles[12] = temp[5];
		tiles[13] = temp[15];
		tiles[14] = temp[16];
		tiles[15] = temp[9];
		tiles[16] = temp[6];
		tiles[17] = temp[7];
		tiles[18] = temp[8];

		/*
		 * Place the thief
		 */
		for (i = 0; i < 18; i++) {
			if (tiles[i].getResource().equals("desert")) {
				thief = tiles[i];
				break;
			}
		}

		Random rand = new Random();
		int startHarbor = rand.nextInt(2);
		int orientation = 0;
		/*
		 * Generate the water/harbor tiles
		 */
		for (i = 19; i < 37; i++) {
			resource = "water";

			/*
			 * Every second tile get's a harbor
			 */
			if ((i + startHarbor) % 2 == 0) {
				resource = getSeaResource();
			}
			/*
			 * Get the orientation for harbor tiles
			 */
			if (!resource.equals("water")) {
				orientation = rand.nextInt(2);
				if (orientation == 0) {
					if (i < 22) {
						orientation = 0;
					} else if (i < 25) {
						orientation = 1;
					} else if (i < 28) {
						orientation = 2;
					} else if (i < 31) {
						orientation = 3;
					} else if (i < 34) {
						orientation = 4;
					} else {
						orientation = 5;
					}
				} else {
					if (i > 34 || i == 19) {
						orientation = 0;
					} else if (i > 31) {
						orientation = 5;
					} else if (i > 28) {
						orientation = 4;
					} else if (i > 25) {
						orientation = 3;
					} else if (i > 22) {
						orientation = 2;
					} else if (i > 19) {
						orientation = 1;
					}
				}
			}

			tiles[i] = new Tile(resource, 0, orientation);
		}

		/*
		 * Generate all 72 roads
		 */
		for (i = 0; i < 72; i++) {
			roads[i] = new Road(i);
		}

		/*
		 * Generate all 54 crossroads
		 */
		for (i = 0; i < 54; i++) {
			crossroads[i] = new Crossroad();
		}

		/*
		 * Link all the crossroads
		 */
		HashMap<String, String> link = Data.readFile("data/Catan/crossroadmap_standard.txt");
		for (Entry<String, String> l : link.entrySet()) {
			id = Integer.parseInt(l.getKey());
			String split[] = l.getValue().split(",");
			for (i = 1; i < split.length; i++) {
				crossroads[id].addRoad(roads[Integer.parseInt(split[i])]);
			}
		}

		/*
		 * Link the crossroads to the tiles
		 */
		link = Data.readFile("data/Catan/tilemap_standard.txt");
		for (Entry<String, String> l : link.entrySet()) {
			id = Integer.parseInt(l.getKey());
			String split[] = l.getValue().split(",");
			for (i = 1; i < split.length; i++) {
				tiles[id].addCrossroad(crossroads[Integer.parseInt(split[i])]);
			}
		}

		/*
		 * Link crossroads to watertiles, depending on orientation
		 */
		link = Data.readFile("data/Catan/watertilemap_standard.txt");
		Tile waterTile = new Tile("water", 0, 0);
		for (Entry<String, String> l : link.entrySet()) {
			id = Integer.parseInt(l.getKey());
			String split[] = l.getValue().split(",");
			if (id == 19 || id == 22 || id == 25 || id == 28 || id == 31 || id == 34) {
				id_1 = Integer.parseInt(split[1]);
				id_2 = Integer.parseInt(split[2]);
				tiles[id].addCrossroad(crossroads[id_1]);
				tiles[id].addCrossroad(crossroads[id_2]);
			} else {
				// TODO use orientations
				if (tiles[i].getOrientation() == 0) {
					id_1 = Integer.parseInt(split[1]);
					id_2 = Integer.parseInt(split[2]);
					id_3 = Integer.parseInt(split[3]);
					tiles[i].addCrossroad(crossroads[id_1]);
					tiles[i].addCrossroad(crossroads[id_2]);
					crossroads[id_3].addTile(waterTile);
				} else {
					id_1 = Integer.parseInt(split[4]);
					id_2 = Integer.parseInt(split[5]);
					id_3 = Integer.parseInt(split[6]);
					tiles[i].addCrossroad(crossroads[id_1]);
					tiles[i].addCrossroad(crossroads[id_2]);
					crossroads[id_3].addTile(waterTile);
				}
			}
		}

		/*
		 * Set the ids and calc the value
		 */
		for (i = 0; i < 37; i++) {
			tiles[i].calcValue();
			tiles[i].setId(i);
		}

		for (i = 0; i < 54; i++) {
			crossroads[i].setId(i);
			for (j = 0; j < 3; j++) {
				try {
					crossroads[i].addValue(crossroads[i].getTiles()[j].getValue());
				} catch (NullPointerException e) {

				}
			}
		}

		for (i = 19; i < 37; i++) {
			temp[i] = tiles[i];
		}

		tiles[19] = temp[19];
		tiles[20] = temp[20];
		tiles[21] = temp[21];
		tiles[22] = temp[22];
		tiles[23] = temp[36];
		tiles[24] = temp[23];
		tiles[25] = temp[35];
		tiles[26] = temp[24];
		tiles[27] = temp[34];
		tiles[28] = temp[25];
		tiles[29] = temp[33];
		tiles[30] = temp[26];
		tiles[31] = temp[32];
		tiles[32] = temp[27];
		tiles[33] = temp[31];
		tiles[34] = temp[30];
		tiles[35] = temp[29];
		tiles[36] = temp[28];

		initialized = true;
		return true;
	}

	/**
	 * Gets a random resource out of the set
	 * 
	 * @return a random resource
	 */
	private String getResource() {

		int tilenumber = desertTile + woodTile + sheepTile + wheatTile + oreTile + clayTile;

		Random rand = new Random();
		int tileType = rand.nextInt(tilenumber);

		if (tileType < desertTile) {
			desertTile--;
			return "desert";
		} else if (tileType < desertTile + woodTile) {
			woodTile--;
			return "wood";
		} else if (tileType < desertTile + woodTile + sheepTile) {
			sheepTile--;
			return "sheep";
		} else if (tileType < desertTile + woodTile + sheepTile + wheatTile) {
			wheatTile--;
			return "wheat";
		} else if (tileType < desertTile + woodTile + sheepTile + wheatTile + oreTile) {
			oreTile--;
			return "ore";
		} else {
			clayTile--;
			return "clay";
		}
	}

	/**
	 * Get's a harbor out of the set
	 * 
	 * @return a harbor out of the set
	 */
	private String getSeaResource() {
		int tilenumber = harbor + sheepHarbor + woodHarbor + wheatHarbor + clayHarbor + oreHarbor;

		Random rand = new Random();
		int tileType = rand.nextInt(tilenumber);

		if (tileType < harbor) {
			harbor--;
			return "harbor";
		} else if (tileType < harbor + sheepHarbor) {
			sheepHarbor--;
			return "sheepHarbor";
		} else if (tileType < harbor + sheepHarbor + woodHarbor) {
			woodHarbor--;
			return "woodHarbor";
		} else if (tileType < harbor + sheepHarbor + woodHarbor + wheatHarbor) {
			wheatHarbor--;
			return "wheatHarbor";
		} else if (tileType < harbor + sheepHarbor + woodHarbor + wheatHarbor + clayHarbor) {
			clayHarbor--;
			return "clayHarbor";
		} else {
			oreHarbor--;
			return "oreHarbor";
		}
	}

	/**
	 * Gets the next dice number out of the set
	 * 
	 * @return the next dice number
	 */
	private int getDiceNumber() {
		diceNumCounter++;
		return diceNumbers[diceNumCounter - 1];
	}

	public String getStatus() {

		String[] res = new String[74];

		int i;

		for (i = 0; i < 19; i++) {
			String resource = tiles[i].getResource();
			res[i] = getResourceStatus(resource);
			
			if (tiles[i].getDice() == 0) {
				res[i + 19] = "" + 0;
			} else if (tiles[i].getDice() < 7) {
				res[i + 19] = "" + (tiles[i].getDice() - 2);
			} else {
				res[i + 19] = "" + (tiles[i].getDice() - 3);
			}
		}

		for (i = 19; i < 37; i++) {
			String resource = tiles[i].getResource();
			res[i + 19] = getResourceStatus(resource);
			
			res[i + 19 + 18] = "" + tiles[i].getOrientation();
		}

		String result = "";
		for (i = 0; i < 74; i++) {
			result += res[i];
		}
		return result;
	}

	/**
	 * Returns the resource represented as an written int
	 * 
	 * @param resource
	 *            the resource as a String
	 * @return the int-representation as a String
	 */
	private String getResourceStatus(String resource) {
		switch (resource) {
		case "desert":
		case "water":
			return "0";
		case "sheep":
		case "harbor":
			return "1";
		case "clay":
		case "sheepHarbor":
			return "2";
		case "ore":
		case "woodHarbor":
			return "3";
		case "wheat":
		case "wheatHarbor":
			return "4";
		case "wood":
		case "clayHarbor":
			return "5";
		case "oreHarbor":
			return "6";
		}
		return "";
	}
}
