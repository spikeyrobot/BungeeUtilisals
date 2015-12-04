package com.dbsoftware.bungeeutilisals.Events;

import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

public class AntiSpam implements Listener {

	  public BungeeUtilisals plugin;
	  
	  public AntiSpam(BungeeUtilisals plugin)
	  {
	    this.plugin = plugin;
	  }
	  
		@EventHandler
		public void Antispam(ChatEvent event){
		final ProxiedPlayer p = (ProxiedPlayer)event.getSender();
		if(p.hasPermission("butilisals.antispam.bypass")){
			return;
		}
		if(plugin.getConfig().getBoolean("AntiSpam.Enabled") && !(plugin.norepeat.containsKey(p) && plugin.norepeat.containsValue(event.getMessage()))){
		      
		      if(BungeeUtilisals.chatspam.contains(p.getName())){
		    	  if(!event.getMessage().startsWith("/")){
			    	  p.sendMessage(new TextComponent(plugin.getConfig().getString("AntiSpam.Message").replace("%time%", plugin.getConfig().getInt("AntiSpam.Seconds") + "").replace("&", "§")));
			    	  event.setCancelled(true);
			    	  
		    	 } else if (event.getMessage().startsWith("/r") || event.getMessage().startsWith("/msg") || event.getMessage().startsWith("/m") || event.getMessage().startsWith("/t") || event.getMessage().startsWith("/w")) {
			    	  p.sendMessage(new TextComponent(plugin.getConfig().getString("AntiSpam.Message").replace("%time%", plugin.getConfig().getInt("AntiSpam.Seconds") + "").replace("&", "§")));
			    	  event.setCancelled(true);
		    	 }
		      } else {
		    	  event.setCancelled(false);
		    	  BungeeUtilisals.chatspam.add(p.getName());
		    	    ProxyServer.getInstance().getScheduler().schedule(plugin , new Runnable(){
						public void run() {
							BungeeUtilisals.chatspam.remove(p.getName());
						}
		    	    },plugin.getConfig().getInt("AntiSpam.Seconds") , TimeUnit.SECONDS);
		      	}
			} if(plugin.getConfig().getBoolean("Norepeat.Enabled")){
							
				if(!event.getMessage().startsWith("/")){
				if(plugin.norepeat.containsKey(p) && plugin.norepeat.get(p).toLowerCase().equalsIgnoreCase(event.getMessage().toLowerCase())){
					event.setCancelled(true);
			    	p.sendMessage(new TextComponent(plugin.getConfig().getString("Norepeat.Message").replace("&", "§")));
			    	return;
				} else {
						plugin.norepeat.remove(p);
						plugin.norepeat.put(p, event.getMessage());
					}
				}
			}
	  }
	
}
