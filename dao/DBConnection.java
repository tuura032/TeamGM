package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


public class DBConnection {
	
	private final static String URL = "jdbc:mysql://localhost:3306/team_cap_space";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "rootMeAmad3us";
	private static Connection connection;
	private static DBConnection instance;
	
	
	private DBConnection(Connection connection) {
		this.connection = connection;
	}
	
	
	public static Connection getConnection() {
		if (instance == null) {
			try {
				connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				instance = new DBConnection(connection);
				System.out.println("connection successful");
			} catch (SQLException e) {
				System.out.println("Error connecting to db");
				e.printStackTrace();
			}
		}
		return DBConnection.connection;
	}

}
