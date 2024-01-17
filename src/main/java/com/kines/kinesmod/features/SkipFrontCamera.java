package com.kines.kinesmod.features;

import com.kines.kinesmod.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class SkipFrontCamera {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (!Config.skipFrontCamera) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings.keyBindTogglePerspective.isKeyDown())
            if (mc.gameSettings.thirdPersonView == 2)
                mc.gameSettings.thirdPersonView = 0;
    }
}
