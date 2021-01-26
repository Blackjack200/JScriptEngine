package com.blocklynukkit.loader.other.chemistry;

import com.blocklynukkit.loader.other.chemistry.ChemistryPack;

import java.util.UUID;

public class ChemistryResourcePack extends ChemistryPack {

    private static final UUID ID = UUID.fromString("0fba4063-dba1-4281-9b89-ff9390653530");

    @Override
    public UUID getPackId() {
        return ID;
    }
}
