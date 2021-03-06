package site.misaka.engine;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public abstract class JSR223Adapter<T extends ScriptEngine> extends EngineAdapter<T> {
    public JSR223Adapter(T engine) {
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

    public Object getValue(String key) {
        return this.engine.get(key);
    }

    @Override
    public T get() {
        return this.engine;
    }
}