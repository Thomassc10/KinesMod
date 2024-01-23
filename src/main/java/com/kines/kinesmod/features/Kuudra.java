package com.kines.kinesmod.features;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.events.CheckRenderEntityEvent;
import com.kines.kinesmod.utils.Island;
import com.kines.kinesmod.utils.RenderUtils;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.List;

public class Kuudra {

    private final Minecraft mc = Minecraft.getMinecraft();

    // TODO: make it more precise
    @SubscribeEvent
    public void supplies(RenderWorldLastEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.showSuppliesLocations) return;
        if (Utils.island != Island.KUUDRA) return;

        List<Entity> entities = mc.theWorld.loadedEntityList;
        if (entities.isEmpty()) return;

        for (Entity entity : entities) {
            if (entity instanceof EntityGiantZombie) {
                if (entity.getPosition().getY() < 78)
                    RenderUtils.renderBeaconBeam(entity.getPosition(), 0x1fd8f1, 0.8f, event.partialTicks);
            }
        }
    }

    // safe spot for stun: -173 23 -169
    private final BlockPos[] spots = {new BlockPos(-173, 23, -169), new BlockPos(-140, 78, -90), new BlockPos(-140, 78, -89),
                                        new BlockPos(-89, 78, -127)};
    private final double[][] coordinates = {{-173.5, 23, -169.5}, {-140.5, 78, -90.5}, {-140.5, 78, -89.5}, {-89.5,78, -127.5}};

    @SubscribeEvent
    public void safeSpots(RenderWorldLastEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.showSafeSpots) return;
        if (Utils.island != Island.KUUDRA) return;

        for (BlockPos pos : spots) {
            double x = pos.getX() - 0.5;
            double y = pos.getY();
            double z = pos.getZ() - 0.5;
            RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x + 0.5, y, z + 0.5, x - 0.5, y - 1, z - 0.5),
                    Color.GREEN, 5, event.partialTicks);
        }
    }

    @SubscribeEvent
    public void onNameTagRender(CheckRenderEntityEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.hideKuudraNameTags) return;
        if (Utils.island != Island.KUUDRA) return;

        if (!(event.entity instanceof EntityArmorStand)) return;
        EntityArmorStand stand = (EntityArmorStand) event.entity;
        if (!stand.hasCustomName()) return;
        if (stand.getDisplayName().getUnformattedText().contains("[Lv"))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void unfinishedSupplies(RenderWorldLastEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.unfinishedSupplyPiles) return;
        if (Utils.island != Island.KUUDRA) return;

        List<Entity> entities = mc.theWorld.loadedEntityList;
        if (entities.isEmpty()) return;

        for (Entity entity : entities) {
            if (entity instanceof EntityArmorStand) {
                if (entity.hasCustomName()) {
                    if (entity.getDisplayName().getUnformattedText().contains("PUNCH"))
                        RenderUtils.renderBeaconBeam(entity.getPosition(), 0x1fd8f1, 0.8f, event.partialTicks);
                }
            }
        }
    }
}
