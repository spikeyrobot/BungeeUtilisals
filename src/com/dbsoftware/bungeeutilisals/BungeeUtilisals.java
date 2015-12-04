package com.dbsoftware.bungeeutilisals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.craftminecraft.bungee.bungeeyaml.pluginapi.ConfigurablePlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import com.dbsoftware.bungeeutilisals.ActionBarAnnouncer.ActionBarAnnouncer;
import com.dbsoftware.bungeeutilisals.Announcer.Announcer;
import com.dbsoftware.bungeeutilisals.Commands.AlertCommand;
import com.dbsoftware.bungeeutilisals.Commands.BUtilisalsCommand;
import com.dbsoftware.bungeeutilisals.Commands.BigAlertCommand;
import com.dbsoftware.bungeeutilisals.Commands.ClearChatCommand;
import com.dbsoftware.bungeeutilisals.Commands.FindCommand;
import com.dbsoftware.bungeeutilisals.Commands.GListCommand;
import com.dbsoftware.bungeeutilisals.Commands.HubCommand;
import com.dbsoftware.bungeeutilisals.Commands.RulesCommand;
import com.dbsoftware.bungeeutilisals.Commands.ServerCommand;
import com.dbsoftware.bungeeutilisals.Commands.VoteCommand;
import com.dbsoftware.bungeeutilisals.Events.AntiAd;
import com.dbsoftware.bungeeutilisals.Events.AntiCaps;
import com.dbsoftware.bungeeutilisals.Events.AntiCurse;
import com.dbsoftware.bungeeutilisals.Events.AntiSpam;
import com.dbsoftware.bungeeutilisals.Events.ChatLock;
import com.dbsoftware.bungeeutilisals.Events.ChatUtilities;
import com.dbsoftware.bungeeutilisals.Events.DisconnectEvent;
import com.dbsoftware.bungeeutilisals.Events.LoginEvent;
import com.dbsoftware.bungeeutilisals.Events.MessageLimiter;
import com.dbsoftware.bungeeutilisals.Events.ServerSwitch;
import com.dbsoftware.bungeeutilisals.Friends.Friends;
import com.dbsoftware.bungeeutilisals.Managers.DatabaseManager;
import com.dbsoftware.bungeeutilisals.Metrics.Metrics;
import com.dbsoftware.bungeeutilisals.Report.Reports;
import com.dbsoftware.bungeeutilisals.StaffChat.StaffChat;
import com.dbsoftware.bungeeutilisals.Tasks.TabUpdateTask;
import com.dbsoftware.bungeeutilisals.TitleAnnouncer.TitleAnnouncer;
import com.dbsoftware.bungeeutilisals.Updater.UpdateChecker;

/**
 * 
 * @author Dieter
 *
 */

