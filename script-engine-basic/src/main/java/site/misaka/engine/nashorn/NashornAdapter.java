package site.misaka.engine.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import site.misaka.engine.PSREngine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

public class NashornAdapter extends PSREngine {
	public NashornAdapter(ScriptEngineFactory factory) {
		super(factory);
	}

	@Override
	public void invoke(String name, Object... args) {
		for (ScriptEngine engine : this.engineList) {
			try {
				((NashornScriptEngine) engine).invokeFunction(name, args);
			} catch (ScriptException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException ignore) {

			}
		}
	}
}