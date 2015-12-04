package com.dbsoftware.bungeeutilisals.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

public class HubCommand
	extends Command {
	
	private static BungeeUtilisals instance = (BungeeUtilisals)BungeeUtilisals.getInstance();

	
	public HubCommand(String cmd) {
		super(cmd);{
		}
	}
	
	public HubCommand(String cm, String[] cmd){
		super(cm, "", cmd);
	}
	
		
	public void execute(CommandSender sender, String[] args){
		if(sender.hasPermission("butilisals.hub") || sender.hasPermission("butilisals.*")){
			if (sender instanceof ProxiedPlayer){
				ProxiedPlayer p = (ProxiedPlayer)sender;
				String server = instance.getConfig().getString("Hub.Server");
				String playerserver = p.getServer().getInfo().getName();
				if(!playerserver.equalsIgnoreCase(server)){
					p.connect(ProxyServer.getInstance().getServerInfo(instance.getConfig().getString("Hub.Server")));
					sender.sendMessage(new TextComponent(instance.getConfig().getString("Hub.Message").replace("&", "§")));
					return;
				}
				p.sendMessage(new TextComponent(instance.getConfig().getString("Hub.inHub").replace("&", "§")));
				return;
			}
		} else {
			TextComponent noperm = new TextComponent( "§cYou don't have the permission to use this Command!" );
			noperm.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§abutilisals.hub").create() ) );
			sender.sendMessage(noperm);
		}
	}
}