public class BungeeUtilisals
	extends ConfigurablePlugin implements Listener {
	public String alertprefix;
	public static BungeeUtilisals instance;
    public static DatabaseManager dbmanager;
	  public boolean chatMuted = false;
	  public static int maxCapsPercentage;
	  public static int minLength;
	  static String upperCase;
	  public static ArrayList<String> chatspam = new ArrayList<String>();
	  public static int currentLine = 0;
	  public static boolean found = false;	  
	  private final static Pattern ipPattern = Pattern.compile("((?<![0-9])(?:(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[.,-:; ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[., ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[., ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))(?![0-9]))");
	  static Pattern webPattern = Pattern.compile("(http://)|(https://)?(www)?\\S{2,}((\\.com)|(\\.ru)|(\\.net)|(\\.au)|(\\.org)|(\\.me)|(\\.bz)|(\\.co\\.uk)|(\\.tk)|(\\.info)|(\\.es)|(\\.de)|(\\.arpa)|(\\.edu)|(\\.earth)|(\\.ly)|(\\.li)|(\\.firm)|(\\.int)|(\\.mil)|(\\.mobi)|(\\.nato)|(\\.to)|(\\.fr)|(\\.ms)|(\\.vu)|(\\.eu)|(\\.nl)|(\\.us)|(\\.dk)|(\\.biz)|(\\,com)|(\\,ru)|(\\,net)|(\\,org)|(\\,me)|(\\,bz)|(\\,co\\,uk)|(\\,tk)|(\\,au)|(\\,earth)|(\\,info)|(\\,es)|(\\,de)|(\\,arpa)|(\\,edu)|(\\,ly)|(\\,li)|(\\,firm)|(\\,int)|(\\,mil)|(\\,mobi)|(\\,nato)|(\\,to)|(\\,fr)|(\\,ms)|(\\,vu)|(\\,eu)|(\\,nl)|(\\,us)|(\\,dk)|(\\,biz))");

	  public Map<ProxiedPlayer, String> norepeat = new HashMap<ProxiedPlayer, String>();
	  public Map<ServerInfo, Boolean> online = new HashMap<ServerInfo, Boolean>();
	  public Map<ProxiedPlayer, Integer> messagelimiter = new HashMap<ProxiedPlayer, Integer>();
	  public static boolean update;
	  public static ArrayList<ScheduledTask> TabTasks = new ArrayList<>();

	public void onEnable(){
		instance = this;
	    saveDefaultConfig();
	    
	    loadCommands();
	    registerEvents();
	    Announcer.loadAnnouncements();
	    TitleAnnouncer.loadAnnouncements();
	    ActionBarAnnouncer.loadAnnouncements();
	    StaffChat.registerStaffChat();
	    
	    alertprefix = getConfig().getString("Alert_Prefix");
	    maxCapsPercentage = getConfig().getInt("AntiCaps.Max_Percentage");
	    minLength = getConfig().getInt("AntiCaps.Min_Length");
	    upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    chatMuted = false;
	    
	    if(getConfig().getBoolean("UpdateChecker")){
	    	UpdateChecker.checkUpdate(instance.getDescription().getVersion());
	    	UpdateRunnable();
	    }
	    
	    try {
	        Metrics metrics = new Metrics(this);
	        metrics.start();
	    } catch (IOException e) {
	        ProxyServer.getInstance().getLogger().info("[BungeeUtilisals] Metrics could not be enabled.");
	    }
	    
	    loadTabPart();
	    
	    if(getConfig().getBoolean("MySQL.Enabled")){
	    	String host = getConfig().getString("MySQL.Host", "localhost");
			int port = getConfig().getInt("MySQL.Port", 3306);
			String database = getConfig().getString("MySQL.Database", "database");
			String username = getConfig().getString("MySQL.Username", "username");
			String password = getConfig().getString("MySQL.Password", "password");
			dbmanager = new DatabaseManager(host, port, database, username, password);
			dbmanager.openConnection();
			
		    Friends.registerFriendsAddons();
		    Reports.registerReportSystem();
			
			ProxyServer.getInstance().getScheduler().schedule(BungeeUtilisals.instance, new Runnable(){

				@Override
				public void run() {
					if(!dbmanager.isConnected()){
						dbmanager.openConnection();
					}
				}
				
			}, 10, TimeUnit.MINUTES);	
	    }
	    
	    ProxyServer.getInstance().getLogger().info("BungeeUtilisals is now Enabled!");
	}
	
    public static DatabaseManager getDatabaseManager(){
    	return dbmanager;
    }
	
	private void loadCommands(){
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new AlertCommand());
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new BigAlertCommand());
	    BigAlertCommand.onStartup();
	    if(getConfig().getBoolean("GList.Enabled")){
	    	ProxyServer.getInstance().getPluginManager().registerCommand(this, new GListCommand());
	    }
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new BUtilisalsCommand());
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new ServerCommand());
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new FindCommand());
	    
	    if(getConfig().getBoolean("ClearChat.Enabled")){
	    	ProxyServer.getInstance().getPluginManager().registerCommand(this, new ClearChatCommand());
	    }
	    if(getConfig().getBoolean("Hub.Enabled")){
	    	String cmd = getConfig().getString("Hub.Command");
	    	if(cmd.contains(";")){
	    		String c = cmd.split(";")[0];
	    		String ncmd = cmd.replace(c + ";", "");
	    		String[] cmds;
	    		if(ncmd.contains(";")){
	    			cmds = ncmd.split(";");
	    		} else {
	    			cmds = new String[]{ncmd};
	    		}
	    		
	    		ProxyServer.getInstance().getPluginManager().registerCommand(this, new HubCommand(c, cmds));
	    	} else {
		    	ProxyServer.getInstance().getPluginManager().registerCommand(this, new HubCommand(cmd));
	    	}
	    }
	    if(getConfig().getBoolean("Vote.Enabled")){
	    	ProxyServer.getInstance().getPluginManager().registerCommand(this, new VoteCommand());
	    }
	    if(getConfig().getBoolean("Rules.Enabled")){
	    	ProxyServer.getInstance().getPluginManager().registerCommand(this, new RulesCommand());
	    }
	}
	
	private void loadTabPart(){
		if(!getConfig().getBoolean("Tab.Enabled")){
			return;
		}
		
        TabUpdateTask tab = new TabUpdateTask();
        for ( String headers : getConfig().getStringList("Tab.Header") ) {
            tab.addHeader( headers );
        }
        for ( String footers : getConfig().getStringList("Tab.Footer") ) {
            tab.addFooter( footers );
        }
        
        int interval = getConfig().getInt("Tab.UpdateInterval");
		
        ScheduledTask t = ProxyServer.getInstance().getScheduler().schedule( instance, tab, interval, interval, TimeUnit.MILLISECONDS );
        TabTasks.add(t);
	}
	
	public void reloadTabPart(){
        for ( ScheduledTask task : TabTasks) {
            task.cancel();
        }
        TabTasks.clear();
        loadTabPart();
	}
	
	private void UpdateRunnable(){
	    ProxyServer.getInstance().getScheduler().schedule(instance, new Runnable(){
			public void run() {
				UpdateChecker.checkUpdate(instance.getDescription().getVersion());
			}
	    }, 15, 15, TimeUnit.MINUTES);
	}
	
	private void registerEvents(){
	    ProxyServer.getInstance().getPluginManager().registerListener(this, new MessageLimiter(this));
	    ProxyServer.getInstance().getPluginManager().registerListener(this, new DisconnectEvent(this));
	    ProxyServer.getInstance().getPluginManager().registerListener(this, new LoginEvent(this));
	    ProxyServer.getInstance().getPluginManager().registerListener(this, new AntiSpam(this));
	    ProxyServer.getInstance().getPluginManager().registerListener(this, new ChatLock(this));
	    ProxyServer.getInstance().getPluginManager().registerListener(this, new ChatUtilities(this));
	    ProxyServer.getInstance().getPluginManager().registerListener(this, new AntiCurse(this));
	    ProxyServer.getInstance().getPluginManager().registerListener(this, new AntiCaps(this));
	    ProxyServer.getInstance().getPluginManager().registerListener(this, new AntiAd(this));
	    ProxyServer.getInstance().getPluginManager().registerListener(this, new ServerSwitch(this));
	}
	
	public void onDisable(){
	    norepeat.clear();
	    chatspam.clear();
	    messagelimiter.clear();
	    online.clear();
	    found = false;
	    currentLine = 0;
	    chatspam.clear();
	    chatMuted = false;
	    dbmanager.closeConnection();
	    	    
	    ProxyServer.getInstance().getLogger().info("BungeeUtilisals is now Disabled!");
	}
	

  public static double getUppercasePercentage(String string)
  {
    double upperCase = 0.0D;
    for (int i = 0; i < string.length(); i++) {
      if (isUppercase(string.substring(i, i + 1))) {
        upperCase += 1.0D;
      }
    }
    return upperCase / string.length() * 100.0D;
  }
  
  public static boolean isUppercase(String string)
  {
    return upperCase.contains(string);
  }
  
  public static boolean isIPorURL(String word)
  {
    Matcher searchforips = ipPattern.matcher(word.toLowerCase());
    Matcher searchforweb = webPattern.matcher(word.toLowerCase());
    if ((searchforips.find()) || (searchforweb.find())) {
      return true;
    }
    return false;
  }
	
	public static BungeeUtilisals getInstance(){
		return instance;
	}
}
