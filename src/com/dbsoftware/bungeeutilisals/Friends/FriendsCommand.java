package com.dbsoftware.bungeeutilisals.Friends;

import java.util.ArrayList;
import java.util.List;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class FriendsCommand
	extends Command {
	
	public FriendsCommand() {
		super("friend");
	}
		
	public void execute(CommandSender sender, String[] args){
      if(!BungeeUtilisals.getDatabaseManager().isConnected()){
    	  Friends.dbmanager.openConnection();
      }
	  if(sender.hasPermission("butilisals.friends") || sender.hasPermission("butilisals.*")){
		if(!(sender instanceof ProxiedPlayer)){
			sender.sendMessage(new TextComponent("This message can only be executed in the console."));
			return;
		}
		if(args.length == 0){
            for(String s : Friends.friends.getStringList( "Friends.Messages.Help", new ArrayList<String>())){
        		ProxiedPlayer p = (ProxiedPlayer)sender;
    			p.sendMessage(new TextComponent(s.replace("&", "�")));            	
            }
		} if(args.length == 1){
			if(args[0].contains("list")){
				ProxiedPlayer p = (ProxiedPlayer)sender;
				List<String> friends = FriendsAPI.getFriends(p.getName());
				if(friends.isEmpty()){
					sender.sendMessage(TextComponent.fromLegacyText(Friends.friends.getString("Friends.Messages.NoFriends", "&cYou have no friends yet!").replace("&", "�")));
					return;
				}
				sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.FriendListFormat.Header", "&a&m&l-------&r&c&l Friends &a&m&l-------").replace("&", "�")));
				for(String s : friends){
					ProxiedPlayer friend = ProxyServer.getInstance().getPlayer(s);
					if(friend != null){
						TextComponent teleport = new TextComponent(" " + Friends.friends.getString("Friends.Messages.Hover.Text.Teleport", "&8&l[&a&lTeleport&8&l]").replace("&", "�") + " ");
						teleport.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Friends.friends.getString("Friends.Messages.Hover.Teleport", "&aThis will teleport you to the &e%server% &aserver!").replace("&", "�").replace("%server%", friend.getServer().getInfo().getName())).create() ) );
						teleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + friend.getServer().getInfo().getName()));
						
						TextComponent format = new TextComponent(Friends.friends.getString("Friends.Messages.FriendListFormat.OnlineFormat", "&7- &a%friend% &7is online in &a%server%&7!").replace("&", "�").replace("%friend%", s).replace("%server%", friend.getServer().getInfo().getName()));
						
						TextComponent remove = new TextComponent(" " + Friends.friends.getString("Friends.Messages.Hover.Text.Remove", "&aClick here to remove &e%player% &afrom your friend list.").replace("&", "�") + " ");
						remove.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Friends.friends.getString("Friends.Messages.Hover.Remove", "&aClick here to remove &e%player% &afrom your friend list.").replace("&", "�").replace("%player%", friend.getName())).create() ) );
						remove.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend remove " + friend.getName()));
						
						TextComponent msg = new TextComponent(" " + Friends.friends.getString("Friends.Messages.Hover.Text.Msg", "&8&l[&e&lMsg&8&l]").replace("&", "�") + " ");
						msg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Friends.friends.getString("Friends.Messages.Hover.Msg", "&eClick here to talk with &a%friend%&e!").replace("&", "�").replace("%friend%", friend.getName())).create() ) );
						msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/friend msg " + friend.getName() + " "));
						
						format.addExtra(teleport);
						format.addExtra(msg);
						format.addExtra(remove);
							
						sender.sendMessage(format);
					} else {
						sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.FriendListFormat.OfflineFormat", "&7- &a%friend% &7is offline!").replace("&", "�").replace("%friend%", s)));
					}
				}
				sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.FriendListFormat.Footer", "&a&m&l-------&r&c&l Friends &a&m&l-------").replace("&", "�")));
			} if(args[0].contains("requests")){
				ProxiedPlayer p = (ProxiedPlayer)sender;
				List<String> requests = FriendsAPI.getFriendRequests(p.getName());
				
				sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.RequestListFormat.Header", "").replace("&", "�")));
				for(String s : requests){
					ProxiedPlayer request = ProxyServer.getInstance().getPlayer(s);
					if(request != null){
						TextComponent accept = new TextComponent(" " + Friends.friends.getString("Friends.Messages.Hover.Text.Accept", "").replace("&", "�") + " ");
						accept.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Friends.friends.getString("Friends.Messages.Hover.Accept", "").replace("&", "�").replace("%player%", request.getName())).create() ) );
						accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept " + request.getName()));
						
						TextComponent format = new TextComponent(Friends.friends.getString("Friends.Messages.RequestListFormat.Format", "").replace("&", "�").replace("%player%", s));
						
						TextComponent deny = new TextComponent(" " + Friends.friends.getString("Friends.Messages.Hover.Text.Deny", "").replace("&", "�") + " ");
						deny.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Friends.friends.getString("Friends.Messages.Hover.Deny", "").replace("&", "�").replace("%player%", request.getName())).create() ) );
						deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend deny " + request.getName()));
						
						format.addExtra(accept);
						format.addExtra(deny);
						
						sender.sendMessage(format);
					}
				}
				sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.FriendListFormat.Footer", "").replace("&", "�")));
			} if(args[0].contains("reply")){
				sender.sendMessage(new TextComponent("�cPlease enter a message."));
			}
		} if(args.length == 2){
			if(args[0].contains("add")){
				if(sender.getName().toLowerCase().equals(args[1].toLowerCase())){
					sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.Error", "&cYou can't add yourself as a friend.")));
					return;
				}
	            List<String> myfriends = FriendsAPI.getFriends(sender.getName());
				int limit = Friends.friends.getInt("Friends.Limit.Limit", 10) + 1;
				for(String perm : sender.getPermissions()){
					if(perm.contains("butilisals.friends.amount")){
						limit = (perm == "butilisals.friends.amount.*" ? 1000000 : Integer.valueOf(perm.replace("butilisals.friends.amount.", "")));
					}
				}
				
            	if(myfriends.size() >= limit && Friends.friends.getBoolean("Friends.Limit.Enabled", true)){
					sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.ReachedLimit", "").replace("&", "�").replace("%player%", args[1])));
            		return;
            	}
				ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);
				if(p == null){
		            List<String> friends = FriendsAPI.getFriends(args[1]);
		            if(friends.contains(args[1])){
						sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.AlreadyFriend", "").replace("&", "�").replace("%player%", sender.getName())));
						return;
		            }
					
		            FriendsAPI.addFriendRequest((ProxiedPlayer)sender, args[1]);
		            sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.RequestSent", "").replace("&", "�").replace("%player%", sender.getName())));
					return;
				} else {
					if(sender.getName().toLowerCase().equals(p.getName().toLowerCase())){
						sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.Error", "&cYou can't add yourself as a friend.")));
						return;
					}
		            List<String> friends = FriendsAPI.getFriends(sender.getName());
		            if(friends.contains(p.getName())){
						sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.AlreadyFriend", "").replace("&", "�").replace("%player%", sender.getName())));
						return;
		            }
					
		            FriendsAPI.addFriendRequest((ProxiedPlayer)sender, p.getName());
		            sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.RequestSent", "").replace("&", "�").replace("%player%", sender.getName())));						
		            
		            TextComponent accept = new TextComponent(" " + Friends.friends.getString("Friends.Messages.Hover.Text.Accept", "").replace("&", "�") + " ");
		            accept.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Friends.friends.getString("Friends.Messages.Hover.Accept", "").replace("&", "�").replace("%player%", sender.getName())).create() ) );
		            accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept " + sender.getName()));
		            
		            TextComponent format = new TextComponent(Friends.friends.getString("Friends.Messages.NewRequest", "").replace("&", "�").replace("%player%", sender.getName()));
		            
		            TextComponent deny = new TextComponent(" " + Friends.friends.getString("Friends.Messages.Hover.Text.Deny", "").replace("&", "�") + " ");
		            deny.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Friends.friends.getString("Friends.Messages.Hover.Deny", "").replace("&", "�").replace("%player%", sender.getName())).create() ) );
		            deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend deny " + sender.getName()));
		            
		            format.addExtra(accept);
		            format.addExtra(deny);
		            
		            p.sendMessage(format);
				}
				
			} if(args[0].equalsIgnoreCase("accept")){
				ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);
				if(p == null){
		            List<String> requests = FriendsAPI.getFriendRequests(sender.getName());
		            List<String> myfriends = FriendsAPI.getFriends(sender.getName());
					int limit = Friends.friends.getInt("Friends.Limit.Limit", 10) + 1;
					for(String perm : sender.getPermissions()){
						if(perm.contains("butilisals.friends.amount")){
							limit = (perm == "butilisals.friends.amount.*" ? 1000000 : Integer.valueOf(perm.replace("butilisals.friends.amount.", "")));
						}
					}
		            if(requests.contains(args[1])){
		            	if(myfriends.size() >= limit && Friends.friends.getBoolean("Friends.Limit.Enabled", true)){
							sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.ReachedLimit", "").replace("&", "�").replace("%player%", args[1])));
		            		return;
		            	}
		            	FriendsAPI.addFriend((ProxiedPlayer)sender, args[1]);
		            	FriendsAPI.removeFriendRequest((ProxiedPlayer)sender, args[1]);
	            	
		            	sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.Accepted", "").replace("&", "�").replace("%player%", args[1])));
		            }
	            	return;
				} else {
		            List<String> requests = FriendsAPI.getFriendRequests(sender.getName());
		            List<String> myfriends = FriendsAPI.getFriends(sender.getName());
					int limit = Friends.friends.getInt("Friends.Limit.Limit", 10) + 1;
					
					for(String perm : sender.getPermissions()){
						if(perm.contains("butilisals.friends.amount")){
							limit = (perm == "butilisals.friends.amount.*" ? 1000000 : Integer.valueOf(perm.replace("butilisals.friends.amount.", "")));
						}
					}

		            if(requests.contains(args[1])){
		            	if(myfriends.size() >= limit && Friends.friends.getBoolean("Friends.Limit.Enabled", true)){
							sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.ReachedLimit", "").replace("&", "�").replace("%player%", args[1])));
		            		return;
		            	}
		            	FriendsAPI.addFriend((ProxiedPlayer)sender, p.getName());
		            	FriendsAPI.removeFriendRequest((ProxiedPlayer)sender, p.getName());
	            	
		            	sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.Accepted", "").replace("&", "�").replace("%player%", p.getName())));
		            	p.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.AcceptedFriend", "").replace("&", "�").replace("%player%", sender.getName())));
		            }
				}
			} if(args[0].equalsIgnoreCase("deny")){
				ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);
				if(p == null){
		            List<String> requests = FriendsAPI.getFriendRequests(sender.getName());
		         
		            if(requests.contains(args[1])){
		            	FriendsAPI.denyFriendRequest((ProxiedPlayer)sender, args[1]);

		            	sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.Denied", "").replace("&", "�").replace("%player%", args[1])));
		            }
	            	return;
				} else {
		            List<String> requests = Friends.friends.getStringList("Friends.Players." + sender.getName() + ".Requests", new ArrayList<String>());

		            if(requests.contains(args[1])){
		            	FriendsAPI.denyFriendRequest((ProxiedPlayer)sender, p.getName());

		            	sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.Denied", "").replace("&", "�").replace("%player%", p.getName())));
		            	p.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.DeniedFriend", "").replace("&", "�").replace("%player%", sender.getName())));
		            }
				}
			} if(args[0].equalsIgnoreCase("remove")){
				ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);
				if(p == null){
					if(!FriendsAPI.getFriends(sender.getName()).contains(args[1])){
						sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.RemoveError", "&cYou can't remove someone who is not your friend.").replace("&", "�")));
						return;
					}
					FriendsAPI.removeFriend((ProxiedPlayer)sender, args[1]);
					sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.Removed", "").replace("&", "�").replace("%friend%", args[1])));
					return;
				} else {
					if(!FriendsAPI.getFriends(sender.getName()).contains(p.getName())){
						sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.RemoveError", "&cYou can't remove someone who is not your friend.").replace("&", "�")));
						return;
					}
					FriendsAPI.removeFriend((ProxiedPlayer)sender, p.getName());
					sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.Removed", "").replace("&", "�").replace("%friend%", args[1])));
					p.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.Remove", "").replace("&", "�").replace("%player%", sender.getName())));
				}
			} if(args[0].contains("msg")){
				sender.sendMessage(new TextComponent("�cPlease enter a message."));
			}
		} if(args.length > 2){
			if(args[0].contains("msg")){
				ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);
				if(p == null){
					sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.NoPlayer", "").replace("&", "�")));
					return;
				} else {
					List<String> friends = FriendsAPI.getFriends(sender.getName());
					if(!friends.contains(p.getName())){
						sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.NoFriendMsgError", "").replace("&", "�")));
						return;
					}
					if(sender == p){
						sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.MsgError", "").replace("&", "�")));
						return;
					} else {
				          String message = "";
				          for (int i = 2; i <= args.length - 1; i++)
				          {
				            String s = String.valueOf(args[i]);
				            if (message != "") {
				              message = message + " " + s;
				            } else {
				              message = s;
				            }
				          }
						sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.MsgSend", "").replace("&", "�").replace("%player%", p.getName()).replace("%message%", message)));
						p.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.MsgReceive", "").replace("&", "�").replace("%player%", sender.getName()).replace("%message%", message)));
						Friends.msging.put((ProxiedPlayer)sender, p);
						Friends.msging.put(p, (ProxiedPlayer)sender);
					}
				}
			}
		} if(args.length > 1){
			if(args[0].contains("reply")){
				if(!Friends.msging.containsKey((ProxiedPlayer)sender)){
					sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.NoReply", "").replace("&", "�").replace("%player%", sender.getName())));
					return;
				}
				ProxiedPlayer p = Friends.msging.get((ProxiedPlayer)sender);
				if(p == null){
					sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.NoPlayer", "").replace("&", "�")));
					return;
				} else {
					if(sender == p){
						sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.MsgError", "").replace("&", "�")));
						return;
					} else {
				          String message = "";
				          for (int i = 2; i <= args.length - 1; i++)
				          {
				            String s = String.valueOf(args[i]);
				            if (message != "") {
				              message = message + " " + s;
				            } else {
				              message = s;
				            }
				          }
						sender.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.MsgSend", "").replace("&", "�").replace("%player%", p.getName()).replace("%message%", message)));
						p.sendMessage(new TextComponent(Friends.friends.getString("Friends.Messages.MsgReceive", "").replace("&", "�").replace("%player%", sender.getName()).replace("%message%", message)));
						Friends.msging.put((ProxiedPlayer)sender, p);
					}
				}
			}
		}
	  } else {
		  sender.sendMessage(new TextComponent("�cYou don't have the permission to use this Command!"));
	  }
	} 
}
