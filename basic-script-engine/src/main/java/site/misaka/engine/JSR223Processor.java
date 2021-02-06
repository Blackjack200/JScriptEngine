package site.misaka.engine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public abstract class JSR223Processor<FT extends ScriptEngineFactory, ET extends EngineAdapter> extends IEngineProcessor<ET> {
    protected FT factory;
    protected Vector<ET> engineList = new Vector<>();

    public JSR223Processor(FT factory) {
        this.factory = factory;
    }

    protected abstract ET createAdapter(ScriptEngine engine);

    @Override
    public ET process(String code, Map<String, Object> variables) {
        return this.process(code, variables, null);
    }

    @Override
    public ET process(String code, Map<String, Object> variables, Processor processor) {
        ET engine = this.createAdapter(this.factory.getScriptEngine());

        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            engine.put(entry.getKey(), entry.getValue());
        }

        if (processor != null) {
            processor.preprocess(engine);
        }

        if (engine.load(code)) {
            this.engineList.add(engine);
            return engine;
        }
        return null;
    }

    @Override
    public List<String> extensions() {
        return this.factory.getExtensions();
    }

    @Override
    public void invokeALL(String name, Object... args) {
        for (ET engine : this.engineList) {
            engine.invoke(name, args);
        }
    }
}
