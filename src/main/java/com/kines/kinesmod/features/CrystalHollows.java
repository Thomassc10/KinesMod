package com.kines.kinesmod.features;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.utils.Island;
import com.kines.kinesmod.utils.RenderUtils;
import com.kines.kinesmod.utils.Utils;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.util.vector.Vector3f;

public class CrystalHollows {

    // 552.5 116 474.5 Mithril Deposits
    // 474.5 116 474.5 Jungle
    // 474.5 116 552.5 Goblin Holdout
    // 552.5 116 552.5 Precursor Remnants

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.chWaypoints) return;
        if (Utils.island != Island.CRYSTAL_HOLLOWS) return;

        RenderUtils.renderWaypoint(new Vector3f(552.5f, 116f, 474.5f), "Mithril Deposits", false, event.partialTicks);
        RenderUtils.renderWaypoint(new Vector3f(474.5f, 116f, 474.5f), "Jungle", false, event.partialTicks);
        RenderUtils.renderWaypoint(new Vector3f(552.5f, 116f, 552.5f), "Goblin Holdout", false, event.partialTicks);
        RenderUtils.renderWaypoint(new Vector3f(552.5f, 116f, 552.5f), "Precursor Remnants", false, event.partialTicks);
    }
}
