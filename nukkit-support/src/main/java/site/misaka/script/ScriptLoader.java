package site.misaka.script;

import site.misaka.Loader;
import site.misaka.engine.EngineAdapter;
import site.misaka.engine.IEngineProcessor;
import site.misaka.engine.Processor;
import site.misaka.process.ScriptEngineFacade;
import site.misaka.process.UnionData;
import site.misaka.script.adapter.*;
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
		for (IEngineProcessor processor : ScriptEngineFacade.getAdapters()) {
			if (processor.extensions().contains(extension)) {
				Thread thread = new Thread(() -> {
					synchronized (processor) {
						try {
							EngineAdapter adapter = (EngineAdapter) processor.process(FileUtils.file_get_content(file), UnionData.getProperties(name), new Processor() {
								@Override
								public void preprocess(EngineAdapter engine) {
									engine.put("ds", new DataStructureUtils(Loader.getInstance(), name, engine));
									engine.put("file", new site.misaka.script.adapter.FileUtils(Loader.getInstance(), name, engine));
									engine.put("parse", new ParseUtils(Loader.getInstance(), name, engine));
									engine.put("command", new CommandUtils(Loader.getInstance(), name, engine));
									engine.put("str", new StringUtils(Loader.getInstance(), name, engine));
									engine.put("thread", new ThreadUtils(Loader.getInstance(), name, engine));
									engine.put("__java__", new JavaUtils(Loader.getInstance(), name, engine));
								}
							});

							if (adapter != null) {
								UnionData.getScripts().put(name, adapter);
								Loader.getInstance().getLogger().info("Load Script: " + name);
								return;
							}
							Loader.getInstance().getLogger().info("Failed to load script: " + name);
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
