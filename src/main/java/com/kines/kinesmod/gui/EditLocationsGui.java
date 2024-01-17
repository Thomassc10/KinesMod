package com.kines.kinesmod.gui;

import com.kines.kinesmod.gui.elements.UIElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditLocationsGui extends GuiScreen {

    private UIElement dragging;
    private float xOffset;
    private float yOffset;
    private Map<UIElement, LocationButton> locationButton = new HashMap<>();

    @Override
    public void initGui() {
        super.initGui();
        for (UIElement element : GuiManager.getElements().values()) {
            LocationButton button = new LocationButton(element);
            buttonList.add(button);
            locationButton.put(element, button);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        onMouseMove();
        drawGradientRect(0, 0, width, height, new Color(0, 0, 0, 50).getRGB(), new Color(0, 0, 0, 200).getRGB());
        for (GuiButton button : this.buttonList) {
            if (button instanceof LocationButton) {
                LocationButton lb = (LocationButton) button;
                GlStateManager.pushMatrix();
                double scale = lb.getElement().scale;

                GlStateManager.scale(scale, scale, scale);
                GlStateManager.translate(lb.x, lb.y, 0);

                button.drawButton(this.mc, mouseX, mouseY);
                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button instanceof LocationButton) {
            LocationButton lb = (LocationButton) button;
            dragging = lb.getElement();
            float x = (float) (Mouse.getX() * width) / Minecraft.getMinecraft().displayWidth;
            float y = height - (float) (Mouse.getY() * height) / Minecraft.getMinecraft().displayHeight - 1;
            xOffset = x - dragging.getX();
            yOffset = y - dragging.getY();
        }
    }

    protected void onMouseMove() {
        if (dragging != null) {
            LocationButton lb = locationButton.get(dragging);
            if (lb == null) return;

            float x = (float) (Mouse.getX() * width) / Minecraft.getMinecraft().displayWidth;
            float y = height - (float) (Mouse.getY() * height) / Minecraft.getMinecraft().displayHeight - 1;

            dragging.setPos(x - xOffset, y - yOffset);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = null;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        try {
            GuiManager.saveConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
