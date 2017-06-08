package games.main;

import java.util.ArrayList;

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
		user.send(data);
	}
	
	public void sendGameDataToUsers(String data){
		for(User user: playerList){
			user.send(data);
		}
	}
}
