package site.misaka.script.object;

import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.TaskHandler;
import site.misaka.engine.EngineAdapter;
import site.misaka.script.UnionData;
import site.misaka.script.object.slapper.HumanSlapper;
import site.misaka.script.object.slapper.HumanSlapperHook;

import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public class ComplexObject extends AbstractObject {
    public ComplexObject(Plugin plugin, String scriptName, EngineAdapter adapter) {
        super(plugin, scriptName, adapter);
    }

    public HumanSlapperHook.HumanSlapperHookBuilder getSlapperBuilder() {
        return HumanSlapperHook.builder();
    }

    public HumanSlapper createSlapper(Position position, String name, Skin skin, HumanSlapperHook hook) {
        return new HumanSlapper(this, position, name, this.nbt(position, skin), hook);
    }

    public HumanSlapper createSlapper(Position position, String name, Skin skin) {
        return new HumanSlapper(this, position, name, this.nbt(position, skin), this.getSlapperBuilder().build());
    }

    private CompoundTag nbt(Position position, Skin skin) {
        CompoundTag nbt = Entity.getDefaultNBT(position);
        CompoundTag skinTag = new CompoundTag()
                .putByteArray("Data", skin.getSkinData().data)
                .putInt("SkinImageWidth", skin.getSkinData().width)
                .putInt("SkinImageHeight", skin.getSkinData().height)
                .putString("ModelId", skin.getSkinId())
                .putString("CapeId", skin.getCapeId())
                .putByteArray("CapeData", skin.getCapeData().data)
                .putInt("CapeImageWidth", skin.getCapeData().width)
                .putInt("CapeImageHeight", skin.getCapeData().height)
                .putByteArray("SkinResourcePatch", skin.getSkinResourcePatch().getBytes(StandardCharsets.UTF_8))
                .putByteArray("GeometryData", skin.getGeometryData().getBytes(StandardCharsets.UTF_8))
                .putByteArray("AnimationData", skin.getAnimationData().getBytes(StandardCharsets.UTF_8))
                .putBoolean("PremiumSkin", skin.isPremium())
                .putBoolean("PersonaSkin", skin.isPersona())
                .putBoolean("CapeOnClassicSkin", skin.isCapeOnClassic());
        nbt.putCompound("Skin", skinTag);
        nbt.putBoolean("ishuman", true);
        nbt.putString("Item", Item.get(0).getName());
        nbt.putString("Helmet", Item.get(0).getName());
        nbt.putString("Chestplate", Item.get(0).getName());
        nbt.putString("Leggings", Item.get(0).getName());
        nbt.putString("Boots", Item.get(0).getName());
        return nbt;
    }

    public TaskHandler scheduleDelayedTask(String callback, int delay, boolean asynchronous) {
        return Server.getInstance().getScheduler().scheduleDelayedTask(this.getPlugin(), () -> this.getAdapter().invoke(callback), delay, asynchronous);
    }

    public TaskHandler scheduleRepeatingTask(String callback, int period, boolean asynchronous) {
        return Server.getInstance().getScheduler().scheduleRepeatingTask(this.getPlugin(), () -> this.getAdapter().invoke(callback), period, asynchronous);
    }

    public TaskHandler scheduleDelayedRepeatingTask(String callback, int period, int delay, boolean asynchronous) {
        return Server.getInstance().getScheduler().scheduleDelayedRepeatingTask(this.getPlugin(), () -> this.getAdapter().invoke(callback), period, delay, asynchronous);
    }
}
