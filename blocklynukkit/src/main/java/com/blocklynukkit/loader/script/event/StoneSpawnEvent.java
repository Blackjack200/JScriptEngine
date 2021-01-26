package com.blocklynukkit.loader.script.event;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.Cancellable;
import cn.nukkit.level.Position;

public class StoneSpawnEvent extends cn.nukkit.event.Event implements Cancellable {
    public Position pos;
    public Block block;
    public StoneSpawnEvent(Position position,Block block1){
        pos=position;block=block1;
    }
    public Position getPosition(){return pos;}
    public Block getBlock(){return block;}
    @Override
    public void setCancelled(boolean cancelled){
        pos.getLevel().setBlock(pos,Block.get(BlockID.AIR));
    }
}
