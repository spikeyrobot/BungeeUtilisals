package com.dbsoftware.bungeeutilisals.Tasks;

import java.util.ArrayList;
import java.util.Collection;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TabUpdateTask implements Runnable {

	ArrayList<String> headers = new ArrayList<String>();
	ArrayList<String> footers = new ArrayList<String>();
	String header;
	String footer;
	public static int headercount = 0;
	public static int footercount = 0;

	public void addHeader(String header) {
		headers.add(colorize(header));
	}
	public void addFooter(String footer) {
		footers.add(colorize(footer));
	}

	public void run() {
		if (headers.isEmpty() && footers.isEmpty()) {
			headers.add(null);
			footers.add(null);
			return;
		}
		if(headers.isEmpty()){
			headers.add(null);
		}
		if(footers.isEmpty()){
			footers.add(null);
		}
		Collection<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers();
		if (players.isEmpty()) {
			return;
		}
		for(ProxiedPlayer p : players){
			if(p.getServer() == null){
				String sheader = headers.get(headercount).replace("%p%", p.getName());
				String sfooter = footers.get(footercount).replace("%p%", p.getName());
				
				BaseComponent[] header = new ComponentBuilder(sheader).create();
				BaseComponent[] footer = new ComponentBuilder(sfooter).create();
				
				p.setTabHeader(header, footer);
			} else {
				String sheader = headers.get(headercount).replace("%p%", p.getName()).replace("%server%", p.getServer().getInfo().getName());
				String sfooter = footers.get(footercount).replace("%p%", p.getName()).replace("%server%", p.getServer().getInfo().getName());
				
				BaseComponent[] header = new ComponentBuilder(sheader).create();
				BaseComponent[] footer = new ComponentBuilder(sfooter).create();

				p.setTabHeader(header, footer);
			}
		}
		
		headercount++;
		footercount++;
		if((headercount + 1) > headers.size()){
			headercount = 0;
		}
		if((footercount + 1) > footers.size()){
			footercount = 0;
		}
	}
	
	public String colorize(String input){
		return input.replace("&", "§");
	}
}