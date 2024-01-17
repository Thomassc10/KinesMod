package com.kines.kinesmod.events;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.eventhandler.Event;

public class GuiContainerEvent extends Event {

    public GuiContainer guiContainer;
    public Container container;
    public GuiContainerEvent(Object guiContainer, Container container) {
        this.guiContainer = (GuiContainer) guiContainer;
        this.container = container;
    }

    public static class ForegroundDrawnEvent extends GuiContainerEvent {

        public int mouseX;
        public int mouseY;
        public float partialTicks;
        public ForegroundDrawnEvent(Object guiContainer, Container container, int mouseX, int mouseY, float partialTicks) {
            super(guiContainer, container);
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.partialTicks = partialTicks;
        }
    }
}
