package site.misaka.blocklynukkit.script.object;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.event.block.BlockUpdateEvent;
import cn.nukkit.inventory.FurnaceRecipe;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.ShapelessRecipe;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.Sound;
import cn.nukkit.level.particle.DestroyBlockParticle;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.Plugin;
import lombok.var;
import site.misaka.engine.EngineAdapter;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class BlockItem extends AbstractObject {
    public BlockItem(Plugin plugin, String scriptName, EngineAdapter adapter) {
        super(plugin, scriptName, adapter);
    }

    public void makeSound(Position position, String soundName) {
        position.getLevel().addSound(position, Sound.valueOf(soundName));
    }

    @Deprecated
    public void makeExpBall(Position position, int expValue) {
        position.getLevel().dropExpOrb(position, expValue);
    }

    @Deprecated
    public void makeDropItem(Position position, Item item) {
        position.level.dropItem(position, item);
    }

    @Deprecated
    public Block getBlock(Position position) {
        return position.getLevelBlock();
    }

    @Deprecated
    public Entity[] getLevelEntities(Position position) {
        return position.getLevel().getEntities();
    }

    @Deprecated
    public Map<Long, Player> getLevelPlayers(Position position) {
        return position.getLevel().getPlayers();
    }

    @Deprecated
    public boolean getIsSunny(Position position) {
        return !position.getLevel().isRaining();
    }

    @Deprecated
    public boolean setLevelWeather(Position position, String weather) {
        var level = position.getLevel();
        switch (weather.toLowerCase(Locale.ROOT)) {
            case "rain":
                level.setRaining(true);
                break;
            case "thunder":
                level.setThundering(true);
                break;
            case "clear":
                level.setRaining(false);
                level.setThundering(false);
                break;
            default:
                return false;
        }
        return true;
    }

    @Deprecated
    public boolean isDay(Position position) {
        return position.getLevel().isDaytime();
    }

    public void setBlock(Position position, Block block, boolean particle) {
        if (particle) {
            position.getLevel().addParticle(new DestroyBlockParticle(position, position.getLevelBlock()));
        }
        Server.getInstance().getPluginManager().callEvent(new BlockUpdateEvent(position.getLevelBlock()));
        position.getLevel().setBlockAt((int) position.getX(), (int) position.getY(), (int) position.getZ(), block.getId(), block.getDamage());
    }

    @Deprecated
    public Item getItemInHand(Player player) {
        return player.getInventory().getItemInHand();
    }

    @Deprecated
    public void setItemInHand(Player player, Item item) {
        player.getInventory().setItemInHand(item);
    }

    @Deprecated
    public boolean addItemToPlayer(Player player, Item item) {
        Inventory inventory = player.getInventory();
        if (inventory.canAddItem(item)) {
            player.getInventory().addItem(item);
            return true;
        }
        return false;
    }

    @Deprecated
    public boolean hasItemToPlayer(Player player, Item item) {
        return player.getInventory().contains(item);
    }

    @Deprecated
    public void removeItemToPlayer(Player player, Item item) {
        player.getInventory().remove(item);
    }

    @Deprecated
    public EntityItem[] getDropItems(Position position) {
        var entities = position.getLevel().getEntities();
        var ret = new ArrayList<EntityItem>();
        for (Entity entity : entities) {
            if (entity instanceof EntityItem) {
                ret.add((EntityItem) entity);
            }
        }
        return ret.toArray(new EntityItem[0]);
    }

    @Deprecated
    public String getLevelName(Level level) {
        return level.getName();
    }

    @Deprecated
    public Block buildBlock(int blockId, int meta) {
        return Block.get(blockId, meta);
    }

    @Deprecated
    public Item buildItem(int itemId, int itemData, int itemCount) {
        return Item.get(itemId, itemData, itemCount);
    }

    @Deprecated
    public Item buildItemFromBlock(Block block) {
        return block.toItem();
    }

    public String getItemLore(Item item) {
        StringBuilder ret = new StringBuilder();
        for (String lore : item.getLore()) {
            ret.append(lore).append(";");
        }
        return ret.toString();
    }

    @Deprecated
    public void addToCreativeBar(Item item) {
        Item.addCreativeItem(item);
    }

    @Deprecated
    public void setItemLore(Item item, String lore) {
        item.setLore(lore.split(";"));
    }

    public void addShapelessCraft(Item[] inputs, Item output) {
        Server.getInstance().addRecipe(new ShapelessRecipe(
                UUID.randomUUID().toString(), 99, output, Arrays.asList(inputs)
        ));
        Server.getInstance().getCraftingManager().rebuildPacket();
    }

    public void addFurnaceCraft(Item input, Item output) {
        Server.getInstance().addRecipe(new FurnaceRecipe(output, input));
        Server.getInstance().getCraftingManager().rebuildPacket();
    }

    //TODO addShapedCraft(String[] shape, Item output, Item[] append)

    public void addItemEnchant(Item item, int enchantId, int enchantLevel) {
        Enchantment enchantment = Enchantment.get(enchantId);
        enchantment.setLevel(enchantLevel);
        item.addEnchantment(enchantment);
    }

    @Deprecated
    public boolean isSame(Item item, Item item2, boolean checkDamage, boolean checkNbt) {
        return item.equals(item2, checkDamage, checkNbt);
    }

    //TODO addBNCraft(String type, String description, Item[] input, Item[] output, int delay, double percent)
    //TODO openBNCraftForPlayer(String type, Player player)

    @Deprecated
    public void PositionMove(Position position, double x, double y, double z) {
        position.x += x;
        position.y += y;
        position.z += z;
    }

    @Deprecated
    public String getNBTString(Item item) {
        return new String(item.getCompoundTag());
    }

    @Deprecated
    public void putinNBTString(Item item, String nbt) {
        item.setCompoundTag(nbt.getBytes(StandardCharsets.UTF_8));
    }

    @Deprecated
    public Enchantment[] getItemEnchant(Item item) {
        return item.getEnchantments();
    }

    @Deprecated
    public int getEnchantId(Enchantment enchantment) {
        return enchantment.getId();
    }

    @Deprecated
    public int getEnchantLevel(Enchantment enchantment) {
        return enchantment.getLevel();
    }

    @Deprecated
    public void setItemColor(Item item, int r, int g, int b) {
        CompoundTag compoundTag = item.hasCompoundTag() ? item.getNamedTag() : new CompoundTag();
        compoundTag.putInt("customColor", r * 65536 + g * 256 + b);
        item.setCompoundTag(compoundTag);
    }

    public void setItemUnbreakable(Item item, boolean unbreakable) {
        if (unbreakable) {
            item.setNamedTag(item.getNamedTag().putBoolean("Unbreakable", true));
        } else {
            item.setNamedTag(item.getNamedTag().remove("Unbreakable"));
        }
    }

    //Extra
    @Deprecated
    public int getBlockId(Block block) {
        return block.getId();
    }

    @Deprecated
    public String getBlockName(Block block) {
        return block.getName();
    }

    @Deprecated
    public void setBlockMeta(Block block, int meta) {
        block.setDamage(meta);
    }

    @Deprecated
    public boolean isSameBlock(Block block, Block block2) {
        return block.equals(block2);
    }
}
