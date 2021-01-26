package com.blocklynukkit.loader.other.generator;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.block.BlockStone;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.biome.BiomeSelector;
import cn.nukkit.level.biome.EnumBiome;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.level.generator.noise.vanilla.f.NoiseGeneratorOctavesF;
import cn.nukkit.level.generator.noise.vanilla.f.NoiseGeneratorPerlinF;
import cn.nukkit.level.generator.object.ore.OreType;
import cn.nukkit.level.generator.populator.impl.PopulatorGroundCover;
import cn.nukkit.level.generator.populator.impl.PopulatorOre;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.MathHelper;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import com.blocklynukkit.loader.Loader;
import com.blocklynukkit.loader.script.LevelManager;

import java.util.*;

public class SkyLand extends Generator {
    private static final float[] biomeWeights = new float[25];

    static {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                biomeWeights[i + 2 + (j + 2) * 5] = (float) (10.0F / Math.sqrt((float) (i * i + j * j) + 0.2F));
            }
        }
    }

    public Map<String, Object> option = new HashMap<>();
    private final List<Populator> populators = new ArrayList<>();
    private final List<Populator> generationPopulators = new ArrayList<>();
    public static int seaHeight = 64;
    public NoiseGeneratorOctavesF scaleNoise;
    public NoiseGeneratorOctavesF depthNoise;
    private ChunkManager level;
    private Random random;
    private NukkitRandom nukkitRandom;
    private long localSeed1;
    private long localSeed2;
    private BiomeSelector selector;
    private ThreadLocal<Biome[]> biomes = ThreadLocal.withInitial(() -> new Biome[10 * 10]);
    private ThreadLocal<float[]> depthRegion = ThreadLocal.withInitial(() -> null);
    private ThreadLocal<float[]> mainNoiseRegion = ThreadLocal.withInitial(() -> null);
    private ThreadLocal<float[]> minLimitRegion = ThreadLocal.withInitial(() -> null);
    private ThreadLocal<float[]> maxLimitRegion = ThreadLocal.withInitial(() -> null);
    private ThreadLocal<float[]> heightMap = ThreadLocal.withInitial(() -> new float[825]);
    private NoiseGeneratorOctavesF minLimitPerlinNoise;
    private NoiseGeneratorOctavesF maxLimitPerlinNoise;
    private NoiseGeneratorOctavesF mainPerlinNoise;
    private NoiseGeneratorPerlinF surfaceNoise;

    private int movey = 0;
    private boolean enableOre = true;
    private int[] coal_option = new int[]{20, 17, 0, 128};
    private int[] iron_option = new int[]{20, 9, 0, 64};
    private int[] redstone_option = new int[]{8, 8, 0, 16};
    private int[] lapis_option = new int[]{1, 7, 0, 10};
    private int[] gold_option = new int[]{2, 9, 0, 32};
    private int[] diamond_option = new int[]{1, 8, 0, 16};
    private int[] dirt_option = new int[]{10, 33, 0, 128};
    private int[] gravel_option = new int[]{8, 33, 0, 128};
    private int[] granite_option = new int[]{10, 33, 0, 80};//花岗岩
    private int[] diorite_option = new int[]{10, 33, 0, 80};//闪长岩
    private int[] andesite_option = new int[]{10, 33, 0, 80};//安山岩
    private boolean enableCave = true;
    private boolean enableBiome = true;
    private boolean enableOcean = false;

    public SkyLand() {
        seaHeight = 64;
    }

    public SkyLand(Map<String, Object> options) {
        this.option = Loader.skyLandOptions;
        if (this.option.keySet().size() < 2) {
            LevelManager.setSkyLandGeneratorStatic(64, 0, true,
                    20, 17, 0, 128, 20, 9, 0, 64,
                    8, 8, 0, 16, 1, 7, 0, 10,
                    2, 9, 0, 32, 1, 8, 0, 16,
                    10, 33, 0, 128, 8, 33, 0, 128,
                    10, 33, 0, 80, 10, 33, 0, 80,
                    10, 33, 0, 80, true, true, false);
            this.option = Loader.skyLandOptions;
        }
        movey = (int) option.get("movey");
        seaHeight = (int) option.get("seaHeight");
        enableOre = (boolean) option.get("enableOre");
        coal_option = new int[]{
                (int) option.get("coal_option_count"),
                (int) option.get("coal_option_size"),
                (int) option.get("coal_option_min"),
                (int) option.get("coal_option_max")
        };
        iron_option = new int[]{
                (int) option.get("iron_option_count"),
                (int) option.get("iron_option_size"),
                (int) option.get("iron_option_min"),
                (int) option.get("iron_option_max")
        };
        redstone_option = new int[]{
                (int) option.get("redstone_option_count"),
                (int) option.get("redstone_option_size"),
                (int) option.get("redstone_option_min"),
                (int) option.get("redstone_option_max")
        };
        lapis_option = new int[]{
                (int) option.get("lapis_option_count"),
                (int) option.get("lapis_option_size"),
                (int) option.get("lapis_option_min"),
                (int) option.get("lapis_option_max")
        };
        gold_option = new int[]{
                (int) option.get("gold_option_count"),
                (int) option.get("gold_option_size"),
                (int) option.get("gold_option_min"),
                (int) option.get("gold_option_max")
        };
        diamond_option = new int[]{
                (int) option.get("diamond_option_count"),
                (int) option.get("diamond_option_size"),
                (int) option.get("diamond_option_min"),
                (int) option.get("diamond_option_max")
        };
        dirt_option = new int[]{
                (int) option.get("dirt_option_count"),
                (int) option.get("dirt_option_size"),
                (int) option.get("dirt_option_min"),
                (int) option.get("dirt_option_max")
        };
        gravel_option = new int[]{
                (int) option.get("gravel_option_count"),
                (int) option.get("gravel_option_size"),
                (int) option.get("gravel_option_min"),
                (int) option.get("gravel_option_max")
        };
        granite_option = new int[]{
                (int) option.get("granite_option_count"),
                (int) option.get("granite_option_size"),
                (int) option.get("granite_option_min"),
                (int) option.get("granite_option_max")
        };
        diorite_option = new int[]{
                (int) option.get("diorite_option_count"),
                (int) option.get("diorite_option_size"),
                (int) option.get("diorite_option_min"),
                (int) option.get("diorite_option_max")
        };
        andesite_option = new int[]{
                (int) option.get("andesite_option_count"),
                (int) option.get("andesite_option_size"),
                (int) option.get("andesite_option_min"),
                (int) option.get("andesite_option_max")
        };
        enableCave = (boolean) option.get("enableCave");
        enableBiome = (boolean) option.get("enableBiome");
        enableOcean = (boolean) option.get("enableOcean");
    }

    @Override
    public int getId() {
        return 4213 + 2;
    }

    @Override
    public ChunkManager getChunkManager() {
        return level;
    }

    @Override
    public String getName() {
        return "skyland_bn3";
    }

    @Override
    public Map<String, Object> getSettings() {
        return option;
    }

    public Biome pickBiome(int x, int z) {
        return this.selector.pickBiome(x, z);
    }

    @Override
    public void init(ChunkManager level, NukkitRandom random) {
        this.level = level;
        this.nukkitRandom = random;
        this.random = new Random();
        this.nukkitRandom.setSeed(this.level.getSeed());
        this.localSeed1 = this.random.nextLong();
        this.localSeed2 = this.random.nextLong();
        this.nukkitRandom.setSeed(this.level.getSeed());
        this.selector = new BiomeSelector(this.nukkitRandom);

        this.minLimitPerlinNoise = new NoiseGeneratorOctavesF(random, 16);
        this.maxLimitPerlinNoise = new NoiseGeneratorOctavesF(random, 16);
        this.mainPerlinNoise = new NoiseGeneratorOctavesF(random, 8);
        this.surfaceNoise = new NoiseGeneratorPerlinF(random, 4);
        this.scaleNoise = new NoiseGeneratorOctavesF(random, 10);
        this.depthNoise = new NoiseGeneratorOctavesF(random, 16);

        //this should run before all other populators so that we don't do things like generate ground cover on bedrock or something
        PopulatorGroundCover cover = new PopulatorGroundCover();
        this.generationPopulators.add(cover);
        //An island in the sky needs no bedrock
        //PopulatorBedrock bedrock = new PopulatorBedrock();
        //this.generationPopulators.add(bedrock);
        if (enableOre) {
            PopulatorOre ores = new PopulatorOre(0, new OreType[]{
                    new OreType(Block.get(BlockID.COAL_ORE), coal_option[0], coal_option[1], coal_option[2], coal_option[3]),
                    new OreType(Block.get(BlockID.IRON_ORE), iron_option[0], iron_option[1], iron_option[2], iron_option[3]),
                    new OreType(Block.get(BlockID.REDSTONE_ORE), redstone_option[0], redstone_option[1], redstone_option[2], redstone_option[3]),
                    new OreType(Block.get(BlockID.LAPIS_ORE), lapis_option[0], lapis_option[1], lapis_option[2], lapis_option[3]),
                    new OreType(Block.get(BlockID.GOLD_ORE), gold_option[0], gold_option[1], gold_option[2], gold_option[3]),
                    new OreType(Block.get(BlockID.DIAMOND_ORE), diamond_option[0], diamond_option[1], diamond_option[2], diamond_option[3]),
                    new OreType(Block.get(BlockID.DIRT), dirt_option[0], dirt_option[1], dirt_option[2], dirt_option[3]),
                    new OreType(Block.get(BlockID.GRAVEL), gravel_option[0], gravel_option[1], gravel_option[2], gravel_option[3]),
                    new OreType(Block.get(BlockID.STONE, BlockStone.GRANITE), granite_option[0], granite_option[1], granite_option[2], granite_option[3]),
                    new OreType(Block.get(BlockID.STONE, BlockStone.DIORITE), diorite_option[0], diorite_option[1], diorite_option[2], diorite_option[3]),
                    new OreType(Block.get(BlockID.STONE, BlockStone.ANDESITE), andesite_option[0], andesite_option[1], andesite_option[2], andesite_option[3])
            });
            this.populators.add(ores);
        }

        if (enableCave) {
            PopulatorCavesNoLava caves = new PopulatorCavesNoLava();
            this.populators.add(caves);
        }
        //TODO: fix ravines
        //PopulatorRavines ravines = new PopulatorRavines();
        //this.populators.add(ravines);
    }

    @Override
    public void generateChunk(final int chunkX, final int chunkZ) {
        int baseX = chunkX << 4;
        int baseZ = chunkZ << 4;
        this.nukkitRandom.setSeed(chunkX * localSeed1 ^ chunkZ * localSeed2 ^ this.level.getSeed());

        BaseFullChunk chunk = level.getChunk(chunkX, chunkZ);

        //generate base noise values
        float[] depthRegion = this.depthNoise.generateNoiseOctaves(this.depthRegion.get(), chunkX * 4, chunkZ * 4, 5, 5, 200f, 200f, 0.5f);
        this.depthRegion.set(depthRegion);
        float[] mainNoiseRegion = this.mainPerlinNoise.generateNoiseOctaves(this.mainNoiseRegion.get(), chunkX * 4, 0, chunkZ * 4, 5, 33, 5, 684.412f / 60f, 684.412f / 160f, 684.412f / 60f);
        this.mainNoiseRegion.set(mainNoiseRegion);
        float[] minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion.get(), chunkX * 4, 0, chunkZ * 4, 5, 33, 5, 684.412f, 684.412f, 684.412f);
        this.minLimitRegion.set(minLimitRegion);
        float[] maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion.get(), chunkX * 4, 0, chunkZ * 4, 5, 33, 5, 684.412f, 684.412f, 684.412f);
        this.maxLimitRegion.set(maxLimitRegion);
        float[] heightMap = this.heightMap.get();

        //generate heightmap and smooth biome heights
        int horizCounter = 0;
        int vertCounter = 0;
        for (int xSeg = 0; xSeg < 5; ++xSeg) {
            for (int zSeg = 0; zSeg < 5; ++zSeg) {
                float heightVariationSum = 0.0F;
                float baseHeightSum = 0.0F;
                float biomeWeightSum = 0.0F;
                Biome biome = pickBiome(baseX + (xSeg * 4), baseZ + (zSeg * 4));

                for (int xSmooth = -2; xSmooth <= 2; ++xSmooth) {
                    for (int zSmooth = -2; zSmooth <= 2; ++zSmooth) {
                        Biome biome1 = pickBiome(baseX + (xSeg * 4) + xSmooth, baseZ + (zSeg * 4) + zSmooth);
                        float baseHeight = biome1.getBaseHeight();
                        float heightVariation = biome1.getHeightVariation();

                        float scaledWeight = biomeWeights[xSmooth + 2 + (zSmooth + 2) * 5] / (baseHeight + 2.0F);

                        if (biome1.getBaseHeight() > biome.getBaseHeight()) {
                            scaledWeight /= 2.0F;
                        }

                        heightVariationSum += heightVariation * scaledWeight;
                        baseHeightSum += baseHeight * scaledWeight;
                        biomeWeightSum += scaledWeight;
                    }
                }

                heightVariationSum = heightVariationSum / biomeWeightSum;
                baseHeightSum = baseHeightSum / biomeWeightSum;
                heightVariationSum = heightVariationSum * 0.9F + 0.1F;
                baseHeightSum = (baseHeightSum * 4.0F - 1.0F) / 8.0F;
                float depthNoise = depthRegion[vertCounter] / 8000.0f;

                if (depthNoise < 0.0f) {
                    depthNoise = -depthNoise * 0.3f;
                }

                depthNoise = depthNoise * 3.0f - 2.0f;

                if (depthNoise < 0.0f) {
                    depthNoise = depthNoise / 2.0f;

                    if (depthNoise < -1.0f) {
                        depthNoise = -1.0f;
                    }

                    depthNoise = depthNoise / 1.4f;
                    depthNoise = depthNoise / 2.0f;
                } else {
                    if (depthNoise > 1.0f) {
                        depthNoise = 1.0f;
                    }

                    depthNoise = depthNoise / 8.0f;
                }

                ++vertCounter;
                float baseHeightClone = baseHeightSum;
                float heightVariationClone = heightVariationSum;
                baseHeightClone = baseHeightClone + depthNoise * 0.2f;
                baseHeightClone = baseHeightClone * 8.5f / 8.0f;
                float baseHeightFactor = 8.5f + baseHeightClone * 4.0f;

                for (int ySeg = 0; ySeg < 33; ++ySeg) {
                    float baseScale = ((float) ySeg - baseHeightFactor) * 12f * 128.0f / 256.0f / heightVariationClone;

                    if (baseScale < 0.0f) {
                        baseScale *= 4.0f;
                    }

                    float minScaled = minLimitRegion[horizCounter] / 512f;
                    float maxScaled = maxLimitRegion[horizCounter] / 512f;
                    float noiseScaled = (mainNoiseRegion[horizCounter] / 10.0f + 1.0f) / 2.0f;
                    float clamp = MathHelper.denormalizeClamp(minScaled, maxScaled, noiseScaled) - baseScale;

                    if (ySeg > 29) {
                        float yScaled = ((float) (ySeg - 29) / 3.0F);
                        clamp = clamp * (1.0f - yScaled) + -10.0f * yScaled;
                    }

                    heightMap[horizCounter] = clamp;
                    ++horizCounter;
                }
            }
        }

        //place blocks
        for (int xSeg = 0; xSeg < 4; ++xSeg) {
            int xScale = xSeg * 5;
            int xScaleEnd = (xSeg + 1) * 5;

            for (int zSeg = 0; zSeg < 4; ++zSeg) {
                int zScale1 = (xScale + zSeg) * 33;
                int zScaleEnd1 = (xScale + zSeg + 1) * 33;
                int zScale2 = (xScaleEnd + zSeg) * 33;
                int zScaleEnd2 = (xScaleEnd + zSeg + 1) * 33;

                for (int ySeg = 0; ySeg < 32; ++ySeg) {
                    double height1 = heightMap[zScale1 + ySeg];
                    double height2 = heightMap[zScaleEnd1 + ySeg];
                    double height3 = heightMap[zScale2 + ySeg];
                    double height4 = heightMap[zScaleEnd2 + ySeg];
                    double height5 = (heightMap[zScale1 + ySeg + 1] - height1) * 0.125f;
                    double height6 = (heightMap[zScaleEnd1 + ySeg + 1] - height2) * 0.125f;
                    double height7 = (heightMap[zScale2 + ySeg + 1] - height3) * 0.125f;
                    double height8 = (heightMap[zScaleEnd2 + ySeg + 1] - height4) * 0.125f;

                    for (int yIn = 0; yIn < 8; ++yIn) {
                        double baseIncr = height1;
                        double baseIncr2 = height2;
                        double scaleY = (height3 - height1) * 0.25f;
                        double scaleY2 = (height4 - height2) * 0.25f;

                        for (int zIn = 0; zIn < 4; ++zIn) {
                            double scaleZ = (baseIncr2 - baseIncr) * 0.25f;
                            double scaleZ2 = baseIncr - scaleZ;

                            for (int xIn = 0; xIn < 4; ++xIn) {
                                int ypos = ySeg * 8 + yIn;
                                if ((scaleZ2 += scaleZ) > 0.0f && ypos >= seaHeight) {
                                    ypos += movey;
                                    chunk.setBlockId(xSeg * 4 + zIn, ypos, zSeg * 4 + xIn, STONE);
                                    chunk.setBlockId(xSeg * 4 + zIn, (2 * seaHeight - ypos) > 0 ? (2 * seaHeight - ypos) : 1, zSeg * 4 + xIn, STONE);
                                } else if (ypos <= seaHeight) {
                                    if (enableOcean)
                                        if (chunk.getBlockIdAt(xSeg * 4 + zIn, ypos, zSeg * 4 + xIn) == Block.AIR) {
                                            chunk.setBlockId(xSeg * 4 + zIn, ySeg * 8 + yIn, zSeg * 4 + xIn, STILL_WATER);
                                        }
                                }
                            }

                            baseIncr += scaleY;
                            baseIncr2 += scaleY2;
                        }

                        height1 += height5;
                        height2 += height6;
                        height3 += height7;
                        height4 += height8;
                    }
                }
            }
        }

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Biome biome = selector.pickBiome(baseX | x, baseZ | z);

                chunk.setBiome(x, z, biome);
            }
        }

        //populate chunk
        for (Populator populator : this.generationPopulators) {
            populator.populate(this.level, chunkX, chunkZ, this.nukkitRandom, chunk);
        }
    }

    @Override
    public void populateChunk(int chunkX, int chunkZ) {
        BaseFullChunk chunk = level.getChunk(chunkX, chunkZ);
        this.nukkitRandom.setSeed(0xdeadbeef ^ ((long) chunkX << 8) ^ chunkZ ^ this.level.getSeed());
        for (Populator populator : this.populators) {
            populator.populate(this.level, chunkX, chunkZ, this.nukkitRandom, chunk);
        }
        if (enableBiome) {
            Biome biome = EnumBiome.getBiome(chunk.getBiomeId(7, 7));
            biome.populateChunk(this.level, chunkX, chunkZ, this.nukkitRandom);
        }
    }

    @Override
    public Vector3 getSpawn() {
        return new Vector3(0.5, 80, 0.5);
    }
}
