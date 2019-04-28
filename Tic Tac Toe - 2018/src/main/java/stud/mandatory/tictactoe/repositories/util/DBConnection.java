package stud.mandatory.tictactoe.repositories.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private final static String USERNAME = "REDACTED";
	private final static String PASSWORD = "REDACTED";
	private final static String CONNECTION_URL = "REDACTED";

	public static Connection getConnection(){
		try{
			return DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

}
