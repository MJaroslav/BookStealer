package com.github.mjaroslav.bookstealer;

import com.github.mjaroslav.bookstealer.config.Config;
import com.github.mjaroslav.bookstealer.event.EventHandler;
import com.github.mjaroslav.bookstealer.lib.ModInfo;
import com.github.mjaroslav.bookstealer.util.ModUtils;
import lombok.Getter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION,
        guiFactory = ModInfo.GUI_FACTORY, clientSideOnly = true
)
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
