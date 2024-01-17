package com.kines.kinesmod.gui;

import com.kines.kinesmod.gui.elements.UIElement;
import gg.essential.universal.UResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.awt.*;

public class LocationButton extends GuiButton {

    private final UIElement element;
    public float x;
    public float y;
    private float x2;
    private float y2;
    public LocationButton(UIElement element) {
        super(-1, 0, 0, null);
        this.element = element;
        updateLocations();
    }

    private void updateLocations() {
        x = element.getX();
        y = element.getY();
        x2 = x + Minecraft.getMinecraft().fontRendererObj.getStringWidth(element.display());
        y2 = y + element.getHeight();
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        updateLocations();
        hovered = mouseX >= x * element.scale && mouseY >= y * element.scale && mouseX < x2 * element.scale && mouseY < y2 * element.scale;

        int stringWidth = mc.fontRendererObj.getStringWidth(element.display());

        drawRect(xPosition, yPosition, xPosition + stringWidth, yPosition + height / 2, (hovered ? new Color(255, 255, 255, 100).getRGB() : new Color(255, 255, 255, 40).getRGB()));

        GlStateManager.pushMatrix();
        drawCenteredString(mc.fontRendererObj, element.display(), this.xPosition + mc.fontRendererObj.getStringWidth(element.display()) / 2, this.yPosition + (this.height /*- 20*/ - element.getHeight() * 2 - 1) / 2, 0);
        GlStateManager.popMatrix();
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return enabled && visible && hovered;
    }

    public UIElement getElement() {
        return element;
    }
}
