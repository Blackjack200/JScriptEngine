package com.xxmicloxx.NoteBlockAPI;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

/**
 * @author xxmicloxx @ NoteBlockAPI
 */
public class SongEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final SongPlayer song;

    public SongEndEvent(SongPlayer song) {
        this.song = song;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }

    public SongPlayer getSongPlayer() {
        return song;
    }
}
