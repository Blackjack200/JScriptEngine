package site.misaka.script.adapter;

import cn.nukkit.plugin.Plugin;
import site.misaka.engine.EngineAdapter;

public class JavaUtils extends AbstractUtils {
	public JavaUtils(Plugin plugin, String scriptName, EngineAdapter adapter) {
		super(plugin, scriptName, adapter);
	}

	public Class<?> getClass(Object object) {
		return object.getClass();
	}

	public Runtime getRuntime() {
		return Runtime.getRuntime();
	}
}
