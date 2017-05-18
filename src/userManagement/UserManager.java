package userManagement;

import java.util.ArrayList;
import java.util.Iterator;

public class UserManager {
	private ArrayList<User> userList;
	private static UserManager instance;

	private UserManager() {
		userList = new ArrayList<User>();
	}

	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
			// TODO load manager from file
		}
		return instance;
	}

	/**
	 * Adds user
	 */
	public void addUser(String name, String pw) {
		userList.add(new User(name, pw));
	}

	/**
	 * @return user by name
	 */
	public User getUserByName(String name) {
		Iterator<User> i = userList.iterator();
		while (i.hasNext()) {
			User u = i.next();
			if (u.getName().equals(name)) {
				return u;
			}
		}
		return null;
	}

	/**
	 * @return the Userlist
	 */
	public ArrayList<User> getUsers() {
		return userList;
	}

	/**
	 * Checks if the user exists
	 */
	public boolean userExists(String name) {
		Iterator<User> i = userList.iterator();
		while (i.hasNext()) {
			if (i.next().getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
