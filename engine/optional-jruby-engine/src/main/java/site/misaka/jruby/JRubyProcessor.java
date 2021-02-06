package site.misaka.jruby;

import org.jruby.embed.jsr223.JRubyEngine;
import org.jruby.embed.jsr223.JRubyEngineFactory;
import site.misaka.engine.JSR223Processor;
import site.misaka.engine.Processor;

import javax.script.ScriptEngine;
import java.io.Writer;
import java.util.Map;

public class JRubyProcessor extends JSR223Processor<JRubyEngineFactory, JRubyAdapter> {

    public JRubyProcessor(JRubyEngineFactory factory) {
        super(factory);
    }

    @Override
    protected JRubyAdapter createAdapter(ScriptEngine engine) {
        return new JRubyAdapter((JRubyEngine) engine);
    }

    @Override
    public JRubyAdapter process(String code, Map<String, Object> variables) {
        return this.process(code, variables, null);
    }

    @Override
    public JRubyAdapter process(String code, Map<String, Object> variables, Processor processor) {
        JRubyAdapter engine = this.createAdapter(this.factory.getScriptEngine());
        engine.get().getContext().setErrorWriter(new Writer() {
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
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            engine.put(entry.getKey(), entry.getValue());
        }

        if (processor != null) {
            processor.preprocess(engine);
        }

        this.engineList.add(engine);
        engine.load(code);
        return engine;
    }
}