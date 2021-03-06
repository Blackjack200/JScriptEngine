package site.misaka.mirai.script;

import site.misaka.engine.EngineAdapter;
import site.misaka.engine.IEngineProcessor;
import site.misaka.mirai.BotServer;
import site.misaka.mirai.Launcher;
import site.misaka.mirai.script.object.FileObject;
import site.misaka.mirai.script.object.InternalObject;
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
        for (IEngineProcessor processor : EngineFacade.getAdapters()) {
            if (processor.extensions().contains(extension)) {
                Thread thread = new Thread(() -> {
                    synchronized (processor) {
                        try {
                            Thread.currentThread().setContextClassLoader(Launcher.class.getClassLoader());
                            EngineAdapter adapter = (EngineAdapter) processor.process(FileUtils.file_get_content(file), UnionData.getProperties(name), engine -> {
                                engine.put("file", new FileObject(BotServer.getInstance().getPath() + "/scripts", name, engine));
                                engine.put("internal", new InternalObject(BotServer.getInstance().getPath() + "/scripts", name, engine));
                            });

                            if (adapter != null) {
                                BotServer.getInstance().getLogger().info("Load Script: " + name);
                                UnionData.getScripts().put(name, adapter);
                                return;
                            }
                            BotServer.getInstance().getLogger().info("Failed to load script: " + name);
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