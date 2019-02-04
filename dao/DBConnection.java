package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import application.Menu;

import java.sql.DriverManager;


public class DBConnection {
	
	private static Scanner scanner = new Scanner(System.in);
	static String user = getUsername();
	static String pw = getPassword();
	
	private final static String URL = "jdbc:mysql://localhost:3306/team_cap_space";
	private final static String USERNAME = user;
	private final static String PASSWORD = pw;
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
				Menu.printWelcome();
			} catch (SQLException e) {
				System.out.println("Error connecting to db");
				e.printStackTrace();
			}
		}
		return DBConnection.connection;
	}
	
	private static String getUsername() {
		System.out.print("Username: ");
		return scanner.nextLine();
	}
	
	public static String getPassword() {
		System.out.print("Password: ");
		return scanner.nextLine();
	}
	

	


}
