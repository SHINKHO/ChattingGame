package service;

import java.sql.Connection;
import java.util.ArrayList;

import context.ConnectionPool;
import dao.UsersDAO;
import vo.UsersVO;

public class UsersService {
	private static UsersService instance = new UsersService();
	private UsersDAO dao = UsersDAO.getInstance();
	private ConnectionPool cp = ConnectionPool.getInstance();
	private UsersService() {}
	
	public String getUser(String userId) {
		Connection conn = cp.getConnection();
		String result = dao.getUser(conn, userId);
		cp.releaseConnection(conn);
		return result;
	}
	
	public ArrayList<String> searchUsers(String finder){
		Connection conn = cp.getConnection();
		ArrayList<String> result = dao.searchUsers(conn, finder);
		cp.releaseConnection(conn);
		return result;
	}
	
	public boolean isMatchedPw(String userId,String userPw) {
		Connection conn = cp.getConnection();
		boolean result = dao.isMatchedPw(conn, userId,userPw);
		cp.releaseConnection(conn);
		return result;
		}
	
	public int addUser(UsersVO newUser) {                              
		Connection conn = cp.getConnection();
		int result = dao.addUser(conn, newUser);
		cp.releaseConnection(conn);
		return result;
	}
	
	public int deleteUser(UsersVO target) {
		Connection conn = cp.getConnection();
		int result = dao.deleteUser(conn,target);
		cp.releaseConnection(conn);
		return result;
	}

	
	public static UsersService getInstance() {
		return instance;
	}

}
