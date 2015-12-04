package com.dbsoftware.bungeeutilisals.Events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

public class DisconnectEvent implements Listener {

	  public BungeeUtilisals plugin;
	  
	  public DisconnectEvent(BungeeUtilisals plugin)
	  {
	    this.plugin = plugin;
	  }
	  
		@EventHandler
		public void onQuit(PlayerDisconnectEvent event){
			ProxiedPlayer p = event.getPlayer();
			if(plugin.norepeat.containsKey(p)){
				plugin.norepeat.remove(p);
			} if(BungeeUtilisals.chatspam.contains(p.getName())){
				BungeeUtilisals.chatspam.remove(p.getName());
			} if(plugin.messagelimiter.containsKey(p)){
				plugin.messagelimiter.remove(p);
			}
		}
	
}
