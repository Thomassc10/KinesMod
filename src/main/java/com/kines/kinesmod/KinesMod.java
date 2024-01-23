package com.kines.kinesmod;

import com.kines.kinesmod.commands.KinesCommand;
import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.features.*;
import com.kines.kinesmod.features.events.Diana;
import com.kines.kinesmod.features.events.GreatSpook;
import com.kines.kinesmod.features.garden.Garden;
import com.kines.kinesmod.features.garden.Pests;
import com.kines.kinesmod.features.mining.CrystalHollows;
import com.kines.kinesmod.features.mining.SkyMall;
import com.kines.kinesmod.features.performance.Fps;
import com.kines.kinesmod.features.performance.Tps;
import com.kines.kinesmod.gui.GuiManager;
import com.kines.kinesmod.listeners.KinesListener;
import com.kines.kinesmod.utils.TitleUtils;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Mod(modid = "kinesmod", name = "KinesMod", version = KinesMod.VERSION, clientSideOnly = true)
public class KinesMod {

    // §

    /*
    TODO LIST:
    - Reaper armor timer.
    - Blaze slayer totem alert/time.
    - Draw waypoint from coords in chat.
    - Custom timer.
    - F7/M7 terminals waypoints (not solved ones).
    - M3 fire freeze timer.
    - Draw box around wither door.
    - Cells alignment time.
    - Gyro.
    - Kuudra
        HP display.
        Pearl throwing spots.
     */

    public static final String VERSION = "0.1";
    public static Config config;
    public static File dir = new File(new File("./config"), "kinesmod");
    public File cfg = new File("./config/kinesmod/config.json");

    @EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        config = new Config();
        dir.mkdirs();
        cfg.createNewFile();
        GuiManager.positionsFile.createNewFile();
        Arrays.asList(
                new Bestiary(),
                new ChatMessagesFilter(),
                new CrystalHollows(),
                new CrimsonIsle(),
                new Diana(),
                new Fps(),
                new Garden(),
                new Garden.SprayTimer(),
                new GreatSpook(),
                new HUD.Coordinates(),
                new HUD.LobbyDate(),
                new Kuudra(),
                new Misc(),
                new Pests(),
                new RemoveOverlays(),
                new KinesListener(),
                new SkyMall(),
                new Slayers(),
                new Slayers.Gummy(),
                new Slayers.WispPot(),
                new TitleUtils(),
                new ToggleSprint(),
                new Tps(),
                new Warnings()
        ).forEach(MinecraftForge.EVENT_BUS::register);
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new KinesCommand());
        GuiManager.loadConfig();
    }
}
