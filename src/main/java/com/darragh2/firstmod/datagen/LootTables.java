package com.darragh2.firstmod.datagen;

import com.darragh2.firstmod.setup.Registration;
import net.minecraft.data.DataGenerator;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        lootTables.put(Registration.POWERGEN.get(), createStandardTable("powergen", Registration.POWERGEN.get(), Registration.POWERGEN_BLOCKENTITY.get()));
        lootTables.put(Registration.ROCK.get(), createSilkTouchTable("rock", Registration.ROCK.get(), Registration.ROCK_ITEM.get(), 1f, 1f));
    }
}
