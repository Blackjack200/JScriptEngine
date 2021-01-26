package com.blocklynukkit.loader.other.generator.render;

import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import com.blocklynukkit.loader.EngineFacade;

public class LevelNameRender extends BaseRender {
    private String forLevel = null;
    private String callback = null;

    public LevelNameRender(String forLevel, String callback) {
        super(0);
        this.forLevel = forLevel;
        this.callback = callback;
    }

    public LevelNameRender(String forLevel, String callback, int priority) {
        super(priority);
        this.forLevel = forLevel;
        this.callback = callback;
    }

    @Override
    public boolean canRend(Level level) {
        return level.getName().equals(forLevel);
    }

    @Override
    public void rend(Level level, FullChunk fullChunk) {
        if (!canRend(level)) return;
        EngineFacade.invokeALL(callback, level, fullChunk);
    }
}
