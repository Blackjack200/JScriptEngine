package site.misaka.mirai.script.object;

import lombok.Getter;
import site.misaka.engine.EngineAdapter;

public abstract class AbstractObject {
    @Getter
    protected final String path;
    @Getter
    protected final String scriptName;
    @Getter
    protected final EngineAdapter adapter;

    public AbstractObject(String path, String scriptName, EngineAdapter adapter) {
        this.path = path;
        this.scriptName = scriptName;
        this.adapter = adapter;
    }
}
