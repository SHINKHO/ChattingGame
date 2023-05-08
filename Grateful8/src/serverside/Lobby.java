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
 * 1) set the Creator of room as the roomaker roomaker is neeeded to set when roomaker quite the room , the room will be exploded
 * 2) 
 * 
 * 4.Enter room
 * 1) find the target room where the user located in, if not in the rooms, return false;
 * 2) if the Room is with password, match the input with its password, if not matched, return false;
 * 3) if there is such room that the user wished to be out of the room, remove the user
 * 4) if succeed to remove and join to the lobby , return true else, false
 * 
 * 5.Exit Room (Server should check if the user is NOT ROOM MAKER )
 * 1) find the target room where the user located in, if not in the rooms, return false;
 * 2) check if the username is in the room, if no in the room return false
 * 3) if there is such room that the user wished to be out of the room, remove the user
 * 4) if succeed to remove and join to the lobby , return true else, false.
 * 
 * 6.Terminate Room (Server Should check if the asked one is the Room maker)
 * 1) find the target room where the roomaker asked to terminate, if not existed, return false
 * 2) take all users into the lobby
 * 3) remove the room from lobby - server won't access it and user would not able to acess to the room(terminated)
 *  
 *  
 * 7.List Members In Specific Room
 * 1) find the target room where the specified String is its name, if not found , return false
 * 2) return the member list in the room
 * 
 * 8.List All rooms in the room
 * 1)
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
		
		if (members.containsKey(newName)) {
		    System.out.println("failed to log in , "+ newName + " << account is already logged in via >> " + members.get(newName));
		    return false;
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
	
	public synchronized boolean createRoom(String makerName,Socket makerSocket,String roomName,String roomPw) {
		for(Room room : rooms){
			String existedRN = room.getRoomName();
			if(roomName.equals(existedRN)) {
				System.out.println(makerName+"'s try to make room failed , there is already room name existed");
				return false;
			}
		}
		
		Room newRoom;
		try {
			newRoom = new Room (makerName,makerSocket,roomName,roomPw);
		}catch(Exception e) {
			System.out.println("something goes wrong while making new room"+roomName);
			return false;
		}
		
		try {
			rooms.add(newRoom);
			return true;
		}catch(Exception e){
			System.out.println("something goes worng while adding newly made room"+roomName+" into rooms was made by "+makerName);
			e.printStackTrace();
			return false;
		}
		
	}
	
	public synchronized boolean enterRoom(String userName, Socket userSocket,Room roomName){
		int roomIdx = -1;
		for(int i = 0 ; i<rooms.size();i++){
			if(rooms.get(i).equals(roomName)) {
				roomIdx = i;
			}
		}
		if(roomIdx==-1) {
			System.out.println(userName +" failed to enter room , there is no such room named "+ roomName);
			return false;
		}
		
		Room target = rooms.get(roomIdx);
		if(target.isLocked()) {
			System.out.println(userName + "failed to enter room, the room " + roomName + "is locked but no Password given as input");
		}
		try {
			target.joinMember(userName, userSocket);
			members.remove(userName);
			return true;
		}catch(Exception e) {
			System.out.println(userName +" failed to Enter room, Error happening while entering the room"+ roomName + ". "
					+ "this might be caused when the entering member get failed to be removed from member or joining to member " );
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized boolean enterRoom(String userName, Socket userSocket,Room roomName,String password){
		int roomIdx = -1;
		for(int i = 0 ; i<rooms.size();i++){
			if(rooms.get(i).equals(roomName)) {
				roomIdx = i;
			}
		}
		if(roomIdx==-1) {
			System.out.println(userName +" failed to enter room , there is no such room named "+ roomName);
			return false;
		}
		
		Room target = rooms.get(roomIdx);
		if(password.equals(target.getRoomPw()) || !target.isLocked()) {
			try {
				target.joinMember(userName, userSocket);
				members.remove(userName);
				return true;
			}catch(Exception e) {
				System.out.println(userName +" failed to Enter room, Error happening while entering the room"+ roomName + ". "
						+ "this might be caused when the entering member get failed to be removed from member or joining to member " );
				e.printStackTrace();
				return false;
			}
		}else {
			System.out.println(userName +" failed to Enter Room , Check the Password ");
			return false;
		}
	}
	
	public synchronized boolean returnToLobby(String userName,Socket userSocket,Room roomName) {
		int roomIdx = -1;
		for(int i = 0 ; i<rooms.size();i++){
			if(rooms.get(i).equals(roomName)) {
				roomIdx = i;
			}
		}
		if(roomIdx==-1) {
			System.out.println(userName +" failed to exit room because there is no such room named "+ roomName);
			return false;
		}
		
		Room targetRoom = rooms.get(roomIdx);
		boolean isUserIn = false;
		for(String mem : targetRoom.getMembers().keySet()) {
			if(mem.equals(userName)) {
				isUserIn = true;
				break;
			}
		}
		if(!isUserIn) {
			System.out.println(userName+"failed to exit the room because there is no such user");
			return false;
		}
		
		try {
			members.putAll(targetRoom.outMember(userName));
			return true;
		}catch(Exception e) {
			System.out.println(userName+"failed to exit the room , this might happended while removing from or adding to room and lobby");
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public boolean terminateRoom(String roomName) {
		int roomIdx= -1;
		for(int i =0 ; i < rooms.size(); i++) {
			if(roomName.equals(rooms.get(i).getRoomName())) {
				roomIdx = i;
				break;
			}
		}
		if(roomIdx == -1) {
			System.out.println("failed to terminate room "+roomName+" becase there is no such room");
			return false;
		}
		
		try{
			Room target = rooms.get(roomIdx);
			members.putAll(target.getMembers());
			rooms.remove(roomIdx);
			return true;
		}catch(Exception e) {
			System.out.println("failed to terminate room "+roomName+" something might goes worng "
					+ "while adding all members and remove the room from roomList");
			return false;
		}
		
	}
	
	public HashMap<String,Socket> listMembersIn(String roomName){
		int roomIdx= -1;
		for(int i =0 ; i < rooms.size(); i++) {
			if(roomName.equals(rooms.get(i).getRoomName())) {
				roomIdx = i;
				break;
			}
		}
		if(roomIdx == -1) {
			System.out.println("failed to list members in the room "+roomName+" becase there is no such room");
			return null;
		}
		
		HashMap<String,Socket> returnMap = rooms.get(roomIdx).getMembers();
		
		return returnMap;
	}
	
	public ArrayList<String> listAllRoom(){
		ArrayList<String> returnList = new ArrayList<String>();
		for(Room r : rooms) {
			returnList.add(r.getRoomName());
		}
		return returnList;
	}
	
	
}
