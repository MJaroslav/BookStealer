package com.github.mjaroslav.bookstealer.util;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.LecternScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public class SaveAction implements ButtonWidget.PressAction {
    @NotNull
    private final Screen parent;
    @Nullable
    private final ItemStack itemStack;

    @Override
    public void onPress(ButtonWidget button) {
        if (parent instanceof LecternScreen) {
            val message = Text.translatable("bookstealer.text.save.lectern");
            message.setStyle(message.getStyle().withColor(Formatting.YELLOW));
            ModUtils.addChatMessage(message);
            return;
        }
        try {
            val result = ModUtils.saveBook(itemStack);
            if (result == null) {
                val message = Text.translatable("bookstealer.text.save.empty");
                message.setStyle(message.getStyle().withColor(Formatting.YELLOW));
                ModUtils.addChatMessage(message);
            } else {
                val message = Text.translatable("bookstealer.text.save.done")
                        .append(" ");
                message.setStyle(message.getStyle().withColor(Formatting.GREEN));
                val fileComponent = Text.literal(result.getFileName().toString());
                fileComponent.setStyle(fileComponent.getStyle().withColor(Formatting.GREEN).withUnderline(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, result.toAbsolutePath()
                        .toString())));
                message.append(fileComponent);
                ModUtils.addChatMessage(message);
            }
        } catch (Exception e) {
            BookStealerMod.warn("Can't save book!", e);
            val message = Text.translatable("bookstealer.text.save.error");
            val style = message.getStyle().withColor(Formatting.RED);
            message.setStyle(style);
            ModUtils.addChatMessage(message);
        }
    }
}
