package sessionHandling;

import java.util.HashMap;
import java.util.Map.Entry;

import org.tritol.server.ClientThread;

import userManagement.User;

public class SessionHandler {
	private static HashMap<User, ClientThread> activeSessions;
	private static SessionHandler instance;

	public SessionHandler() {
		activeSessions = new HashMap<User, ClientThread>();
	}

	public static SessionHandler getInstance() {
		if (instance == null) {
			instance = new SessionHandler();
		}
		return instance;
	}
	
	public void addSession(User user, ClientThread clientThread){
		activeSessions.put(user, clientThread);
	}
	
	public ClientThread getClientThread(User user){
		return activeSessions.get(user);
	}
	
	public User getUser(ClientThread clientThread){
		for(Entry<User, ClientThread> s : activeSessions.entrySet()){
			if(clientThread.equals(s.getValue()))
				return s.getKey();
		}
		return null;
	}
}
