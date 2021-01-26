package site.misaka.blocklynukkit.engine.luaj.function;

import org.luaj.vm2.LuaNil;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.ArrayList;
import java.util.Arrays;

public class BNLuaAsList extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue arg) {
        if (arg.istable()) {
            return CoerceJavaToLua.coerce(new ArrayList<Object>(Arrays.asList((((LuaTable) arg)).keys())));
        }
        return LuaNil.NIL;
    }
}
