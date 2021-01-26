package com.blocklynukkit.loader.other.Blocks;

import cn.nukkit.block.BlockSolid;
import cn.nukkit.item.Item;

public abstract class BaseSolidBlock extends BlockSolid {
    public abstract int getTier();

    @Override
    public Item[] getDrops(Item item) {
        System.out.println(this);
        if (item.getTier() >= this.getTier()) {
            if (this.getToolType() == 0
                    || (this.getToolType() == 1 && item.isSword())
                    || (this.getToolType() == 2 && item.isShovel())
                    || (this.getToolType() == 3 && item.isPickaxe())
                    || (this.getToolType() == 4 && item.isAxe())
                    || (this.getToolType() == 5 && item.isShears())) {
                return new Item[]{Item.get(this.getId())};
            }
        }
        return new Item[]{Item.get(0)};
    }
}
