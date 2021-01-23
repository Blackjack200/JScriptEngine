package site.misaka.script.adapter;

import cn.nukkit.plugin.Plugin;
import lombok.Getter;
import site.misaka.engine.EngineAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils extends AbstractUtils {
	@Getter
	private final String path;

	public FileUtils(Plugin plugin, String scriptName, EngineAdapter adapter) {
		super(plugin, scriptName, adapter);
		this.path = plugin.getDataFolder().getAbsoluteFile() + File.separator + scriptName.replace(".", "-") + File.separator;
		File file = new File(this.path);
		file.mkdirs();
	}

	public String read(String path) {
		File file = new File(this.path + path);
		try {
			return site.misaka.utils.FileUtils.file_get_content(file);
		} catch (IOException ignore) {
		}
		return null;
	}

	public boolean put(String path, String content) {
		try {
			File file = new File(this.path + path);
			file.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(content);
			out.close();
			return true;
		} catch (Throwable ignore) {
		}
		return false;
	}
}
