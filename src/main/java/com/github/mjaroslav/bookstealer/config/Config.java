package com.github.mjaroslav.bookstealer.config;

import com.github.mjaroslav.bookstealer.lib.ModInfo;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lombok.Getter;
import lombok.val;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.io.File;

public class Config {
    @Getter
    private final Configuration cfg;

    public Config(@NotNull File file) {
        this.cfg = new Configuration(file, "1.7.10-1");
        FMLCommonHandler.instance().bus().register(this);
    }

    private Property rudeText;
    private Property saveDir;
    private Property bookFileFormat;
    private Property dateFormat;

    public boolean isRudeText() {
        return rudeText.getBoolean();
    }

    public void setRudeText(boolean rudeText) {
        this.rudeText.set(rudeText);
        saveConfiguration();
    }

    @NotNull
    public String getSaveDir() {
        return saveDir.getString();
    }

    public void setSaveDir(@NotNull String saveDir) {
        this.saveDir.set(saveDir);
        saveConfiguration();
    }

    @NotNull
    public String getDateFormat() {
        return dateFormat.getString();
    }

    public void setDateFormat(@NotNull String dateFormat) {
        this.dateFormat.set(dateFormat);
        saveConfiguration();
    }

    @NotNull
    public String getBookFileFormat() {
        return bookFileFormat.getString();
    }

    public void setBookFileFormat(@NotNull String bookFileFormat) {
        this.bookFileFormat.set(bookFileFormat);
        saveConfiguration();
    }

    private Property getProp(@NotNull String key, String defaultValue, @NotNull String comment) {
        val result = cfg.get("general", key, defaultValue, comment);
        result.setLanguageKey(String.format("bookstealer.config.%s", key));
        return result;
    }

    public void loadConfiguration() {
        cfg.load();
        rudeText = cfg.get("general", "rude_text", false, "Use rude text for save book button.");
        rudeText.setLanguageKey("bookstealer.config.rude_text");
        saveDir = getProp("save_dir", "%MINECRAFT%/books", "" + "Book saving directory, use %MINECRAFT% for game " +
                "instance directory (it's --gameDir launch param).");
        bookFileFormat = getProp("book_file_format", "%TITLE%_%AUTHOR%_%DATE%.txt", "Book file name pattern, you can " +
                "use %TITLE%, %AUTHOR% and %DATE% for autoreplacing.");
        dateFormat = getProp("date_format", "yyyy-MM-dd_hh-mm", "Date format for %DATE% in book file name. Uses " +
                "formatting from SimpleDateFormat.");
        saveConfiguration();
    }

    public void saveConfiguration() {
        cfg.save();
    }

    @NotNull
    public String getId() {
        return cfg.getConfigFile().toString();
    }

    @UnknownNullability
    public ConfigElement<?> getConfigCategory(@NotNull String category) {
        return new ConfigElement<>(cfg.getCategory(category));
    }

    @SubscribeEvent
    public void onOnConfigChangedEvent(OnConfigChangedEvent event) {
        if (ModInfo.MOD_ID.equals(event.modID)) saveConfiguration();
    }
}
