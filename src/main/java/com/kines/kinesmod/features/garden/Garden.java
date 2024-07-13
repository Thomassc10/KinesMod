package com.kines.kinesmod.features.garden;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.events.GuiContainerEvent;
import com.kines.kinesmod.gui.GuiManager;
import com.kines.kinesmod.gui.elements.Point;
import com.kines.kinesmod.gui.elements.UIElement;
import com.kines.kinesmod.utils.Island;
import com.kines.kinesmod.utils.TitleUtils;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Garden {

    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void cropMilestones(GuiContainerEvent.ForegroundDrawnEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.milestonesLevel) return;
        if (event.guiContainer == null || event.container == null) return;
        if (!(event.guiContainer instanceof GuiChest)) return;
        GuiChest gui = (GuiChest) event.guiContainer;
        IInventory chest = ((ContainerChest) gui.inventorySlots).getLowerChestInventory();
        if (!chest.hasCustomName()) return;
        if (chest.getDisplayName().getUnformattedText().equals("Crop Milestones")) {
            for (int i = 11; i < 25; i++) {
                if (i > 15 && i < 20) continue;
                ItemStack item = chest.getStackInSlot(i);
                if (item == null) continue;
                String[] name = Utils.stripColor(item.getDisplayName()).split(" ");
                int amount = Utils.romanToInteger(name[name.length - 1]);
                Slot slot = event.container.getSlotFromInventory(chest, i);
                Utils.drawStringOnSlot(mc.fontRendererObj, String.valueOf(amount), slot);
            }
        }
    }

    public static class SprayTimer extends UIElement {

        private final String display = EnumChatFormatting.GOLD + "Spray" + EnumChatFormatting.GRAY + "> " + EnumChatFormatting.WHITE;
        private boolean active;
        private int minutes = 30;
        private int seconds = 0;
        private int tick = 0;
        public SprayTimer() {
            super("spray-timer", new Point(30, 40));
            GuiManager.addElement(this);
        }

        @SubscribeEvent
        public void onMessageReceived(ClientChatReceivedEvent event) {
            if (!Utils.isInSkyBlock) return;
            if (Utils.island != Island.GARDEN) return;
            if (event.type != 0) return;
            if (event.message.getUnformattedText().contains("SPRAYONATOR! This will expire in 30m!")) {
                active = true;
            }
        }

        @SubscribeEvent
        public void onTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.START) return;
            if (!active) return;

            if (minutes == 0 && seconds == 0) {
                active = false;
                minutes = 30;
                tick = 0;
                TitleUtils.sendTitle(EnumChatFormatting.RED + "Spray Expired", 3);
                return;
            }

            if (seconds == 0) {
                seconds = 59;
                minutes--;
            }

            if (tick == 20) {
                tick = 0;
                seconds--;
            }

            tick++;
        }

        @SubscribeEvent
        public void renderOverlay(RenderGameOverlayEvent.Text event) {
            if (!Utils.isInSkyBlock) return;
            if (!Config.sprayTimer) return;

            String text;
            if (active) {
                text = Utils.formatTimer(minutes, seconds);
            }
            else text = EnumChatFormatting.RED + "Inactive";

            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(display + text, this.getX(), this.getY(), 0);
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
            return Config.sprayTimer;
        }
    }
}
