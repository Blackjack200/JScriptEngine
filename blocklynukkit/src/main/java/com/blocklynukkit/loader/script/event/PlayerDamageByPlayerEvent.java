package com.blocklynukkit.loader.script.event;

import cn.nukkit.Player;
import cn.nukkit.Server;
@Deprecated
public class PlayerDamageByPlayerEvent extends EntityDamageByPlayerEvent {
    public PlayerDamageByPlayerEvent(EntityDamageByPlayerEvent event){
        super(event);
    }
    @Override
    public Player getPlayer(){
        Player player = null;
        if(getEntity() instanceof Player && (getEntity().getNetworkId()==63 || getEntity().getNetworkId()==-1)){
            player = Server.getInstance().getPlayer(getEntity().getNameTag());
            if(player.equals(null))player = Server.getInstance().getPlayer(getEntity().getName());
        }
        return player;
    }
    @Override
    public Player getDamager(){
        Player player = null;
        if(getDamager() instanceof Player && (getDamager().getNetworkId()==63 || getDamager().getNetworkId()==-1)){
            player = Server.getInstance().getPlayer(getDamager().getNameTag());
            if(player.equals(null))player = Server.getInstance().getPlayer(getDamager().getName());
        }
        return player;
    }
}
