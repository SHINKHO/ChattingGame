package serverside;

import java.net.Socket;
import java.util.HashMap;


public class Room {
	/* Class room is the Object container for Server, 
	 * 
	 * IN THIS LEVEL, FOLLWOING FUNCTIONALITIES MUST BE DONE.
	 * 1.MESSAGE SEND AND RECIEVES
	 * 	1)When CLEINT sends message to the SERVER, SERVER will find where does the member is located.
	 * 	2)If the ROOM of CLIENT is specified, SERVER sends user's message to the every members in the ROOM
	 * 2.ENTER ROOM AND EXIT ROOM
	 *	1) When User tries to the Room, ROOM will check if the password is matched.
	 *	2) if no, returns false , if yes let USER enter the ROOM by putting its name - socket K-V to the HashMap;
	 *	3) When USER asked to server to exit, ROOM removes the K-V of name-socket from its members HashMap and return to LOBBY
	 */
	private String roomName;
	private String roomPw;
	private HashMap<String,Socket> members;
	private String makerName;
	private Socket makerSocket;
	private boolean locked= false;
	//room with password
	public Room(String makerName,Socket makerSocket,String roomName,String roomPw) {
		this.roomName = roomName;
		this.roomPw = roomPw;
		this.locked = true;
		this.members = new HashMap<>();
		this.makerName = makerName;
		this.makerSocket = makerSocket;
	}
	//room without password
	public Room(String makerName,Socket makerSocket,String roomName) {
		this.roomName = roomName;
		this.roomPw = null;
		this.members = new HashMap<>();
		this.makerName = makerName;
		this.makerSocket = makerSocket;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomPw() {
		return roomPw;
	}

	public void setRoomPw(String roomPw) {
		this.roomPw = roomPw;
	}

	public HashMap<String, Socket> getMembers() {
		return members;
	}

	public void setMembers(HashMap<String, Socket> members) {
		this.members = members;
	}
	
	public HashMap<String,Socket> outMember(String name) {
		try{
			Socket soc= this.members.get(name);
			HashMap<String,Socket> returnMap = new HashMap<String,Socket>();
			returnMap.put(name,soc);
			System.out.println("Sccessfully out the member in the room : "+toString());
			return returnMap;
		}catch(Exception e) {
			System.out.println("failed to out member");
			return null;
		}
		
	}
	
	public boolean joinMember(String name,Socket socket) {
		try {
			this.members.put(name, socket);
			System.out.println("Successfully new member "+name+"joined to this room"+toString());
			return true;
		}catch(Exception e) {
			System.out.println("failed to join member into the room : "+ toString());
			return false;
		}
	}
	
	public boolean isLocked() {
		return this.locked;
	}

	@Override
	public String toString() {
		return "Room [roomName=" + roomName + ", roomPw=" + roomPw + ", members=" + members + ", makerName=" + makerName
				+ ", makerSocket=" + makerSocket + "]";
	}
	
}
