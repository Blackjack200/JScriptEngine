package site.misaka.blocklynukkit.script.object;

import cn.nukkit.block.Block;
import cn.nukkit.level.Position;
import cn.nukkit.level.particle.DestroyBlockParticle;
import cn.nukkit.plugin.Plugin;
import site.misaka.engine.EngineAdapter;

public class Particle extends AbstractObject {
    public Particle(Plugin plugin, String scriptName, EngineAdapter adapter) {
        super(plugin, scriptName, adapter);
    }

    //TODO public void drawCircle(Position pos, double radius, int id, double sep)

    //TODO public void drawLine(Position pos1, Position pos2, double sep, int id)

    //TODO public void drawFireWork(Position position, int colorID, boolean flick, boolean trail, int shape, int second)

    public void drawBlockBreak(Position pos, Block block) {
        pos.level.addParticle(new DestroyBlockParticle(pos, block));
    }

    //TODO public void drawParticleFactoryMcFunction(String fun, Position pos, double turn)
}
