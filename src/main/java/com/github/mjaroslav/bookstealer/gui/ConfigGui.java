package com.github.mjaroslav.bookstealer.gui;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import com.github.mjaroslav.bookstealer.lib.ModInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {
    public ConfigGui(GuiScreen parentScreen) {
        super(parentScreen, BookStealerMod.getConfig().getConfigCategory("general").getChildElements(), ModInfo.MOD_ID,
                false, false, ModInfo.NAME);
    }
}
