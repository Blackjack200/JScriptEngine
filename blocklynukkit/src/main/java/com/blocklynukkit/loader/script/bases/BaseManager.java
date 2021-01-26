package com.blocklynukkit.loader.script.bases;

import site.misaka.engine.EngineAdapter;

public class BaseManager {
    public EngineAdapter scriptEngine;

    public BaseManager(EngineAdapter scriptEngine) {
        this.scriptEngine = scriptEngine;
    }

    public String getScriptName() {
        return null;
    }
}
