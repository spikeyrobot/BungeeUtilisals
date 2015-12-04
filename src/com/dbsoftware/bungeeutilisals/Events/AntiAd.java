package com.dbsoftware.bungeeutilisals.Events;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

public class AntiAd implements Listener {

	  public BungeeUtilisals plugin;
	  
	  public AntiAd(BungeeUtilisals plugin)
	  {
	    this.plugin = plugin;
	  }
	  	  
		@EventHandler
		public void AntiAD(ChatEvent event){
			ProxiedPlayer p = (ProxiedPlayer)event.getSender();
			if(BungeeUtilisals.instance.getConfig().getBoolean("AntiAd.Enabled")){
				for(String whitelist : BungeeUtilisals.instance.getConfig().getStringList("AntiAd.Whitelist")){
					if(event.getMessage().contains(whitelist)){
						return;
					}
				}
				if(p.hasPermission("butilisals.antiad.bypass") || p.hasPermission("butilisals.*")){
					return;
				} else {
			  	    for (String s : event.getMessage()
			  	    		.replace("c0m", "com")
			  	    		.replace("0rg", "org")
			  	    		.replace("(dot)", ".")
			  	    		.replace("(.)", ".")
			  	    		.replace("{.}", ".")
			  	    		.replace("[.]", ".")
			  	    		.replace("<.>", ".")
			  	    		.replace(" . ", ".")
			  	    		.replace("(", ".")
			  	    		.replace(")", ".")
			  	    		.replace(",", ".")
			  	    		.replace("[", ".")
			  	    		.replace("]", ".")
			  	    		.replace("{", ".")
			  	    		.replace("}", ".")
			  	    		.replace("<", ".")
			  	    		.replace(">", ".")
			  	    		.split(" ")){
			  	      if (BungeeUtilisals.isIPorURL(s)) {
			  	    	  BungeeUtilisals.found = true;
			  	      }
			  	      if(BungeeUtilisals.found){
				        	event.setCancelled(true);
				        	BungeeUtilisals.found = false;
				        	p.sendMessage(new TextComponent(plugin.getConfig().getString("AntiAd.Message").replace("&", "§")));
			  	    }
				}
			 }
	  }
	}
}