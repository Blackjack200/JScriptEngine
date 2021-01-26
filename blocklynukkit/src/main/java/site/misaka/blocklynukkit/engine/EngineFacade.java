package site.misaka.blocklynukkit.engine;

import cn.nukkit.Server;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import lombok.Getter;
import org.luaj.vm2.script.LuaScriptEngineFactory;
import site.misaka.blocklynukkit.engine.luaj.BNLuaJProcessor;
import site.misaka.blocklynukkit.engine.nashorn.BNNashornProcessor;
import site.misaka.engine.EngineAdapter;
import site.misaka.engine.IEngineProcessor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class EngineFacade {
    @Getter
    private static final Vector<IEngineProcessor> processors = new Vector<>();
    @Getter
    private static final ConcurrentHashMap<EngineAdapter, ConcurrentHashMap<String, String>> direction = new ConcurrentHashMap<>();
    @Getter
    private static final ConcurrentHashMap<String, EngineAdapter> scripts = new ConcurrentHashMap<>();
    private static boolean init = false;

    public static void init() {
        if (!init) {
            init = true;
            processors.add(new BNNashornProcessor(new NashornScriptEngineFactory()));
            processors.add(new BNLuaJProcessor(new LuaScriptEngineFactory()));
        }
    }

    public static Map<String, Object> getProperties(String scriptName) {
        LinkedHashMap<String, Object> properties = new LinkedHashMap<>();
        properties.put("server", Server.getInstance());
        properties.put("__NAME__", scriptName);
        return properties;
    }

    public static void invokeALL(String funcName, Object... args) {
        for (IEngineProcessor adapter : getProcessors()) {
            adapter.invokeALL(funcName, args);
        }
    }

    public static void invokeEvent(cn.nukkit.event.Event ev, String funcName) {
        invokeALL(funcName, ev);
    }

    public static void invokeEvent(cn.nukkit.event.Event ev) {
        invokeEvent(ev, ev.getClass().getSimpleName());
    }
}
