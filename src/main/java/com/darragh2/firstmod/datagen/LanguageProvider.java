package com.darragh2.firstmod.datagen;

import com.darragh2.firstmod.FirstMod;
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

        add(Registration.ROCK.get(), "Rock");
    }
}
