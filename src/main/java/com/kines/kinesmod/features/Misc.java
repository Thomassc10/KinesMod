package com.kines.kinesmod.features;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.events.BlockBreakEvent;
import com.kines.kinesmod.events.GuiContainerEvent;
import com.kines.kinesmod.events.PacketEvent;
import com.kines.kinesmod.events.CheckRenderEntityEvent;
import com.kines.kinesmod.utils.TitleUtils;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class Misc {

    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onFallingBlock(CheckRenderEntityEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.removeFallingBlocks) return;
        if (!(event.entity instanceof EntityFallingBlock)) return;
        EntityFallingBlock block = (EntityFallingBlock) event.entity;
        if (block.getBlock().getBlock().getBlockState() != Blocks.beacon.getBlockState())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onEndermanTeleport(EnderTeleportEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (Config.stopEndermanTeleport)
            event.setCanceled(true);
    }

    private final String[] enderman = {"Voidling Fanatic", "Voidling Extremist", "Voidgloom Seraph"};

    // TODO: change it to create a name tag instead of teleporting the armor stand.
    @SubscribeEvent
    public void onRenderEnderman(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.endermanTag) return;
        Entity entity = event.entity;
        if (!(entity instanceof EntityEnderman)) return;

        List<EntityArmorStand> stands = mc.theWorld.getEntitiesWithinAABB(EntityArmorStand.class,
                new AxisAlignedBB(entity.posX - 1, entity.posY, entity.posZ - 1, entity.posX + 1, entity.posY + 4, entity.posZ + 1));
        if (stands.isEmpty()) return;

        for (EntityArmorStand armorStand : stands) {
            if (!armorStand.hasCustomName()) return;
            for (String str : enderman) {
                if (armorStand.getDisplayName().getUnformattedText().contains(str))
                    armorStand.setPosition(entity.posX, entity.posY + 2, entity.posZ);
            }
        }
    }

    @SubscribeEvent
    public void onPotionExpire(ClientChatReceivedEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.godPotExpired) return;

        String message = event.message.getUnformattedText();
        if (message.contains("God Potion expires in 30 Minutes")) {
            TitleUtils.sendTitleWithSub(EnumChatFormatting.RED + "God Pot", EnumChatFormatting.RED + "Expires in 30 Minutes", 3);
        } else if (message.contains("God Potion Expired!")) {
            TitleUtils.sendTitle(EnumChatFormatting.RED + "God Pot Expired", 3);
        }
    }

    @SubscribeEvent
    public void onPacketBreakAnim(PacketEvent.ReceivePacket event) {
        if (event.packet instanceof S25PacketBlockBreakAnim) {
            S25PacketBlockBreakAnim packet = (S25PacketBlockBreakAnim) event.packet;
            MinecraftForge.EVENT_BUS.post(new BlockBreakEvent(packet));
        }
    }

    @SubscribeEvent
    public void onMessage(ClientChatReceivedEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.autoLobby) return;

        if (event.message.getUnformattedText().contains("You were kicked while joining that server!")) {
            Utils.scheduleCommand(1000, "/l");
        }
    }

    @SubscribeEvent
    public void skillsLevel(GuiContainerEvent.ForegroundDrawnEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.skillLevel) return;
        if (event.guiContainer == null || event.container == null) return;
        if (!(event.guiContainer instanceof GuiChest)) return;
        GuiChest gui = (GuiChest) event.guiContainer;
        IInventory chest = ((ContainerChest) gui.inventorySlots).getLowerChestInventory();
        if (!chest.hasCustomName()) return;
        if (chest.getDisplayName().getUnformattedText().equals("Your Skills")) {
            for (int i = 19; i < 33; i++) {
                if (i > 25 && i < 29) continue;
                ItemStack item = chest.getStackInSlot(i);
                if (item == null) continue;
                int amount = Utils.romanToInteger(Utils.stripColor(item.getDisplayName()).split(" ")[1]);
                Slot slot = event.container.getSlotFromInventory(chest, i);
                Utils.drawStringOnSlot(mc.fontRendererObj, String.valueOf(amount), slot);
            }
        }
    }

    @SubscribeEvent
    public void statsTuning(GuiContainerEvent.ForegroundDrawnEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.tuningPoints) return;
        if (event.guiContainer == null || event.container == null) return;
        if (!(event.guiContainer instanceof GuiChest)) return;
        GuiChest gui = (GuiChest) event.guiContainer;
        IInventory chest = ((ContainerChest) gui.inventorySlots).getLowerChestInventory();
        if (!chest.hasCustomName()) return;
        if (chest.getDisplayName().getUnformattedText().equals("Stats Tuning")) {
            for (int i = 19; i < 32; i++) {
                if (i >= 23 && i <= 27) continue;
                ItemStack item = chest.getStackInSlot(i);
                if (item == null) continue;
                NBTTagList tagList = item.getTagCompound().getCompoundTag("display").getTagList("Lore", 8);

                for (int j = 0; j < tagList.tagCount(); j++) {
                    int points = 0;
                    if (Utils.stripColor(tagList.get(j).toString()).contains("Stat has: ")) {
                        points = Integer.parseInt(Utils.stripColor(tagList.get(j).toString()).split(" ")[2]);
                    }
                    if (points == 0) continue;
                    Slot slot = event.container.getSlotFromInventory(chest, i);
                    Utils.drawStringOnSlot(mc.fontRendererObj, String.valueOf(points), slot);
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderDead(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.hideDeadEntity) return;
        if (!(event.entity instanceof EntityArmorStand)) return;

        EntityArmorStand stand = (EntityArmorStand) event.entity;
        if (!stand.hasCustomName()) return;

        if (stand.getDisplayName().getUnformattedText().contains(" 0/")) {
            event.setCanceled(true);
        }
    }
}
