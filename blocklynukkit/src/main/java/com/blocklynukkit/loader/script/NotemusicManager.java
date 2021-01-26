package com.blocklynukkit.loader.script;

import cn.nukkit.Player;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import com.blocklynukkit.loader.Loader;
import com.blocklynukkit.loader.script.bases.BaseManager;
import com.xxmicloxx.NoteBlockAPI.*;
import site.misaka.engine.EngineAdapter;

import java.io.File;
import java.util.List;

public class NotemusicManager extends BaseManager {

    public NotemusicManager(EngineAdapter scriptEngine) {
        super(scriptEngine);
    }

    @Override
    public String toString() {
        return "BlocklyNukkit Based Object";
    }

    public Song getSongFromFile(String name) {
        return NBSDecoder.parse(new File(Loader.plugin.getDataFolder() + "/notemusic/" + name));
    }

    public String getSongTitle(Song song) {
        return song.getTitle();
    }

    public String getSongDescription(Song song) {
        return song.getDescription();
    }

    public String getSongAuthor(Song song) {
        return song.getAuthor();
    }

    public RadioSongPlayer buildRadio(Song song, boolean isloop, boolean isautodestroy) {
        RadioSongPlayer radioSongPlayer = new RadioSongPlayer(song);
        radioSongPlayer.setLoop(isloop);
        radioSongPlayer.setAutoDestroy(isautodestroy);
        return radioSongPlayer;
    }

    public void addPlayerToRadio(RadioSongPlayer radioSongPlayer, Player player) {
        radioSongPlayer.addPlayer(player);
        NoteBlockPlayerMain.getPlayerVolume(player);
    }

    public void removePlayerToRadio(RadioSongPlayer radioSongPlayer, Player player) {
        radioSongPlayer.removePlayer(player);
    }

    public List getPlayerInRadio(RadioSongPlayer radioSongPlayer) {
        return radioSongPlayer.getPlayerList();
    }

    public void setRadioStatus(RadioSongPlayer radioSongPlayer, boolean isplaying) {
        radioSongPlayer.setPlaying(isplaying);
    }

    public Song getSongInRadio(RadioSongPlayer radioSongPlayer) {
        return radioSongPlayer.getSong();
    }

    public int getSongLength(Song song) {
        return song.getLength();
    }

    public HornSongPlayer buildHorn(Song song, Position pos, boolean isloop, boolean isautodestroy) {
        HornSongPlayer hornSongPlayer = new HornSongPlayer(song);
        hornSongPlayer.setTargetLocation(Location.fromObject(pos, pos.level));
        hornSongPlayer.setLoop(isloop);
        hornSongPlayer.setAutoDestroy(isautodestroy);
        return hornSongPlayer;
    }

    public void addPlayerToHorn(HornSongPlayer SongPlayer, Player player) {
        SongPlayer.addPlayer(player);
        NoteBlockPlayerMain.getPlayerVolume(player);
    }

    public void removePlayerToHorn(HornSongPlayer SongPlayer, Player player) {
        SongPlayer.removePlayer(player);
    }

    public List getPlayerInHorn(HornSongPlayer radioSongPlayer) {
        return radioSongPlayer.getPlayerList();
    }

    public void setHornStatus(HornSongPlayer radioSongPlayer, boolean isplaying) {
        radioSongPlayer.setPlaying(isplaying);
    }

    public Song getSongInHorn(HornSongPlayer radioSongPlayer) {
        return radioSongPlayer.getSong();
    }
}
