package com.dbsoftware.bungeeutilisals.Events;

import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

public class ChatUtilities implements Listener {

	  public BungeeUtilisals plugin;
	  
	  public ChatUtilities(BungeeUtilisals plugin)
	  {
	    this.plugin = plugin;
	  }
	  
	  @EventHandler
	  public void Chat(ChatEvent event){
	  	String message = event.getMessage();
	  	if((!message.endsWith("!")) && (!message.endsWith("?")) && (!message.endsWith(".")) && (!message.startsWith("/") && (plugin.getConfig().getBoolean("Point_After_Sentence")))){
	  		 event.setMessage(message + ".");
	  	}
	  	
	  		if(plugin.getConfig().getBoolean("First_UpperCase_Letter")){
	  			if(!event.getMessage().toLowerCase().startsWith("http")){
	  				String msg = event.getMessage().trim();
	  				event.setMessage(("" + msg.charAt(0)).toUpperCase() + msg.substring(1));
	  			}
	  		}
	  	}
}