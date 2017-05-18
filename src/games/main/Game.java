package games.main;

import userManagement.User;

public class Game extends Thread{
	private Thread thread;
	private User creator = null;

	public Game(){
		thread = new Thread(this);
		thread.start();
	}
	
	public void sendGameDataToUser(User user, String data){
		user.send(data);
	}
}
