package com.dbsoftware.bungeeutilisals.Events;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import com.dbsoftware.bungeeutilisals.BungeeUtilisals;
import com.dbsoftware.bungeeutilisals.Tasks.TabUpdateTask;
import com.dbsoftware.bungeeutilisals.Updater.UpdateChecker;

public class LoginEvent implements Listener {

	  public BungeeUtilisals plugin;
	  
	  public LoginEvent(BungeeUtilisals plugin){
	    this.plugin = plugin;
	  }
		
		public void onPlayerJoin(ServerConnectEvent event){
			if((event.getPlayer().hasPermission("butilisals.notify") || event.getPlayer().hasPermission("butilisals.*")) && BungeeUtilisals.update){
				String version = UpdateChecker.getLatestVersion();
				TextComponent message = new TextComponent( "§8§l[§a§lBungeeUtilisals§8§l] §7Go download it by " );
				
				TextComponent clickhere = new TextComponent( "§aClicking Here" );
				clickhere.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/bungeeutilisals-v1-6-antiswear-antispam-anticaps-antiad-more-bungee.7865/") );
				
				message.addExtra(clickhere);
				
				event.getPlayer().sendMessage(new TextComponent( "§8§l[§a§lBungeeUtilisals§8§l] §7Version §a" + version + " §7is available on Spigot!"));
				event.getPlayer().sendMessage(message);
			}
			if(plugin.getConfig().getBoolean("Tab.Enabled")){
				String sheader = plugin.getConfig().getStringList("Tab.Header").get(TabUpdateTask.headercount);
				String sfooter = plugin.getConfig().getStringList("Tab.Header").get(TabUpdateTask.footercount);
				sheader = sheader.replace("&", "§");
				sheader = sheader.replace("%p%", event.getPlayer().getName()).replace("%server%", event.getPlayer().getServer().getInfo().getName());
				sfooter = sfooter.replace("&", "§");
				sfooter = sfooter.replace("%p%", event.getPlayer().getName()).replace("%server%", event.getPlayer().getServer().getInfo().getName());
				
				BaseComponent[] header = new ComponentBuilder(sheader).create();
				BaseComponent[] footer = new ComponentBuilder(sfooter).create();
				
				event.getPlayer().setTabHeader(header, footer);
			}
		}
}
