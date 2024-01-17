package com.kines.kinesmod.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketEvent extends Event {

    public Packet<?> packet;
    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public static class ReceivePacket extends PacketEvent {

        public ReceivePacket(Packet<?> packet) {
            super(packet);
        }
    }
}
