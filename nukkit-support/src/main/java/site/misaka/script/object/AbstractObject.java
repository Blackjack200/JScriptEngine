package site.misaka.script.object;

import cn.nukkit.plugin.Plugin;
import lombok.Getter;
import site.misaka.engine.EngineAdapter;

public abstract class AbstractObject {
    @Getter
    protected final Plugin plugin;
    @Getter
    protected final String scriptName;
    @Getter
    protected final EngineAdapter adapter;

    public AbstractObject(Plugin plugin, String scriptName, EngineAdapter adapter) {
        this.plugin = plugin;
        this.scriptName = scriptName;
        this.adapter = adapter;
    }
}
