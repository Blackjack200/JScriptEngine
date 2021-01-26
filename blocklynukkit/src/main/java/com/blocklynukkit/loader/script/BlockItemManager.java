package com.blocklynukkit.loader.script;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockUnknown;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.event.block.BlockUpdateEvent;
import cn.nukkit.inventory.FurnaceRecipe;
import cn.nukkit.inventory.ShapedRecipe;
import cn.nukkit.inventory.ShapelessRecipe;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.Sound;
import cn.nukkit.level.particle.DestroyBlockParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import com.blocklynukkit.loader.Loader;
import com.blocklynukkit.loader.UnionData;
import com.blocklynukkit.loader.script.bases.BaseManager;
import com.blocklynukkit.loader.utils.Utils;
import io.netty.util.collection.CharObjectHashMap;
import org.jetbrains.annotations.NotNull;
import site.misaka.engine.EngineAdapter;

import java.lang.reflect.Constructor;
import java.util.*;

public class BlockItemManager extends BaseManager {
    public BlockItemManager(EngineAdapter scriptEngine) {
        super(scriptEngine);
    }

    @Override
    public String toString() {
        return "BlocklyNukkit Based Object";
    }

    //-添加声音
    public void makeSound(Position position, String soundname) {
        position.getLevel().addSound(new Vector3(position.x, position.y, position.z), Sound.valueOf(soundname));
    }

    //-生成经验求
    public void makeExpBall(Position position, int point) {
        position.getLevel().dropExpOrb(new Vector3(position.x, position.y, position.z), point);
    }

    //-生成掉落物
    public void makeDropItem(Position position, Item item) {
        position.getLevel().dropItem(new Vector3(position.x, position.y, position.z), item);
    }

    //-获取方块
    public Block getBlock(Position position) {
        return position.getLevelBlock();
    }

    //-获取生物数组
    public List<Entity> getLevelEntities(Position position) {
        return Arrays.asList(position.getLevel().getEntities());
    }

    //-获取世界中的玩家
    public List<Player> getLevelPlayers(Position position) {
        return new ArrayList<Player>(position.getLevel().getPlayers().values());
    }

    //-获取是否晴天
    public boolean getIsSunny(Position position) {
        Level level = position.getLevel();
        return !(level.isRaining() || level.isThundering());
    }

    //-设置天气
    public void setLevelWeather(Position position, String mode) {
        Level level = position.getLevel();
        if (!mode.equals("clear")) {
            if (mode.equals("rain")) {
                level.setRaining(true);
                level.setThundering(false);
            } else if (mode.equals("thunder")) {
                level.setThundering(true);
                level.setRaining(false);
            }
        } else {
            level.setRaining(false);
            level.setThundering(false);
        }
    }

    //-获取白天黑夜
    public boolean isDay(Position position) {
        return position.getLevel().isDaytime();
    }

    //-设置方块
    public void setBlock(Position position, Block block, boolean particle) {
        if (particle == true) {
            position.getLevel().addParticle(new DestroyBlockParticle(new Vector3(position.x + 0.5, position.y + 0.5, position.z + 0.5), position.getLevelBlock()));
        }
        Server.getInstance().getPluginManager().callEvent(new BlockUpdateEvent(position.getLevelBlock()));
        position.getLevel().setBlockAt(
                (int) position.x, (int) position.y, (int) position.z, block.getId(), block.getDamage()
        );
    }

    //-获取玩家手中物品
    public Item getItemInHand(Player player) {
        return player.getInventory().getItemInHand();
    }

    //-设置玩家手中物品
    public void setItemInHand(Player player, Item item) {
        player.getInventory().setItemInHand(item);
    }

    //-向玩家背包添加物品
    public void addItemToPlayer(Player player, Item item) {
        if (player.getInventory().canAddItem(item)) {
            player.getInventory().addItem(item);
        } else {
            player.sendPopup("你有一些" + item.getName() + "装不下掉到了地上");
            player.getLevel().dropItem(player, item);
        }
    }

    //-玩家背包是否有物品
    public boolean hasItemToPlayer(Player player, Item item) {
        return player.getInventory().contains(item);
    }

    public boolean PlayercontainsItem(Player player, int x, Item... item) {
        Item[] tmp = item;
        boolean have = true;
        if (x != 1)
            for (Item each : tmp) {
                each.setCount(each.getCount() * x);
            }
        for (Item each : tmp) {
            if (!have) return false;
            if (!player.getInventory().contains(each)) {
                have = false;
            }
        }
        return true;
    }

    public void removeItemToPlayer(Player player, Item item) {
        if (PlayercontainsItem(player, 1, item)) {
            player.getInventory().removeItem(item);
        }
    }

