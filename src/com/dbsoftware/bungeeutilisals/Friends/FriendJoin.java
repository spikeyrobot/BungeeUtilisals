package com.dbsoftware.bungeeutilisals.Friends;

import java.util.Iterator;
import java.util.List;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class FriendJoin implements Listener {

	  public BungeeUtilisals plugin;
	  
	  public FriendJoin(BungeeUtilisals plugin)
	  {
	    this.plugin = plugin;
	  }
	  
		@EventHandler
		   public void FriendJoining(PostLoginEvent event){
		     ProxiedPlayer p = event.getPlayer();
		     
			 List<String> requests = FriendsAPI.getFriendRequests(p.getName());
		     if(requests.size() != 0){
		    	 p.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.RequestJoinMessage", "").replace("&", "§").replace("%requests%", requests.size() + "")));
		     }
		     
		     Iterator<ProxiedPlayer> i = ProxyServer.getInstance().getPlayers().iterator();
		     while(i.hasNext()){
			     ProxiedPlayer player = i.next();
				 List<String> friends = FriendsAPI.getFriends(player.getName());
				 if(friends.contains(p.getName())){
					 player.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.FriendJoin", "").replace("&", "§").replace("%friend%", p.getName())));
				 }
		     }
		}
}