package com.github.mjaroslav.bookstealer.event;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import com.github.mjaroslav.bookstealer.lib.BooksTab;
import com.github.mjaroslav.bookstealer.util.ModUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventHandler {
    public static final EventHandler INSTANCE = new EventHandler();
    private static final int BUTTON_ID = 228;
    private static final int BOOK_IMAGE_HEIGHT = 192;

    private GuiButton currentButton;

    @SuppressWarnings({"unchecked"})
    @SubscribeEvent
    public void onInitGuiEvent(@NotNull InitGuiEvent.Post event) {
        if (event.gui instanceof GuiScreenBook) {
            val gui = (GuiScreenBook) event.gui;
            event.buttonList.add(new SaveButton(gui.width));
        }
    }

    @SubscribeEvent
    public void onActionPerformedEvent(@NotNull ActionPerformedEvent.Pre event) {
        if (event.gui instanceof GuiScreenBook) {
            val gui = (GuiScreenBook) event.gui;
            if (event.button.id == BUTTON_ID) {
                try {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                        val result = BooksTab.addBookStack(gui.bookObj);
                        if (!result) {
                            val message = new ChatComponentTranslation("bookstealer.text.save.null");
                            message.getChatStyle().setColor(EnumChatFormatting.YELLOW);
                            ModUtils.addChatMessage(message);
                        } else {
                            val message = new ChatComponentTranslation("bookstealer.text.save.tab.done")
                                    .appendText(" ");
                            message.getChatStyle().setColor(EnumChatFormatting.GREEN);
                            ModUtils.addChatMessage(message);
                        }
                    } else {
                        val result = ModUtils.saveBook(gui.bookObj);
                        if (result == null) {
                            val message = new ChatComponentTranslation("bookstealer.text.save.null");
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

    private static class SaveButton extends GuiButton {
        private boolean toggle = false;

        private SaveButton(int width) {
            super(BUTTON_ID, width / 2 - 100, BOOK_IMAGE_HEIGHT + 28, 200, 20,
                    ModUtils.getButtonTranslatedText());
        }

        @Override
        public void drawButton(@NotNull Minecraft mc, int x, int y) {
            val shiftIsPressed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
            if (toggle != shiftIsPressed) {
                displayString = (shiftIsPressed ? EnumChatFormatting.UNDERLINE : "")
                        + ModUtils.getButtonTranslatedText();
                toggle = shiftIsPressed;
            }
            super.drawButton(mc, x, y);
        }
    }
}
