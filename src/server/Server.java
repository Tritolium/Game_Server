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
	private GameHandler handler;

	public Server() {
		super("Server");
		this.handler = new GameHandler();
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
				ClientThread client = new ClientThread(handler, clientSocket);
				threads.put("Client", client);
				client.start();

				GUI.getInstance().write("Connection to " + clientSocket.getInetAddress());
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}