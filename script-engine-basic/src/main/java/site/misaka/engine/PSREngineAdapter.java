package site.misaka.engine;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public abstract class PSREngineAdapter<T extends ScriptEngine> extends EngineAdapter<T> {
	public PSREngineAdapter(T engine) {
		super(engine);
	}

	@Override
	public void put(String name, Object val) {
		this.engine.put(name, val);
	}

	@Override
	public boolean load(String code) {
		try {
			this.engine.eval(code);
		} catch (ScriptException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public T get() {
		return this.engine;
	}
}