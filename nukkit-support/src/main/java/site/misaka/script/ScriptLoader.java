package site.misaka.script;

import site.misaka.NukkitScriptLoader;
import site.misaka.engine.EngineAdapter;
import site.misaka.process.ScriptEngineFacade;
import site.misaka.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class ScriptLoader {
	public static void loadScript(File file) {
		final String name = file.getName();
		String extension = name.substring(name.lastIndexOf('.') + 1);
		for (EngineAdapter adapter : ScriptEngineFacade.getAdapters()) {
			if (adapter.extensions().contains(extension)) {
				Thread thread = new Thread(() -> {
					synchronized (adapter) {
						try {
							adapter.load(FileUtils.file_get_content(file), ScriptEngineFacade.properties(name));
							NukkitScriptLoader.getInstance().getLogger().info("Load Script:" + name);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
				thread.start();
				break;
			}
		}
	}

	public static void scanLoader(Path path) throws IOException {
		Files.walkFileTree(path, new FileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attr) {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes attr) {
				ScriptLoader.loadScript(path.toFile());
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path path, IOException exception) {
				return FileVisitResult.TERMINATE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path path, IOException exception) {
				return FileVisitResult.CONTINUE;
			}
		});
	}
}
