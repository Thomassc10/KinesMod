package com.kines.kinesmod.features;

import com.kines.kinesmod.config.Config;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatMessagesFilter {

    // ♨ Selling multiple items for a limited time!
    // Profile ID:
    // You are playing on profile: Blueberry (Co-op)
    /*
    [WATCHDOG ANNOUNCEMENT]
    Watchdog has banned 8,702 players in the last
    Staff have banned an additional 8,335 in the
    Blacklisted modifications are a bannable
     */
    String[] messages = {"♨", "Profile ID:", "You are playing on profile:", "[WATCHDOG ANNOUNCEMENT]", "Watchdog has banned", "Staff have banned an additional", "Blacklisted modifications are a bannable",
                        "slid into the lobby!", "GEXP", "FIRE SALE", "from playing SkyBlock!", "joined the lobby!"};

    @SubscribeEvent
    public void onMessageReceive(ClientChatReceivedEvent event) {
        if (!Config.filterMessages) return;
        String message = event.message.getUnformattedText();

        for (String str : messages) {
            if (message.contains(str))
                event.setCanceled(true);
        }
    }
}
