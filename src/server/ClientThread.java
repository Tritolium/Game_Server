package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import games.main.Game;

public class ClientThread extends Thread {

	private Server server;
	private BufferedReader is = null;
	private PrintStream os = null;
	private Socket clientSocket = null;

	private Game runningGame = null;
	private boolean listen = true;

	public ClientThread(Server server, Socket clientSocket) {
		this.server = server;
		this.clientSocket = clientSocket;
	}
	
	public Socket getClientSocket(){
		return clientSocket;
	}

	public void run() {
		try {
			String line;
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os = new PrintStream(clientSocket.getOutputStream());

			/*
			 * Give received String to the Server to execute it
			 */
			while (listen) {
				line = is.readLine();
				server.execute(this, line);
				os.println("From server: " + line);
			}
		} catch (SocketException e) {
			this.interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(String s) {
		os.println(s);
		os.flush();
	}
}
