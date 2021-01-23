package site.misaka.engine.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import site.misaka.engine.PSREngineAdapter;

import javax.script.ScriptException;

public class NashornAdapter extends PSREngineAdapter<NashornScriptEngine> {
	public NashornAdapter(NashornScriptEngine engine) {
		super(engine);
	}

	@Override
	public void invoke(String name, Object... args) {
		try {
			this.engine.invokeFunction(name, args);
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException ignore) {

		}
	}
}
