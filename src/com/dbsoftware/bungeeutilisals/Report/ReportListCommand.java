package com.dbsoftware.bungeeutilisals.Report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.dbsoftware.bungeeutilisals.Managers.FileManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReportListCommand extends Command {

	public ReportListCommand() {
		super("reports");{
		}
	}
		
	public FileManager getReports(){
		return Reports.reports;
	}
	
	Integer[] numbers = new Integer[]{1,2,3,4,5,6,7,8,9};
	String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
			"Z", "&", "|", "�", "@", "'", "#", "(", "�", "^", "�", "!", "�", "{",
			"�", "}", ")", "�", "-", "_", "^", "�", "$", "*", "�", "%", "�", "�", "`",
			"[", "]", "�", "+", "=", "~", ":", ";", ".", ",", "?", ">", "<"};

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer)){
			sender.sendMessage(new TextComponent("Only the Console can use this Command!"));
			return;
		}
		ProxiedPlayer p = (ProxiedPlayer)sender;
		if(!p.hasPermission("butilisals.reportlist") && !p.hasPermission("butilisals.*")){
			p.sendMessage(TextComponent.fromLegacyText(getReports().getString("Reports.Messages.NoPermission", "&cYou don't have the permission to use the Report Command!").replace("&", "�")));
			return;
		}
		if(args.length != 1){
			p.sendMessage(TextComponent.fromLegacyText(getReports().getString("Reports.Messages.WrongListArgs", "&cPlease use /reports (page)!").replace("&", "�")));
			return;
		}
		
		String spage = args[0];
		for(String s : this.letters){
			spage = spage.replace(s, "");
			spage = spage.replace(s.toLowerCase(), "");
		}
		
		int page = 0;
		if(!spage.isEmpty()){
			page = Integer.parseInt(spage) - 1;
		}
				
		List<Integer> numbers = new ArrayList<Integer>();
		for(int n : this.numbers){
			if(ReportAPI.getReportNumbers().size() > (n + (page * 9))){
				numbers.add(ReportAPI.getReportNumbers().get(n + (page * 9)));
			}
		}
		if(!numbers.isEmpty()){
			if(numbers.size() != 9 && ReportAPI.getReportNumbers().contains(Collections.max(numbers) + 1)){
				numbers.add(Collections.max(numbers) + 1);
			}
			if(numbers.size() != 9 && ReportAPI.getReportNumbers().contains(Collections.max(numbers) + 1)){
				numbers.add(Collections.max(numbers) + 1);
			}
			if(numbers.size() != 9 && ReportAPI.getReportNumbers().contains(Collections.max(numbers) + 1)){
				numbers.add(Collections.max(numbers) + 1);
			}
			if(numbers.size() != 9 && ReportAPI.getReportNumbers().contains(Collections.max(numbers) + 1)){
				numbers.add(Collections.max(numbers) + 1);
			}
			if(numbers.size() != 9 && ReportAPI.getReportNumbers().contains(Collections.max(numbers) + 1)){
				numbers.add(Collections.max(numbers) + 1);
			}
			if(numbers.size() != 9 && ReportAPI.getReportNumbers().contains(Collections.max(numbers) + 1)){
				numbers.add(Collections.max(numbers) + 1);
			}
			if(numbers.size() != 9 && ReportAPI.getReportNumbers().contains(Collections.max(numbers) + 1)){
				numbers.add(Collections.max(numbers) + 1);
			}
			if(numbers.size() != 9 && ReportAPI.getReportNumbers().contains(Collections.max(numbers) + 1)){
				numbers.add(Collections.max(numbers) + 1);
			}
			if(numbers.size() != 9 && ReportAPI.getReportNumbers().contains(Collections.max(numbers) + 1)){
				numbers.add(Collections.max(numbers) + 1);
			}
			if(numbers.size() != 9 && ReportAPI.getReportNumbers().contains(Collections.max(numbers) + 1)){
				numbers.add(Collections.max(numbers) + 1);
			}
			if(numbers.size() != 9 && ReportAPI.getReportNumbers().contains(Collections.max(numbers) + 1)){
				numbers.add(Collections.max(numbers) + 1);
			}
		} else {
			p.sendMessage(TextComponent.fromLegacyText(getReports().getString("Reports.Messages.NoReportsOnThisPage", "&cThere are no reports on this page!!").replace("&", "�")));
			return;
		}
		
		for(int i : numbers){
			String reporter = ReportAPI.getReporter(i);
			String reported = ReportAPI.getReportedPlayer(i);
			String reason = ReportAPI.getReason(i);
			
			TextComponent message = new TextComponent(getReports().getString("Reports.Messages.ReportListFormat",
					"&bReport &6%number%&b) &6%player% &bhas reported &6%reported% &bfor &6%reason%&b!")
					.replace("&", "�").replace("%number%", i + "").replace("%player%", reporter).replace("%reported%", reported).replace("%reason%", reason));
			
			TextComponent join = new TextComponent(" �8�l[�a�l>�8�l]");
			ProxiedPlayer pl = ProxyServer.getInstance().getPlayer(reported);
			if(pl == null){
				join.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, reported + " is not online anymore!"));
			} else {
				join.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/server " + pl.getServer().getInfo().getName()));
			}
			TextComponent remove = new TextComponent(" �8�l[�c�lX�8�l]");
			remove.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/report remove " + i));
			
			message.addExtra(join);
			message.addExtra(remove);
			
			p.sendMessage(message);
		}
	}

}
