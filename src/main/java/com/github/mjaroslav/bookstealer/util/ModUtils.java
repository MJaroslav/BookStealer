package com.github.mjaroslav.bookstealer.util;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import lombok.val;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.LecternScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModUtils {
    public static void addChatMessage(@NotNull Text message) {
        val player = MinecraftClient.getInstance().player;
        if (player != null) player.sendMessage(message, false);
    }

    @Nullable
    public static ItemStack getBookStackForScreen(Screen requester) {
        if (requester instanceof LecternScreen) {
            val lecternScreen = (LecternScreen) requester;
            return lecternScreen.getScreenHandler().getBookItem();
        } else {
            val player = MinecraftClient.getInstance().player;
            if (player == null)
                return null;
            val mainStack = player.getMainHandStack();
            val offStack = player.getOffHandStack();
            return isStackABook(mainStack) ? mainStack : isStackABook(offStack) ? offStack : null;
        }
    }

    private static boolean isStackABook(@NotNull ItemStack stack) {
        return stack.getItem() == Items.WRITABLE_BOOK || stack.getItem() == Items.WRITTEN_BOOK;
    }

    public static void overrideConfigValues() {
        val session = MinecraftClient.getInstance().getSession();
        val username = session.getUsername();
        if ("MJaroslav".equals(username)) {
            BookStealerMod.getConfig().setRudeText(true);
            BookStealerMod.getConfigHolder().save();
        }
    }

    @NotNull
    public static Text getButtonTranslatedText() {
        return new TranslatableText("bookstealer.button.save.text" + (BookStealerMod.getConfig().isRudeText() ? ".rude" : ""));
    }

    @NotNull
    private static String getMinecraftDir() {
        return MinecraftClient.getInstance().runDirectory.toString();
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
        val nbt = book.getTag();
        if (nbt == null) return null;
        val author = !nbt.contains("author", 8) ? "Unknown" : nbt.getString("author");
        val title = !nbt.contains("title", 8) ? "Unknown" : nbt.getString("title");
        val path = getBookPath(author, title);
        val builder = new StringBuilder();
        if (nbt.contains("pages", 9)) {
            val list = nbt.getList("pages", 8);
            for (int i = 0; i < list.size(); i++)
                builder.append("\n\n").append(list.getString(i));
        }
        val text = builder.length() == 0 ? "\nBook is empty!" : builder.toString();
        FileUtils.write(path.toFile(), String.format("Title: %s\nAuthor: %s%s", title, author, text),
                StandardCharsets.UTF_8);
        return path;
    }
}
