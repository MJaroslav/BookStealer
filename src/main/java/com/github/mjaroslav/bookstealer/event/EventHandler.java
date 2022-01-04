package com.github.mjaroslav.bookstealer.event;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import com.github.mjaroslav.bookstealer.util.ModUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventHandler {
    public static final EventHandler INSTANCE = new EventHandler();
    private static final int BUTTON_ID = 228;
    private static final int BOOK_IMAGE_HEIGHT = 192;

    @SuppressWarnings({"unchecked", "unused"})
    @SubscribeEvent
    public void onInitGuiEvent(InitGuiEvent.Post event) {
        if (event.gui instanceof GuiScreenBook) {
            val gui = (GuiScreenBook) event.gui;
            event.buttonList.add(new GuiButton(BUTTON_ID, gui.width / 2 - 100, BOOK_IMAGE_HEIGHT + 28, 200, 20,
                    ModUtils.getButtonTranslatedText()));
        }
    }

    @SubscribeEvent
    public void onActionPerformedEvent(ActionPerformedEvent.Pre event) {
        if (event.gui instanceof GuiScreenBook) {
            val gui = (GuiScreenBook) event.gui;
            if (event.button.id == BUTTON_ID) {
                try {
                    val result = ModUtils.saveBook(gui.bookObj);
                    if (result == null) {
                        val message = new ChatComponentTranslation("bookstealer.text.save.empty");
                        message.getChatStyle().setColor(EnumChatFormatting.YELLOW);
                        ModUtils.addChatMessage(message);
                    } else {
                        val message = new ChatComponentTranslation("bookstealer.text.save.done")
                                .appendText(" ");
                        message.getChatStyle().setColor(EnumChatFormatting.GREEN);
                        val fileComponent = new ChatComponentText(result.getFileName().toString());
                        val style = fileComponent.getChatStyle();
                        style.setColor(EnumChatFormatting.GREEN);
                        style.setUnderlined(true);
                        style.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, result.toAbsolutePath()
                                .toString()));
                        message.appendSibling(fileComponent);
                        ModUtils.addChatMessage(message);
                    }
                } catch (Exception e) {
                    BookStealerMod.getLog().warn("Can't save book!", e);
                    val message = new ChatComponentTranslation("bookstealer.text.save.error");
                    message.getChatStyle().setColor(EnumChatFormatting.RED);
                    ModUtils.addChatMessage(message);
                }
            }
        }
    }
}
