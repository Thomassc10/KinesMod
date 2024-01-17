package com.kines.kinesmod.features.performance;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.gui.GuiManager;
import com.kines.kinesmod.gui.elements.Point;
import com.kines.kinesmod.gui.elements.UIElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Fps extends UIElement {

    private final String display = EnumChatFormatting.GOLD + "Fps" + EnumChatFormatting.GRAY + "> " + EnumChatFormatting.WHITE;
    public Fps() {
        super("fps-display", new Point(10, 40));
        GuiManager.addElement(this);
    }

    @SubscribeEvent
    public void onOverlayRender(RenderGameOverlayEvent.Text event) {
        if (!Config.showFps) return;
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

        int fps = Minecraft.getDebugFPS();
        Point point = this.getPos();
        fontRenderer.drawStringWithShadow(display + fps, point.getX(), point.getY(), 0);
    }

    @Override
    public String display() {
        return display;
    }

    @Override
    public double scale() {
        return 1;
    }

    @Override
    public boolean toggled() {
        return Config.showFps;
    }
}
