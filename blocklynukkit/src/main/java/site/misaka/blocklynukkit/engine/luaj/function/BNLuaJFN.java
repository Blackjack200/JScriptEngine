package site.misaka.blocklynukkit.engine.luaj.function;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class BNLuaJFN extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue arg) {
        if (arg.isfunction()) {
            return arg;
        }
        return LuaValue.NIL;
    }
}
