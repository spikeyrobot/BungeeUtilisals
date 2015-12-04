package com.dbsoftware.bungeeutilisals.Events;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

public class AntiCaps implements Listener {

	  public BungeeUtilisals plugin;
	  
	  public AntiCaps(BungeeUtilisals plugin)
	  {
	    this.plugin = plugin;
	  }
	  
		@EventHandler
		   public void Anticaps(ChatEvent event){
		     ProxiedPlayer p = (ProxiedPlayer)event.getSender();
		     if(event.getMessage().startsWith("/") || p.hasPermission("butilisals.caps.bypass") || p.hasPermission("butilisals.*")){
			   return;
		     } else { 
		    	 if(plugin.getConfig().getBoolean("AntiCaps.Enabled")){
		    	 if ((event.getMessage().length() >= BungeeUtilisals.minLength) && (BungeeUtilisals.getUppercasePercentage(event.getMessage()) > BungeeUtilisals.maxCapsPercentage)) {
		    		 
			          event.setMessage(event.getMessage().toLowerCase());
			          p.sendMessage(new TextComponent(plugin.getConfig().getString("AntiCaps.Message").replace("&", "§")));
			        }
		    	 } else {
		    		 return;
		    	 }
		     	}
		   }
	
}