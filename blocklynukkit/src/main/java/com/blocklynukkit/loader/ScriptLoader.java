package com.blocklynukkit.loader;

import com.blocklynukkit.loader.other.BNLogger;
import com.blocklynukkit.loader.script.*;
import site.misaka.engine.EngineAdapter;
import site.misaka.engine.IEngineProcessor;
import site.misaka.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashMap;
import java.util.Map;

public class ScriptLoader {
    public static void loadScript(File file) {
        final String name = file.getName();
        String extension = name.substring(name.lastIndexOf('.') + 1);
        for (IEngineProcessor processor : EngineFacade.getAdapters()) {
            if (processor.extensions().contains(extension)) {
                Thread thread = new Thread(() -> {
                    synchronized (processor) {
                        try {
                            Map<String, Object> map = new LinkedHashMap<>();
                            map.put("server", Loader.plugin.getServer());
                            map.put("plugin", Loader.plugin);
                            EngineAdapter adapter = (EngineAdapter) processor.process(FileUtils.file_get_content(file), map, engine -> {
                                EngineFacade.redirect.put(engine, new LinkedHashMap<>());
                                engine.put("manager", new FunctionManager(engine));
                                engine.put("logger", new BNLogger(name));
                                engine.put("window", new WindowManager(engine));
                                engine.put("blockitem", new BlockItemManager(engine));
                                engine.put("algorithm", new AlgorithmManager(engine));
                                engine.put("inventory", new InventoryManager(engine));
                                engine.put("world", new LevelManager(engine));
                                engine.put("entity", new EntityManager(engine));
                                engine.put("database", new DatabaseManager(engine));
                                engine.put("notemusic", new NotemusicManager(engine));
                                engine.put("particle", new ParticleManager(engine));
                                engine.put("__NAME__", name);
                            });

                            if (adapter != null) {
                                UnionData.scriptPlugins.put(name, adapter);
                                Loader.plugin.getLogger().info("Load Script: " + name);
                                return;
                            }
                            Loader.plugin.getLogger().info("Failed to load script: " + name);
                            EngineFacade.redirect.remove(null);
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

