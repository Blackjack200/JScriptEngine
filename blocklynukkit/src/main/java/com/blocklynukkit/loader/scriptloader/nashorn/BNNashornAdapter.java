package com.blocklynukkit.loader.scriptloader.nashorn;

import com.blocklynukkit.loader.EngineFacade;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import site.misaka.nashorn.NashornAdapter;

import java.util.function.Consumer;

public class BNNashornAdapter extends NashornAdapter {
    public BNNashornAdapter(NashornScriptEngine engine) {
        super(engine);
        this.engine.put("F", (Consumer<String>) this::back);
    }

    private <T> T back(T object) {
        return object;
    }

    @Override
    public void invoke(String name, Object... args) {
        if (EngineFacade.redirect.get(this).containsKey(name)) {
            name = EngineFacade.redirect.get(this).get(name);
        }
        super.invoke(name, args);
    }
}