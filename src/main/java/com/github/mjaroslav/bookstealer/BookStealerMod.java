package com.github.mjaroslav.bookstealer;

import com.github.mjaroslav.bookstealer.config.Config;
import com.github.mjaroslav.bookstealer.event.EventHandler;
import com.github.mjaroslav.bookstealer.lib.ModInfo;
import com.github.mjaroslav.bookstealer.util.ModUtils;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lombok.Getter;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION,
        acceptableRemoteVersions = ModInfo.ACCEPTABLE_REMOTE_VERSIONS, guiFactory = ModInfo.GUI_FACTORY)
public class BookStealerMod {
    @Getter
    private static Config config;
    @Getter
    private static Logger log;

    @Mod.EventHandler
    public void onFMLPreInitializationEvent(FMLPreInitializationEvent event) {
        log = event.getModLog();
        log.info("Sup, 2ch.hk!");
        config = new Config(event.getSuggestedConfigurationFile());
        config.loadConfiguration();
        ModUtils.overrideConfigValues();
    }

    @Mod.EventHandler
    public void onFMLInitializationEvent(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(EventHandler.INSTANCE);
    }
}
