package com.example.fw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.tests.User;

public class JDBCHelper extends BaseHelper {

	private Connection dbConnection;
	
	public JDBCHelper(ApplicationManager manager, String dbName, String dbLogin, String dbPass) {
		super(manager);
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	    
	    //try to connect to DataBase
		try {
	        dbConnection = DriverManager.getConnection(dbName, dbLogin, dbPass);
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	public void dbClose() {
	//try to close connection
		try {
			dbConnection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public int deleteUser(String login) {
		Statement statement = null;
		ResultSet res = null;
		int id = 0;
		//try to execute some query
		try {
			statement = dbConnection.createStatement();
			res = statement.executeQuery("SELECT `id` FROM `mantis_user_table` WHERE `username` = '" + login + "'");
			while (res.next()) {
				id = Integer.parseInt(res.getString(1));
			}
			statement.execute("DELETE FROM `mantis_user_pref_table` WHERE `user_id` = " + id);
			statement.execute("DELETE FROM `mantis_user_table` WHERE `id` = " + id);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				statement.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	
	public void updateTimeStamps(User user, String timestamp) {
		Statement statement = null;
		//try to execute some query
		try {
			statement = dbConnection.createStatement();
			statement.execute(
				"UPDATE `mantis_user_table` SET `last_visit` = '" + timestamp 
					+ "' WHERE `username` = '" + user.getLogin() + "'");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void insertUser(User user) {
		Statement statement = null;
		//try to execute some query
		try {
			statement = dbConnection.createStatement();
			statement.execute(
				"INSERT INTO `mantis_user_table` (`username`, `email`) VALUES ('" + user.getLogin() + "', '" + user.getEmail() + "');");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
