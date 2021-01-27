package site.misaka.blocklynukkit.script;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import site.misaka.blocklynukkit.engine.EngineFacade;

public class ExtraListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerInteractEvent(cn.nukkit.event.player.PlayerInteractEvent event) {
        switch (event.getAction()) {
            case RIGHT_CLICK_BLOCK:
                EngineFacade.invokeEvent(event, "RightClickBlockEvent");
                break;
            case LEFT_CLICK_BLOCK:
                EngineFacade.invokeEvent(event, "LeftClickBlockEvent");
                break;
            case RIGHT_CLICK_AIR:
                EngineFacade.invokeEvent(event, "ClickOnAirEvent");
                break;
            case PHYSICAL:
                EngineFacade.invokeEvent(event, "PhysicalTouchEvent");
                break;
        }
    }
}