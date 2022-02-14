package com.github.mjaroslav.bookstealer.mixin;

import com.github.mjaroslav.bookstealer.util.ModUtils;
import com.github.mjaroslav.bookstealer.util.SaveAction;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookEditScreen.class)
public abstract class BookEditScreenMixin extends Screen {
    protected BookEditScreenMixin(Text title) {
        super(title);
    }

    @Shadow
    private ItemStack itemStack;

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, 220, 200, 20,
                ModUtils.getButtonTranslatedText(), new SaveAction(this, itemStack)));
    }
}