    //获取世界掉落物
    public EntityItem[] getDropItems(Position position) {
        ArrayList<EntityItem> list = new ArrayList();
        for (Entity entity : position.getLevel().getEntities()) {
            if (entity.getNetworkId() == 64) {
                list.add((EntityItem) entity);
            }
        }
        return list.toArray(new EntityItem[list.size()]);
    }

    public EntityItem[] getDropItems(Level level) {
        ArrayList<EntityItem> list = new ArrayList();
        for (Entity entity : level.getEntities()) {
            if (entity.getNetworkId() == 64) {
                list.add((EntityItem) entity);
            }
        }
        return list.toArray(new EntityItem[list.size()]);
    }

    //获取世界生物
    public Entity[] getEntities(Position position) {
        ArrayList<Entity> list = new ArrayList();
        for (Entity entity : position.getLevel().getEntities()) {
            list.add(entity);
        }
        return list.toArray(new Entity[list.size()]);
    }

    //获取世界名称
    public String getLevelName(Level level) {
        return level.getName();
    }

    //here 4/23
    public void PositionMove(Position position, double x, double y, double z) {
        position.x += x;
        position.y += y;
        position.z += z;
    }

    /********************************* 纯方块物品方法 *************************************/
    //-构建方块
    public Block buildBlock(int id, int data) {
        return Block.get(id, data);
    }

    //-获取方块id*
    //-构建物品
    public Item buildItem(int id, int data, int count) {
        return Item.get(id, data, count);
    }

    //-从方块构建物品
    public Item buildItemFromBlock(Block block) {
        return block.toItem();
    }

    //-设置物品数量*
    //-设置物品数据值*
    //-设置物品名称*
    //-获取物品id*
    //-获取物品数量*
    //-获取物品数据值*
    //-获取物品名称*
    //-物品不可破坏*
    public void setItemProperty(@NotNull Item item, Integer data, Integer count, Boolean unbreakable, String name, String lore, String nbt) {

    }

    //未完成-获取工具种类*
    public String getItemLore(Item item) {
        String string = "";
        for (String a : item.getLore()) {
            string += a + ";";
        }
        return string;
    }

    //添加创造物品栏
    public void addToCreativeBar(Item item) {
        if (!Item.isCreativeItem(item)) {
            Item.addCreativeItem(item);
        }
    }

    public void setItemLore(Item item, String string) {
        item.setLore(string.split(";"));
    }

    //-无序合成
    public void addShapelessCraft(Item[] inputs, Item output) {
        Server.getInstance().addRecipe(new ShapelessRecipe(
                UUID.randomUUID().toString(), 99, output, Arrays.asList(inputs)
        ));
        Server.getInstance().getCraftingManager().rebuildPacket();
    }

    //-冶炼合成
    public void addFurnaceCraft(Item input, Item output) {
        Server.getInstance().addRecipe(new FurnaceRecipe(output, input));
        Server.getInstance().getCraftingManager().rebuildPacket();
        UnionData.furnaceMap.put(input, output);
    }

    //-有序合成
    public void addShapedCraft(String[] shape, Item output, Item[] append) {
        if (shape[2].equals("   ")) {
            String[] tmp = shape;
            shape = new String[]{tmp[0], tmp[1]};
            if (shape[1].equals("   ")) {
                tmp = shape;
                shape = new String[]{tmp[0]};
            }
        }
        for (int i = 0; i < shape.length; i++) {
            if (shape[i].endsWith(' ' + "")) {
                if (shape[i].length() == 3) {
                    String tt = shape[i];
                    shape[i] = tt.charAt(0) + "" + tt.charAt(1);
                } else if (shape[i].length() == 2) {
                    String tt = shape[i];
                    shape[i] = tt.charAt(0) + "";
                }
            }
            if (shape[i].endsWith(' ' + "")) {
                if (shape[i].length() == 3) {
                    String tt = shape[i];
                    shape[i] = tt.charAt(0) + "" + tt.charAt(1);
                } else if (shape[i].length() == 2) {
                    String tt = shape[i];
                    shape[i] = tt.charAt(0) + "";
                }
            }
        }
        int max = 0;
        for (String a : shape) {
            if (a.length() >= max) max = a.length();
        }
        for (int i = 0; i < shape.length; i++) {
            if (shape[i].length() < max) {
                String tt = shape[i];
                if (max - tt.length() == 1) {
                    tt += " ";
                } else if (max - tt.length() == 2) {
                    tt += "  ";
                }
                shape[i] = tt;
            }
        }
        if (!Item.isCreativeItem(output)) {
            Item.addCreativeItem(output);
        }
        Map<Character, Item> map = new CharObjectHashMap<>();
        for (String a : shape) {
            for (char b : a.toCharArray()) {
                if (b != ' ')
                    map.put(b, (Item) UnionData.easyTempMap.get(b + ""));
            }
        }
        LinkedList<Item> linkedList = new LinkedList<>();
        for (Item a : append) {
            linkedList.add(a);
        }
        ShapedRecipe recipe = new ShapedRecipe(
                UUID.randomUUID().toString(), 100,
                output, shape, map, linkedList
        );
        Server.getInstance().addRecipe(recipe);
        Server.getInstance().getCraftingManager().rebuildPacket();
    }

