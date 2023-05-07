package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.UsersVO;

public class UsersDAO {
	
	private static UsersDAO instance = new UsersDAO();
	
	private UsersDAO() {}
	
	public static UsersDAO getInstance() {
		return instance;
	}
	
	/**
	 * Commonly used to logging in.
	 * Getting a String of userId from users table which is equal to the parameter userId.
	 * if The result is more or less than 1; return null
	 * @param conn Connection that send Query to Database
	 * @param userId userId that user looking for
	 * @return null if in case there are more or less than 1 
	 * @return a String if in case exactly 1 result;
	 */
	public String getUser(Connection conn,String userId) {
		StringBuffer query = new StringBuffer();
		query.append(" SELECT user_id ");
		query.append(" FROM users ");
		query.append(" WHERE 1=1 ");
		query.append(" AND user_id = ? ");
		
		ResultSet rs = null;
		try {
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, userId);
		rs = ps.executeQuery();
		}catch(SQLException e) {
			System.out.println("Please check the query, something failed while preparing state and execute it");
			System.out.println("METHOD : getUser(conn,"+userId+") PROCESS:PREPARING ps");
			e.printStackTrace();
		}
		
		ArrayList<String> result = new ArrayList<String>();
		try {
			while(rs.next()) {
				result.add(rs.getString(1));
			}
		} catch (SQLException e) {
			System.out.println("Please check the result set, problem occured while putting result a string from result");
			System.out.println("METHOD : getUser(conn,"+userId+") PROCESS: re.next()");
			e.printStackTrace();
		}
		
		// if the result is more than 1 return 0
		if(result.size()>1 || result.size() == 0) {
			return null;
		}else {
			return result.get(0);
		}
	}
	
	/**
	 * get all users in the Database with a specific characters
	 * do not allow the letter less than 2(return null)
	 * @param conn Connection used to connect to Database
	 * @param finder used to find specific user
	 * @return a ArrayList of String containing all like %finder% in the db.
	 */
	public ArrayList<String> searchUsers(Connection conn,String finder){
		
		if(finder.length()<2) {
			System.out.println("String input less than 2 Detected, return nothing.");
			return null;
		}
		
		StringBuffer query = new StringBuffer();
		query.append(" SELECT user_id ");
		query.append(" FROM users ");
		query.append(" WHERE 1=1 ");
		query.append(" AND user_id like '%'||?||'%' ");
		
		ResultSet rs = null;
		try {
			PreparedStatement ps = conn.prepareStatement(query.toString());
			ps.setString(1, finder);
			rs = ps.executeQuery();
		}catch(SQLException e) {
			System.out.println("Please check the query, something failed while preparing state and execute it");
			System.out.println("METHOD : searchUsers(conn,"+finder+") PROCESS:PREPARING ps");
			e.printStackTrace();
		}
		
		ArrayList<String> result = new ArrayList<String>();
		try {
			while(rs.next()) {
				result.add(rs.getString(1));
			}
		}catch(SQLException e) {
			System.out.println("Please check the resultset, something failed while adding String to result from rs");
			System.out.println("METHOD : searchUsers(conn,"+finder+") PROCESS: ADDING RESULT");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 *  Each user_id,user_pw set in the database must be only one.
	 *  if the query returns more than 1 , this method returns false;
	 * @param conn Connection connected to the database;
	 * @param userId will be measured to check if they are matched to userPw;
	 * @param userPw will be measured to check if they are matched to userId;
	 * @return false if the result of query is more or less than 1, true if it is equals to 1;
	 */
	public boolean isMatchedPw(Connection conn,String userId,String userPw) {
		StringBuffer query = new StringBuffer();
		
		query.append(" SELECT user_id, user_pw ");
		query.append(" FROM users ");
		query.append(" WHERE 1=1 ");
		query.append(" AND users_id = ? " );
		query.append(" AND users_pw = ? " );
		
		ResultSet rs=  null;
		try {
			PreparedStatement ps = conn.prepareStatement(query.toString());
			ps.setString(1, userId);
			ps.setString(2, userPw);
			rs = ps.executeQuery();
		}catch(SQLException e) {
			System.out.println("Please check the query, something failed while preparing state and execute it");
			System.out.println("METHOD : isMatchedPw(conn,"+userId+","+userPw+") PROCESS:PREPARING ps");
			e.printStackTrace();
		}
		
		int measure = 0;
		
		try {
			while(rs.next()) {
				measure++;
			}
		} catch (SQLException e) {
			System.out.println("Please check the resultset, Measuring number of return caused error");
			System.out.println("METHOD : isMatchedPw(conn,"+userId+","+userPw+") PROCESS:PREPARING ps");
			e.printStackTrace();
		}
		
		return measure==1 ? true : false;
	}
	/**
	 * adding user to the DB connected via Connection conn
	 * @param conn is connected to DB
	 * @param newUser is VO of USERS going to be added to DB.
	 * @return -1 if when error occurred, 1 if successfully added, 0 if some uncaught error happened;
	 */
	public int addUser(Connection conn,UsersVO newUser) {                              
		StringBuffer query = new StringBuffer();
		
		query.append(" INSERT INTO users( ");
		query.append(" user_id, user_pw ");
		query.append(" ) values ( ? , ? ) ");
		
		int result = -1;
		try {
			PreparedStatement ps = conn.prepareStatement(query.toString());
			ps.setString(1, newUser.getUserId());
			ps.setString(2, newUser.getUserPw());
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println("Please check the resultset, Measuring number of return caused error");
			System.out.println("METHOD : AddUser(conn,"+newUser.toString()+") PROCESS:PREPARING ps");
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * Delete user from the DB connected via Connection conn
	 * @param conn is connected to DB
	 * @param newUser is VO of USERS going to be added to DB.
	 * @return -1 if when error occurred, 1 if successfully added, 0 if some uncaught error happened;
	 */
	public int deleteUser(Connection conn,UsersVO targetUser) {
		StringBuffer query = new StringBuffer();
		query.append(" DELETE FROM  users ");
		query.append(" WHERE 1=1 ");
		query.append(" AND user_id = ? ");
		query.append(" AND user_pw = ? ");
		int result = -1;
		try {
			PreparedStatement ps = conn.prepareStatement(query.toString());
			ps.setString(1, targetUser.getUserId());
			ps.setString(2, targetUser.getUserPw());
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println("Please check the query,something failed while preparing state and execute it ");
			System.out.println("METHOD : deleteUser(conn,"+targetUser.toString()+") PROCESS:PREPARING ps");
			e.printStackTrace();
		}
		
		return result;
	}
}
