package com.kines.kinesmod.features.mining;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.events.GemstoneBreakEvent;
import com.kines.kinesmod.utils.Island;
import com.kines.kinesmod.utils.RenderUtils;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.util.vector.Vector3f;

import java.util.*;

public class CrystalHollows {

    // 552.5 116 474.5 Mithril Deposits
    // 474.5 116 474.5 Jungle
    // 474.5 116 552.5 Goblin Holdout
    // 552.5 116 552.5 Precursor Remnants

    public static Map<BlockPos, String> map = new HashMap<>();

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.chWaypoints) return;
        if (Utils.island != Island.CRYSTAL_HOLLOWS) return;

        RenderUtils.renderWaypoint(new Vector3f(552.5f, 118f, 474.5f), "Mithril Deposits", true, event.partialTicks);
        RenderUtils.renderWaypoint(new Vector3f(474.5f, 118f, 474.5f), "Jungle", true, event.partialTicks);
        RenderUtils.renderWaypoint(new Vector3f(474.5f, 118f, 552.5f), "Goblin Holdout", true, event.partialTicks);
        RenderUtils.renderWaypoint(new Vector3f(552.5f, 118f, 552.5f), "Precursor Remnants", true, event.partialTicks);
    }

    @SubscribeEvent
    public void onBreak(GemstoneBreakEvent event) {
        if (!Config.gemstoneTimer) return;
        if (map.isEmpty()) {
            map.put(event.position, Utils.formatTimer(5, 0));
            return;
        }

        BlockPos blockPos = null;

        for (BlockPos pos : map.keySet()) {
            double dist = Math.round(Math.sqrt(pos.distanceSq(event.position)));
            if (dist < 10) {
                blockPos = pos;
            }
        }

        if (blockPos != null) {
            if (map.get(blockPos).contains("Ready")) {
                map.remove(blockPos);
                savePos(event.position, 5, 0);
            }
            return;
        }

        savePos(event.position, 5, 0);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (!Config.gemstoneTimer) return;
        if (Utils.island != Island.CRYSTAL_HOLLOWS) return;
        if (map.isEmpty()) return;

        for (BlockPos pos : map.keySet()) {
            RenderUtils.renderWaypoint(pos, map.get(pos), true, event.partialTicks);
        }
    }

    private int tick = 0;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (map.isEmpty()) return;
        if (!Config.gemstoneTimer) return;
        if (Utils.island != Island.CRYSTAL_HOLLOWS) return;


        if (tick == 20) {
            tick = 0;
            for (BlockPos pos : map.keySet()) {
                if (map.get(pos).contains("Ready")) continue;
                String[] split = Utils.stripColor(map.get(pos)).split(":");
                int sec = Integer.parseInt(split[1]);
                int min = Integer.parseInt(split[0]);

                if (sec == 0 && min == 0) {
                    map.put(pos, EnumChatFormatting.GREEN + "Ready");
                    continue;
                }

                if (sec == 0) {
                    sec = 59;
                    min--;
                    savePos(pos, min, sec);
                    continue;
                }

                sec--;
                savePos(pos, min, sec);
            }
            return;
        }

        tick++;
    }

    private void savePos(BlockPos pos, int min, int sec) {
        map.put(pos, EnumChatFormatting.RED + Utils.formatTimer(min, sec));
    }

    @SubscribeEvent
    public void onUnload(WorldEvent.Unload event) {
        map.clear();
    }

    @SubscribeEvent
    public void onMessage(ClientChatReceivedEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (Utils.island != Island.CRYSTAL_HOLLOWS) return;
        if (!Config.chPass) return;

        if (event.message.getUnformattedText().contains("Your pass to the Crystal Hollows will expire in 1 minute")) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/purchasecrystallhollowspass");
        }
    }
}
