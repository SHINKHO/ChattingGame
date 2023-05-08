package serverside;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread{
	private Socket clientSocket;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	
	public ClientHandler(Socket socket) {
		this.clientSocket = socket;
		this.lobby = lobby;
	}
	
	public void run() {
		try {
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            Object message;
            while((message = objectInputStream.readObject()) !=null){
            	if (message instanceof LoginMessage) {
            		
            	}
            }
		}
	}
}
