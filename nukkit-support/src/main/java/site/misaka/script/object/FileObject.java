package site.misaka.script.object;

import cn.nukkit.plugin.Plugin;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;
import site.misaka.engine.EngineAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class FileObject extends AbstractObject {
	@Getter
	private final String path;

	public FileObject(Plugin plugin, String scriptName, EngineAdapter adapter) {
		super(plugin, scriptName, adapter);
		this.path = plugin.getDataFolder().getAbsoluteFile() + File.separator + scriptName.replace(".", "-") + File.separator;
		File file = new File(this.path);
		file.mkdirs();
	}

	public Object parseYAML(String content) {
		Yaml yaml = new Yaml();
		return yaml.load(content);
	}

	public String emitYAML(Object data) {
		Yaml yaml = new Yaml();
		return yaml.dump(data);
	}

	public Object parseJSON(String content) {
		return JSON.parse(content);
	}

	public String emitJSON(Object data) {
		return JSON.toJSONString(data);
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
			//noinspection ResultOfMethodCallIgnored
			file.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(content);
			out.close();
			return true;
		} catch (Throwable ignore) {
		}
		return false;
	}

	@SneakyThrows
	public boolean touch(String path) {
		File file = new File(this.path + path);
		return file.createNewFile();
	}

	@SneakyThrows
	public boolean mkdir(String path) {
		File file = new File(this.path + path);
		return file.mkdirs();
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	private void realRemove(File file) {
		if (!file.isFile()) {
			try {
				Files.walkFileTree(file.toPath(), new FileVisitor<Path>() {
					@Override
					public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attr) {
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(Path path, BasicFileAttributes attr) {
						path.toFile().delete();
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path path, IOException exception) {
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path path, IOException exception) {
						path.toFile().delete();
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		file.delete();
	}

	public void remove(String path) {
		this.realRemove(new File(this.path + path));
	}
}
