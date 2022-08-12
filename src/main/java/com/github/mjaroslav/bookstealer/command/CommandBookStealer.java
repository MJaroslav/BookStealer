package com.github.mjaroslav.bookstealer.command;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import com.github.mjaroslav.bookstealer.lib.BooksTab;
import com.github.mjaroslav.bookstealer.lib.ModInfo;
import com.github.mjaroslav.bookstealer.util.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

import static net.minecraft.util.EnumChatFormatting.GREEN;
import static net.minecraft.util.EnumChatFormatting.YELLOW;

@SideOnly(Side.CLIENT)
public class CommandBookStealer extends CommandBase {
    @Override
    public String getCommandName() {
        return ModInfo.MOD_ID;
    }

    @Override
    public String getCommandUsage(@NotNull ICommandSender sender) {
        return "commands." + ModInfo.MOD_ID + ".usage";
    }

    @Override
    public boolean canCommandSenderUseCommand(@NotNull ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(@NotNull ICommandSender sender, @NotNull String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("tab")) {
                try {
                    if (args.length == 2 && args[1].equals("clear")) {
                        BooksTab.removeAll();
                        ModUtils.addChatMessage(ModUtils.makeTranslatedAndColoredComponent(GREEN,
                                "command.bookstealer.tab.clear.done"));
                    } else if (args[1].equals("remove")) {
                        if (args.length == 3) {
                            if (BooksTab.size() > 0) {
                                val index = parseIntBounded(sender, args[2], 1, BooksTab.size());
                                val removed = BooksTab.removeByIndex(index - 1);
                                if (removed != null)
                                    ModUtils.addChatMessage(ModUtils.makeTranslatedAndColoredComponent(GREEN,
                                            "command.bookstealer.tab.remove.index.done", index));
                                else
                                    ModUtils.addChatMessage(ModUtils.makeTranslatedAndColoredComponent(YELLOW,
                                            "command.bookstealer.tab.remove.null"));
                            }
                        } else if (args.length == 2) {
                            if (BooksTab.removeStack(Minecraft.getMinecraft().thePlayer.getHeldItem()))
                                ModUtils.addChatMessage(ModUtils.makeTranslatedAndColoredComponent(GREEN,
                                        "command.bookstealer.tab.remove.done"));
                            else
                                ModUtils.addChatMessage(ModUtils.makeTranslatedAndColoredComponent(YELLOW,
                                        "command.bookstealer.tab.remove.null"));
                        } else throw new CommandException(getCommandUsage(sender) + ".tab");
                    } else throw new CommandException(getCommandUsage(sender) + ".tab");
                } catch (IOException e) {
                    BookStealerMod.getLog().error("Can't save " + BooksTab.getFilePath(), e);
                    ModUtils.addChatMessage(ModUtils.makeTranslatedAndColoredComponent(YELLOW,
                            "command.bookstealer.tab.error"));
                }
            }
        } else throw new CommandException(getCommandUsage(sender));
    }

    @SuppressWarnings("rawtypes")
    @Override
    public @Nullable List addTabCompletionOptions(@NotNull ICommandSender sender, @NotNull String[] args) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, "tab") : args.length == 2 &&
                args[0].equals("tab") ? getListOfStringsMatchingLastWord(args, "clear", "remove") : null;
    }
}
