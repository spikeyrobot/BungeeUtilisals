package com.dbsoftware.bungeeutilisals.Announcer;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.dbsoftware.bungeeutilisals.BungeeUtilisals;
import com.dbsoftware.bungeeutilisals.Tasks.GlobalAnnouncements;
import com.dbsoftware.bungeeutilisals.Tasks.ServerAnnouncements;

public class Announcer {
    public static ArrayList<ScheduledTask> announcementTasks = new ArrayList<>();
    static ProxyServer proxy = ProxyServer.getInstance();
    private static BungeeUtilisals instance = BungeeUtilisals.getInstance();

    public static void loadAnnouncements() {
        setDefaults();
        if ( Announcements.announcements.getBoolean("Announcements.Enabled", true) ) {
            List<String> global = Announcements.announcements.getStringList( "Announcements.Global.Messages", new ArrayList<String>() );
            if ( !global.isEmpty() ) {
            	if(Announcements.announcements.getBoolean( "Announcements.Global.Enabled", true)){
                int interval = Announcements.announcements.getInt( "Announcements.Global.Interval", 0 );
                if ( interval > 0 ) {
                    GlobalAnnouncements g = new GlobalAnnouncements();
                    for ( String messages : global ) {
                        g.addAnnouncement( messages );
                    }
                    ScheduledTask t = proxy.getScheduler().schedule( instance, g, interval, interval, TimeUnit.SECONDS );
                    announcementTasks.add( t );
                	}
            	}
            }
            for ( String server : proxy.getServers().keySet() ) {
                List<String> servermessages = Announcements.announcements.getStringList( "Announcements." + server + ".Messages", new ArrayList<String>() );
                if ( !servermessages.isEmpty() ) {
                	if(Announcements.announcements.getBoolean( "Announcements." + server + ".Enabled", true)){
                    int interval = Announcements.announcements.getInt( "Announcements." + server + ".Interval", 0 );
                    if ( interval > 0 ) {
                        ServerAnnouncements s = new ServerAnnouncements( proxy.getServerInfo( server ));
                        for ( String messages : servermessages ) {
                            s.addAnnouncement( messages );
                        }
                        ScheduledTask t = proxy.getScheduler().schedule( instance, s, interval, interval, TimeUnit.SECONDS );
                        announcementTasks.add( t );
                    	}
                	}
                }
            }
        }
    }

    private static void setDefaults() {
        List<String> check = Announcements.announcements.getConfigurationSection( "Announcements" );
        if ( !check.contains( "Enabled" ) ) {
            Announcements.announcements.setBoolean("Announcements.Enabled", true);
        }
        if ( !check.contains( "Global" ) ) {
            Announcements.announcements.setBoolean("Announcements.Global.Enabled", true);
            Announcements.announcements.setInt( "Announcements.Global.Interval", 150 );
            List<String> l = new ArrayList<String>();
            l.add( "&a&lWelcome to our network!" );
            l.add( "&aThis server is using BungeeUtilisals." );
            l.add( "&aDon't forget to take a little look at our website!" );
            Announcements.announcements.setStringList( "Announcements.Global.Messages", l );
        }
        for ( String server : proxy.getServers().keySet() ) {
            if ( !check.contains( server ) ) {
                Announcements.announcements.setBoolean("Announcements." + server + ".Enabled", true);
                Announcements.announcements.setInt( "Announcements." + server + ".Interval", 75 );
                List<String> l = new ArrayList<String>();
                l.add( "&aHello Everyone, &eWelcome to the &a" + server + " &eserver!" );
                l.add( "&aThis server is using BungeeUtilisals!" );
                Announcements.announcements.setStringList( "Announcements." + server + ".Messages", l );
            }
        }
    }

    public static void reloadAnnouncements() {
        for ( ScheduledTask task : announcementTasks ) {
            task.cancel();
        }
        announcementTasks.clear();
        loadAnnouncements();
    }
}