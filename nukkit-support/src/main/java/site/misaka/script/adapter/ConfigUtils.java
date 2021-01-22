package site.misaka.script.adapter;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import lombok.Getter;

import java.io.File;

public class ConfigUtils extends AbstractUtils {
	@Getter
	private final String path;

	public ConfigUtils(Plugin plugin, String scriptName) {
		super(plugin, scriptName);
		this.path = plugin.getDataFolder().getAbsoluteFile() + File.separator + scriptName.replace(".", "-") + File.separator;
		File file = new File(this.path);
		file.mkdirs();
	}

	public Config create(String fileName) {
		return new Config(this.path + fileName);
	}

	public Config create(String fileName, int type) {
		return new Config(this.path + fileName, type);
	}
}
