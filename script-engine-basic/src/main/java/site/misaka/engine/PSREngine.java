package site.misaka.engine;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class PSREngine extends EngineAdapter {
	protected ScriptEngineFactory factory;
	protected ArrayList<ScriptEngine> engineList = new ArrayList<>();

	public PSREngine(ScriptEngineFactory factory) {
		this.factory = factory;
	}

	@Override
	public void load(String code, Map<String, Object> variables) {
		ScriptEngine engine = this.factory.getScriptEngine();

		for (Map.Entry<String, Object> entry : variables.entrySet()) {
			engine.put(entry.getKey(), entry.getValue());
		}

		try {
			engine.eval(code);
			this.engineList.add(engine);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> extensions() {
		return this.factory.getExtensions();
	}

	@Override
	public void invoke(String name, Object... args) {
		for (ScriptEngine engine : this.engineList) {
			if (engine instanceof Invocable) {
				try {
					((Invocable) engine).invokeFunction(name, args);
				} catch (Throwable ignore) {

				}
			}
		}
	}
}
