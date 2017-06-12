package gameManagement;

import org.tritol.server.GameExchange;

import games.catan.Catan;
import sessionHandling.SessionHandler;
import userManagement.User;

public class GameManager {
	private static GameManager instance;
	private Catan runningGame;

	public static GameManager getInstance() {
		if (instance == null)
			instance = new GameManager();
		return instance;
	}
	
	public Catan getRunningGame(){
		return runningGame;
	}
	
	public void joinGame(GameExchange exchange){
		GameExchange response = new GameExchange();
		User user = SessionHandler.getInstance().getUser(exchange.getClientThread());

		if(runningGame == null){
			runningGame = new Catan();
			runningGame.init();
		}
		
		runningGame.addUser(user);
		
		response.setMethod("joingame");
		response.setBody(runningGame.getGameData("SETUP"));
		
		exchange.respond(response);
	}
}
