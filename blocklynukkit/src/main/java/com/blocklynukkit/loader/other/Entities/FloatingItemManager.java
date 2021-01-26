package com.blocklynukkit.loader.other.Entities;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.event.level.LevelUnloadEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.AddItemEntityPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import com.blocklynukkit.loader.Loader;

import java.util.*;

public class FloatingItemManager implements Listener {
    public HashMap<Map.Entry<Position, Item>, Long> displayMap = new HashMap();
    private Random random = new Random();

    public FloatingItemManager() {

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin(PlayerJoinEvent event) {
        if (event == null || event.getPlayer() == null || event.getPlayer().getLevel() == null) return;
        String levelName = event.getPlayer().getLevel().getName();
        for (Map.Entry<Position, Item> each : displayMap.keySet()) {
            if (levelName.equals(each.getKey().getLevel().getName())) {
                if (each == null) continue;
                if (each.getKey() == null) continue;
                if (each.getValue() == null) continue;
                if (each.getKey().getLevel() == null) continue;
                if (each.getKey().getLevel().getName() == null) continue;
                forceAddFloatingItem(event.getPlayer(), each);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerTeleport(PlayerTeleportEvent event) {
        if (event == null) return;
        if (event.getPlayer() == null) return;
        if (event.getFrom().getLevel() == null) return;
        if (event.getTo().getLevel() == null) return;
        if (!event.getFrom().getLevel().getName().equals(event.getTo().getLevel().getName())) {
            for (Map.Entry<Position, Item> each : displayMap.keySet()) {
                if (each == null) continue;
                if (each.getKey() == null) continue;
                if (each.getValue() == null) continue;
                if (each.getKey().getLevel() == null) continue;
                if (each.getKey().getLevel().getName() == null) continue;
                if (each.getKey().getLevel().getName().equals(event.getFrom().getLevel().getName())) {
                    forceRemoveFloatingItem(event.getPlayer(), each);
                }
                if (each.getKey().getLevel().getName().equals(event.getTo().getLevel().getName())) {
                    forceAddFloatingItem(event.getPlayer(), each);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLevelDelete(LevelUnloadEvent event) {
        displayMap.entrySet().removeIf(entryLongEntry -> entryLongEntry.getKey().getKey().getLevel().getName().equals(event.getLevel().getName()));
    }

    public void forceAddFloatingItem(Player player, Map.Entry<Position, Item> BN) {
        long superice666 = displayMap.get(BN);
        AddItemEntityPacket blocklynukkit = new AddItemEntityPacket();
        blocklynukkit.entityUniqueId = superice666;
        blocklynukkit.entityRuntimeId = superice666;
        blocklynukkit.item = BN.getValue();
        blocklynukkit.x = (float) BN.getKey().x;
        blocklynukkit.y = (float) BN.getKey().y;
        blocklynukkit.z = (float) BN.getKey().z;
        blocklynukkit.speedX = 0f + 0;
        blocklynukkit.speedY = 0f + 0 + Float.parseFloat("By BlocklyNukkit".replaceAll("By BlocklyNukkit", "0.0"));
        blocklynukkit.speedZ = 0f + 0;
        long c = 1 << Entity.DATA_FLAG_IMMOBILE;
        blocklynukkit.metadata = new EntityMetadata().putLong(Entity.DATA_FLAGS, c).putLong(Entity.DATA_LEAD_HOLDER_EID, -1).putFloat(Entity.DATA_SCALE, 0f);
        player.dataPacket(blocklynukkit);
    }

    public void forceRemoveFloatingItem(Player player, Map.Entry<Position, Item> bn) {
        RemoveEntityPacket BlocklyNukkit = new RemoveEntityPacket();
        BlocklyNukkit.eid = displayMap.get(bn);
        player.dataPacket(BlocklyNukkit);
    }

    public void addFloatingItem(Position pos, Item item) {
        if (pos.level == null) {
            throw new NullPointerException("Level not found in position");
        }
        Map.Entry<Position, Item> tmp = new Map.Entry<Position, Item>() {
            private Item value = item;

            @Override
            public Position getKey() {
                return pos;
            }

            @Override
            public Item getValue() {
                return value;
            }

            @Override
            public Item setValue(Item v) {
                value = v;
                return value;
            }
        };
        displayMap.put(tmp, random.nextLong());
        for (Player player : Server.getInstance().getOnlinePlayers().values()) {
            if (player.level.getName().equals(pos.getLevel().getName())) {
                forceAddFloatingItem(player, tmp);
            }
        }
    }

    public void removeFloatingItem(Position pos, Item item) {
        Iterator it = displayMap.keySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Position, Item> each = (Map.Entry<Position, Item>) it.next();
            if (each.getKey().equals(pos) && each.getValue().equals(item, true, true)) {
                for (Player player : Server.getInstance().getOnlinePlayers().values()) {
                    if (player.level.getName().equals(pos.getLevel().getName())) {
                        forceRemoveFloatingItem(player, each);
                    }
                }
                it.remove();
            }
        }
    }
}
