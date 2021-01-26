package com.blocklynukkit.loader.scriptloader.luaj;

import org.luaj.vm2.script.LuaScriptEngine;
import org.luaj.vm2.script.LuaScriptEngineFactory;
import site.misaka.engine.PSREngineProcessor;

import javax.script.ScriptEngine;

public class BNLuaJProcessor extends PSREngineProcessor<LuaScriptEngineFactory, BNLuaJAdapter> {
    public BNLuaJProcessor(LuaScriptEngineFactory factory) {
        super(factory);
    }

    @Override
    protected BNLuaJAdapter createAdapter(ScriptEngine engine) {
        return new BNLuaJAdapter((LuaScriptEngine) engine);
    }
}