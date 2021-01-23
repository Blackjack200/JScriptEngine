package site.misaka.script.adapter;

import cn.nukkit.plugin.Plugin;
import lombok.Getter;
import site.misaka.engine.EngineAdapter;

public abstract class AbstractUtils {
	@Getter
	protected final Plugin plugin;
	@Getter
	protected final String scriptName;
	@Getter
	protected final EngineAdapter adapter;

	public AbstractUtils(Plugin plugin, String scriptName, EngineAdapter adapter) {
		this.plugin = plugin;
		this.scriptName = scriptName;
		this.adapter = adapter;
	}
}
