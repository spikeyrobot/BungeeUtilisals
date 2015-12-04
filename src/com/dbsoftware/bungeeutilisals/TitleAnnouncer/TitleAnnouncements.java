package com.dbsoftware.bungeeutilisals.TitleAnnouncer;

import java.io.File;

import com.dbsoftware.bungeeutilisals.Managers.FileManager;

public class TitleAnnouncements {
    private static String path = File.separator + "plugins" + File.separator + "BungeeUtilisals" + File.separator + "titleannouncer.yml";
    public static FileManager titleannouncements = new FileManager( path );

    public static void reloadAnnouncements() {
        titleannouncements = null;
        titleannouncements = new FileManager( path );
        TitleAnnouncer.reloadAnnouncements();
    }
}