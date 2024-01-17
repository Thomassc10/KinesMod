package com.kines.kinesmod.features;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RemoveOverlays {

    @SubscribeEvent
    public void onOverlayRender(RenderGameOverlayEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (event.type == RenderGameOverlayEvent.ElementType.HEALTH) {
            if (Config.healthOverlay)
                event.setCanceled(true);
        }
        if (event.type == RenderGameOverlayEvent.ElementType.ARMOR) {
            if (Config.armorOverlay)
                event.setCanceled(true);
        }
        if (event.type == RenderGameOverlayEvent.ElementType.FOOD) {
            if (Config.foodOverlay)
                event.setCanceled(true);
        }
        if (event.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            if (Config.crosshairOverlay && Minecraft.getMinecraft().gameSettings.thirdPersonView == 1)
                event.setCanceled(true);
        }
    }
}
