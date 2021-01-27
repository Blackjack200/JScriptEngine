package site.misaka.blocklynukkit.script.object;

import cn.nukkit.block.Block;
import cn.nukkit.level.Position;
import cn.nukkit.plugin.Plugin;
import lombok.val;
import lombok.var;
import site.misaka.engine.EngineAdapter;

import java.util.Vector;

public class Algorithm extends AbstractObject {
    public Algorithm(Plugin plugin, String scriptName, EngineAdapter adapter) {
        super(plugin, scriptName, adapter);
    }

    public double variance(double elements[]) {
        val average = this.average(elements);
        var sum = 0D;
        for (var e : elements) {
            sum += Math.pow(e - average, 2);
        }
        return Math.sqrt(sum / elements.length);
    }

    public double average(double elements[]) {
        var sum = 0D;
        for (var e : elements) {
            sum += e;
        }
        return sum / elements.length;
    }

    public static class Action {
        public void invoke(Block block) {

        }
    }

    public void forEachBlockInArea(Position first, Position second, Action action) {
        val minX = Math.min(first.getX(), second.getX());
        val maxX = Math.max(first.getX(), second.getX());
        val minY = Math.min(first.getY(), second.getY());
        val maxY = Math.max(first.getY(), second.getY());
        val minZ = Math.min(first.getZ(), second.getZ());
        val maxZ = Math.max(first.getZ(), second.getZ());
        val level = first.getLevel();

        for (var tempX = minX; tempX < maxX; tempX++) {
            for (var tempY = minY; tempY < maxY; tempY++) {
                for (var tempZ = minZ; tempZ < maxZ; tempZ++) {
                    var block = level.getBlock((int) tempX, (int) tempY, (int) tempZ);
                    action.invoke(block);
                }
            }
        }
    }

    public void forEachBlockInArea(Position first, Position second, boolean air, String callback) {
        this.forEachBlockInArea(first, second, new Action() {
            @Override
            public void invoke(Block block) {
                if (!air && block.getId() == 0) {
                    return;
                }
                getAdapter().invoke(callback, block);
            }
        });
    }

    public void forLinkedBlock(Vector<Block> vector, Position position, String callback) {
        val originBlock = position.getLevelBlock();
        if (vector.contains(originBlock)) {
            return;
        }
        this.forEachBlockInArea(position.add(1D, 1D, 1D), position.add(-1D, -1D, -1D), new Action() {
            @Override
            public void invoke(Block block) {
                if (originBlock.getId() == block.getId()) {
                    getAdapter().invoke(callback, block);
                    vector.add(block);
                    forLinkedBlock(vector, block, callback);
                }
            }
        });
    }

    public void forLinkedBlock(Position position, String callback) {
        this.forLinkedBlock(new Vector<>(), position, callback);
    }
}