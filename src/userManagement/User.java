package userManagement;

import sessionHandling.SessionHandler;

public class User {
	private String name;
	private String password;

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public boolean checkPassword(String pw) {
		if (pw.equals(password))
			return true;
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void send(String data){
		SessionHandler.getInstance().getClientThread(this).write(data);
	}
}
