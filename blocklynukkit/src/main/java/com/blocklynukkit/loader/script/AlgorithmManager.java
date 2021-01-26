package com.blocklynukkit.loader.script;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import com.blocklynukkit.loader.EngineFacade;
import com.blocklynukkit.loader.Loader;
import com.blocklynukkit.loader.script.bases.BaseManager;
import site.misaka.engine.EngineAdapter;

public class AlgorithmManager extends BaseManager {
    public AlgorithmManager(EngineAdapter scriptEngine) {
        super(scriptEngine);
    }

    public void forEachBlockInArea(Position a, Position b, boolean isair, String callback) {
        Level level = a.level;
        for (int i = (int) a.x; i < (int) b.x; i++) {
            for (int j = (int) a.y; i < (int) b.y; j++) {
                for (int k = (int) a.z; i < (int) b.z; k++) {
                    if (level.getBlockIdAt(i, j, k) == 0) {
                        if (isair) {
                            EngineFacade.invokeALL(callback, level.getBlock(i, j, k));
                        }
                    } else {
                        EngineFacade.invokeALL(callback, level.getBlock(i, j, k));
                    }
                }
            }
        }
    }

    public void forLinkedBlock(Position a, String callback) {
        Loader.positionsTemp = "";
        forLinkedBlock((int) a.x, (int) a.y, (int) a.z, callback, 0, a.level, a.getLevelBlock().getId());
    }


    public static String posinttostr(int x, int y, int z) {
        return x + "," + y + "," + z + ";";
    }

    public void forLinkedBlock(int x, int y, int z, String callback, int step, Level level, int id) {
        if (level.getBlockIdAt(x, y, z) == 0) return;
        if (step > 50) return;
        EngineFacade.invokeALL(callback, Position.fromObject(new Vector3(x, y, z), level));
        Loader.positionsTemp += posinttostr(x, y, z);
        if (level.getBlockIdAt(x, y + 1, z) == id && (!Loader.positionsTemp.contains(posinttostr(x, y + 1, z))))
            forLinkedBlock(x, y + 1, z, callback, step + 1, level, id);
        if (level.getBlockIdAt(x, y - 1, z) == id && (!Loader.positionsTemp.contains(posinttostr(x, y - 1, z))))
            forLinkedBlock(x, y - 1, z, callback, step + 1, level, id);
        if (level.getBlockIdAt(x + 1, y, z) == id && (!Loader.positionsTemp.contains(posinttostr(x + 1, y, z))))
            forLinkedBlock(x + 1, y, z, callback, step + 1, level, id);
        if (level.getBlockIdAt(x - 1, y, z) == id && (!Loader.positionsTemp.contains(posinttostr(x - 1, y, z))))
            forLinkedBlock(x - 1, y, z, callback, step + 1, level, id);
        if (level.getBlockIdAt(x, y, z + 1) == id && (!Loader.positionsTemp.contains(posinttostr(x, y, z + 1))))
            forLinkedBlock(x, y, z + 1, callback, step + 1, level, id);
        if (level.getBlockIdAt(x, y, z - 1) == id && (!Loader.positionsTemp.contains(posinttostr(x, y, z - 1))))
            forLinkedBlock(x, y, z - 1, callback, step + 1, level, id);
    }

    public Position buildPositionfromPlayer(Player player) {
        return player.getPosition();
    }

    public Position buildPositionfromBlock(Block block) {
        return (Position) block;
    }

    public Position buildPositionfromEntity(Entity entity) {
        return (Position) entity;
    }

    @Override
    public String toString() {
        return "BlocklyNukkit Based Object";
    }
}
