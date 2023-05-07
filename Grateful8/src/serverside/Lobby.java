package serverside;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
/**
 * LOBBY must not be more than one instance, it must shared by USERs.
 * and Multiple USERs sharing it, rooms and members  must be SINCHRONIZED .
 * 
 * 1.LOGGING IN
 * 1) when SERVER finished checking if the user is in the Database with matching password, send it with its socket into LOBBY 
 * 2) LOBBY will add String - username-Socket into memebers with its socket after chekcing if the name is already on the List
 * 
 * 2.LOGGIN OUT
 * 1) when USER quite the program or finished it's program remove it from ROOM ( other process would be done in SERVER )
 * 
 * 3.CREATE ROOM
 * 1) set the Creator of room as the roomaker, when roomaker quite the room , the room will be exploded
 * 2) public static voidm ains trin args 
 * 
 * 
 *  * @author sinborninkr@gmail.com
 *
 */
public class Lobby {
	private List<Room> rooms = Collections.synchronizedList(new ArrayList<Room>());
	private static Lobby instance = new Lobby();
	private ConcurrentHashMap<String,Socket> members = new ConcurrentHashMap<String, Socket>();
	
	
	private Lobby() {}
	
	public static Lobby getinstance() {
		return instance;
	}
	
	public synchronized boolean login(String newName,Socket newSocket) {
		
		for(String name :members.keySet()) {
			if(newName == name) {
				System.out.println("failed to log in ,"+ newName  +" << account is already loggied via >> " + members.get(newName));
				return false;
			}
		}
		
		try {
			this.members.put(newName,newSocket);
			return true;
		}catch(Exception e) {
			System.out.println("failed to log in user : "+ newName);
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized boolean CreateRoom(String makerName,Socket makerSocket,String roomName,String roomPw) {
		for(Room room : rooms){
			String existedRN = room.getRoomName();
			if(roomName.equals(existedRN.get)) {
				System.out.println(makerName+"'s try to make room failed , there is already room existed");
				return false;
			};
		}
	}
	
	public synchronized boolean EnterRoom(String username, Socket userSocket,Room roomname){
		try {
			
		}catch(Exception e) {
			System.out.println("failed to Enter room");
		}
		
	}
	
	
	
}
