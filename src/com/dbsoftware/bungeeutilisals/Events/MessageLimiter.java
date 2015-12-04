package com.dbsoftware.bungeeutilisals.Events;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

public class MessageLimiter implements Listener {

	  public BungeeUtilisals plugin;
	  
	  public MessageLimiter(BungeeUtilisals plugin)
	  {
	    this.plugin = plugin;
	  }
	  
	  @EventHandler
	  public void Messagelimiter(ChatEvent event){
		  final ProxiedPlayer p = (ProxiedPlayer)event.getSender();
		  if(event.isCommand()){
			  return;
		  }
		  if(plugin.getConfig().getBoolean("MessageLimiter.Enabled")){
			  int max = plugin.getConfig().getInt("MessageLimiter.Max");
			  int time = plugin.getConfig().getInt("MessageLimiter.Time");
			  if(plugin.messagelimiter.containsKey(p)){
				  int got = plugin.messagelimiter.get(p);
				  if(got >= max){
					  event.setCancelled(true);
					  p.sendMessage(new TextComponent(plugin.getConfig().getString("MessageLimiter.Message").replace("&", "§").replace("%player%", p.getName())));
				  } else {
					  plugin.messagelimiter.put(p, got + 1);
				  }
			  } else {
				 plugin.messagelimiter.put(p, 0);
				 
		    	    ProxyServer.getInstance().getScheduler().schedule(BungeeUtilisals.instance, new Runnable(){
						public void run() {
			    		  	plugin.messagelimiter.remove(p);
						}
		    	    }, time, TimeUnit.SECONDS);
			  }
		  }
	  }

	  
	  
}
