package server;

import java.net.*;
import java.util.HashMap;

import dataManagement.Data;
import games.catan.Catan;
import sessionHandling.SessionHandler;
import userInterface.GUI;
import userManagement.User;
import userManagement.UserManager;

import java.io.*;

public class Server extends Thread {
	private ServerSocket echoServer = null;
	private final int maxUsers = 4;
	private Catan activeGame;
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
		 * Open a server socket on port port. Note that we can't choose a port
		 * less than 1023 if we are not privileged users (root).
		 */
		try {
			echoServer = new ServerSocket(port);
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
		while (true) {
			try {
				clientSocket = echoServer.accept();
				ClientThread client = new ClientThread(this, clientSocket);
				threads.put("Client", client);
				client.start();

				GUI.getInstance().write("Connection to " + clientSocket.getInetAddress());
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

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
}