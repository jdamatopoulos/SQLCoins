package com.damathedev.sqlcoins.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.damathedev.sqlcoins.SQLCoins;
import com.damathedev.sqlcoins.utils.ColorUtils;

public class Coins implements CommandExecutor {

    private SQLCoins p = SQLCoins.getPlugin(SQLCoins.class);
    private int coins;
    
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
	
	if(!(s instanceof Player)) {
	    s.sendMessage("This may only be executed by players!");
	    return false;
	}
	
	Player player = (Player) s;
	coins = p.sqlUtils.getCoins(player.getUniqueId());
	
	if(!(player.hasPermission(p.getConfig().getString("admin-permission")))) {
	    player.sendMessage("Your coins amount: " + coins);
	} else {
	    player.sendMessage("Your coins amount: " + coins);
	    player.sendMessage("");
	    player.sendMessage(ColorUtils.format("&cAdmin Coins Panel"));
	    player.sendMessage("To be done soon :)");
	}
	return true;
    }
}
