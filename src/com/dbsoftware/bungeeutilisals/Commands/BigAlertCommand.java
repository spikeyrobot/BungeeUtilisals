package com.dbsoftware.bungeeutilisals.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;
import com.dbsoftware.bungeeutilisals.utils.ActionBarUtil;
import com.dbsoftware.bungeeutilisals.utils.TitleUtil;

public class BigAlertCommand extends Command {
	
	public BigAlertCommand() {
		super("bigalert");{
		}
	}
	
	private static BungeeUtilisals instance = BungeeUtilisals.getInstance();
	
	public static boolean chat;
	public static boolean title;
	public static boolean actionbar;
	
	public static void onStartup(){
		chat = instance.getConfig().getBoolean("BigAlert.Chat.Enabled");
		title = instance.getConfig().getBoolean("BigAlert.Title.Enabled");
		actionbar = instance.getConfig().getBoolean("BigAlert.ActionBar.Enabled");
	}
		
	public void execute(CommandSender sender, String[] args){
	    String msg1 = ChatColor.translateAlternateColorCodes('&', BungeeUtilisals.instance.alertprefix);
		if(sender.hasPermission("butilisals.bigalert") || sender.hasPermission("butilisals.*")){
			 if (args.length >= 1) {
				if((args.length == 2) && (args[0].equalsIgnoreCase("config"))){
					if(instance.getConfig().getString("BigAlert.Messages." + args[1]) != null){
						String message = instance.getConfig().getString("BigAlert.Messages." + args[1]);
						
			            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
				        	p.sendMessage(new TextComponent(msg1 + " " + ChatColor.translateAlternateColorCodes('&', message.replace("%p%", p.getName()).replaceAll("%n", " "))));
				        	ActionBarUtil.sendActionBar(p, ChatColor.translateAlternateColorCodes('&', message.replace("%p%", p.getName()).replaceAll("%n", " ")));	
			            }

			        	if(message.contains("%n")){
				        	String[] titles = message.split("%n");
				        	String title = titles[0];
				        	String subtitle = titles[1];
				        	int fadeIn = instance.getConfig().getInt("BigAlert.Title.FadeIn");
				        	int stay = instance.getConfig().getInt("BigAlert.Title.Stay");
				        	int fadeOut = instance.getConfig().getInt("BigAlert.Title.FadeOut");
				        	
				            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
					            
					        	BaseComponent[] btitle = new ComponentBuilder(title.replace("&", "§").replace("%p%", p.getName())).create();
					        	BaseComponent[] stitle = new ComponentBuilder(subtitle.replace("&", "§").replace("%p%", p.getName())).create();
					        	
					        	TitleUtil.sendFullTitle(p, fadeIn, stay, fadeOut, stitle, btitle);
				        	}
				        	return;
				        } else {
				        	int fadeIn = instance.getConfig().getInt("BigAlert.Title.FadeIn");
			        		int stay = instance.getConfig().getInt("BigAlert.Title.Stay");
			        		int fadeOut = instance.getConfig().getInt("BigAlert.Title.FadeOut");
				            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
					            
					        	BaseComponent[] btitle = new ComponentBuilder(message.replace("&", "§").replace("%p%", p.getName())).create();
					        	BaseComponent[] stitle = new ComponentBuilder("").create();
					        	
					        	TitleUtil.sendFullTitle(p, fadeIn, stay, fadeOut, stitle, btitle);
				        	}
			        		return;
				        }
					} else {
						sender.sendMessage(new TextComponent( "§cThat String doesn't exist in the Config!" ));
						return;
					}
				}
				if((args.length != 2) && (args[0].equalsIgnoreCase("config"))){
					return;
				}
				 
		        String msg = "";
		        for (String messages : args) {
		          msg = msg + messages + " ";
		        }
		        
	            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
		        	p.sendMessage(new TextComponent(msg1 + " " + ChatColor.translateAlternateColorCodes('&', msg.replace("%p%", p.getName()).replaceAll("%n", " "))));
		        	ActionBarUtil.sendActionBar(p, ChatColor.translateAlternateColorCodes('&', msg.replace("%p%", p.getName()).replaceAll("%n", " ")));	
	            }
	            
		        if(msg.contains("%n")){
		        	String[] titles = msg.split("%n");
		        	String title = titles[0];
		        	String subtitle = titles[1];
		        	int fadeIn = instance.getConfig().getInt("BigAlert.Title.FadeIn");
		        	int stay = instance.getConfig().getInt("BigAlert.Title.Stay");
		        	int fadeOut = instance.getConfig().getInt("BigAlert.Title.FadeOut");
		        	
		            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
			            
			        	BaseComponent[] btitle = new ComponentBuilder(title.replace("&", "§").replace("%p%", p.getName())).create();
			        	BaseComponent[] stitle = new ComponentBuilder(subtitle.replace("&", "§").replace("%p%", p.getName())).create();
			        	
			        	TitleUtil.sendFullTitle(p, fadeIn, stay, fadeOut, stitle, btitle);
		        	}
		        	return;
		        } else {
		        	int fadeIn = instance.getConfig().getInt("BigAlert.Title.FadeIn");
	        		int stay = instance.getConfig().getInt("BigAlert.Title.Stay");
	        		int fadeOut = instance.getConfig().getInt("BigAlert.Title.FadeOut");
		            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
			            
			        	BaseComponent[] btitle = new ComponentBuilder(msg.replace("&", "§").replace("%p%", p.getName())).create();
			        	BaseComponent[] stitle = new ComponentBuilder("").create();
			        	
			        	TitleUtil.sendFullTitle(p, fadeIn, stay, fadeOut, stitle, btitle);
		        	}
	        		return;
		        }
		      } else {
					TextComponent alertargs = new TextComponent( "§cYou need to enter at least 1 word!" );
					sender.sendMessage(alertargs);
		      }
		} else {
			TextComponent noperm = new TextComponent( "§cYou don't have the permission to use this Command!" );
			noperm.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§abutilisals.bigalert'!").create() ) );
			sender.sendMessage(noperm);
		}
	}
}