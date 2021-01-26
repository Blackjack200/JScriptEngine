package com.xxmicloxx.NoteBlockAPI;

import cn.nukkit.Player;
import cn.nukkit.Server;
import com.blocklynukkit.loader.Loader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author xxmicloxx @ NoteBlockAPI
 */
public class NoteBlockPlayerMain {

    public static NoteBlockPlayerMain plugin;
    public HashMap<String, ArrayList<SongPlayer>> playingSongs = new HashMap<String, ArrayList<SongPlayer>>();
    public HashMap<String, Byte> playerVolume = new HashMap<String, Byte>();

    public static boolean isReceivingSong(Player p) {
        return ((plugin.playingSongs.get(p.getName()) != null) && (!plugin.playingSongs.get(p.getName()).isEmpty()));
    }

    public static void stopPlaying(Player p) {
        if (plugin.playingSongs.get(p.getName()) == null) {
            return;
        }
        for (SongPlayer s : plugin.playingSongs.get(p.getName())) {
            s.removePlayer(p);
        }
    }

    public static void setPlayerVolume(Player p, byte volume) {
        plugin.playerVolume.put(p.getName(), volume);
    }

    public static byte getPlayerVolume(Player p) {
        Byte b = plugin.playerVolume.get(p.getName());
        if (b == null) {
            b = 100;
            plugin.playerVolume.put(p.getName(), b);
        }
        return b;
    }

    public void onEnable() {
        plugin = this;
    }

    public void onDisable() {
        Server.getInstance().getScheduler().cancelTask(Loader.plugin);
    }
}
