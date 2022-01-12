package com.github.mjaroslav.bookstealer;

import com.github.mjaroslav.bookstealer.config.ModConfig;
import com.github.mjaroslav.bookstealer.lib.ModInfo;
import lombok.Getter;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookStealerMod implements ClientModInitializer {
    @Getter
    private static ModConfig config;
    @Getter
    private static ConfigHolder<ModConfig> configHolder;

    @Getter
    private static Logger log;

    @Override
    public void onInitializeClient() {
        log = LogManager.getLogger(ModInfo.NAME);
        log.info("Sup, 2ch.hk!");
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        configHolder = AutoConfig.getConfigHolder(ModConfig.class);
        config = configHolder.getConfig();
    }
}
