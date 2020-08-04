package connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * 
 * A singleton class that holds all of the connections to the database and
 * distributes them to the asking methods.
 *
 */
public class ConnectionPool {

	private static ConnectionPool instance = new ConnectionPool();
	public static final int MAX_CON = 2;
	
	Set<Connection> connections = new HashSet<>();
	String driverName = "com.mysql.cj.jdbc.Driver";
//	private String url = "jdbc:mysql://localhost/dbzoo";
//	String dbName="sql7357485";
//	String userName="sql7357485";
//	String password= "ei5JKQSkEn";
//	String hostname="sql7.freemysqlhosting.net";
//	String port ="3306";
	
	String dbName="heroku_a1d97850aa3934f";
	String userName="bbae76b9b8bf00";
	String password= "6b1f6019";
	String hostname="eu-cdbr-west-03.cleardb.net";
	String port ="3306";
	
	 String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName
			+"&password=" +  password;
	
	

	private ConnectionPool() {
		try {
			Class.forName(driverName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			
			return;
		}

		for (int i = 0; i < MAX_CON; i++) {
			try {
//				Connection con = DriverManager.getConnection(url,"root", "");
				Connection con = DriverManager.getConnection(url,userName, password);
				connections.add(con);
				// System.out.println("Connected to the database" +" "+i+" " +con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static ConnectionPool getInstance() {
		return instance;
	}

	public synchronized Connection getConnection() {
		while (connections.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Iterator<Connection> it = connections.iterator();
	
		Connection con = it.next();
		it.remove();
		return con;
	}

	public synchronized void returnConnection(Connection con) {
		connections.add(con);
		notifyAll();

	}

	public void closeAllConnections() {

		int counter = 0;

		while (counter < MAX_CON) {
			while (connections.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			for (Connection connection : connections) {
				try {
					connection.close();
					counter++;
				} catch (SQLException e) {
				}
			}
		}

	}

}
