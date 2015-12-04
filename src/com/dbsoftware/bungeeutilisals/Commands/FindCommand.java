package com.dbsoftware.bungeeutilisals.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

public class FindCommand
	extends Command {
	
	public FindCommand() {
		super("find");{
		}
	}
	
	private BungeeUtilisals instance = BungeeUtilisals.getInstance();
		
	public void execute(CommandSender sender, String[] args){
		if(sender.hasPermission("butilisals.find") || sender.hasPermission("butilisals.*")){
			find(sender, args);
		} else {
			TextComponent noperm = new TextComponent( "§cYou don't have the permission to use this Command!" );
			noperm.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§abutilisals.find").create() ) );
			sender.sendMessage(noperm);
		}
	}
	
	private void find(CommandSender sender, String[] args){
		if(args.length == 1){
			ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
			if(target == null){
				sender.sendMessage(new TextComponent(instance.getConfig().getString("Offline_Player").replace("&", "§")));
			} else {
				String server = target.getServer().getInfo().getName();
				if(instance.getConfig().getBoolean("Find_Click_Enabled")){
					TextComponent message = new TextComponent(instance.getConfig().getString("Find_Message").replace("&", "§").replace("%server%", server).replace("%player%", sender.getName()).replace("%target%", target.getName()));
					message.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/server " + server));
					
					sender.sendMessage(message);
					return;
				}
				sender.sendMessage(new TextComponent(instance.getConfig().getString("Find_Message").replace("&", "§").replace("%server%", server).replace("%player%", sender.getName()).replace("%target%", target.getName())));
			}
		} else {
			sender.sendMessage(new TextComponent(instance.getConfig().getString("Use_Find").replace("&", "§")));
		}
	}
}
