package com.kines.kinesmod.mixins;

import com.kines.kinesmod.events.GuiContainerEvent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public class MixinGuiContainer extends GuiScreen {

    @Shadow public Container inventorySlots;
    /*@Unique
    private final GuiContainerHook kinesMod$hook = new GuiContainerHook(this);*/

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawGuiContainerForegroundLayer(II)V", shift = At.Shift.AFTER))
    private void onForegroundDraw(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new GuiContainerEvent.ForegroundDrawnEvent(this, this.inventorySlots, mouseX, mouseY, partialTicks));
        //kinesMod$hook.foregroundDrawn(mouseX, mouseY, partialTicks);
    }
}
