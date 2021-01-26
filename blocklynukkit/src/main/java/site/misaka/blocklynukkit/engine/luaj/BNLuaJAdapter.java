package site.misaka.blocklynukkit.engine.luaj;

import org.luaj.vm2.script.LuaScriptEngine;
import site.misaka.blocklynukkit.engine.EngineFacade;
import site.misaka.blocklynukkit.engine.luaj.function.BNLuaAsList;
import site.misaka.blocklynukkit.engine.luaj.function.BNLuaAsTable;
import site.misaka.blocklynukkit.engine.luaj.function.BNLuaJFN;
import site.misaka.luaj.LuaJAdapter;

public class BNLuaJAdapter extends LuaJAdapter {
    public BNLuaJAdapter(LuaScriptEngine engine) {
        super(engine);
        this.engine.put("asTable", new BNLuaAsTable());
        this.engine.put("asList", new BNLuaAsList());
        this.engine.put("F", new BNLuaJFN());
        //TODO asMap
    }

    @Override
    public void invoke(String name, Object... args) {
        if (EngineFacade.getDirection().get(this).containsKey(name)) {
            name = EngineFacade.getDirection().get(this).get(name);
        }
        super.invoke(name, args);
    }
}