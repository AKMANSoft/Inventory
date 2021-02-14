package com.mi6.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Javaconnect {

    Connection conn = null;

    public static Connection ConnecrDb() {
        try {
            String dbName = "productDB.db";
            Class.forName("org.sqlite.JDBC");
            File dbFile = new File(dbName);
            
            if(!dbFile.exists()) {
            	System.err.println("File not Found");
            	return null;
            }
            
            String url = "jdbc:sqlite:" + dbFile.getPath();
            Connection conn = DriverManager.getConnection(url);
            return conn;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database empty or not connected"+ " or " + e.getMessage() + "");
            return null;
        }
    }
    
    public static void main(String[] args) {
		Javaconnect.ConnecrDb();
	}
}
