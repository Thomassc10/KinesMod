package com.kines.kinesmod.features.performance;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.events.PacketEvent;
import com.kines.kinesmod.gui.GuiManager;
import com.kines.kinesmod.gui.elements.Point;
import com.kines.kinesmod.gui.elements.UIElement;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class Tps extends UIElement {

    private final Minecraft mc = Minecraft.getMinecraft();
    private int packetsFromLastSecond = 0;
    private List<Integer> tpsList = new ArrayList<>();
    private int ignoreFirstTicks = 3;
    private boolean hasPacketReceived = false;
    private double tps = -1;
    private int ticks = 0;

    private final String display = EnumChatFormatting.GOLD + "Tps" + EnumChatFormatting.GRAY + "> " + EnumChatFormatting.WHITE;
    public Tps() {
        super("tps-display", new Point(new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() + 30, 40));
        GuiManager.addElement(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!Config.showTps) return;

        if (hasPacketReceived) {
            hasPacketReceived = false;
            packetsFromLastSecond++;
        }

        if (ticks == 20) {
            ticks = 0;

            if (packetsFromLastSecond == 0) return;
            if (ignoreFirstTicks > 0) {
                ignoreFirstTicks--;
                packetsFromLastSecond = 0;
                return;
            }
            tpsList.add(packetsFromLastSecond);
            packetsFromLastSecond = 0;
            if (tpsList.size() > 10) {
                //tpsList = tpsList.subList(1, tpsList.size());
                tpsList = new ArrayList<>(tpsList.subList(1, tpsList.size()));
            }
            if (tpsList.size() >= 3) {
                double sum = 0;
                for (int tps : tpsList) {
                    sum += tps;
                }
                tps = sum / tpsList.size();
                tps = Math.min(20.0, tps);
            }
            return;
        }

        ticks++;
    }

    @SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
    public void onPacket(PacketEvent.ReceivePacket event) {
        if (!Config.showTps) return;
        hasPacketReceived = true;
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        tpsList.clear();
        packetsFromLastSecond = 0;
        ignoreFirstTicks = 3;
        tps = -1;
        ticks = 0;
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (!Config.showTps) return;
        FontRenderer renderer = mc.fontRendererObj;
        String text = Utils.round(tps, 1) + "";

        if (tps == -1)
            text = "LOADING";

        Point point = this.getPos();
        renderer.drawStringWithShadow(display + text, point.getX(), point.getY(), 0);
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
        return Config.showTps;
    }
}
