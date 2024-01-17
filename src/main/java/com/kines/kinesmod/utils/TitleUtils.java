package com.kines.kinesmod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TitleUtils {

    private static String display;
    private static String subTitle;
    private static long endTime;
    private static long subEndTime;

    public static void sendTitle(String text, int duration) {
        display = text;
        endTime = System.currentTimeMillis() + (duration * 1000L);
    }

    public static void sendTitleWithSub(String text, String sub, int duration) {
        subTitle = sub;
        display = text;
        subEndTime = System.currentTimeMillis() + (duration * 1000L);
        endTime = System.currentTimeMillis() + (duration * 1000L);
        sendTitle(text, duration);
    }

    @SubscribeEvent
    public void onRenderSubTitle(RenderGameOverlayEvent.Text event) {
        if (System.currentTimeMillis() > subEndTime) return;

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;

        GlStateManager.pushMatrix();
        GlStateManager.translate(((float) width / 2), ((float) height / 3 + (float) renderer.getStringWidth(display)), 0.0f);
        GlStateManager.scale(1.8f, 1.8f, 1.8f);
        drawStringCenteredScaledMaxWidth(subTitle, renderer, 0f, 0f, true, 75, 0);
        GlStateManager.popMatrix();
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        if (System.currentTimeMillis() > endTime) return;

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;

        GlStateManager.pushMatrix();
        GlStateManager.translate(((float) width / 2), ((float) height / 3), 0.0f);
        GlStateManager.scale(4.0f, 4.0f, 4.0f);
        drawStringCenteredScaledMaxWidth(display, renderer, 0f, 0f, true, 75, 0);
        GlStateManager.popMatrix();
    }

    public static void drawStringCenteredScaledMaxWidth(String str, FontRenderer fr, float x, float y, boolean shadow, int len, int colour) {
        int strLen = fr.getStringWidth(str);
        float factor = len / (float) strLen;
        factor = Math.min(1, factor);
        int newLen = Math.min(strLen, len);

        float fontHeight = 8 * factor;

        drawStringScaled(str, fr, x - (float) newLen / 2, y - fontHeight / 2, shadow, colour, factor);
    }

    public static void drawStringScaled(String str, FontRenderer fr, float x, float y, boolean shadow, int colour, float factor) {
        GlStateManager.scale(factor, factor, 1);
        fr.drawString(str, x / factor, y / factor, colour, shadow);
        GlStateManager.scale(1 / factor, 1 / factor, 1);
    }
}
