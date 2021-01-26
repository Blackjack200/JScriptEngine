package com.blocklynukkit.loader.script.event;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
@Deprecated
public class PlayerDamageByEntityEvent extends EntityDamageByEntityEvent {
    public PlayerDamageByEntityEvent(EntityDamageByEntityEvent event){
        super(event.getDamager(),event.getEntity(),event.getCause(),event.getDamage(),event.getKnockBack());
    }
    public Player getPlayer(){
        Player player = null;
        if(getEntity() instanceof Player && (getEntity().getNetworkId()==63 || getEntity().getNetworkId()==-1)){
            player = Server.getInstance().getPlayer(getEntity().getNameTag());
            if(player.equals(null))player = Server.getInstance().getPlayer(getEntity().getName());
        }
        return player;
    }
}
