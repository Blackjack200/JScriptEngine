package com.blocklynukkit.loader.other.particle;

import cn.nukkit.entity.item.EntityFirework;
import cn.nukkit.item.ItemFirework;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.utils.DyeColor;
import cn.nukkit.item.ItemFirework;

import java.util.Random;

public class FireworkRocket{
    public static void make(Level level, Vector3 vec, int colornum,boolean flick,boolean trail,int shape,int second){
        ItemFirework item = new ItemFirework();
        CompoundTag tag = new CompoundTag();
        Random random = new Random();
        CompoundTag ex = new CompoundTag();
        ex.putByteArray("FireworkColor", new byte[]{(byte)colornum});
        ex.putByteArray("FireworkFade", new byte[0]);
        ex.putBoolean("FireworkFlicker", flick);
        ex.putBoolean("FireworkTrail", trail);
        ex.putByte("FireworkType", ItemFirework.FireworkExplosion.ExplosionType.values()[shape].ordinal());
        tag.putCompound("Fireworks", (new CompoundTag("Fireworks")).putList((new ListTag("Explosions")).add(ex)).putByte("Flight", second));
        item.setNamedTag(tag);
        CompoundTag nbt = new CompoundTag();
        nbt.putList((new ListTag("Pos")).add(new DoubleTag("", vec.x + 0.5D)).add(new DoubleTag("", vec.y + 0.5D)).add(new DoubleTag("", vec.z + 0.5D)));
        nbt.putList((new ListTag("Motion")).add(new DoubleTag("", 0.0D)).add(new DoubleTag("", 0.0D)).add(new DoubleTag("", 0.0D)));
        nbt.putList((new ListTag("Rotation")).add(new FloatTag("", 0.0F)).add(new FloatTag("", 0.0F)));
        nbt.putCompound("FireworkItem", NBTIO.putItemHelper(item));
        EntityFirework entity = new EntityFirework(level.getChunk((int)vec.x >> 4, (int)vec.z >> 4), nbt);
        entity.spawnToAll();
    }
}
