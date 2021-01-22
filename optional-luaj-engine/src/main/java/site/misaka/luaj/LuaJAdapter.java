package site.misaka.luaj;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.script.LuajContext;
import site.misaka.engine.PSREngine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class LuaJAdapter extends PSREngine {
	public LuaJAdapter(ScriptEngineFactory factory) {
		super(factory);
	}

	@Override
	public void invoke(String name, Object... args) {
		for (ScriptEngine engine : this.engineList) {
			try {
				LuaValue func = ((LuajContext) engine.getContext()).globals.get(name);
				LuaValue[] luaArgs = new LuaValue[args.length];
				for (int i = 0; i < luaArgs.length; i++) {
					luaArgs[i] = CoerceJavaToLua.coerce(args[i]);
				}
				func.invoke(luaArgs);
			} catch (LuaError ignore) {

			}
		}
	}
}