package com.kines.kinesmod.features.garden;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.events.GuiContainerEvent;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
                int index;
                String[] name = Utils.stripColor(item.getDisplayName()).split(" ");
                index = name.length == 3 ? 2 : 1;
                int amount = Utils.romanToInteger(name[index]);
                Slot slot = event.container.getSlotFromInventory(chest, i);
                Utils.drawStringOnSlot(mc.fontRendererObj, String.valueOf(amount), slot);
            }
        }
    }
}
