package com.dbsoftware.bungeeutilisals.Commands;

import java.util.ArrayList;
import java.util.Collections;

import net.craftminecraft.bungee.bungeeyaml.bukkitapi.ConfigurationSection;
import net.md_5.bungee.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

public class GListCommand extends Command {
	
	public GListCommand() {
		super("glist", "", instance.getConfig().getString("GList.Aliase"));{
		}
	}
	
	private static BungeeUtilisals instance = BungeeUtilisals.getInstance();
		
	public void execute(CommandSender sender, String[] args){
		if(sender.hasPermission("butilisals.glist") || sender.hasPermission("butilisals.*")){
			glist(sender);
		} else {
			TextComponent noperm = new TextComponent( "§cYou don't have the permission to use this Command!" );
			noperm.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§abutilisals.glist").create() ) );
			sender.sendMessage(noperm);
		}
	}
	
	private void glist(CommandSender sender){
		if(sender instanceof ProxiedPlayer){
		if(!instance.getConfig().getBoolean("GList.Custom_GList")){
	          ProxiedPlayer localProxiedPlayer1 = (ProxiedPlayer)sender;
	          for (ServerInfo localServerInfo : ProxyServer.getInstance().getServers().values()) {
	            if (localServerInfo.canAccess(sender))
	            {
	              ArrayList<String> localArrayList = new ArrayList<String>();
	              for (ProxiedPlayer localProxiedPlayer2 : localServerInfo.getPlayers()) {
	                localArrayList.add(localProxiedPlayer2.getDisplayName());
	              }
	              Collections.sort(localArrayList, String.CASE_INSENSITIVE_ORDER);
	              localProxiedPlayer1.sendMessage(new ComponentBuilder(instance.getConfig().getString("GList.Format")
	            		  .replace("%server%", localServerInfo.getName())
	            		  .replace("%players%", localServerInfo.getPlayers().size() + "")
	            		  .replace("%playerlist%", Util.format(localArrayList, instance.getConfig().getString("GList.PlayerListColor") + ", " + instance.getConfig().getString("GList.PlayerListColor")))
	            		  .replaceAll("&", "§")).create());
	            }
	          }
	          sender.sendMessage(new ComponentBuilder(instance.getConfig().getString("GList.Total").replace("%totalnum%", instance.getProxy().getOnlineCount() + "").replaceAll("&", "§")).create());
		} else {
			ConfigurationSection cs = instance.getConfig().getConfigurationSection("GList.Servers");
			for(String s : cs.getKeys(false)){
				int serverPlayers = 0;
				ArrayList<String> localArrayList = new ArrayList<String>();
				if(cs.getString(s).contains(",")){
					for(String calculate : cs.getString(s).split(",")){
						
						ServerInfo server = ProxyServer.getInstance().getServerInfo(calculate);
						if(server != null){
							serverPlayers = serverPlayers + server.getPlayers().size();
							for(ProxiedPlayer pl : server.getPlayers()){
								localArrayList.add(pl.getDisplayName());
							}
						}
					}
				} else {
					ServerInfo server = ProxyServer.getInstance().getServerInfo(cs.getString(s));
					if(server != null){
						serverPlayers = serverPlayers + server.getPlayers().size();
						for(ProxiedPlayer pl : server.getPlayers()){
							localArrayList.add(pl.getDisplayName());
						}
					}
				}
				Collections.sort(localArrayList, String.CASE_INSENSITIVE_ORDER);
				sender.sendMessage(new TextComponent(instance.getConfig().getString("GList.Format")
						.replace("%server%", s)
						.replace("%playerlist%", Util.format(localArrayList, instance.getConfig().getString("GList.PlayerListColor") + ", " + instance.getConfig().getString("GList.PlayerListColor")))
						.replace("%players%", serverPlayers + "")
						.replaceAll("&", "§")));
				}
	          sender.sendMessage(new ComponentBuilder(instance.getConfig().getString("GList.Total").replace("%totalnum%", instance.getProxy().getOnlineCount() + "").replaceAll("&", "§")).create());
			}
		} else {
			sender.sendMessage(new TextComponent("§cThat command can only be used ingame!"));
		}
	}
}
