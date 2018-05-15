package com.damathedev.sqlcoins.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.damathedev.sqlcoins.SQLCoins;

public class PlayerJoin implements Listener {
    
    private SQLCoins p = SQLCoins.getPlugin(SQLCoins.class);
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
	Player player = (Player) event.getPlayer();
	p.sqlUtils.addPlayer(player.getUniqueId(), player);
    }
}
