package com.comcast.crm.generic.databaseutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class DataBaseUtility {

	Connection con; // import from java sql
	// here connection con is global variable so that in another method i can close
	// db connection and acces this variable in that method

	public void getDbconnection(String url, String username, String password) throws SQLException {

		try {
			Driver driver = new Driver(); // import driver from mysql
			DriverManager.registerDriver(driver);

			DriverManager.getConnection(url, username, password);

			con = DriverManager.getConnection(url, username, password);

		} catch (Exception e) {
		}

	}

	public void getDbconnection() throws SQLException {

		try {
			Driver driver = new Driver(); // import driver from mysql
			DriverManager.registerDriver(driver);
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/projects", "root", "admin");

		} catch (Exception e) {
		}

	}          //if one database is there

	public void closeDbconnection() throws SQLException {
		try {
			con.close();
		} catch (Exception e) {
		}
	}

	public ResultSet executeSelectQuery(String query) throws Throwable {
		ResultSet result = null;
		try {
			Statement stat = con.createStatement();
			result = stat.executeQuery(query);
		} catch (Exception e) {
		}

		return result;
	}

	public int executeNonSelectQuery(String query) throws Throwable {
		int result = 0;
		try {
			Statement stat = con.createStatement();
			result = stat.executeUpdate(query);
		} catch (Exception e) {
		}

		return result;
	}

}