    //添加附魔
    public void addItemEnchant(Item item, int id, int level) {
        Enchantment enchantment = Enchantment.getEnchantment(id);
        enchantment.setLevel(level);
        addEnchantment(item, level, enchantment);
    }

    //获取附魔
    public Enchantment[] getItemEnchant(Item item) {
        return item.getEnchantments();
    }

    public int getEnchantID(Enchantment enchantment) {
        return enchantment.getId();
    }

    public int getEnchantLevel(Enchantment enchantment) {
        return enchantment.getLevel();
    }

    //比较物品
    public boolean isSame(Item item1, Item item2, boolean damage, boolean nbt) {
        return item1.equals(item2, damage, nbt);
    }

    //makeenchant
    private void addEnchantment(Item item, int level, Enchantment... enchantments) {
        CompoundTag tag;
        if (!item.hasCompoundTag()) {
            tag = new CompoundTag();
        } else {
            tag = item.getNamedTag();
        }

        ListTag<CompoundTag> ench;
        if (!tag.contains("ench")) {
            ench = new ListTag<>("ench");
            tag.putList(ench);
        } else {
            ench = tag.getList("ench", CompoundTag.class);
        }

        for (Enchantment enchantment : enchantments) {
            boolean found = false;

            for (int k = 0; k < ench.size(); k++) {
                CompoundTag entry = ench.get(k);
                if (entry.getShort("id") == enchantment.getId()) {
                    ench.add(k, new CompoundTag()
                            .putShort("id", enchantment.getId())
                            .putShort("lvl", level)
                    );
                    found = true;
                    break;
                }
            }

            if (!found) {
                ench.add(new CompoundTag()
                        .putShort("id", enchantment.getId())
                        .putShort("lvl", level)
                );
            }
        }

        item.setNamedTag(tag);
    }

    //添加BN合成
    public void addBNCraft(String type, String description, Item[] input, Item[] output, int delay, double percent) {
        Loader.bnCrafting.addCraft(type, description, input, output, delay, percent);
    }

    //打开BN合成
    public void openBNCraftForPlayer(String type, Player player) {
        Loader.bnCrafting.showTypeToPlayer(type, player);
    }

    //获取nbt字符串
    public String getNBTString(Item item) {
        return Utils.bytesToHexString(item.getCompoundTag());
    }

    //给物品注入nbt字符串
    public void putinNBTString(Item item, String str) {
        item.setCompoundTag(Utils.hexStringToBytes(str));
    }

    //设置物品颜色
    public void setItemColor(Item item, int r, int g, int b) {
        CompoundTag compoundTag = item.hasCompoundTag() ? item.getNamedTag() : new CompoundTag();
        compoundTag.putInt("customColor", r * 65536 + g * 256 + b);
        item.setCompoundTag(compoundTag);
    }

    //设置不可破坏
    public void setItemUnbreakable(Item item, boolean unbreakable) {
        CompoundTag compoundTag = item.hasCompoundTag() ? item.getNamedTag() : new CompoundTag();
        compoundTag.putBoolean("Unbreakable", unbreakable);
        item.setCompoundTag(compoundTag);
    }

    //不对外暴露: 注册新方块
    public void registerBlock(int id, Class<? extends Block> clazz) {
        Item.list[id] = clazz;
        Block.list[id] = clazz;
        Block block;
        try {
            block = clazz.newInstance();
            try {
                Constructor constructor = clazz.getDeclaredConstructor(int.class);
                constructor.setAccessible(true);
                for (int data = 0; data < 16; ++data) {
                    Block.fullList[(id << 4) | data] = (Block) constructor.newInstance(data);
                }
                Block.hasMeta[id] = true;
            } catch (NoSuchMethodException ignore) {
                for (int data = 0; data < 16; ++data) {
                    Block.fullList[(id << 4) | data] = block;
                }
            }
        } catch (Exception e) {
            Loader.getPluginLogger().alert("Error while registering " + clazz.getName(), e);
            for (int data = 0; data < 16; ++data) {
                Block.fullList[(id << 4) | data] = new BlockUnknown(id, data);
            }
            return;
        }
        Block.solid[id] = block.isSolid();
        Block.transparent[id] = block.isTransparent();
        Block.hardness[id] = block.getHardness();
        Block.light[id] = block.getLightLevel();
        if (block.isSolid()) {
            if (block.isTransparent()) {
                Block.lightFilter[id] = 1;
            } else {
                Block.lightFilter[id] = 15;
            }
        } else {
            Block.lightFilter[id] = 1;
        }
    }
}
