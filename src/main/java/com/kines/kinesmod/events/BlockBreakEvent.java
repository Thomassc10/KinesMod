package com.kines.kinesmod.events;

import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Event;

public class BlockBreakEvent extends Event {

    public BlockPos position;
    public int progress;
    public S25PacketBlockBreakAnim packet;
    public BlockBreakEvent(S25PacketBlockBreakAnim packet) {
        this.packet = packet;
        /*this.position = position;
        this.progress = progress;*/
    }
}
