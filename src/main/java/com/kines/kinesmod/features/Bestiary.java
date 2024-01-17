package com.kines.kinesmod.features;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.utils.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class Bestiary {

    @SubscribeEvent
    public void onRenderMob(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (!Utils.isInSkyBlock) return;

        if (!Config.highlightMob.isEmpty()) {
            String mob = Config.highlightMob;
            //Class<?> e = Class.forName("net.minecraft.entity.passive.Entity" + "Zombie");
            if (event.entity.getClass().getName().contains("Entity" + mob)) {
                AxisAlignedBB axis = event.entity.getEntityBoundingBox();
                RenderUtils.drawOutlinedBoundingBox(
                        new AxisAlignedBB(axis.minX - 0.1, axis.minY, axis.minZ - 0.1, axis.maxX + 0.1, axis.maxY, axis.maxZ + 0.1),
                        Color.CYAN, 3, RenderUtils.getPartialTicks());
            }
        }
    }

    @SubscribeEvent
    public void onRenderStand(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (!Utils.isInSkyBlock) return;

        if (!Config.highlightStand.isEmpty()) {
            String name = Config.highlightStand;
            if (event.entity instanceof EntityArmorStand) {
                EntityArmorStand stand = (EntityArmorStand) event.entity;
                if (!stand.hasCustomName()) return;
                double x = event.x + RenderUtils.getRenderX();
                double y = event.y + RenderUtils.getRenderY();
                double z = event.z + RenderUtils.getRenderZ();
                if (stand.getDisplayName().getUnformattedText().contains(name)) {
                    RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - 0.5, y - 2, z - 0.5, x + 0.5, y, z + 0.5), Color.CYAN, 3, RenderUtils.getPartialTicks());
                }
            }
        }
    }

    // TODO: Change it to check for the skin, and not the name tag. (might be better? name tags don't render from far)
    @SubscribeEvent
    public void onRenderMatcho(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.highlightMatcho) return;
        if (Utils.island != Island.CRIMSON_ISLE) return;
        if (!(event.entity instanceof EntityArmorStand)) return;
        EntityArmorStand stand = (EntityArmorStand) event.entity;
        if (!stand.hasCustomName()) return;

        double x = event.x + RenderUtils.getRenderX();
        double y = event.y + RenderUtils.getRenderY();
        double z = event.z + RenderUtils.getRenderZ();

        String name = stand.getDisplayName().getUnformattedText();

        if (name.contains("Matcho")) {
            RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - 0.5, y - 2, z - 0.5, x + 0.5, y, z + 0.5),
                    Color.CYAN, 3, RenderUtils.getPartialTicks());
        }
    }

    @SubscribeEvent
    public void onRenderBroodmother(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.highlightBroodmother) return;
        if (Utils.island != Island.SPIDER_DEN) return;
        if (!(event.entity instanceof EntityArmorStand)) return;
        EntityArmorStand stand = (EntityArmorStand) event.entity;
        if (!stand.hasCustomName()) return;

        double x = event.x + RenderUtils.getRenderX();
        double y = event.y + RenderUtils.getRenderY();
        double z = event.z + RenderUtils.getRenderZ();

        String name = stand.getDisplayName().getUnformattedText();

        if (name.contains("Broodmother")) {
            RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - 1, y - 1, z - 1, x + 1, y, z + 1),
                    Color.CYAN, 3, RenderUtils.getPartialTicks());
        }
    }

    private static boolean alerted = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!Utils.isInSkyBlock) return;
        if (!Config.broodmotherAlive) return;
        if (Utils.island != Island.SPIDER_DEN) return;
        if (alerted) return;

        TabListUtils.getTabList().forEach(line -> {
            if (line.contains(Utils.stripColor("Broodmother: Alive!")) || line.contains(Utils.stripColor("Broodmother: Imminent"))) {
                alerted = true;
                TitleUtils.sendTitle(EnumChatFormatting.RED + "Broodmother", 3);
            }
        });
    }
}
