package com.dbsoftware.bungeeutilisals.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

public class ServerCommand
	extends Command {
	
	public ServerCommand() {
		super("server");{
		}
	}
	
	private BungeeUtilisals instance = BungeeUtilisals.getInstance();
	
	public void execute(CommandSender sender, String[] args){
		if(sender.hasPermission("butilisals.server") || sender.hasPermission("butilisals.*")){
			server(sender, args);
		} else {
			TextComponent noperm = new TextComponent( "§cYou don't have the permission to use this Command!" );
			noperm.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§abutilisals.server").create() ) );
			sender.sendMessage(noperm);
		}
	}
	
	private void server(CommandSender sender, String[] args){
		if(sender instanceof ProxiedPlayer){
			if(args.length == 0){
				String s = instance.getConfig().getString("Server_Message");
				ProxiedPlayer p = (ProxiedPlayer)sender;
				sender.sendMessage(new TextComponent(s.replace("%player%", sender.getName()).replace("%servername%", p.getServer().getInfo().getName()).replaceAll("&", "§")));
			} if(args.length == 1){
				if(instance.getConfig().getBoolean("Server_Switch_Enabled")){
					ServerInfo server = ProxyServer.getInstance().getServerInfo(args[0]);
					ProxiedPlayer p = (ProxiedPlayer)sender;
					if(server != null) {
						if(p.getServer().getInfo().getName() == server.getName()){
							p.sendMessage(new TextComponent(instance.getConfig().getString("Already_Connected").replace("&", "§")));
							return;
						} 
					    p.connect(server);
						p.sendMessage(new TextComponent(instance.getConfig().getString("Sended_Message").replace("&", "§").replace("%player%", sender.getName()).replace("%server%", server.getName())));
					  } else {
						 p.sendMessage(new TextComponent(instance.getConfig().getString("No_Server").replace("&", "§").replace("%player%", sender.getName()).replace("%server%", args[0])));  
					  }
				} else {
					return;
				}
			} else if(args.length != 1 && args.length != 0){
				sender.sendMessage(new TextComponent(instance.getConfig().getString("Use_Server").replace("&", "§")));
			}
		} else {
			sender.sendMessage(new TextComponent("§cThat command can only be used ingame!"));
		}
	}
}
