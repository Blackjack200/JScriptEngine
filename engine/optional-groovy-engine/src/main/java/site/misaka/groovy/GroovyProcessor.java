package site.misaka.groovy;

import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import site.misaka.engine.PSREngineProcessor;

import javax.script.ScriptEngine;

public class GroovyProcessor extends PSREngineProcessor<GroovyScriptEngineFactory, GroovyAdapter> {

	public GroovyProcessor(GroovyScriptEngineFactory factory) {
		super(factory);
	}

	@Override
	protected GroovyAdapter createAdapter(ScriptEngine engine) {
		return new GroovyAdapter((GroovyScriptEngineImpl) this.factory.getScriptEngine());
	}
}