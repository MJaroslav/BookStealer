package com.github.mjaroslav.bookstealer.gui;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import com.github.mjaroslav.bookstealer.lib.ModInfo;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import java.util.Set;

public class GuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraft) {

    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ModGuiConfig.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement runtimeOptionCategoryElement) {
        return null;
    }

    public static class ModGuiConfig extends GuiConfig {
        public ModGuiConfig(GuiScreen parentScreen) {
            super(parentScreen, new ConfigElement<>(BookStealerMod.config.getCategory("general")).getChildElements(), ModInfo.MOD_ID, ModInfo.MOD_ID, false, false, "general");
        }
    }
}
