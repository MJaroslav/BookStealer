package com.github.mjaroslav.bookstealer.lib;

import com.github.mjaroslav.bookstealer.BookStealerMod;
import com.github.mjaroslav.bookstealer.util.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.val;
import lombok.var;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.Constants.NBT;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraft.util.EnumChatFormatting.RESET;

@SideOnly(Side.CLIENT)
public class BooksTab extends CreativeTabs {
    public static final BooksTab INSTANCE = new BooksTab();

    private final List<ItemStack> books = new ArrayList<>();
    private boolean needUpdate;

    public BooksTab() {
        super(ModInfo.MOD_ID);
    }

    @Override
    public @NotNull Item getTabIconItem() {
        return Items.writable_book;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void displayAllReleventItems(@NotNull List list) {
        list.add(getInfoItem());
        if (needUpdate) {
            books.clear();
            books.addAll(loadFromNBT());
            needUpdate = false;
        }
        list.addAll(books);
        super.displayAllReleventItems(list);
    }

    public static @NotNull ItemStack getInfoItem() {
        val result = new ItemStack(Items.paper, 1);
        result.setStackDisplayName(RESET + I18n.format("itemGroup.bookstealer.info.name"));
        val display = result.getTagCompound().getCompoundTag("display");
        val lore = new NBTTagList();
        lore.appendTag(new NBTTagString(EnumChatFormatting.ITALIC.toString() + EnumChatFormatting.GRAY + I18n.format("itemGroup.bookstealer.info.shift")));
        lore.appendTag(new NBTTagString(EnumChatFormatting.ITALIC.toString() + EnumChatFormatting.GRAY + I18n.format("itemGroup.bookstealer.info.command")));
        display.setTag("Lore", lore);
        return result;
    }

    public static @NotNull Path getFilePath() {
        return Paths.get(ModUtils.getBookSavingDir()).resolve("creative_tab.dat");
    }

    public static @NotNull List<ItemStack> loadFromNBT() {
        val file = getFilePath().toFile();
        if (file.isFile()) {
            try {
                val result = new ArrayList<ItemStack>();
                val root = CompressedStreamTools.read(file);
                val list = root.getTagList("list", NBT.TAG_COMPOUND);
                for (var i = 0; i < list.tagCount(); i++)
                    result.add(ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i)));
                return result;
            } catch (IOException e) {
                BookStealerMod.getLog().error("Can't load " + file.getAbsolutePath(), e);
            }
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeToNBT(@NotNull List<ItemStack> books) throws IOException {
        val parent = getFilePath().getParent().toFile();
        if (!parent.isDirectory())
            parent.mkdirs();
        val file = getFilePath().toFile();
        val root = new NBTTagCompound();
        val list = new NBTTagList();
        books.forEach(book -> list.appendTag(book.writeToNBT(new NBTTagCompound())));
        root.setTag("list", list);
        CompressedStreamTools.write(root, file);
    }

    public static int size() {
        return INSTANCE.books.size();
    }

    public static void removeAll() throws IOException {
        INSTANCE.books.clear();
        writeToNBT(INSTANCE.books);
        markForUpdate();
    }

    public static @Nullable ItemStack removeByIndex(int index) throws IOException {
        val result = INSTANCE.books.remove(index);
        writeToNBT(INSTANCE.books);
        markForUpdate();
        return result;
    }

    public static boolean removeStack(@Nullable ItemStack bookStack) throws IOException {
        val result = INSTANCE.books.removeIf(stack -> ItemStack.areItemStacksEqual(stack, bookStack));
        if (result) {
            writeToNBT(INSTANCE.books);
            markForUpdate();
        }
        return result;
    }

    public static boolean addBookStack(@Nullable ItemStack bookStack) throws IOException {
        if (bookStack == null || !bookStack.hasTagCompound())
            return false;
        INSTANCE.books.add(bookStack);
        writeToNBT(INSTANCE.books);
        markForUpdate();
        return true;
    }

    public static void markForUpdate() {
        INSTANCE.needUpdate = true;
    }
}
