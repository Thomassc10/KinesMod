package com.kines.kinesmod.features;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.gui.GuiManager;
import com.kines.kinesmod.gui.elements.Point;
import com.kines.kinesmod.gui.elements.UIElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ToggleSprint extends UIElement {

    private final Minecraft mc = Minecraft.getMinecraft();
    private boolean isToggled = false;
    private final String display = EnumChatFormatting.WHITE + Config.sprintingText;
    public ToggleSprint() {
        super("toggle-sprint", new Point(2, new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight()));
        GuiManager.addElement(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (!Config.toggleSprint) return;
        if (mc.gameSettings.keyBindSprint.isPressed()) {
            isToggled = !isToggled;
        }
        int key = mc.gameSettings.keyBindSprint.getKeyCode();
        if (isToggled && mc.gameSettings.keyBindForward.isPressed()) {
            KeyBinding.setKeyBindState(key, true);
        }
    }

    @SubscribeEvent
    public void onHUDRender(RenderGameOverlayEvent.Text event) {
        if (!isToggled) return;
        if (mc.ingameGUI.getChatGUI().getChatOpen()) return;
        FontRenderer renderer = mc.fontRendererObj;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.scale(scale, scale, scale);
        renderer.drawString(EnumChatFormatting.WHITE + display, getX(), /*new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() + 30*/getY(), 0, true);
        GlStateManager.popMatrix();
    }

    @Override
    public String display() {
        return display;
    }

    @Override
    public double scale() {
        return 0.9;
    }

    @Override
    public boolean toggled() {
        return Config.toggleSprint;
    }
}
