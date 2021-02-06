package site.misaka.script.object;

import cn.nukkit.plugin.Plugin;
import site.misaka.engine.EngineAdapter;

public class JavaObject extends AbstractObject {
    public JavaObject(Plugin plugin, String scriptName, EngineAdapter adapter) {
        super(plugin, scriptName, adapter);
    }

    public Class<?> getClass(Object object) {
        return object.getClass();
    }

    public Runtime getRuntime() {
        return Runtime.getRuntime();
    }
}
