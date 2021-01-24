package site.misaka.luaj;

import lombok.SneakyThrows;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.script.LuaScriptEngine;

import javax.script.ScriptException;

public class LuaExternFunction extends OneArgFunction {
	private final LuaScriptEngine engine;

	public LuaExternFunction(LuaScriptEngine engine) {
		this.engine = engine;
	}

	public LuaExternFunction() {
		this.engine = null;
	}

	@SneakyThrows
	@Override
	public LuaValue call(LuaValue arg) {
		final Class clazz = Class.forName(arg.checkjstring(1), true, ClassLoader.getSystemClassLoader());
		if (this.engine != null) {
			if (this.engine.get(clazz.getSimpleName()) != null) {
				throw new ScriptException("Global variable " + clazz.getSimpleName() + " is already defined");
			}
			this.engine.put(clazz.getSimpleName(), CoerceJavaToLua.coerce(clazz));
		}
		return NIL;
	}
}
