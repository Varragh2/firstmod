package com.darragh2.firstmod.datagen;

import com.darragh2.firstmod.FirstMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTags extends ItemTagsProvider {
    public ItemTags(DataGenerator p_126530_, BlockTagsProvider p_126531_, ExistingFileHelper helper) {
        super(p_126530_, p_126531_, FirstMod.MODID, helper);
    }
}
