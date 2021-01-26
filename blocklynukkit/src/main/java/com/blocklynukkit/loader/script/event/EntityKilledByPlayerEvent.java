package com.blocklynukkit.loader.script.event;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
@Deprecated
public class EntityKilledByPlayerEvent extends EntityDamageByEntityEvent {
    public EntityKilledByPlayerEvent(EntityDamageByEntityEvent event){
        super(event.getDamager(),event.getEntity(),event.getCause(),event.getDamage(),event.getKnockBack());
    }
    @Override
    public Player getDamager(){
        Player player = null;
        if(super.getDamager() instanceof Player && (super.getDamager().getNetworkId()==63 || super.getDamager().getNetworkId()==-1)){
            player = Server.getInstance().getPlayer(super.getDamager().getNameTag());
            if(player.equals(null))player = Server.getInstance().getPlayer(super.getDamager().getName());
        }
        return player;
    }
    public Player getPlayer(){
        Player player = null;
        if(getDamager() instanceof Player && (getDamager().getNetworkId()==63 || getDamager().getNetworkId()==-1)){
            player = Server.getInstance().getPlayer(getDamager().getNameTag());
            if(player.equals(null))player = Server.getInstance().getPlayer(getDamager().getName());
        }
        return player;
    }
}
