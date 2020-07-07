package com.fayence.mothquiet;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;


public class Main extends JavaPlugin implements Listener{
	public Chat vaultChat = null;
	
	public void onEnable() {
		Chat vaultChat = getServer().getServicesManager().load(Chat.class);
        if (vaultChat != this.vaultChat) {
            getLogger().info("Chat registered: " + (vaultChat == null ? "null" : vaultChat.getName()));
        }
        this.vaultChat = vaultChat;
		
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("MQChat enabled!");
        
	}
	
	public void onDisable() {
	    getLogger().info("MQChat disabled!");
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void chatFormat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		String format = event.getFormat();
		if (vaultChat != null){
			String prefix = vaultChat.getPlayerPrefix(p);
			String suffix = vaultChat.getPlayerSuffix(p);
			if (prefix.contains("&f")) {
				format = colorize(
						prefix.substring(0,2)+p.getName()
						+ "&7:&f "
						+event.getMessage() ); 
			} else {
				format = colorize(
						prefix 
						+ " &8| " 
						+ prefix.substring(0,2)+p.getName()
						+ "&7:&f "
						+event.getMessage() ); 
			}
			
		}
		event.setFormat(format);
	}
	
	/*@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.setGameMode(GameMode.SURVIVAL);
		p.sendMessage(colorize("&6Добро пожаловать на &cАНРАХИЮ&6!"));
		p.sendMessage(colorize("&6Тут царит настоящий хаос, правила ОТСУТСТВУЮТ&6!"));
		p.sendMessage(colorize("&6Однако, читерить все же нельзя."));
		p.sendMessage(colorize("&6Хорошей тебе игры!"));
	}*/
	
	 public static String colorize(String s) {
	        return s == null ? null : ChatColor.translateAlternateColorCodes('&', s);
	    }
}
