package com.damathedev.sqlcoins.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.damathedev.sqlcoins.SQLCoins;

public class SQLUtils {
    
    private SQLCoins p = SQLCoins.getPlugin(SQLCoins.class);
    static int playerCoinsAmount;
    
    public boolean playerExists(UUID uuid) {
	try {
	    PreparedStatement statement = p.getConnection().prepareStatement
		    ("SELECT * FROM " + p.table + " WHERE UUID=?");
	    statement.setString(1, uuid.toString());
	    
	    ResultSet results = statement.executeQuery();
	    if(results.next()) {
		return true;
	    }
	} catch(SQLException e) {
	    e.printStackTrace();
	}
	return false;
    }
    
    public void addPlayer(final UUID uuid, Player player) {
	try {
	    PreparedStatement statement = p.getConnection().prepareStatement
		    ("SELECT * FROM " + p.table + " WHERE UUID=?");
	    statement.setString(1, uuid.toString());
	    
	    ResultSet results = statement.executeQuery();
	    results.next();
	    if(!(playerExists(uuid))) {
		PreparedStatement insert = p.getConnection().prepareStatement
			("INSERT INTO " + p.table + " (UUID,COINS) VALUES (?,?)");
		insert.setString(1, uuid.toString());
		insert.setInt(2, p.getConfig().getInt("first-login-coins-amnt"));
		insert.executeUpdate();
		
	    }
	    
	} catch(SQLException e) {
	    e.printStackTrace();
	}
    }
    
    public void setCoins(UUID uuid, int amnt) {
	try {
	    PreparedStatement statement = p.getConnection().prepareStatement
		    ("UPDATE " + p.table + " SET COINS=? WHERE UUID=?");
	    statement.setInt(1, getCoins(uuid) + amnt);
	    statement.setString(2, uuid.toString());
	    statement.executeUpdate();
	} catch(SQLException e) {
	    e.printStackTrace();
	}
    }
    
    public int getCoins(UUID uuid) {
	try {
	    PreparedStatement statement = p.getConnection().prepareStatement
		    ("SELECT * FROM " + p.table + "WHERE UUID=?");
	    statement.setString(1, uuid.toString());
	    ResultSet results = statement.executeQuery();
	    results.next();
	    playerCoinsAmount = results.getInt("COINS");
	} catch(SQLException e) {
	    e.printStackTrace();
	}
	return playerCoinsAmount;
    }
}
