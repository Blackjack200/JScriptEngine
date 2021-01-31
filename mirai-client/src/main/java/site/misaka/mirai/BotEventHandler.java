package site.misaka.mirai;

import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import site.misaka.mirai.script.EngineFacade;

public class BotEventHandler extends SimpleListenerHost {
    @EventHandler
    public void onMessage(Event event) {
        EngineFacade.invokeEvent(event);
    }
}