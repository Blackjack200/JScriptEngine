package com.blocklynukkit.loader.other.generator;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;

import java.util.HashMap;
import java.util.Map;

public class VoidGenerator extends Generator {
    private ChunkManager level;
    private NukkitRandom random;
    private Map<String, Object> options;

    public VoidGenerator() {
        this(new HashMap<>());
    }
    public VoidGenerator(Map<String, Object> options) {
        this.options = options;
    }
    @Override
    public int getId() {
        return 4;
    }
    @Override
    public void init(ChunkManager chunkManager, NukkitRandom nukkitRandom) {
        this.level = chunkManager;
        this.random = nukkitRandom;
    }
    @Override
    public void generateChunk(int chunkX, int chunkZ) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {

            }
        }
    }
    @Override
    public void populateChunk(int chunkX, int chunkZ) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                this.level.getChunk(chunkX, chunkZ).setBiomeId(x, z, Biome.AIR);
            }
        }
    }
    @Override
    public Map<String, Object> getSettings() {
        return this.options;
    }
    @Override
    public String getName() {
        return "void_bn2";
    }
    @Override
    public Vector3 getSpawn() {
        return new Vector3(128.0D, 68.0D, 128.0D);
    }
    @Override
    public ChunkManager getChunkManager() {
        return this.level;
    }
}