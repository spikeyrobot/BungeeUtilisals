package com.dbsoftware.bungeeutilisals.Commands;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class RulesCommand extends Command {
		
	public RulesCommand() {
			super("rules");{
		}
	}
	
	private BungeeUtilisals instance = (BungeeUtilisals)BungeeUtilisals.getInstance();

	public void execute(CommandSender sender, String[] args){
		if(sender.hasPermission("butilisals.rules") || sender.hasPermission("butilisals.*")){
			TextComponent click = new TextComponent( instance.getConfig().getString("Rules.Text").replace("&", "§").replace("%player%", sender.getName()) );
			click.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(instance.getConfig().getString("Rules.Hover").replace("&", "§").replace("%player%", sender.getName())).create() ) );
			click.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, instance.getConfig().getString("Rules.Site") ) );
			
	      sender.sendMessage(new TextComponent(instance.getConfig().getString("Rules.Header").replace("&", "§")));
	      
	      for (String rules : instance.getConfig().getStringList("Rules.Rules")) {
	    	  sender.sendMessage(new TextComponent(rules.replace("&", "§").replace("%player%", sender.getName())));
	      }
	      
	      sender.sendMessage(click);
	      
	      sender.sendMessage(new TextComponent(instance.getConfig().getString("Rules.Footer").replace("&", "§")));
		} else {
			sender.sendMessage(new TextComponent("§cYou don't have the permission to use this Command!"));
		}
	}
}