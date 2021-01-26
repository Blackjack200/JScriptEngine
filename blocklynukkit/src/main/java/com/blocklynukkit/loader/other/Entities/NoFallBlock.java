package com.blocklynukkit.loader.other.Entities;

import cn.nukkit.entity.item.EntityFallingBlock;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class NoFallBlock extends EntityFallingBlock {
    public boolean canplace = true;

    public NoFallBlock(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    public NoFallBlock(FullChunk chunk, CompoundTag nbt, boolean canplace) {
        super(chunk, nbt);
        this.canplace = canplace;
    }

    @Override
    public float getGravity() {
        return 0f;
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if (canplace) {
            return super.onUpdate(currentTick);
        } else {
            return !this.isClosed();
        }
    }
}
