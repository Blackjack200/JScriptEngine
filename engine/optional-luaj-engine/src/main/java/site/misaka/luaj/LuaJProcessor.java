package site.misaka.luaj;

import org.luaj.vm2.script.LuaScriptEngine;
import org.luaj.vm2.script.LuaScriptEngineFactory;
import site.misaka.engine.JSR223Processor;

import javax.script.ScriptEngine;

public class LuaJProcessor extends JSR223Processor<LuaScriptEngineFactory, LuaJAdapter> {

    public LuaJProcessor(LuaScriptEngineFactory factory) {
        super(factory);
    }

    @Override
    protected LuaJAdapter createAdapter(ScriptEngine engine) {
        return new LuaJAdapter((LuaScriptEngine) engine);
    }
}