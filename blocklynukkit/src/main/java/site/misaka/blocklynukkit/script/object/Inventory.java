package site.misaka.blocklynukkit.script.object;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemArmor;
import cn.nukkit.plugin.Plugin;
import com.nukkitx.fakeinventories.inventory.ChestFakeInventory;
import com.nukkitx.fakeinventories.inventory.DoubleChestFakeInventory;
import com.nukkitx.fakeinventories.inventory.FakeInventory;
import com.nukkitx.fakeinventories.inventory.FakeSlotChangeEvent;
import site.misaka.engine.EngineAdapter;

import java.util.Collection;
import java.util.Map;

public class Inventory extends AbstractObject {
    public Inventory(Plugin plugin, String scriptName, EngineAdapter adapter) {
        super(plugin, scriptName, adapter);
    }

    public FakeInventory addInv(boolean doubleChest, Item[] item, String name) {
        ChestFakeInventory fakeInventory;
        if (doubleChest) {
            fakeInventory = new DoubleChestFakeInventory();
        } else {
            fakeInventory = new ChestFakeInventory();
        }

        for (int i = 0, max = item.length; i < max; i++) {
            fakeInventory.setItem(i, item[i]);
        }

        fakeInventory.setName(name);
        fakeInventory.addListener(
                (FakeSlotChangeEvent slotChangeEvent) ->
                        this.getAdapter().invoke("FakeSlotChangeEvent", slotChangeEvent));
        return fakeInventory;
    }

    @Deprecated
    public Collection<Item> getItemsInInv(cn.nukkit.inventory.Inventory inv) {
        return inv.getContents().values();
    }

    public void showFakeInv(Player player, FakeInventory inv) {
        player.addWindow(inv);
    }

    @Deprecated
    public cn.nukkit.inventory.Inventory editInvByMap(cn.nukkit.inventory.Inventory inv, Map<Integer, Item> invContent) {
        inv.setContents(invContent);
        return inv;
    }

    @Deprecated
    public cn.nukkit.inventory.Inventory editInv(cn.nukkit.inventory.Inventory inv, Item[] item) {
        for (int i = 0, size1 = inv.getSize(), size2 = item.length;
             i < size1 && i < size2;
             i++) {
            inv.setItem(i, item[i]);
        }
        return inv;
    }

    @Deprecated
    public cn.nukkit.inventory.Inventory editInvBySlot(cn.nukkit.inventory.Inventory inv, int slot, Item item) {
        inv.setItem(slot, item);
        return inv;
    }

    @Deprecated
    public cn.nukkit.inventory.Inventory addItemToInv(cn.nukkit.inventory.Inventory inv, Item item) {
        if (inv.canAddItem(item)) {
            inv.addItem(item);
        }
        return inv;
    }

    @Deprecated
    public cn.nukkit.inventory.Inventory removeItemFromInv(cn.nukkit.inventory.Inventory inv, Item item) {
        inv.removeItem(item);
        return inv;
    }

    @Deprecated
    public boolean containsItemInInv(cn.nukkit.inventory.Inventory inv, Item item) {
        return inv.contains(item);
    }

    //TODO getBlockInv
    //TODO setBlockInv
    //TODO getPlayerInv

    @Deprecated
    public void setPlayerInv(Player player, cn.nukkit.inventory.Inventory inv) {
        player.getInventory().setContents(inv.getContents());
    }

    @Deprecated
    public Item getEntityHelmet(Entity entity) {
        if (entity instanceof EntityHuman) {
            return ((EntityHuman) entity).getInventory().getHelmet();
        }
        return null;
    }

    @Deprecated
    public Item getEntityChestplate(Entity entity) {
        if (entity instanceof EntityHuman) {
            return ((EntityHuman) entity).getInventory().getChestplate();
        }
        return null;
    }

    @Deprecated
    public Item getEntityLeggings(Entity entity) {
        if (entity instanceof EntityHuman) {
            return ((EntityHuman) entity).getInventory().getLeggings();
        }
        return null;
    }

    @Deprecated
    public Item getEntityBoots(Entity entity) {
        if (entity instanceof EntityHuman) {
            return ((EntityHuman) entity).getInventory().getBoots();
        }
        return null;
    }

    @Deprecated
    public Item getEntityItemInHand(Entity entity) {
        if (entity instanceof EntityHuman) {
            return ((EntityHuman) entity).getInventory().getItemInHand();
        }
        return null;
    }

    @Deprecated
    public Item getEntityItemInOffHand(Entity entity) {
        if (entity instanceof EntityHuman) {
            return ((EntityHuman) entity).getOffhandInventory().getItem(0);
        }
        return null;
    }


    @Deprecated
    public void setEntityHelmet(Entity entity, ItemArmor armor) {
        if (entity instanceof EntityHuman) {
            ((EntityHuman) entity).getInventory().setHelmet(armor);
        }
    }

    @Deprecated
    public void setEntityChestplate(Entity entity, ItemArmor armor) {
        if (entity instanceof EntityHuman) {
            ((EntityHuman) entity).getInventory().setChestplate(armor);
        }
    }

    @Deprecated
    public void setEntityLeggings(Entity entity, ItemArmor armor) {
        if (entity instanceof EntityHuman) {
            ((EntityHuman) entity).getInventory().setLeggings(armor);
        }
    }

    @Deprecated
    public void setEntityBoots(Entity entity, ItemArmor armor) {
        if (entity instanceof EntityHuman) {
            ((EntityHuman) entity).getInventory().setBoots(armor);
        }
    }

    @Deprecated
    public void setEntityItemInHand(Entity entity, Item item) {
        if (entity instanceof EntityHuman) {
            ((EntityHuman) entity).getInventory().setItemInHand(item);
        }
    }

    @Deprecated
    public void setEntityItemInOffHand(Entity entity, Item item) {
        if (entity instanceof EntityHuman) {
            ((EntityHuman) entity).getOffhandInventory().setItem(0, item);
        }
    }
}
