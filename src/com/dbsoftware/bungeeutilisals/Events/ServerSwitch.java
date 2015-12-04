package com.dbsoftware.bungeeutilisals.Events;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;
import com.dbsoftware.bungeeutilisals.Updater.UpdateChecker;

public class ServerSwitch implements Listener {

	  public BungeeUtilisals plugin;
	  
	  public ServerSwitch(BungeeUtilisals plugin)
	  {
	    this.plugin = plugin;
	  }
	  
		@EventHandler
		public void onJoin(ServerSwitchEvent event){
			ProxiedPlayer p = event.getPlayer();
			if((p.hasPermission("butilisals.notify") || p.hasPermission("butilisals.*")) && BungeeUtilisals.update){
				String version = UpdateChecker.getLatestVersion();
				TextComponent clickhere = new TextComponent( "§8§l[§a§lBUtilisals§8§l] §7Go download it by §aClicking Here." );
				clickhere.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/bungeeutilisals-v1-6-antiswear-antispam-anticaps-antiad-more-bungee.7865/") );
				
				p.sendMessage(new TextComponent( "§8§l[§a§lBUtilisals§8§l] §7Version §a" + version + " §7is available on Spigot!"));
				p.sendMessage(new TextComponent(clickhere));
			}
		}
	
}
