package com.darragh2.firstmod.datagen;

import com.darragh2.firstmod.FirstMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class BlockTags extends BlockTagsProvider {
    public BlockTags(DataGenerator p_126511_, @Nullable ExistingFileHelper helper) {
        super(p_126511_, FirstMod.MODID, helper);
    }

    @Override
    protected void addTags() {
        super.addTags();
    }
}
