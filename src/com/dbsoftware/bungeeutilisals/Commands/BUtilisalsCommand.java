package com.dbsoftware.bungeeutilisals.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;
import com.dbsoftware.bungeeutilisals.ActionBarAnnouncer.ActionBarAnnouncements;
import com.dbsoftware.bungeeutilisals.Announcer.Announcements;
import com.dbsoftware.bungeeutilisals.Friends.Friends;
import com.dbsoftware.bungeeutilisals.Report.Reports;
import com.dbsoftware.bungeeutilisals.TitleAnnouncer.TitleAnnouncements;

public class BUtilisalsCommand
	extends Command {
	
	public BUtilisalsCommand() {
		super("butilisals", "", "butili");{
		}
	}
	
	private BungeeUtilisals instance = BungeeUtilisals.getInstance();
		
	public void execute(CommandSender sender, String[] args){
		TextComponent butilisals = new TextComponent( " §8§l[§a§lBungeeUtilisals§8§l] §7Created by didjee2!" );
		TextComponent reload = new TextComponent( " §aClick §lHere§r §ato reload the Plugin!!" );
		reload.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aReloads the Plugin").create() ) );
		reload.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/butilisals reload"));
		
		if(sender.hasPermission("butilisals.admin") || sender.hasPermission("butilisals.*")){
			if(args.length == 0){
				sender.sendMessage(butilisals);
				sender.sendMessage(reload);
			} if(args.length == 1){
				if(args[0].equalsIgnoreCase("reload")){
					ReloadConfig(sender);
				} if(args[0].equalsIgnoreCase("lock")){
					toggleChat(sender);
				} else if(!args[0].equalsIgnoreCase("reload") && !args[0].equalsIgnoreCase("lock")){
					sender.sendMessage(butilisals);
					sender.sendMessage(reload);
				}
			} else if((args.length != 0) && (args.length != 1)){
				sender.sendMessage(butilisals);
				sender.sendMessage(reload);
			}
		} else {
			TextComponent noperm = new TextComponent( "§cYou don't have the permission to use this Command!" );
			noperm.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§abutilisals.info").create() ) );
			sender.sendMessage(noperm);
		}
	}
	
	private void toggleChat(CommandSender sender) {
	    if (instance.chatMuted){
	    	instance.chatMuted = false;
	    	sender.sendMessage(new TextComponent(instance.getConfig().getString("ChatLock.Unlock").replace("&", "§")));
	    	if(instance.getConfig().getBoolean("ChatLock.Broadcast.Enabled")){
	        	for(String s : instance.getConfig().getStringList("ChatLock.Broadcast.UnLock")){
	            	ProxyServer.getInstance().broadcast(new TextComponent(s.replace("&", "§")));
	        	}
	        }
	    } else {
	    	instance.chatMuted = true;
	    	sender.sendMessage(new TextComponent(instance.getConfig().getString("ChatLock.Lock").replace("&", "§")));
	    	if(instance.getConfig().getBoolean("ChatLock.Broadcast.Enabled")){
	    		for(String s : instance.getConfig().getStringList("ChatLock.Broadcast.Lock")){
	        		ProxyServer.getInstance().broadcast(new TextComponent(s.replace("&", "§")));
	    		}
	    	}
	    }
	  }
	
	private void ReloadConfig(CommandSender sender){
		instance.reloadConfig();
	    instance.alertprefix = instance.getConfig().getString("Alert_Prefix");
	    Announcements.reloadAnnouncements();
	    TitleAnnouncements.reloadAnnouncements();
	    ActionBarAnnouncements.reloadAnnouncements();
	    instance.reloadTabPart();
	    Friends.reloadFriendsData();
	    Reports.reloadReportsData();
		sender.sendMessage(new TextComponent("§8§l[§a§lBUtilisals§8§l] §7Config Reloaded!"));
	}
}
