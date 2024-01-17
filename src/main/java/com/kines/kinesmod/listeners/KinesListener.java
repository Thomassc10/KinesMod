package com.kines.kinesmod.listeners;

import com.kines.kinesmod.KinesMod;
import com.kines.kinesmod.gui.EditLocationsGui;
import com.kines.kinesmod.utils.*;
import gg.essential.universal.UScreen;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class KinesListener {

    @SubscribeEvent
    public void onRender(TickEvent.ClientTickEvent event) {
        if (KinesMod.openGui == 1) {
            UScreen.displayScreen(KinesMod.config.gui());
            KinesMod.openGui = 0;
        }
        if (KinesMod.editGui == 1) {
            UScreen.displayScreen(new EditLocationsGui());
            KinesMod.editGui = 0;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!Utils.isInSkyBlock)
            Utils.hasSkyBlockScoreboard();
    }

    @SubscribeEvent
    public void onUnload(WorldEvent.Unload event) {
        Utils.isInSkyBlock = false;
        Utils.island = Island.NONE;
    }

    @SubscribeEvent
    public void onTickLoc(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!Utils.isInSkyBlock) return;
        if (Utils.island != Island.NONE) return;

        List<String> tabList = TabListUtils.getTabList();

        String area = "";
        for (String s : tabList) {
            if (Utils.stripColor(s).contains("Area: "))
                area = Utils.stripColor(s.replace("Area: ", ""));
        }

        Utils.island = Island.getByName(area);
    }
}
