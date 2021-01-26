package com.blocklynukkit.loader.other.generator;

import cn.nukkit.block.Block;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.Position;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.biome.EnumBiome;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import com.blocklynukkit.loader.Loader;

import java.util.HashMap;
import java.util.Map;

public class OceanGenerator extends cn.nukkit.level.generator.Generator {
    private ChunkManager level;
    private NukkitRandom random;
    private Map<String, Object> options;
    public int seaLevel = 64;

    public OceanGenerator() {
        this(new HashMap<>());
    }
    public OceanGenerator(Map<String, Object> options) {
        this.options = options;
        seaLevel= Loader.OceanSeaLevel;
    }
    @Override
    public int getId() {
        return 5;
    }
    @Override
    public void init(ChunkManager chunkManager, NukkitRandom nukkitRandom) {
        this.level = chunkManager;
        this.random = nukkitRandom;
    }
    @Override
    public void generateChunk(int chunkX, int chunkZ) {
        BaseFullChunk chunk = level.getChunk(chunkX, chunkZ);
        for(int x=0; x < 16; x++){
            for(int z=0; z<16; z++){
                chunk.setBlockId(x,1,z, Block.BEDROCK);
                for(int y=2 ;y<seaLevel;y++){
                    chunk.setBlockId(x,y,z,Block.WATER);
                }
            }
        }
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBiome(x, z, EnumBiome.DEEP_OCEAN.biome);
            }
        }
    }
    @Override
    public void populateChunk(int chunkX, int chunkZ) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                this.level.getChunk(chunkX, chunkZ).setBiomeId(x, z, EnumBiome.DEEP_OCEAN.id);
            }
        }
    }
    @Override
    public Map<String, Object> getSettings() {
        return this.options;
    }
    @Override
    public String getName() {
        return "ocean_bn4";
    }
    @Override
    public Vector3 getSpawn() {
        return new Vector3(128.0D, seaLevel+1, 128.0D);
    }
    @Override
    public ChunkManager getChunkManager() {
        return this.level;
    }
}