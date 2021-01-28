package site.misaka.quercus;

import com.caucho.quercus.script.QuercusScriptEngine;
import com.caucho.quercus.script.QuercusScriptEngineFactory;
import site.misaka.engine.PSREngineProcessor;

import javax.script.ScriptEngine;

public class QuercusProcessor extends PSREngineProcessor<QuercusScriptEngineFactory, QuercusAdapter> {
	public QuercusProcessor(QuercusScriptEngineFactory factory) {
		super(factory);
	}

	@Override
	protected QuercusAdapter createAdapter(ScriptEngine engine) {
		return new QuercusAdapter((QuercusScriptEngine) engine);
	}
}
