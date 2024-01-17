package com.kines.kinesmod.events;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class CheckRenderEntityEvent extends Event {

    public Entity entity;
    public CheckRenderEntityEvent(Entity entity) {
        this.entity = entity;
    }
}
