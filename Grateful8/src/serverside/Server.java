package serverside;

import java.net.ServerSocket;
import service.UsersService;

public class Server {
	private static ServerSocket serversocket;
	private static Lobby lobby = Lobby.getinstance();
	private static Server instance = new Server();
	
	private static Server getInstance() {
		return instance;
	}
	
	public synchronized boolean registUser(String userName,String passWord) {
		
	}
	
	public synchronized boolean logInUser(String userName,String passWord) {
		
	}
	
}
