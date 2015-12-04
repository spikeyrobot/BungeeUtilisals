package com.dbsoftware.bungeeutilisals.Friends;

import java.util.Iterator;
import java.util.List;
import com.dbsoftware.bungeeutilisals.BungeeUtilisals;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class FriendQuit implements Listener {

	  public BungeeUtilisals plugin;
	  
	  public FriendQuit(BungeeUtilisals plugin)
	  {
	    this.plugin = plugin;
	  }
	  
		@EventHandler
		   public void FriendLeaving(PlayerDisconnectEvent event){
		     ProxiedPlayer p = event.getPlayer();
		     
		     Iterator<ProxiedPlayer> i = ProxyServer.getInstance().getPlayers().iterator();
		     while(i.hasNext()){
			     ProxiedPlayer player = i.next();
				 List<String> friends = FriendsAPI.getFriends(player.getName());
				 if(friends.contains(p.getName())){
					 player.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.FriendQuit", "").replace("&", "�").replace("%friend%", p.getName())));
				 }
		     }
		}
}