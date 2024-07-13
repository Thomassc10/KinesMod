package com.kines.kinesmod.features.events;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Diana {

    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onMessage(ClientChatReceivedEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (Config.alertInquisitor == 0) return;
        if (event.type != 0) return;

        if (event.message.getUnformattedText().contains("Danger! You dug out a Minos Inquisitor!")) {
            int x = mc.thePlayer.getPosition().getX();
            int y = mc.thePlayer.getPosition().getY();
            int z = mc.thePlayer.getPosition().getZ();
            if (Config.alertInquisitor == 1) {
                mc.thePlayer.sendChatMessage("/pc x: " + x + ", y: " + y + ", z: " + z);
            } else mc.thePlayer.sendChatMessage("/ac x: " + x + ", y: " + y + ", z: " + z);
        }
    }
}
