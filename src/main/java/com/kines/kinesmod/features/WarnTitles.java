package com.kines.kinesmod.features;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.utils.Island;
import com.kines.kinesmod.utils.TitleUtils;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WarnTitles {

    @SubscribeEvent
    public void onMessageReceive(ClientChatReceivedEvent event) {
        if (!Utils.isInSkyBlock) return;
        String message = Utils.stripColor(event.message.getUnformattedText());
        if (Config.lordJawbus) {
            if (message.contains("was killed by Lord Jawbus.")) {
                TitleUtils.sendTitle(EnumChatFormatting.DARK_AQUA + "Jawbus", 3);
                return;
            }
        }

        if (Config.endstoneProtector) {
            if (message.contains("The ground begins to shake as an Endstone Protector rises from below!")) {
                TitleUtils.sendTitle(EnumChatFormatting.RED + "Protector", 3);
                return;
            }
        }

        if (Config.miningSpeedBoost) {
            if (message.contains("Mining Speed Boost is now available!")) {
                if (Config.showOnlyInsideMines) {
                    if (Utils.island == Island.CRYSTAL_HOLLOWS || Utils.island == Island.DWARVEN_MINES) {
                        TitleUtils.sendTitle(EnumChatFormatting.GOLD + "Mining Speed", 3);
                    }
                } else {
                    TitleUtils.sendTitle(EnumChatFormatting.GOLD + "Mining Speed", 3);
                }
                return;
            }
        }
        if (Config.primalFearAlert) {
            if (message.contains("FEAR. A Primal Fear has been summoned!"))  {
                TitleUtils.sendTitle(EnumChatFormatting.DARK_PURPLE + "Fear", 3);
            }
        }
    }
}
