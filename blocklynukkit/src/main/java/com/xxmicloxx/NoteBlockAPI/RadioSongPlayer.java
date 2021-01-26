package com.xxmicloxx.NoteBlockAPI;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;

/**
 * @author xxmicloxx @ NoteBlockAPI
 */
public class RadioSongPlayer extends SongPlayer {

    public RadioSongPlayer(Song song) {
        super(song);
    }

    @Override
    public void playTick(Player p, int tick) {
        byte playerVolume = NoteBlockPlayerMain.getPlayerVolume(p);

        for (Layer l : song.getLayerHashMap().values()) {
            Note note = l.getNote(tick);
            if (note == null) {
                continue;
            }
            Position po = p.getPosition();
            p.getLevel().addSound(new Vector3(po.x, po.y, po.z),
                    Instrument.getInstrument(note.getInstrument()),
                    ((l.getVolume() * (int) volume * (int) playerVolume) / 1000000f),
                    NotePitch.getPitch(note.getKey() - 33)
            );
        }
    }
}
