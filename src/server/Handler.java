package server;

import java.util.HashMap;

import org.tritol.server.GameExchange;

import gameManagement.GameManager;
import sessionHandling.SessionHandler;
import userInterface.GUI;
import userManagement.User;
import userManagement.UserManager;

public class Handler extends org.tritol.server.Handler {
	
	public void handle(GameExchange exchange) {

		HashMap<String, String> params = exchange.toHashMap();

		GameExchange response = new GameExchange();

		switch (exchange.getMethod()) {
		case "register":
			String name = params.get("name");
			String password = params.get("password");
			if (!UserManager.getInstance().userExists(name)) {
				UserManager.getInstance().addUser(name, password);
				response.setMethod("register");
				response.setBody("register=true");
				exchange.respond(response);
				GUI.getInstance().write("User " + name + " registered");
			} else {
				response.setMethod("register");
				response.setBody("register=false");
				exchange.respond(response);
			}
			break;
		case "login":
			name = params.get("name");
			password = params.get("password");
			if (UserManager.getInstance().userExists(name)) {
				response.setMethod("login");
				User user = UserManager.getInstance().getUserByName(name);
				if (user.checkPassword(password)) {
					response.setBody("login=true&name=" + name);
					SessionHandler.getInstance().addSession(user, exchange.getClientThread());
					exchange.respond(response);
					GUI.getInstance().write("User " + name + " logged in");
				} else {
					response.setBody("login=false&name=" + name);
					exchange.respond(response);
				}
			}else{
				response.setMethod("error");
				response.setBody("error=User not registered");
			}
			break;
		case "joingame":
			GameManager.getInstance().joinGame(exchange);
			break;
		case "disconnect":
			response.setMethod("disconnect");
			response.setBody("disconnect=true");
		}

	}
}
