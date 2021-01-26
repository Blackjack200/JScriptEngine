package com.blocklynukkit.loader;

import com.blocklynukkit.loader.scriptloader.luaj.BNLuaJProcessor;
import com.blocklynukkit.loader.scriptloader.nashorn.BNNashornProcessor;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.luaj.vm2.script.LuaScriptEngineFactory;
import site.misaka.engine.EngineAdapter;
import site.misaka.engine.IEngineProcessor;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EngineFacade {
    private static final ArrayList<IEngineProcessor> adapters = new ArrayList<>();
    private static boolean init = false;
    public static final LinkedHashMap<EngineAdapter, LinkedHashMap<String, String>> redirect = new LinkedHashMap<>();

    public static void init() {
        if (!init) {
            init = true;
            adapters.add(new BNNashornProcessor(new NashornScriptEngineFactory()));
            adapters.add(new BNLuaJProcessor(new LuaScriptEngineFactory()));
            Loader.plugin.getServer().getPluginManager().registerEvents(new EventLoader(), Loader.plugin);
        }
    }

    public static void invokeALL(String funcName, Object... args) {
        for (IEngineProcessor adapter : adapters) {
            adapter.invokeALL(funcName, args);
        }
    }

    public static ArrayList<IEngineProcessor> getAdapters() {
        return adapters;
    }

    public static void invokeEvent(cn.nukkit.event.Event ev, String funcName) {
        invokeALL(funcName, ev);
    }

    public static void invokeEvent(cn.nukkit.event.Event ev) {
        invokeEvent(ev, ev.getClass().getSimpleName());
    }
}
