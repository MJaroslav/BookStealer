package com.github.mjaroslav.bookstealer.lib;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModInfo {
    public static final String MOD_ID = "bookstealer";
    public static final String NAME = "BookStealer";
    public static final String VERSION = "@VERSION@";
    public static final String ACCEPTABLE_REMOTE_VERSIONS = "*";
    public static final String GUI_FACTORY = "com.github.mjaroslav.bookstealer.gui.GuiFactory";
}
