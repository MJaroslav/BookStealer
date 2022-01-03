package com.github.mjaroslav.bookstealer.event;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import com.github.mjaroslav.bookstealer.lib.ModInfo;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.resources.I18n;
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
                    I18n.format(BookStealerMod.getLangKey())));
        }
    }

    @SubscribeEvent
    public void onActionPerformedEvent(ActionPerformedEvent.Pre event) {
        if (event.gui instanceof GuiScreenBook) {
            val gui = (GuiScreenBook) event.gui;
            if (event.button.id == BUTTON_ID)
                BookStealerMod.saveBook(gui.bookObj);
        }
    }

    @SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event) {
        if (ModInfo.MOD_ID.equals(event.modID))
            BookStealerMod.updateConfigValues();
    }
}
