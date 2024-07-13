package com.kines.kinesmod.features.events;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.gui.GuiManager;
import com.kines.kinesmod.gui.elements.Point;
import com.kines.kinesmod.gui.elements.UIElement;
import com.kines.kinesmod.utils.Calculator;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class GreatSpook extends UIElement {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final String display = EnumChatFormatting.GOLD + "Fear" + EnumChatFormatting.GRAY + "> ";
    public GreatSpook() {
        super("primal-fear-timer", new Point(100, 10));
        GuiManager.addElement(this);
    }

    @SubscribeEvent
    public void onMathSolve(ClientChatReceivedEvent event) throws Calculator.CalculatorException {
        if (!Utils.isInSkyBlock) return;
        if (event.type != 0) return;
        String message = event.message.getUnformattedText();
        if (Config.calculateMath) {
            if (message.contains(Utils.stripColor("QUICK MATHS! Solve:"))) {
                BigDecimal calculate = Calculator.calculate(message.split("Solve: ")[1].replace("x", "*"));
                DecimalFormat formatter = new DecimalFormat("#,##0.##");
                String format = formatter.format(calculate);
                if (Config.copyMathToClipboard) {
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(new StringSelection(format), null);
                }
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[KinesMod] " +
                        EnumChatFormatting.GRAY + "Answer: "+ EnumChatFormatting.GREEN + format));
            }
        }
        if (message.contains("FEAR. A Primal Fear has been summoned!") && Config.primalFearTimer) {
            start = true;
            time = 0;
        }
    }

    private int time = 0;
    private int tick = 0;
    private int seconds = 0;
    private int minutes = 0;
    private boolean start = false;
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.primalFearTimer) return;
        if (!start) return;
        FontRenderer renderer = mc.fontRendererObj;
        EnumChatFormatting color = EnumChatFormatting.YELLOW;
        if (minutes >= 3)
            color = EnumChatFormatting.GREEN;

        renderer.drawStringWithShadow(display + color + Utils.formatTimer(minutes, seconds), this.getX(), this.getY(), 0);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ClientTickEvent event) {
        if (!start) return;
        if (event.phase != TickEvent.Phase.START) return;

        if (time == 0) {
            seconds = 0;
            minutes = 0;
        }

        if (tick == 20) {
            tick = 0;
            time++;
            seconds++;
        }
        tick++;

        if (seconds == 60) {
            seconds = 0;
            minutes++;
        }
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
        return Config.primalFearTimer;
    }
}
