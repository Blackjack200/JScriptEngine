package site.misaka.blocklynukkit.engine.luaj;

import org.luaj.vm2.script.LuaScriptEngine;
import org.luaj.vm2.script.LuaScriptEngineFactory;
import site.misaka.engine.JSR223Processor;

import javax.script.ScriptEngine;

public class BNLuaJProcessor extends JSR223Processor<LuaScriptEngineFactory, BNLuaJAdapter> {
    public BNLuaJProcessor(LuaScriptEngineFactory factory) {
        super(factory);
    }

    @Override
    protected BNLuaJAdapter createAdapter(ScriptEngine engine) {
        return new BNLuaJAdapter((LuaScriptEngine) engine);
    }
}
