package site.misaka.groovy;

import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import site.misaka.engine.PSREngine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

public class GroovyAdapter extends PSREngine {
	public GroovyAdapter(ScriptEngineFactory factory) {
		super(factory);
	}

	@Override
	public void invoke(String name, Object... args) {
		for (ScriptEngine engine : this.engineList) {
			try {
				((GroovyScriptEngineImpl) engine).invokeFunction(name, args);
			} catch (ScriptException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException ignore) {

			}
		}
	}
}