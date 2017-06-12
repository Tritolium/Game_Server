package server;

import java.net.*;
import java.util.HashMap;

import org.tritol.server.ClientThread;
import userInterface.GUI;
import java.io.*;

public class Server extends Thread {
	private ServerSocket server = null;
	private HashMap<String, ClientThread> threads = new HashMap<String, ClientThread>();
	private int port;
	private boolean online = false;

	public Server() {
		super("Server");
	}

	public boolean isOnline() {
		return online;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public void run() {

		Socket clientSocket = null;

		/*
		 * Open a server socket on port port. Note that you cannot choose a port
		 * less than 1023 if you are not privileged users (root).
		 */
		try {
			server = new ServerSocket(port);
			online = true;
		} catch (IOException e) {
			System.out.println(e);
		}

		/*
		 * Create a socket object from the ServerSocket to listen to and accept
		 * connections. Open input and output streams.
		 */
		System.out.println("The server started.");
		GUI.getInstance().write("Server started on port " + port);
		try {
			GUI.getInstance().write("Running on " + InetAddress.getLocalHost() + ":" + port);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				clientSocket = server.accept();
				ClientThread client = new ClientThread(new Handler(), clientSocket);
				threads.put("Client", client);
				client.start();

				GUI.getInstance().write("Connection to " + clientSocket.getInetAddress());
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
	/*
	public void execute(ClientThread client, String s) {
		System.out.println(s);
		String[] split = s.split("\\?");
		String command = split[0];
		HashMap<String, String> params = new HashMap<String, String>();
		try {
			params = Data.parseParameters(split[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("no data");
		} catch (NullPointerException e) {
			System.err.println("Nullpointer");
		}
		switch (command) {
		case "register":
			String name = params.get("name");
			String password = params.get("password");
			if (!UserManager.getInstance().userExists(name)) {
				UserManager.getInstance().addUser(name, password);
				client.write("register?register=true");
				GUI.getInstance().write("User " + name + " registered");
			} else {
				client.write("register?register=false");
			}
			break;
		case "login":
			name = params.get("name");
			password = params.get("password");
			if (UserManager.getInstance().userExists(name)) {
				// User exists
				User user = UserManager.getInstance().getUserByName(name);
				if (user.checkPassword(password)) {
					// Password OK
					client.write("login?login=true&name=" + name);
					SessionHandler.getInstance().addSession(user, client);
					GUI.getInstance().write("User " + name + " logged in");
				} else {
					// Password not OK
					client.write("login?login=false&name=" + name);
				}
			} else {
				// TODO User not registered
				client.write("error?error=User not registered");
			}
			// TODO server-side login
			break;
		case "joingame":

			if (activeGame == null) {
				activeGame = new Catan();
				activeGame.init();
			}
			activeGame.addUser(SessionHandler.getInstance().getUser(client));
			client.write(activeGame.getGameData("SETUP"));
			client.setRunningGame(activeGame);
			// client.write(
			// "creategame?game=Catan&setup=53411505415322241344031670528157342698060510063002100101000151515242524333");

			// TODO gamemanager
			break;
		case "disconnect":
			client.write("disconnect");
			GUI.getInstance().write(client.getClientSocket().getInetAddress() + " disconnected");
			break;
		}
	}
	*/
}