package context;

import java.sql.Connection;
import java.util.Vector;

public class ConnectionPool {
	private static ConnectionFactory cf=ConnectionFactory.getInstace();
	private static Vector<Connection> pool = new Vector<Connection>();
	private static ConnectionPool instance = new ConnectionPool();
	
	
	private ConnectionPool(){
		for(int i =0;i<cf.getMaxConn();i++) {
			Connection conn = cf.getConnection();
			pool.add(conn);
		}
		System.out.println("Number of Connections : "+ pool.size());
		System.out.println("All Connections were stored successfully");
	}
	
	public static ConnectionPool getInstance() {
		return instance;
	}
	
	public synchronized Connection getConnection() {
		if(pool.size()==0) {
			try{
				wait();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		Connection conn = pool.get(0);
		pool.remove(conn);
		return conn;
	}
	
	public synchronized void releaseConnection(Connection conn) {
		pool.add(conn);
		notify();
	}
	
	
	
	
}
