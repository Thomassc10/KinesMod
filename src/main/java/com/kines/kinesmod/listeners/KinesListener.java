package com.kines.kinesmod.listeners;

import com.kines.kinesmod.KinesMod;
import com.kines.kinesmod.events.GemstoneBreakEvent;
import com.kines.kinesmod.events.PacketEvent;
import com.kines.kinesmod.gui.EditLocationsGui;
import com.kines.kinesmod.utils.*;
import gg.essential.universal.UScreen;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class KinesListener {

    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRender(TickEvent.ClientTickEvent event) {
        if (KinesMod.openGui == 1) {
            UScreen.displayScreen(KinesMod.config.gui());
            KinesMod.openGui = 0;
        }
        if (KinesMod.editGui == 1) {
            UScreen.displayScreen(new EditLocationsGui());
            KinesMod.editGui = 0;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!Utils.isInSkyBlock)
            Utils.hasSkyBlockScoreboard();
    }

    @SubscribeEvent
    public void onUnload(WorldEvent.Unload event) {
        Utils.isInSkyBlock = false;
        Utils.island = Island.NONE;
    }

    @SubscribeEvent
    public void onTickLoc(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!Utils.isInSkyBlock) return;
        if (Utils.island != Island.NONE) return;

        List<String> tabList = TabListUtils.getTabList();

        String area = "";
        for (String s : tabList) {
            if (Utils.stripColor(s).contains("Area: "))
                area = Utils.stripColor(s.replace("Area: ", ""));
        }

        Utils.island = Island.getByName(area);
    }

    private int lastProgress = -1;
    @SubscribeEvent
    public void onPacketBreakAnim(PacketEvent.ReceivePacket event) {
        if (event.packet instanceof S25PacketBlockBreakAnim) {
            if (!Utils.isInSkyBlock) return;
            if (Utils.island != Island.CRYSTAL_HOLLOWS) return;
            if (mc.objectMouseOver == null) return;
            if (mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) return;
            if (mc.objectMouseOver.getBlockPos() == null) return;

            S25PacketBlockBreakAnim packet = (S25PacketBlockBreakAnim) event.packet;
            BlockPos pos = mc.objectMouseOver.getBlockPos();

            if (!packet.getPosition().equals(pos)) return;
            IBlockState state = mc.theWorld.getBlockState(pos);
            if (state.getBlock() == Blocks.stained_glass_pane || state.getBlock() == Blocks.stained_glass) {

                if (packet.getProgress() == 10 && lastProgress == 9) {
                    MinecraftForge.EVENT_BUS.post(new GemstoneBreakEvent(packet.getPosition(), packet.getProgress()));
                }

                lastProgress = packet.getProgress();
            }
        }
    }
}
