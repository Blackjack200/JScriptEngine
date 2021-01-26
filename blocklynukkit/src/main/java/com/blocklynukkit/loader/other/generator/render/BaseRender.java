package com.blocklynukkit.loader.other.generator.render;

import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;

public abstract class BaseRender implements Comparable<BaseRender> {
    protected int priority = 0;
    public BaseRender(int priority){
        this.priority = priority;
    }
    public abstract boolean canRend(Level level);
    public abstract void rend(Level level,FullChunk fullChunk);
    @Override
    public int compareTo(BaseRender render){
        return this.priority-render.priority;
    }
}
