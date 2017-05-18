package games.catan.board;

import java.util.Random;

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
	 * Constructor of the board
	 * @param catan: the game logic
	 */
	public Board(Catan catan) {
		this.catan = catan;
	}

	/**
	 * Initializes the board
	 * @return true, if successful
	 */
	public boolean init() {

		if (initialized)
			return false;

		Tile[] temp = new Tile[37];
		String resource;
		int dice;

		/*
		 * Generate the Tiles in wrong order. In the board-game, the order of
		 * the dice-numbers starts in the middle of the board
		 */
		for (int i = 0; i < 19; i++) {
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

		initialized = true;
		return true;
	}

	/**
	 * Gets a random resource out of the set
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
	 * Gets the next dice number out of the set
	 * @return the next dice number
	 */
	private int getDiceNumber() {
		diceNumCounter++;
		return diceNumbers[diceNumCounter - 1];
	}
}
