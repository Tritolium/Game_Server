/**
 * 
 */
package games.catan;

import java.util.ArrayList;

import games.catan.board.Board;
import games.main.Game;
import userManagement.User;

/**
 * @author Dominik
 *
 */
public class Catan extends Game {

	private ArrayList<User> playerList = new ArrayList<User>();
	private User playerTurn;
	private Board board;
	
	private String gameData;
	
	public int getMaxPlayerAmount() {
		return 4;
	}

	public int getCurrentPlayerAmount() {
		return playerList.size();
	}

	public void execute(User user, String s) {

	}

	public void addUser(User user) {
		if (playerList.size() < getMaxPlayerAmount() && !playerList.contains(user)) {
			playerList.add(user);

			// sendDataToClients("USERJOINED");

			if (playerTurn == null) {
				playerTurn = user;
			}
		}
	}

	public String getGameData(String event) {
		switch (event) {
		case "SETUP":
			board.init();
			gameData = board.getStatus();
			return "joingame?game=Catan&setup=" + gameData;
			//return "joingame?game=Catan&setup=53411505415322241344031670528157342698060510063002100101000151515242524333";
		}
		return "";
	}

	public void init() {
		board = new Board(this);
	}
}
