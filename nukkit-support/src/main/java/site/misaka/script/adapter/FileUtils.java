package site.misaka.script.adapter;

import cn.nukkit.plugin.Plugin;
import lombok.Getter;
import lombok.SneakyThrows;
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
