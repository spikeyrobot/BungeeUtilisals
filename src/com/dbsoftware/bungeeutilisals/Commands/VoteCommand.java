package com.dbsoftware.bungeeutilisals.Commands;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class VoteCommand extends Command {
		
	public VoteCommand() {
			super("vote");{
		}
	}
	
	private BungeeUtilisals instance = (BungeeUtilisals)BungeeUtilisals.getInstance();

	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args){
		if(sender.hasPermission("butilisals.vote") || sender.hasPermission("butilisals.*")){
			TextComponent click = new TextComponent( instance.getConfig().getString("Vote.Text").replace("&", "§").replace("%player%", sender.getName()) );
			click.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(instance.getConfig().getString("Vote.Hover").replace("&", "§").replace("%player%", sender.getName())).create() ) );
			click.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, instance.getConfig().getString("Vote.Site") ) );
	      sender.sendMessage(new TextComponent(instance.getConfig().getString("Vote.Header").replace("&", "§")));

	      for (String links : instance.getConfig().getStringList("Vote.Links")) {
	    	  sender.sendMessage(links.replace("&", "§").replace("%player%", sender.getName()));
	      }
	      
	      sender.sendMessage(click);
	      sender.sendMessage(new TextComponent(instance.getConfig().getString("Vote.Footer").replace("&", "§")));
		} else {
			sender.sendMessage(new TextComponent("§cYou don't have the permission to use this Command!"));
		}
	}
}