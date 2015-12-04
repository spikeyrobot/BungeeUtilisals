package com.dbsoftware.bungeeutilisals.Announcer;

import java.io.File;

import com.dbsoftware.bungeeutilisals.Managers.FileManager;

public class Announcements {
    private static String path = File.separator + "plugins" + File.separator + "BungeeUtilisals" + File.separator + "announcer.yml";
    public static FileManager announcements = new FileManager( path );

    public static void reloadAnnouncements() {
        announcements = null;
        announcements = new FileManager( path );
        Announcer.reloadAnnouncements();
    }
}