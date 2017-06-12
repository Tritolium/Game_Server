package games.main;

import java.util.ArrayList;

import org.tritol.server.GameExchange;

import userManagement.User;

public class Game extends Thread{
	
	private Thread thread;
	
	protected User creator = null;
	protected ArrayList<User> playerList = new ArrayList<User>();

	public Game(){
		thread = new Thread(this);
		thread.start();
	}
	
	public User getCreator(){
		return creator;
	}
	
	public void setCreator(User creator){
		this.creator = creator;
	}
	
	public void sendGameDataToUser(User user, String data){
		GameExchange response = new GameExchange("gameData", data);
		user.send(response);
	}
	
	public void sendGameDataToUsers(String data){
		GameExchange response = new GameExchange("gameData", data);
		for(User user: playerList){
			user.send(response);
		}
	}
}
