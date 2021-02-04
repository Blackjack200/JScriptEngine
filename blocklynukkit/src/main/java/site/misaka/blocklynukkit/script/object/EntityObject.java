package site.misaka.blocklynukkit.script.object;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.Sound;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.potion.Effect;
import site.misaka.engine.EngineAdapter;

public class EntityObject extends AbstractObject {
    public EntityObject(Plugin plugin, String scriptName, EngineAdapter adapter) {
        super(plugin, scriptName, adapter);
    }

    @Deprecated
    public Item getDropItemStack(EntityItem entityItem) {
        return entityItem.getItem();
    }

    @Deprecated
    public void removeEntity(Entity entity) {
        entity.kill();
    }

    @Deprecated
    public void setEntityName(Entity entity, String name) {
        entity.setNameTag(name);
    }

    @Deprecated
    public void setEntityNameTagAlwaysVisible(Entity entity, boolean visible) {
        entity.setNameTagAlwaysVisible(visible);
        entity.setNameTagVisible(visible);
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Deprecated
    public void setEntityNameTagAlwaysVisable(Entity entity, boolean visible) {
        setEntityNameTagAlwaysVisible(entity, visible);
    }

    @Deprecated
    public void setEntityHealth(Entity entity, float health) {
        entity.setHealth(health);
    }

    @Deprecated
    public void setEntityMaxHealth(Entity entity, int health) {
        entity.setMaxHealth(health);
    }

    @Deprecated
    public float getEntityHealth(Entity entity) {
        return entity.getHealth();
    }

    @Deprecated
    public float getEntityMaxHealth(Entity entity) {
        return entity.getMaxHealth();
    }

    @Deprecated
    public void clearEntityEffect(Entity entity) {
        entity.removeAllEffects();
    }

    @Deprecated
    public Effect getEffect(int id, int amplifier, int duration, boolean visible, boolean ambient) {
        return Effect.getEffect(id)
                .setAmplifier(amplifier)
                .setDuration(duration)
                .setVisible(visible)
                .setAmbient(ambient);
    }

    @Deprecated
    public void addEntityEffect(Entity entity, int id, int amplifier, int duration, boolean visible, boolean ambient, int r, int g, int b) {
        Effect effect = this.getEffect(id, amplifier, duration, visible, ambient);
        effect.setColor(r, g, b);
        entity.addEffect(effect);
    }

    @Deprecated
    public void addEntityEffect(Entity entity, int id, int amplifier, int duration) {
        entity.addEffect(this.getEffect(id, amplifier, duration, true, false));
    }

    @Deprecated
    public void setPlayerExp(Player player, int exp) {
        player.setExperience(exp);
    }

    @Deprecated
    public int getPlayerExp(Player player) {
        return player.getExperience();
    }

    @Deprecated
    public void setPlayerExpLevel(Player player, int level) {
        player.sendExperienceLevel(level);
    }

    @Deprecated
    public int getPlayerExpLevel(Player player) {
        return player.getExperienceLevel();
    }

    @Deprecated
    public void setPlayerHunger(Player player, int hunger) {
        player.getFoodData().setLevel(hunger);
    }

    @Deprecated
    public int getPlayerHunger(Player player) {
        return player.getFoodData().getLevel();
    }

    @Deprecated
    public String getEntityID(Entity entity) {
        return String.valueOf(entity.getId());
    }

    @Deprecated
    public Entity getEntityByLevelAndID(Level level, String id) {
        return level.getEntity(Long.parseLong(id));
    }

    @Deprecated
    public Level getEntityLevel(Entity entity) {
        return entity.getLevel();
    }

    @Deprecated
    public String getEntityName(Entity entity) {
        return entity.getNameTag();
    }

    @Deprecated
    public Position getEntityPosition(Entity entity) {
        return entity.getPosition();
    }

    @Deprecated
    public void setEntityPosition(Entity entity, Position position) {
        entity.teleport(position);
    }

    //TODO public Entity buildFloatingText(String text, Position pos, int tick, String callback)

    public void startDisplayFloatingText(Entity entity) {
        if (entity.getNetworkId() != 61) {
            return;
        }
        this.setEntityNameTagAlwaysVisible(entity, true);
        entity.getLevel().addEntity(entity);
        entity.spawnToAll();
    }

    //TODO public FloatingText[] getLevelFloatingText(Level level)

    //TODO public static void recycleAllFloatingText()

    //TODO public static void recycleAllBNNPC()

    @Deprecated
    public Effect[] getEntityEffect(Entity entity) {
        return entity.getEffects().values().toArray(new Effect[0]);
    }

    @Deprecated
    public int getEffectLevel(Effect effect) {
        return effect.getAmplifier();
    }

    @Deprecated
    public int getEffectID(Effect effect) {
        return effect.getId();
    }

    @Deprecated
    public int getEffectTime(Effect effect) {
        return effect.getDuration();
    }

    @Deprecated
    public int getNetworkID(Entity entity) {
        return entity.getNetworkId();
    }

    @Deprecated
    public String getIDName(Entity entity) {
        if (entity.getNetworkId() == 63) {
            return "Player";
        }
        return entity.getName();
    }

    @Deprecated
    public Entity spawnEntity(String name, Position position) {
        Level level = position.level;
        Entity entity = Entity.createEntity(
                name,
                level.getChunk(
                        ((int) position.x) >> 4,
                        ((int) position.z) >> 4
                ),
                Entity.getDefaultNBT(position));
        level.addEntity(entity);
        entity.spawnToAll();
        return entity;
    }

    //TODO public HumanSlapper buildNPC(Position position String name, Skin skinID)

    //TODO public HumanSlapper buildNPC(Position position, String name, Skin skinID, int callTick, String callFunction)

    //TODO public HumanSlapper buildNPC(Position position String name, String skinID, int callTick, String callfunction, String attackFunction)

    //TODO public void showFloatingItem(Position position Item item)

    //TODO public void removeFloatingItem(Position position Item item

    public boolean isPlayer(Entity e) {
        return e instanceof Player;
    }

    //TODO public Entity spawnFallingBlock(Position position Block block, boolean enableGravity, boolean canBePlaced)

    @Deprecated
    public void makeSoundToPlayer(Player player, String sound) {
        player.getLevel().addSound(player, Sound.valueOf(sound));
    }

    //public EntityArrow shootArrow(Position from, Position to)

    //public EntityArrow shootArrow(Position from, Position to, double multiply)

    //public EntityArrow shootArrow(Position from, Position to, boolean pickup)

    //public EntityArrow shootArrow(Position from, Position to, boolean pickup, double multiply)

    //public EntityArrow shootArrow(Position from, Position to, Entity sender, boolean pickup, double multiply)

    //public EntitySnowball shootSnowball(Position from, Position to)

    //public EntitySnowball shootSnowball(Position from, Position to, double multiply)

    //public EntitySnowball shootSnowball(Position from, Position to, boolean canPickUp)

    //public EntitySnowball shootSnowball(Position from, Position to, boolean canPickUp, double multiply)

    //public EntitySnowball shootSnowball(Position from, Position to, Entity shooter, boolean canPickUp, double multiply)

    //public void lookAt(Entity e, Position pos)
}
