package com.blocklynukkit.loader.other.chemistry;

import cn.nukkit.Server;
import cn.nukkit.resourcepacks.ResourcePack;
import cn.nukkit.resourcepacks.ResourcePackManager;

import java.lang.reflect.Field;
import java.util.*;

public class EnableChemistryBlocks {
    public static void enable(){
        List<ResourcePack> packs = new ArrayList<>();
        packs.add(new ChemistryResourcePack());
        packs.add(new ChemistryBehaviorPack());
        ResourcePackManager manager = Server.getInstance().getResourcePackManager();
        synchronized (manager) {
            try {
                Field f1 = ResourcePackManager.class.getDeclaredField("resourcePacksById");
                f1.setAccessible(true);
                Map<UUID, ResourcePack> byId = (Map<UUID, ResourcePack>) f1.get(manager);
                packs.forEach(pack -> byId.put(pack.getPackId(), pack));

                Field f2 = ResourcePackManager.class.getDeclaredField("resourcePacks");
                f2.setAccessible(true);
                packs.addAll(Arrays.asList((ResourcePack[]) f2.get(manager)));
                f2.set(manager, packs.toArray(new ResourcePack[0]));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
