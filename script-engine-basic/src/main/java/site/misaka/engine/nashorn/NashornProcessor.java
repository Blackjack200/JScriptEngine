package site.misaka.engine.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import site.misaka.engine.PSREngineProcessor;

import javax.script.ScriptEngine;

public class NashornProcessor extends PSREngineProcessor<NashornScriptEngineFactory, NashornAdapter> {
	public NashornProcessor(NashornScriptEngineFactory factory) {
		super(factory);
	}

	@Override
	protected NashornAdapter createAdapter(ScriptEngine engine) {
		return new NashornAdapter((NashornScriptEngine) engine);
	}
}