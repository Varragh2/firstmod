package com.darragh2.firstmod.datagen;

import com.darragh2.firstmod.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.storage.loot.LootTable;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        lootTables.put(Registration.POWERGEN.get(), createStandardTable("powergen", Registration.POWERGEN.get(), Registration.POWERGEN_BLOCKENTITY.get()));
        lootTables.put(Registration.ROCK.get(), createSimpleTable("rock", Registration.ROCK.get()));
        lootTables.put(Registration.CHEST.get(), createSimpleTable("chest", Registration.CHEST.get()));
    }
}
