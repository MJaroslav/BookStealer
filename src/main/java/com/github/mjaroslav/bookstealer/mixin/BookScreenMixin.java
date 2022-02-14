package com.github.mjaroslav.bookstealer.mixin;

import com.github.mjaroslav.bookstealer.util.ModUtils;
import com.github.mjaroslav.bookstealer.util.SaveAction;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookScreen.class)
public abstract class BookScreenMixin extends Screen {
    protected BookScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, 220, 200, 20,
                ModUtils.getButtonTranslatedText(), new SaveAction(this, ModUtils.getBookStackFromHands())));
    }
}
