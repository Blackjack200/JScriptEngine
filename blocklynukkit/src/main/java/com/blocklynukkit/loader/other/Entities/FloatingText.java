package com.blocklynukkit.loader.other.Entities;

import cn.nukkit.Player;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import com.blocklynukkit.loader.EngineFacade;
import com.blocklynukkit.loader.Loader;

public class FloatingText extends EntityMob {
    public int CallTick = 20;
    public String CallBack = "FloatingTextUpdate";
    private final int NetWorkId = 61;

    public FloatingText(FullChunk chunk, CompoundTag nbt, int callTick, String callBack) {
        super(chunk, nbt);
        CallTick = callTick;
        CallBack = callBack;
    }

    public float getWidth() {
        return 0.1F;
    }

    public float getLength() {
        return 0.1F;
    }

    public float getHeight() {
        return 0.1F;
    }

    @Override
    public int getNetworkId() {
        return this.NetWorkId;
    }

    @Override
    public void spawnTo(Player player) {
        if (this.chunk != null && !this.closed) {
            super.spawnTo(player);
        }
    }

    @Override
    public void saveNBT() {
        super.saveNBT();
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if (this.closed) {
            return false;
        } else {
            this.timing.startTiming();
            if (currentTick % CallTick == 0) {
                EngineFacade.invokeALL(CallBack, this);
                this.spawnToAll();
            }
            boolean hasUpdate = super.onUpdate(currentTick);
            this.timing.stopTiming();
            return hasUpdate;
        }
    }

    @Override
    public void close() {
        this.setNameTagVisible(false);
        this.setNameTagAlwaysVisible(false);
        super.close();
    }

    @Override
    public String getName() {
        return "BNFloatingText";
    }

}
