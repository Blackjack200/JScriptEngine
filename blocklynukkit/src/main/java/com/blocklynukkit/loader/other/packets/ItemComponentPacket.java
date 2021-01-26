package com.blocklynukkit.loader.other.packets;

import cn.nukkit.Server;
import cn.nukkit.network.protocol.DataPacket;
import com.blocklynukkit.loader.other.Items.ItemComponentEntry;

import java.util.ArrayList;
import java.util.List;

public class ItemComponentPacket extends DataPacket {
    public List<ItemComponentEntry> entries = new ArrayList<>();

    public ItemComponentPacket() {
        Server.getInstance().getNetwork().registerPacket((byte) 162, this.getClass());
    }

    @Override
    public byte pid() {
        return (byte) 162;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.putUnsignedVarInt(entries.size());
        for (ItemComponentEntry entry : entries) {
            this.putString(entry.name);
            this.putByteArray(entry.toBytes());
        }
    }
}
