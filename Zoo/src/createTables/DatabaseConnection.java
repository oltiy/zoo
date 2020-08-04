package createTables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import connections.ConnectionPool;


/**
 * 
 * Database connection and creating tables by SQL
 */
public class DatabaseConnection {
	public static void main(String[] args) {
		ConnectionPool pool = ConnectionPool.getInstance();

//		String url = "jdbc:mysql://localhost/dbzoo";
//		String driverName = "org.apache.derby.jdbc.ClientDriver";
		
		String driverName = "com.mysql.cj.jdbc.Driver";

//		String dbName="sql7357485";
//		String userName="sql7357485";
//		String password= "ei5JKQSkEn";
//		String hostname="sql7.freemysqlhosting.net";
//		String port ="3306";
		
		String dbName="heroku_a1d97850aa3934f";
		String userName="bbae76b9b8bf00";
		String password= "6b1f6019";
		String hostname="eu-cdbr-west-03.cleardb.net";
		String port ="3306";
		
//		
		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName
				+"&password=" +  password;		
		Connection con = pool.getConnection();

		try (Statement stmt = con.createStatement();) {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
//			con = DriverManager.getConnection(url,"root","");
			con = DriverManager.getConnection(url,userName,password);

			System.out.println("connected to " + url);
			String sql = "CREATE TABLE IF NOT EXISTS purchasing (id MEDIUMINT NOT NULL AUTO_INCREMENT,PRIMARY KEY (id), purch_name varchar(200), password varchar(200),email varchar(200))";
			String sql2 = "CREATE TABLE IF NOT EXISTS supplier ( id MEDIUMINT NOT NULL AUTO_INCREMENT,PRIMARY KEY (id), supplier_name varchar(200),email varchar(200), title varchar(200) , type varchar(200), price int, phoneNumbe int,address varchar(200))";
			String sql3 = "CREATE TABLE IF NOT EXISTS workers ( id MEDIUMINT NOT NULL AUTO_INCREMENT,PRIMARY KEY (id),  worker_name varchar(200), password varchar(200),email varchar(200))";
			String sql6 = "CREATE TABLE IF NOT EXISTS product ( id MEDIUMINT NOT NULL AUTO_INCREMENT,PRIMARY KEY (id), title varchar(200),  amount int, type varchar(200),message varchar(200), price int, start_date date, end_date date)";
			String sql4 = "CREATE TABLE IF NOT EXISTS purchasing_product (id_pruch MEDIUMINT NOT NULL, id_product int )";
			String sql5 = "CREATE TABLE IF NOT EXISTS worker_product (id_worker MEDIUMINT NOT NULL, id_product int )";
		stmt.executeUpdate(sql6);

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}

		System.out.println("disconnected from " + url);
	}
}
