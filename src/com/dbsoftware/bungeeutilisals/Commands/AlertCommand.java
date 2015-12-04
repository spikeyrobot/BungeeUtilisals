package com.dbsoftware.bungeeutilisals.Commands;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class AlertCommand
	extends Command {
	
	public AlertCommand() {
		super("alert");{
		}
	}
		
	public void execute(CommandSender sender, String[] args){
	    String prefix = ChatColor.translateAlternateColorCodes('&', BungeeUtilisals.instance.alertprefix);
		if(sender.hasPermission("butilisals.alert") || sender.hasPermission("butilisals.*")){
			 if (args.length >= 1) {
			        String msg = "";
			        for (String messages : args) {
			        	msg = msg + messages + " ";
			        }
			        BaseComponent[] message = TextComponent.fromLegacyText(prefix + " " + msg.replace("&", "§"));
			        ProxyServer.getInstance().broadcast(message);
			      } else {
						TextComponent alertargs = new TextComponent( "§cYou need to enter at least 1 word!" );
						sender.sendMessage(alertargs);
			      }
		} else {
			TextComponent noperm = new TextComponent( "§cYou don't have the permission to use this Command!" );
			noperm.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§abutilisals.alert'!").create() ) );
			sender.sendMessage(noperm);
		}
	}
}
