package com.dbsoftware.bungeeutilisals.StaffChat;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand
	extends Command {
	
	public StaffChatCommand() {
		super("staffchat");{
		}
	}
		
	public void execute(CommandSender sender, String[] args){
	  if(sender.hasPermission("butilisals.staffchat") || sender.hasPermission("butilisals.*")){
		  	if (!StaffChat.inchat.contains(sender.getName())){
		  		StaffChat.inchat.add(sender.getName());
		  		sender.sendMessage(new TextComponent(BungeeUtilisals.instance.getConfig().getString("StaffChat.ChatEnabled").replace("&", "§")));
		  		return;
		    }
		    StaffChat.inchat.remove(sender.getName());
	  		sender.sendMessage(new TextComponent(BungeeUtilisals.instance.getConfig().getString("StaffChat.ChatDisabled").replace("&", "§")));
	  } else {
		  sender.sendMessage(new TextComponent("§cYou don't have the permission to use this Command!"));
	  }
	} 
}
