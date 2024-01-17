package com.kines.kinesmod.features.garden;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.utils.Island;
import com.kines.kinesmod.utils.RenderUtils;
import com.kines.kinesmod.utils.ScoreboardUtils;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class Pests {

    private final String[] pests = {"Rat", "Mosquito", "Slug", "Beetle", "Cricket", "Earthworm", "Fly", "Locust", "Mite", "Moth"};

    // TODO: change it so is checks for the skull id, because name tags don't render properly on pests...

    @SubscribeEvent
    public void onRender(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.highlightPest) return;
        if (Utils.island != Island.GARDEN) return;
        if (!ScoreboardUtils.hasLine("The Garden")) return;
        Entity entity = event.entity;
        if (!(entity instanceof EntityArmorStand)) return;
        if (!entity.hasCustomName()) return;

        String name = entity.getDisplayName().getUnformattedText();

        double x = event.x + RenderUtils.getRenderX();
        double y = event.y + RenderUtils.getRenderY();
        double z = event.z + RenderUtils.getRenderZ();

        for (String s : pests) {
            if (name.contains(s)) {
                RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - 0.5, y + 2, z - 0.5, x + 0.5, y, z + 0.5),
                        Color.CYAN, 3, RenderUtils.getPartialTicks());
            }
        }
    }

    /*@SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        if (!KinesMod.hasSkyBlockScoreboard()) return;
        if (!Config.drawLineToPest) return;
        if (x == 0 && y == 0 && z == 0) return;
        Minecraft mc = Minecraft.getMinecraft();

        float eyeHeight = mc.thePlayer.eyeHeight;
        if (mc.thePlayer.isSneaking()) eyeHeight = 1.54f;
        RenderUtils.drawLine(new Vector3f((float) mc.thePlayer.posX, (float) mc.thePlayer.posY + eyeHeight, (float) mc.thePlayer.posZ),
                new Vector3f((float) x, (float) y, (float) z), Color.MAGENTA, 3, event.partialTicks);
    }*/
}
