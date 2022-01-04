package com.github.mjaroslav.bookstealer.util;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModUtils {
    public static void addChatMessage(@NotNull ITextComponent message) {
        val player = Minecraft.getMinecraft().player;
        if (player != null) player.sendMessage(message);
    }

    public static void overrideConfigValues() {
        val session = Minecraft.getMinecraft().getSession();
        val username = session.getUsername();
        if ("MJaroslav".equals(username)) BookStealerMod.getConfig().setRudeText(true);
    }

    @NotNull
    public static String getButtonTranslatedText() {
        return I18n.format("bookstealer.button.save.text" + (BookStealerMod.getConfig().isRudeText() ? ".rude" : ""));
    }

    @NotNull
    private static String getMinecraftDir() {
        return Minecraft.getMinecraft().gameDir.toString();
    }

    @NotNull
    private static String getBookSavingDir() {
        return BookStealerMod.getConfig().getSaveDir().replace("%MINECRAFT%", getMinecraftDir());
    }

    @NotNull
    private static String getFormattedDate() {
        val formatter = new SimpleDateFormat(BookStealerMod.getConfig().getDateFormat());
        return formatter.format(new Date());
    }

    private static Path getBookPath(@NotNull String author, @NotNull String title) {
        val fileName = BookStealerMod.getConfig().getBookFileFormat().replace("%AUTHOR%", author)
                .replace("%TITLE%", title).replace("%DATE%", getFormattedDate());
        return Paths.get(getBookSavingDir()).resolve(fileName);
    }

    @Nullable
    public static Path saveBook(@Nullable ItemStack book) throws Exception {
        if (book == null) return null;
        val nbt = book.getTagCompound();
        if (nbt == null) return null;
        val author = !nbt.hasKey("author", 8) ? "Unknown" : nbt.getString("author");
        val title = !nbt.hasKey("title", 8) ? "Unknown" : nbt.getString("title");
        val path = getBookPath(author, title);
        val builder = new StringBuilder();
        if (nbt.hasKey("pages", 9)) {
            val list = nbt.getTagList("pages", 8);
            for (int i = 0; i < list.tagCount(); i++)
                builder.append("\n").append(list.getStringTagAt(i));
        }
        val text = builder.length() == 0 ? "\nBook is empty!" : builder.toString();
        FileUtils.write(path.toFile(), String.format("Title: %s\nAuthor: %s\nPages NBT:%s", title, author, text),
                StandardCharsets.UTF_8);
        return path;
    }
}
