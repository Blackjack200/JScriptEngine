package site.misaka.luaj;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.script.LuaScriptEngine;
import org.luaj.vm2.script.LuajContext;
import site.misaka.engine.JSR223Adapter;

public class LuaJAdapter extends JSR223Adapter<LuaScriptEngine> {
    public LuaJAdapter(LuaScriptEngine engine) {
        super(engine);
        this.engine.put("extern", new LuaExternFunction(this.engine));
        this.engine.put("extern_name", new LuaExternNFunction(this.engine));
    }

    @Override
    public void invoke(String name, Object... args) {
        try {
            LuaValue func = ((LuajContext) this.engine.getContext()).globals.get(name);
            LuaValue[] luaArgs = new LuaValue[args.length];
            for (int i = 0; i < luaArgs.length; i++) {
                luaArgs[i] = CoerceJavaToLua.coerce(args[i]);
            }
            func.invoke(luaArgs);
        } catch (LuaError ignore) {

        }
    }
}
