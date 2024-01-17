package com.kines.kinesmod.features;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.utils.Island;
import com.kines.kinesmod.utils.ScoreboardUtils;
import com.kines.kinesmod.utils.TitleUtils;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CrimsonIsle {

    private final Minecraft mc = Minecraft.getMinecraft();
    private boolean invited = false;
    private long time;
    private int x;
    private int y;
    private int z;

    @SubscribeEvent
    public void onVanquisherMessage(ClientChatReceivedEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (Config.alertVanquisher == 0) return;
        if (Utils.island != Island.CRIMSON_ISLE) return;

        if (event.message.getUnformattedText().contains("A Vanquisher is spawning nearby!")) {
            x = mc.thePlayer.getPosition().getX();
            y = mc.thePlayer.getPosition().getY();
            z = mc.thePlayer.getPosition().getZ();
            if (!Config.partyMembers.isEmpty()) {
                mc.thePlayer.sendChatMessage("/p " + Config.partyMembers);
                time = System.currentTimeMillis() + 5000;
                invited = true;
            } else {
                if (Config.alertVanquisher == 1) {
                    mc.thePlayer.sendChatMessage("/pc x: " + x + ", y: " + y + ", z: " + z);
                } else mc.thePlayer.sendChatMessage("/ac x: " + x + ", y: " + y + ", z: " + z);
            }
        }
    }

    @SubscribeEvent
    public void onTickWarp(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!invited) return;

        if (System.currentTimeMillis() < time) {
            mc.thePlayer.sendChatMessage("/p warp");
            mc.thePlayer.sendChatMessage("/pc x: " + x + ", y: " + y + ", z: " + z);
            mc.thePlayer.sendChatMessage("/p disband");
            invited = false;
        }
    }

    @SubscribeEvent
    public void onInviteMessage(ClientChatReceivedEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.acceptParty) return;

        String[] party = Config.partyMembers.split(" ");

        for (String s : party) {
            if (event.message.getUnformattedText().contains(s + " has invited you to join their party!")) {
                mc.thePlayer.sendChatMessage("/party accept " + s);
            }
        }
    }

    private boolean alerted = false;

    @SubscribeEvent
    public void onTickGhast(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!Utils.isInSkyBlock) return;
        if (!Config.alertGhast) return;
        if (alerted) return;

        ScoreboardUtils.getSidebarLines().forEach(line -> {
            if (line.contains("9:00pm")) {
                alerted = true;
                TitleUtils.sendTitle(EnumChatFormatting.RED + "Ghast", 3);
            }
        });
    }
}
