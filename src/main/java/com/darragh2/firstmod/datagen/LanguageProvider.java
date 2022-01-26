package com.darragh2.firstmod.datagen;

import com.darragh2.firstmod.FirstMod;
import com.darragh2.firstmod.common.block.PowergenBlock;
import com.darragh2.firstmod.common.block.RockBlock;
import com.darragh2.firstmod.setup.ModSetup;
import com.darragh2.firstmod.setup.Registration;
import net.minecraft.data.DataGenerator;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

    public LanguageProvider(DataGenerator gen, String locale) {
        super(gen, FirstMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + ModSetup.TAB_NAME, "Firstmod");
        add(PowergenBlock.MESSAGE_POWERGEN, "Power generator generating %s per tick!");
        add(PowergenBlock.SCREEN_TUTORIAL_POWERGEN, "Power generator");
        add(Registration.POWERGEN.get(), "Power generator");

        add(Registration.ROCK.get(), "Rock");
        add(RockBlock.MESSAGE_ROCK, "A Rock (in aqua!)");
    }
}
