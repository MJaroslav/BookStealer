package com.github.mjaroslav.bookstealer.config;

import com.github.mjaroslav.bookstealer.lib.ModInfo;
import lombok.Data;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = ModInfo.MOD_ID)
@Data
public class ModConfig implements ConfigData {
    @Comment("Use rude text for save book button.")
    private boolean rudeText = false;
    @Comment("Book saving directory, use %MINECRAFT% for game instance directory (it's --gameDir launch param).")
    private String saveDir = "%MINECRAFT%/books";
    @Comment("Book file name pattern, you can use %TITLE%, %AUTHOR% and %DATE% for autoreplacing.")
    private String bookFileFormat = "%TITLE%_%AUTHOR%_%DATE%.txt";
    @Comment("Date format for %DATE% in book file name. Uses formatting from SimpleDateFormat.")
    private String dateFormat = "yyyy-MM-dd_hh-mm";
}
