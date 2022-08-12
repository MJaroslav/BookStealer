package com.github.mjaroslav.bookstealer.gui;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import com.github.mjaroslav.bookstealer.lib.ModInfo;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;

@SideOnly(Side.CLIENT)
public class ConfigGui extends GuiConfig {
    public ConfigGui(GuiScreen parentScreen) {
        super(parentScreen, BookStealerMod.getConfig().getConfigCategory("general").getChildElements(), ModInfo.MOD_ID,
                false, false, ModInfo.NAME);
    }
}
