package com.github.mjaroslav.bookstealer;

import com.github.mjaroslav.bookstealer.event.EventHandler;
import com.github.mjaroslav.bookstealer.lib.ModInfo;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lombok.val;
import lombok.var;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION,
        acceptableRemoteVersions = ModInfo.ACCEPTABLE_REMOTE_VERSIONS,
        guiFactory = ModInfo.GUI_FACTORY)
public class BookStealerMod {
    public static Configuration config;

    private static boolean rudeTranslation;

    public static String getLangKey() {
        return "bookstealer.button.save.text" + (rudeTranslation ? ".rude" : "");
    }

    public static void updateConfigValues() {
        rudeTranslation = config.getBoolean("rude", "general", false, "Use rude translation for save button");
        if (config.hasChanged())
            config.save();
    }

    @Mod.EventHandler
    public void onFMLPreInitializationEvent(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        updateConfigValues();
        if (Minecraft.getMinecraft().getSession().getUsername().equals("MJaroslav"))
            rudeTranslation = true;
    }

    @Mod.EventHandler
    public void onFMLInitializationEvent(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(EventHandler.INSTANCE);
    }

    private static final String outFormat = "Title: %s\nAuthor: %s%s";
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm");

    public static void saveBook(ItemStack bookStack) {
        val nbt = bookStack.getTagCompound();
        val title = nbt == null || !nbt.hasKey("title", 8) ? "Unknown" : nbt.getString("title");
        val author = nbt == null || !nbt.hasKey("author", 8) ? "Unknown" : nbt.getString("author");
        val pagesTag = nbt == null || !nbt.hasKey("pages", 9) ? null : nbt.getTagList("pages", 8);
        var text = "\n\nUnknown";
        if (pagesTag != null) {
            val builder = new StringBuilder();
            for (var i = 0; i < pagesTag.tagCount(); i++)
                builder.append("\n\n").append(pagesTag.getStringTagAt(i));
            text = builder.toString();
        }
        val result = String.format(outFormat, title, author, text);
        val file = new File(String.format("./books/book_%s.txt", format.format(new Date())));
        try {
            FileUtils.forceMkdir(new File("./book/"));
            FileUtils.write(file, result, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
