package com.kines.kinesmod.events;

import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Event;

public class GemstoneBreakEvent extends Event {

    public BlockPos position;
    public int progress;
    public GemstoneBreakEvent(BlockPos position, int progress) {
        this.position = position;
        this.progress = progress;
    }
}
