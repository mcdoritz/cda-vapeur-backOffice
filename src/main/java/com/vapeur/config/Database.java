package com.vapeur.config;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.vapeur.config.Debug.prln;

import javax.servlet.http.HttpServletRequest;

public class Database {
	/*
	private static String url = "jdbc:mysql://localhost:3306/lesformulix?useUnicode=yes&characterEncoding=UTF-8";
	private static String admin = "mcdoritz";
	private static String adminpw = "corvette$72";*/
	
	private static String url = "jdbc:mysql://localhost:3306/vapeur?useUnicode=yes&characterEncoding=UTF-8";
	private static String admin = "root";
	private static String adminpw = "";
	
	public static Connection connexion = null;

	public static void connect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connexion = DriverManager.getConnection(url, admin, adminpw);
			prln("connection to db.........ok");

		} catch (Exception ex) {
			System.err.println(ex.getMessage());	
		}
	}
}
