package com.github.mjaroslav.bookstealer.util;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.experimental.UtilityClass;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@SideOnly(Side.CLIENT)
@UtilityClass
public class ModUtils {
    public void addChatMessage(@NotNull IChatComponent message) {
        val player = Minecraft.getMinecraft().thePlayer;
        if (player != null) player.addChatMessage(message);
    }

    public void overrideConfigValues() {
        val session = Minecraft.getMinecraft().getSession();
        if (session == null) return;
        val username = session.getUsername();
        if ("MJaroslav".equals(username)) BookStealerMod.getConfig().setRudeText(true);
    }

    @NotNull
    public String getButtonTranslatedText() {
        return I18n.format("bookstealer.button.save.text" + (BookStealerMod.getConfig().isRudeText() ? ".rude" : ""));
    }

    @NotNull
    private String getMinecraftDir() {
        return Minecraft.getMinecraft().mcDataDir.toString();
    }

    @NotNull
    public String getBookSavingDir() {
        return BookStealerMod.getConfig().getSaveDir().replace("%MINECRAFT%", getMinecraftDir());
    }

    @NotNull
    private String getFormattedDate() {
        val formatter = new SimpleDateFormat(BookStealerMod.getConfig().getDateFormat());
        return formatter.format(new Date());
    }

    private Path getBookPath(@NotNull String author, @NotNull String title) {
        val fileName = BookStealerMod.getConfig().getBookFileFormat().replace("%AUTHOR%", author)
                .replace("%TITLE%", title).replace("%DATE%", getFormattedDate());
        return Paths.get(getBookSavingDir()).resolve(fileName);
    }

    @Nullable
    public Path saveBook(@Nullable ItemStack book) throws IOException {
        if (book == null || !book.hasTagCompound()) return null;
        val nbt = book.getTagCompound();
        val author = !nbt.hasKey("author", 8) ? "Unknown" : nbt.getString("author");
        val title = !nbt.hasKey("title", 8) ? "Unknown" : nbt.getString("title");
        val path = getBookPath(author, title);
        val builder = new StringBuilder();
        if (nbt.hasKey("pages", 9)) {
            val list = nbt.getTagList("pages", 8);
            for (int i = 0; i < list.tagCount(); i++)
                builder.append("\n\n").append(list.getStringTagAt(i));
        }
        val text = builder.length() == 0 ? "\n\nBook is empty!" : builder.toString();
        FileUtils.write(path.toFile(), String.format("Title: %s\nAuthor: %s%s", title, author, text),
                StandardCharsets.UTF_8);
        return path;
    }

    public @NotNull IChatComponent makeTranslatedAndColoredComponent(@NotNull EnumChatFormatting color,
                                                                     @NotNull String langKey, @NotNull Object... args) {
        val result = new ChatComponentTranslation(langKey, args);
        result.getChatStyle().setColor(color);
        return result;
    }
}
