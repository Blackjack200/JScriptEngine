package site.misaka.jruby;

import org.jruby.embed.jsr223.JRubyEngine;
import site.misaka.engine.PSREngine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import java.io.Writer;
import java.util.Map;

public class JRubyAdapter extends PSREngine {
	public JRubyAdapter(ScriptEngineFactory factory) {
		super(factory);
	}

	@Override
	public void load(String code, Map<String, Object> variables) {
		JRubyEngine engine = (JRubyEngine) this.factory.getScriptEngine();

		for (Map.Entry<String, Object> entry : variables.entrySet()) {
			engine.put(entry.getKey(), entry.getValue());
		}

		engine.getContext().setErrorWriter(new Writer() {
			@Override
			public void write(char[] buf, int off, int len) {

			}

			@Override
			public void flush() {

			}

			@Override
			public void close() {

			}
		});

		try {
			engine.eval(code);
			this.engineList.add(engine);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void invoke(String name, Object... args) {
		for (ScriptEngine engine : this.engineList) {
			try {
				JRubyEngine eng = ((JRubyEngine) engine);
				eng.invokeFunction(name, args);
			} catch (NoSuchMethodException ignore) {

			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
	}
}