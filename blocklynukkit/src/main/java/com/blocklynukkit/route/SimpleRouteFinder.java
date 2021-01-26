package com.blocklynukkit.route;

import cn.nukkit.entity.Entity;

/**
 * @author zzz1999 @ MobPlugin
 */
public class SimpleRouteFinder extends RouteFinder {

    public SimpleRouteFinder(Entity entity) {
        super(entity);
    }

    @Override
    public void setSearchLimit(int limit) {

    }

    @Override
    public boolean search() {
        this.resetNodes();
        this.addNode(new Node(this.destination));
        return WalkerRouteFinder.canPassThroughBlock(entity.level.getBlock(this.destination, false));
    }
}