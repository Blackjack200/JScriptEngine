package site.misaka.mirai.script;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import lombok.Getter;
import net.mamoe.mirai.event.Event;
import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;
import org.luaj.vm2.script.LuaScriptEngineFactory;
import site.misaka.engine.IEngineProcessor;
import site.misaka.groovy.GroovyProcessor;
import site.misaka.luaj.LuaJProcessor;
import site.misaka.nashorn.NashornProcessor;

import java.util.Vector;

public class EngineFacade {
    @Getter
    private static final Vector<IEngineProcessor> adapters = new Vector<>();
    private static boolean init = false;

    public static void init() {
        if (!init) {
            init = true;
            adapters.add(new NashornProcessor(new NashornScriptEngineFactory()));
            adapters.add(new LuaJProcessor(new LuaScriptEngineFactory()));
            adapters.add(new GroovyProcessor(new GroovyScriptEngineFactory()));
        }
    }

    public static void invokeALL(String funcName, Object... args) {
        for (IEngineProcessor adapter : adapters) {
            adapter.invokeALL(funcName, args);
        }
    }

    public static void invokeEvent(Event ev, String funcName) {
        EngineFacade.invokeALL(funcName, ev);
    }

    public static void invokeEvent(Event ev) {
        invokeEvent(ev, ev.getClass().getSimpleName());
    }
}
