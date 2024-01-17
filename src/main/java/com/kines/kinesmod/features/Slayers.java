package com.kines.kinesmod.features;

import com.kines.kinesmod.config.Config;
import com.kines.kinesmod.gui.GuiManager;
import com.kines.kinesmod.gui.elements.Point;
import com.kines.kinesmod.gui.elements.UIElement;
import com.kines.kinesmod.utils.RenderUtils;
import com.kines.kinesmod.utils.ScoreboardUtils;
import com.kines.kinesmod.utils.TitleUtils;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Slayers {

    static Minecraft mc = Minecraft.getMinecraft();
    private final String[] zombieSlayer = {"Revenant Sycophant", "Revenant Champion", "Deformed Revenant", "Atoned Champion", "Atoned Revenant"};
    private final String[] spiderSlayer = {"Tarantula Vermin", "Tarantula Beast", "Mutant Tarantula"};
    private final String[] wolfSlayer = {"Pack Enforcer", "Sven Follower", "Sven Alpha"};
    private final String[] endermanSlayer = {"Voidling Devotee", "Voidling Radical", "Voidcrazed Maniac"};
    private final String[] blazeSlayer = {"Flare Demon", "Kindleheart Demon", "Burningsoul Demon"};

    @SubscribeEvent
    public void onMiniBoss(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.highlightSlayerMini) return;
        if (!(event.entity instanceof EntityArmorStand)) return;
        if (!event.entity.hasCustomName()) return;

        String name = event.entity.getDisplayName().getUnformattedText();

        double x = event.x + RenderUtils.getRenderX();
        double y = event.y + RenderUtils.getRenderY();
        double z = event.z + RenderUtils.getRenderZ();
        for (String str : zombieSlayer) {
            if (name.contains(str)) {
                RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - 0.5, y - 2, z - 0.5, x + 0.5, y, z + 0.5),
                        Color.CYAN, 3, RenderUtils.getPartialTicks());
                return;
            }
        }
        for (String str : spiderSlayer) {
            if (name.contains(str)) {
                RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - 1, y - 1, z - 1, x + 1, y, z + 1),
                        Color.CYAN, 3, RenderUtils.getPartialTicks());
                return;
            }
        }
        for (String str : wolfSlayer) {
            if (name.contains(str)) {
                RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - 0.5, y - 1, z - 0.5, x + 0.5, y, z + 0.5),
                        Color.CYAN, 3, RenderUtils.getPartialTicks());
                return;
            }
        }
        for (String str : endermanSlayer) {
            if (name.contains(str)) {
                RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - 0.5, y - 3, z - 0.5, x + 0.5, y, z + 0.5),
                        Color.CYAN, 3, RenderUtils.getPartialTicks());
                return;
            }
        }
        for (String str : blazeSlayer) {
            if (name.contains(str)) {
                RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - 0.5, y - 2, z - 0.5, x + 0.5, y, z + 0.5),
                        Color.CYAN, 3, RenderUtils.getPartialTicks());
                return;
            }
        }
    }

    private Entity eman;
    private int seconds = 9;
    private int milli = 0;
    private int tick = 0;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!Config.lazerPhaseTimer) return;
        if (event.phase != TickEvent.Phase.START) return;
        if (eman == null) return;
        if (!eman.isRiding()) {
            milli = 0;
            seconds = 9;
            return;
        }

        if (milli == 0 && seconds == 0)
            return;

        if (milli == 0) {
            milli = 9;
            seconds--;
        }
        if (tick%2 == 0) {
            milli--;
        }

        if (tick == 20)
            tick = 0;

        tick++;
    }

    @SubscribeEvent
    public void onHit(AttackEntityEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.lazerPhaseTimer) return;
        Entity target = event.target;
        if (!(target instanceof EntityEnderman)) return;
        List<EntityArmorStand> stands = mc.theWorld.getEntitiesWithinAABB(EntityArmorStand.class,
                new AxisAlignedBB(target.posX - 1, target.posY, target.posZ - 1, target.posX + 1, target.posY + 4, target.posZ + 1));
        if (stands.isEmpty()) return;

        for (EntityArmorStand stand : stands) {
            if (!stand.hasCustomName()) continue;
            if (stand.getDisplayName().getUnformattedText().contains("Voidgloom Seraph"))
                eman = target;
        }
    }

    @SubscribeEvent
    public void onLaserPhase(RenderWorldLastEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.lazerPhaseTimer) return;
        if (eman == null) return;
        if (!eman.isRiding()) return;

        RenderUtils.renderWaypoint(new Vector3f((float) eman.posX, (float) eman.posY + 1.67f, (float) eman.posZ),
                EnumChatFormatting.BOLD + "" + EnumChatFormatting.GREEN + "Lazer: " + EnumChatFormatting.RED + seconds + "." + milli, false, event.partialTicks);
    }

    @SubscribeEvent
    public void onEyeHeads(RenderWorldLastEvent event) {
        if (!Utils.isInSkyBlock) return;
        if (!Config.highlightEyeThings) return;
        if (!ScoreboardUtils.hasLine("Voidgloom Seraph")) return;
        if (!ScoreboardUtils.hasLine("Slay the boss!")) return;

        List<EntityArmorStand> stands = new ArrayList<>();
        List<Entity> entities = mc.theWorld.loadedEntityList;
        if (entities.isEmpty()) return;

        for (Entity entity : entities) {
            if (entities instanceof EntityArmorStand)
                stands.add((EntityArmorStand) entity);
        }

        for (EntityArmorStand stand : stands) {
            if (Utils.getSkullTexture(stand.getEquipmentInSlot(4)).equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWIwNzU5NGUyZGYyNzM5MjFhNzdjMTAxZDBiZmRmYTExMTVhYmVkNWI5YjIwMjllYjQ5NmNlYmE5YmRiYjRiMyJ9fX0=")) {
                double x = stand.posX + RenderUtils.getRenderX();
                double y = stand.posY + RenderUtils.getRenderY();
                double z = stand.posZ + RenderUtils.getRenderZ();
                RenderUtils.drawOutlinedBoundingBox(
                        new AxisAlignedBB(x + 0.5, y + 1, z + 0.5, x - 0.5, y + 2, z -0.5), Color.RED, 3, event.partialTicks);
            }
        }
    }

    public static class Gummy extends UIElement {

        private final String display = EnumChatFormatting.GOLD + "Gummy" + EnumChatFormatting.GRAY + "> " + EnumChatFormatting.WHITE;
        private boolean active;
        public Gummy() {
            super("gummy-polar-bear", new Point(10, 60));
            GuiManager.addElement(this);
        }

        @SubscribeEvent
        public void onMessageReceived(ClientChatReceivedEvent event) {
            if (!Utils.isInSkyBlock) return;
            if (event.message.getUnformattedText().contains("You ate a Re-heated Gummy Polar Bear!")) {
                if (active) {
                    minutes = 60;
                    seconds = 0;
                    tick = 0;
                }
                active = true;
            }
        }

        private int minutes = 60;
        private int seconds = 0;
        private int tick = 0;

        @SubscribeEvent
        public void onTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.START) return;
            if (!active) return;

            if (seconds == 0) {
                seconds = 59;
                minutes--;
            }

            if (tick == 20) {
                tick = 0;
                seconds--;
            }

            tick++;

            if (minutes == 0 && seconds == 0) {
                active = false;
                minutes = 60;
                tick = 0;
                TitleUtils.sendTitle(EnumChatFormatting.RED + "Gummy Expired", 3);
            }
        }

        @SubscribeEvent
        public void onOverlayRender(RenderGameOverlayEvent.Text event) {
            if (!Utils.isInSkyBlock) return;
            if (!Config.gummyTimer) return;

            String text;
            if (active) {
                String s = "";
                String m = "";
                if (seconds < 10)
                    s = "0";
                if (minutes < 10)
                    m = "0";
                text = m + minutes + ":" + s + seconds;
            }
            else text = EnumChatFormatting.RED + "Inactive";

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
            return Config.gummyTimer;
        }
    }

    public static class WispPot extends UIElement {

        private final String display = EnumChatFormatting.GOLD + "Wisp Potion" + EnumChatFormatting.GRAY + "> " + EnumChatFormatting.WHITE;
        private boolean interacted = false;
        private int potSec;
        private int potMin;
        private boolean active = false;
        public WispPot() {
            super("wisp-potion", new Point(10, 60));
            GuiManager.addElement(this);
        }

        @SubscribeEvent
        public void onChat(ClientChatReceivedEvent event) {
            if (!Utils.isInSkyBlock) return;
            if (!Config.wispTimer) return;

            if (event.message.getUnformattedText().contains("You splashed yourself with Wisp's Ice-Flavored Water I!")) {
                System.out.println("-----Splash!-----");
                active = true;
                seconds = potSec;
                minutes = potMin;
                tick = 0;
            }
        }

        @SubscribeEvent
        public void onThrow(PlayerInteractEvent event) {
            if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) return;
            if (interacted) {
                interacted = false;
                return;
            }
            ItemStack item = mc.thePlayer.getCurrentEquippedItem();
            if (item == null) return;

            if (item.getDisplayName().contains("Wisp's Ice-Flavored Water I Splash Potion")) {
                interacted = true;
                String line = item.getTagCompound().getCompoundTag("display").getTagList("Lore", 8).get(1).toString();
                String duration = line.split("I ")[1].replace("§f", "").replace("(", "").replace(")", ""); // 30:00
                potSec = Integer.parseInt(duration.split(":")[1].replace("\"", ""));
                potMin = Integer.parseInt(duration.split(":")[0]);
            }
        }

        private int minutes;
        private int seconds;
        private int tick = 0;

        @SubscribeEvent
        public void onTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.START) return;
            if (!active) return;

            if (seconds == 0) {
                seconds = 59;
                minutes--;
            }

            if (tick == 20) {
                tick = 0;
                seconds--;
            }

            tick++;

            if (minutes == 0 && seconds == 0) {
                active = false;
                tick = 0;
                TitleUtils.sendTitle(EnumChatFormatting.RED + "Wisp Potion Expired", 3);
            }
        }

        @SubscribeEvent
        public void onOverlayRender(RenderGameOverlayEvent.Text event) {
            if (!Utils.isInSkyBlock) return;
            if (!Config.wispTimer) return;

            String text;
            if (active) {
                String s = "";
                String m = "";
                if (seconds < 10)
                    s = "0";
                if (minutes < 10)
                    m = "0";
                text = m + minutes + ":" + s + seconds;
            }
            else text = EnumChatFormatting.RED + "Inactive";

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
            return false;
        }
    }
}
