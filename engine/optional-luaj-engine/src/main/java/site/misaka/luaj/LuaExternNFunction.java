package site.misaka.luaj;

import lombok.SneakyThrows;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.script.LuaScriptEngine;

import javax.script.ScriptException;

public class LuaExternNFunction extends TwoArgFunction {
	private final LuaScriptEngine engine;

	public LuaExternNFunction(LuaScriptEngine engine) {
		this.engine = engine;
	}

	public LuaExternNFunction() {
		this.engine = null;
	}

	@SneakyThrows
	@Override
	public LuaValue call(LuaValue arg1, LuaValue arg2) {
		final Class clazz = Class.forName(arg1.checkjstring(1));
		if (this.engine != null) {
			if (this.engine.get(arg2.checkjstring(1)) != null) {
				throw new ScriptException("Global variable " + arg2.checkjstring(1) + " is already defined");
			}
			this.engine.put(arg2.checkjstring(1), CoerceJavaToLua.coerce(clazz));
		}
		return NIL;
	}
}
