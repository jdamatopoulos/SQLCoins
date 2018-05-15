package com.damathedev.sqlcoins;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.damathedev.sqlcoins.commands.Coins;
import com.damathedev.sqlcoins.events.PlayerJoin;
import com.damathedev.sqlcoins.utils.SQLUtils;

import net.md_5.bungee.api.ChatColor;

public class SQLCoins extends JavaPlugin {
    
    FileConfiguration config = getConfig();
    public String host, db, username, password, table;
    public int port;
    private Connection connection;
    public SQLUtils sqlUtils;

    @Override
    public void onEnable() {
	setupConfig();
	registerCommands();
	registerEvents();
	sqlSetup();
	sqlUtils = new SQLUtils();
    }
    
    private void setupConfig() {
	config.addDefault("host", "localhost");
	config.addDefault("port", 3306);
	config.addDefault("database", "default");
	config.addDefault("username", "root");
	config.addDefault("password", "password");
	config.addDefault("table", "table");
	config.addDefault("first-time-join-coins-amount", 100);
	config.addDefault("admin-permission", "coins.admin");
	config.addDefault("display-first-time-join-message", true);
	config.addDefault("first-time-join-message", "&aSuccesfully earned %coins% for logging in for the first time!");
	config.options().copyDefaults(true);
	saveConfig();
    }
    
    private void registerCommands() {
	getCommand("coins").setExecutor(new Coins());
    }
    
    private void registerEvents() {
	PluginManager pm = getServer().getPluginManager();
	pm.registerEvents(new PlayerJoin(), this);
    }
    
    public void sqlSetup() {
	host = config.getString("host");
	port = config.getInt("port");
	db = config.getString("database");
	username = config.getString("username");
	password = config.getString("password");
	table = config.getString("table");
	
	try {
	    synchronized(this) {
		if(getConnection() != null && !getConnection().isClosed()) {
		    return;
		}
		Class.forName("com.mysql.jdbc.Driver");
		setConnection(DriverManager.getConnection("jbdc:mysql://" + this.host + ":" + this.port + "/" + this.db, this.username, this.password));
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[COINS]" + ChatColor.GREEN + "MYSQL CONNECTION ESTABLISHED");
	
	    }
	} catch(SQLException e) {
	    e.printStackTrace();
	} catch(ClassNotFoundException e) {
	    e.printStackTrace();
	}
    }
    
    public Connection getConnection() {
	return connection;
    }
    
    public void setConnection(Connection connection) {
	this.connection = connection;
    }
}
