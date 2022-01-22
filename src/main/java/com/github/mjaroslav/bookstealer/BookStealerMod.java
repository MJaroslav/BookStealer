package com.github.mjaroslav.bookstealer;

import com.github.mjaroslav.bookstealer.config.ModConfig;
import com.github.mjaroslav.bookstealer.util.ModUtils;
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
    private static ConfigHolder<ModConfig> configHolder;

    private static Logger log;

    public static ModConfig getConfig() {
        return configHolder.getConfig();
    }

    @Override
    public void onInitializeClient() {
        log = LogManager.getLogger();
        info("Sup, 2ch.hk!");
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        configHolder = AutoConfig.getConfigHolder(ModConfig.class);
        ModUtils.overrideConfigValues();
    }

    public static void info(String message, Object... params) {
        log.info(String.format("[%s] ", ModInfo.NAME) + message, params);
    }

    public static void warn(String message, Throwable e, Object... params) {
        log.error(String.format("[%s] ", ModInfo.NAME) + message, e, params);
    }
}
