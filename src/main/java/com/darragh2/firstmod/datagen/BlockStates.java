package com.darragh2.firstmod.datagen;

import com.darragh2.firstmod.FirstMod;
import com.darragh2.firstmod.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {

    public BlockStates (DataGenerator gen, ExistingFileHelper helper) {
        super(gen, FirstMod.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(Registration.ROCK.get());
    }
}
