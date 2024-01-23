package com.kines.kinesmod.features;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.gui.GuiManager;
import com.kines.kinesmod.gui.elements.Point;
import com.kines.kinesmod.gui.elements.UIElement;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HUD {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static class Coordinates extends UIElement {

        private final String display = EnumChatFormatting.GOLD + "Loc" + EnumChatFormatting.GRAY + "> " + EnumChatFormatting.WHITE;
        public Coordinates() {
            super("coordinates", new Point(10, 50));
            GuiManager.addElement(this);
        }

        @SubscribeEvent
        public void onRenderLoc(RenderGameOverlayEvent.Text event) {
            if (!Config.showCoords) return;
            double x = Utils.round(mc.thePlayer.posX, 1);
            double y = Utils.round(mc.thePlayer.posY, 1);
            double z = Utils.round(mc.thePlayer.posZ, 1);
            String text;
            if (Config.showYCoord)
                text = x + " | " + y + " | " + z;
            else text = x + " | " + z;
            mc.fontRendererObj.drawStringWithShadow(display + text, this.getX(), this.getY(), 0);
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
            return Config.showCoords;
        }
    }

    public static class LobbyDate extends UIElement {

        private final String display = EnumChatFormatting.GOLD + "Day" + EnumChatFormatting.GRAY + "> " + EnumChatFormatting.WHITE;
        public LobbyDate() {
            super("lobby-date", new Point(20, 40));
            GuiManager.addElement(this);
        }

        @SubscribeEvent
        public void onRenderDate(RenderGameOverlayEvent.Text event) {
            if (!Utils.isInSkyBlock) return;
            if (!Config.showLobbyDate) return;

            mc.fontRendererObj.drawStringWithShadow(display + mc.theWorld.getWorldTime()/20/60/20, this.getX(), this.getY(), 0);
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
            return Config.showLobbyDate;
        }
    }
}
