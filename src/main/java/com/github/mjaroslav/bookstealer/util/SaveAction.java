package com.github.mjaroslav.bookstealer.util;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public class SaveAction implements ButtonWidget.PressAction {
    @Nullable
    private final ItemStack itemStack;

    @Override
    public void onPress(ButtonWidget button) {
        try {
            val result = ModUtils.saveBook(itemStack);
            if (result == null) {
                val message = new TranslatableText("bookstealer.text.save.empty");
                val style = message.getStyle().withColor(Formatting.YELLOW);
                message.setStyle(style);
                ModUtils.addChatMessage(message);
            } else {
                val message = new TranslatableText("bookstealer.text.save.done")
                        .append(" ");
                message.getStyle().withColor(Formatting.GREEN);
                val fileComponent = new LiteralText(result.getFileName().toString());
                val style = fileComponent.getStyle().withColor(Formatting.GREEN).withUnderline(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, result.toAbsolutePath()
                        .toString()));
                fileComponent.setStyle(style);
                message.append(fileComponent);
                ModUtils.addChatMessage(message);
            }
        } catch (Exception e) {
            BookStealerMod.getLog().warn("Can't save book!", e);
            val message = new TranslatableText("bookstealer.text.save.error");
            val style = message.getStyle().withColor(Formatting.RED);
            message.setStyle(style);
            ModUtils.addChatMessage(message);
        }
    }
}
