package context;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	private String driver;
	private String url;
	private String id;
	private String pw;
	private int maxConn;
	
	private static ConnectionFactory instance = new ConnectionFactory();
	
	private ConnectionFactory() {
		InputStream reader = getClass().getClassLoader().getResourceAsStream("config/db.properties");
	
		Properties prop = new Properties();
		try {
			prop.load(reader);
			
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			id = prop.getProperty("id");
			pw = prop.getProperty("pw");
			maxConn =Integer.parseInt(prop.getProperty("maxConn"));
			
		}catch(IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			Class.forName(driver);
			System.out.println("driver loaded!");
		}catch(ClassNotFoundException e2) {
			System.out.println("driver not found!");
			e2.printStackTrace();
			System.exit(0);
		}		
	}
	
	public static ConnectionFactory getInstace() {
		return ConnectionFactory.instance;
	}
	
	public Connection getConnection() {
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url,id,pw);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error occurs to get Connection from ConnectionFactory");
			System.out.println("Please Check if getConnection() method got wrong driver,id,pw.");
		}
		return conn;
	}
	
	public int getMaxConn() {
		return maxConn;
	}
}
