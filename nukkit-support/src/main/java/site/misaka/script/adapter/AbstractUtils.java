package site.misaka.script.adapter;

import cn.nukkit.plugin.Plugin;
import lombok.Getter;

public abstract class AbstractUtils {
	@Getter
	protected final Plugin plugin;
	@Getter
	protected final String scriptName;

	public AbstractUtils(Plugin plugin, String scriptName) {
		this.plugin = plugin;
		this.scriptName = scriptName;
	}
}
