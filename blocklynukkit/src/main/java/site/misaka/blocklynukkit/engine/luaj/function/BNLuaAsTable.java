package site.misaka.blocklynukkit.engine.luaj.function;

import lombok.val;
import lombok.var;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import java.util.Collection;
import java.util.Map;

public class BNLuaAsTable extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue arg) {
        return convert(CoerceLuaToJava.coerce(arg, Object.class));
    }

    public LuaValue convert(Object object) {
        val table = new LuaTable();
        if (object instanceof Collection) {
            var index = 0;
            for (Object next : (Collection<?>) object) {
                table.set(index, convert(next));
                index++;
            }
            return table;
        }
        if (object instanceof Map) {
            for (Map.Entry<?, ?> next : ((Map<?, ?>) object).entrySet()) {
                table.set(convert(next.getKey()), convert(next.getValue()));
            }
            return table;
        }

        if (object instanceof Object[]) {
            var offset = 0;
            for (var value : (Object[]) object) {
                table.set(offset, convert(value));
                offset++;
            }
        }

        if (object instanceof LuaValue) {
            if (((LuaValue) object).istable()) {
                return (LuaValue) object;
            }
        }

        return CoerceJavaToLua.coerce(object);
    }
}
