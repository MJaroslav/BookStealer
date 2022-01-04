package com.github.mjaroslav.bookstealer.event;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import com.github.mjaroslav.bookstealer.util.ModUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventHandler {
    public static final EventHandler INSTANCE = new EventHandler();
    private static final int BUTTON_ID = 228;
    private static final int BOOK_IMAGE_HEIGHT = 192;

    @SubscribeEvent
    public void onInitGuiEvent(InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiScreenBook) {
            val gui = (GuiScreenBook) event.getGui();
            event.getButtonList().add(new GuiButton(BUTTON_ID, gui.width / 2 - 100, BOOK_IMAGE_HEIGHT + 28, 200, 20,
                    ModUtils.getButtonTranslatedText()));
        }
    }

    @SubscribeEvent
    public void onActionPerformedEvent(ActionPerformedEvent.Pre event) {
        if (event.getGui() instanceof GuiScreenBook) {
            val gui = (GuiScreenBook) event.getGui();
            if (event.getButton().id == BUTTON_ID) {
                try {
                    val result = ModUtils.saveBook(gui.book);
                    if (result == null) {
                        val message = new TextComponentTranslation("bookstealer.text.save.empty");
                        message.getStyle().setColor(TextFormatting.YELLOW);
                        ModUtils.addChatMessage(message);
                    } else {
                        val message = new TextComponentTranslation("bookstealer.text.save.done")
                                .appendText(" ");
                        message.getStyle().setColor(TextFormatting.GREEN);
                        val fileComponent = new TextComponentString(result.getFileName().toString());
                        val style = fileComponent.getStyle();
                        style.setColor(TextFormatting.GREEN);
                        style.setUnderlined(true);
                        style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, result.toAbsolutePath()
                                .toString()));
                        message.appendSibling(fileComponent);
                        ModUtils.addChatMessage(message);
                    }
                } catch (Exception e) {
                    BookStealerMod.getLog().warn("Can't save book!", e);
                    val message = new TextComponentTranslation("bookstealer.text.save.error");
                    message.getStyle().setColor(TextFormatting.RED);
                    ModUtils.addChatMessage(message);
                }
            }
        }
    }
}
