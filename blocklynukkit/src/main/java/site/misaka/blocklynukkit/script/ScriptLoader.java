package site.misaka.blocklynukkit.script;

import cn.nukkit.utils.Logger;
import site.misaka.blocklynukkit.BlocklyNukkit;
import site.misaka.blocklynukkit.engine.EngineFacade;
import site.misaka.blocklynukkit.script.object.Algorithm;
import site.misaka.blocklynukkit.script.object.BlockItem;
import site.misaka.blocklynukkit.script.object.Function;
import site.misaka.blocklynukkit.script.object.Inventory;
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

public class ScriptLoader {
    public static void loadScript(File file, Logger logger) {
        final String name = file.getName();
        String extension = name.substring(name.lastIndexOf('.') + 1);
        for (IEngineProcessor processor : EngineFacade.getProcessors()) {
            if (processor.extensions().contains(extension)) {
                Thread thread = new Thread(() -> {
                    synchronized (processor) {
                        try {
                            EngineAdapter adapter = (EngineAdapter) processor.process(FileUtils.file_get_content(file), EngineFacade.getProperties(name), engine -> {
                                engine.put("manager", new Function(BlocklyNukkit.getInstance(), name, engine));
                                //noinspection SpellCheckingInspection
                                engine.put("blockitem", new BlockItem(BlocklyNukkit.getInstance(), name, engine));
                                engine.put("algorithm", new Algorithm(BlocklyNukkit.getInstance(), name, engine));
                                engine.put("inventory", new Inventory(BlocklyNukkit.getInstance(), name, engine));
                                //TODO world
                                //TODO entity
                                //TODO database
                                //TODO noteMusic
                                //TODO window
                                //TODO particle
                            });

                            if (adapter != null) {
                                EngineFacade.getScripts().put(name, adapter);
                                logger.info("Load Script: " + name);
                                return;
                            }
                            BlocklyNukkit.getInstance().getLogger().info("Failed to load script: " + name);
                        } catch (IOException e) {
                            logger.error("", e);
                        }
                    }
                });
                thread.start();
                break;
            }
        }
    }

    public static void scanLoader(Path path, Logger logger) throws IOException {
        Files.walkFileTree(path, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attr) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attr) {
                ScriptLoader.loadScript(path.toFile(), logger);
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