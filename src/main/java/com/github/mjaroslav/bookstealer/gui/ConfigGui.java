package com.github.mjaroslav.bookstealer.gui;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import com.github.mjaroslav.bookstealer.lib.ModInfo;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;

public class ConfigGui extends GuiConfig {
    public ConfigGui(GuiScreen parentScreen) {
        super(parentScreen, BookStealerMod.getConfig().getConfigCategory("general").getChildElements(), ModInfo.MOD_ID,
                false, false, ModInfo.NAME);
    }
}
