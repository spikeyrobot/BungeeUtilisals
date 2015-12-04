package com.dbsoftware.bungeeutilisals.StaffChat;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ProxyServer;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;

public class StaffChat {
	
	  public static List<String> inchat = new ArrayList<String>();
	
    public static void registerStaffChat(){
    	if(BungeeUtilisals.instance.getConfig().getBoolean("StaffChat.Enabled")){
    		ProxyServer.getInstance().getPluginManager().registerCommand(BungeeUtilisals.instance, new StaffChatCommand());
	    	ProxyServer.getInstance().getPluginManager().registerListener(BungeeUtilisals.instance, new StaffChatEvent(BungeeUtilisals.instance));
    	}
    }

}
