package com.kines.kinesmod.features.mining;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.gui.GuiManager;
import com.kines.kinesmod.gui.elements.Point;
import com.kines.kinesmod.gui.elements.UIElement;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkyMall extends UIElement {

    //Yum! You gain +5☘ Mining Fortune for 48 hours!

    private String buff = "";
    private final String display = EnumChatFormatting.GOLD + "Sky Mall" + EnumChatFormatting.GRAY + "> " + EnumChatFormatting.WHITE;
    public SkyMall() {
        super("skymall-display", new Point(10, 50));
        GuiManager.addElement(this);
    }

    @SubscribeEvent
    public void onMessage(ClientChatReceivedEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (event.type != 0) return;
        String message = event.message.getUnformattedText();
        if (message.contains("New buff:")) {
            buff = message.split(": ")[1];
        }

        if (Config.hideSkyMallMessages) {
            if (message.contains("You can disable this messaging by toggling Sky Mall in your /hotm!") || message.contains("New day! Your Sky Mall buff changed!") || message.contains("New buff:")) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.displaySkyMall) return;

        FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;
        if (buff.isEmpty())
            buff = "Unknown";

        GlStateManager.pushMatrix();
        GlStateManager.scale(0.95, 0.95, 0.95);
        if (Config.shorterBuff)
            renderer.drawStringWithShadow(display + getShorterBuff(buff), this.getX(), this.getY(), 0);
        else renderer.drawStringWithShadow(display + buff, this.getX(), this.getY(), 0);
        GlStateManager.popMatrix();
    }

    @Override
    public String display() {
        return display;
    }

    @Override
    public double scale() {
        return 0.95;
    }

    @Override
    public boolean toggled() {
        return Config.displaySkyMall;
    }

    public String getShorterBuff(String buff) {
        if (buff.contains("Mining Speed."))
            return "+100 Mining Speed";
        else if (buff.contains("Mining Fortune"))
            return "+50 Fortune";
        switch (buff) {
            case "Gain +15% more Powder while mining.": return "+15% Powder";
            case "Reduce Pickaxe Ability cooldown by 20%.": return "Ability cooldown";
            case "10x chance to find Golden and Diamond Goblins.": return "10x Goblins";
            case "Gain 5x Titanium drops.": return "5x Titanium";
            default:
                return "";
        }
    }

    /*case "Gain +100 ⸕ Mining Speed.": return "+100 Speed";*/
    /*case "Gain +50 ☘ Mining Fortune.": return "+50 Fortune";*/
    /*case "+15% chance to gain extra ᠅ Mithril Powder while mining.": return "+15% Mithril Powder";*/
}
