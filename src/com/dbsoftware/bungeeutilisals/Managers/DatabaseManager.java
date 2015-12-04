package com.dbsoftware.bungeeutilisals.Managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class DatabaseManager {
	private static String host;
	private static int port;
	private static String database;
	private static String username;
	private static String password;
	private static Connection connection;
  
  public DatabaseManager(String host, int port, String database, String username, String password){
    DatabaseManager.host = host;
    DatabaseManager.port = port;
    DatabaseManager.database = database;
    DatabaseManager.username = username;
    DatabaseManager.password = password;
    
    System.out.println("[BungeeUtilisals] Connecting to database..");
  }
  
  public void openConnection(){
    if (!isConnected()) {
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        System.out.println("[BungeeUtilisals]: Connected to database: ");
        System.out.println("[BungeeUtilisals]: Host: " + host);
        System.out.println("[BungeeUtilisals]: Database name: " + database);
        this.prepareDatabase();
      } catch (SQLException ex1) {
        System.out.println("[BungeeUtilisals]: Can't connect to database: " + ex1.getMessage());
        ex1.printStackTrace();
      } catch (ClassNotFoundException ex2) {
        System.out.println("[BungeeUtilisals]: Can't connect to database: " + ex2.getMessage());
      }
    } else {
    	System.out.println("[BungeeUtilisals]: Database already connected");
    }
  }
  
  public void prepareDatabase()
  {
    if (isConnected()) {
      try {
        Statement st = getConnection().createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS Friends (Player VARCHAR(32) NOT NULL, Friends VARCHAR(32) NOT NULL)");
        st.executeUpdate("CREATE TABLE IF NOT EXISTS Requests (Player VARCHAR(32) NOT NULL, Requests VARCHAR(32) NOT NULL)");
        st.executeUpdate("CREATE TABLE IF NOT EXISTS Reports (Number INT NOT NULL, Reporter VARCHAR(32) NOT NULL, Player VARCHAR(32) NOT NULL, Reason TEXT NOT NULL)");
        
        System.out.println("[BungeeUtilisals]: Prepared database!");
      } catch (SQLException e) {
        System.out.println("[BungeeUtilisals]: Can't prepare database: " + e.getMessage());
      }
    } else {
      System.out.println("[BungeeUtilisals]: Database isn't connected ");
    }
  }
  
  public boolean isConnected(){
    boolean connected = false;
    try {
      connected = (connection != null) && (!connection.isClosed());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connected;
  }
  
  public Connection getConnection(){
	  return connection;
  }
  
  public boolean isInTable(ProxiedPlayer player, String table) {
    if (isConnected()) {
      try {
        Statement statement = getConnection().createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM '" + table + "' WHERE NAME='" + player.getName() + "'");
        
        return result.next();
      } catch (SQLException exception) {
        exception.printStackTrace();
        
        return false;
      }
    }
    return false;
  }
  
  public void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println("[BungeeUtilisals]: Can't close the connection: " + e.getMessage());
    } finally {
      connection = null;
    }
  }
}