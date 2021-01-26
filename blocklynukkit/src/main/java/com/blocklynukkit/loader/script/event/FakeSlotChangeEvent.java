package com.blocklynukkit.loader.script.event;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import com.nukkitx.fakeinventories.inventory.FakeInventory;

public class FakeSlotChangeEvent extends Event implements Cancellable {
    public com.nukkitx.fakeinventories.inventory.FakeSlotChangeEvent parent;
    public FakeSlotChangeEvent(com.nukkitx.fakeinventories.inventory.FakeSlotChangeEvent event){
        parent = event;
    }

    public com.nukkitx.fakeinventories.inventory.FakeSlotChangeEvent getParent() {
        return parent;
    }

    public SlotChangeAction getAction(){
        return parent.getAction();
    }

    public FakeInventory getInventory(){
        return parent.getInventory();
    }

    public Player getPlayer(){
        return parent.getPlayer();
    }

    @Override
    public void setCancelled(boolean value) {
        parent.setCancelled(value);
    }

    @Override
    public void setCancelled() {
        parent.setCancelled();
    }

    @Override
    public boolean isCancelled() {
        return parent.isCancelled();
    }
}
