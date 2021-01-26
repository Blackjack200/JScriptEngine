package site.misaka.engine;

public abstract class EngineAdapter<T> {
    protected T engine;

    public EngineAdapter(T engine) {
        this.engine = engine;
    }

    public abstract void invoke(String name, Object... args);

    public abstract void put(String name, Object val);

    public abstract Object get(String key);

    public abstract boolean load(String code);

    public abstract T get();
}