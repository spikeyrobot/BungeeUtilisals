package com.dbsoftware.bungeeutilisals.Commands;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ClearChatCommand
	extends Command {
	
	public ClearChatCommand() {
		super("clearchat");{
		}
	}
	
	private BungeeUtilisals instance = BungeeUtilisals.getInstance();
		
	public void execute(CommandSender sender, String[] args){
		if(sender.hasPermission("butilisals.clearchat") || sender.hasPermission("butilisals.*")){
			if(args.length != 1){
				sender.sendMessage(new TextComponent("§cUse /clearchat local or /clearchat global"));
			} else {
				if(args[0].equalsIgnoreCase("local") || args[0].equalsIgnoreCase("l")){
					if(!(sender instanceof ProxiedPlayer)){
						return;
					}
					ProxiedPlayer p = (ProxiedPlayer)sender;
					String server = p.getServer().getInfo().getName();
					for(ProxiedPlayer players : ProxyServer.getInstance().getServerInfo(server).getPlayers()){
					for(int i = 0; i < 100; i++){
							players.sendMessage(new TextComponent(" "));
						}
						players.sendMessage(new TextComponent(instance.getConfig().getString("ClearChat.Local").replace("&", "§").replace("%player%", p.getName())));
					}
				} if(args[0].equalsIgnoreCase("global") || args[0].equalsIgnoreCase("g")){
					for(int i = 0; i < 100; i++){
						ProxyServer.getInstance().broadcast(new TextComponent(" "));
					}
					ProxyServer.getInstance().broadcast(new TextComponent(instance.getConfig().getString("ClearChat.Global").replace("&", "§").replace("%player%", sender.getName())));
				} if(!args[0].equalsIgnoreCase("global") && !args[0].equalsIgnoreCase("g") && !args[0].equalsIgnoreCase("local") && !args[0].equalsIgnoreCase("l")){
					sender.sendMessage(new TextComponent("§cUse /clearchat local or /clearchat global"));
				}
			}
		} else {
			TextComponent noperm = new TextComponent( "§cYou don't have the permission to use this Command!" );
			noperm.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§abutilisals.clearchat'!").create() ) );
			sender.sendMessage(noperm);
		}
	}
}
