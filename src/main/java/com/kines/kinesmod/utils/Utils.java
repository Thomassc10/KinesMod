package com.kines.kinesmod.utils;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class Utils {

    private static final Minecraft mc = Minecraft.getMinecraft();
    public static boolean isInSkyBlock = false;
    public static int openGui = 0;
    public static int editGui = 0;
    private static final Set<String> SKYBLOCK_IN_ALL_LANGUAGES = Sets.newHashSet("SKYBLOCK", "\u7A7A\u5C9B\u751F\u5B58", "\u7A7A\u5CF6\u751F\u5B58");
    public static Island island = Island.NONE;

    public static String stripColor(String text) {
        return text.replaceAll("(?i)\\u00A7.", "");
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String getSkullTexture(ItemStack skull) {
        if (skull == null || !skull.hasTagCompound()) {
            return "";
        }
        NBTTagCompound nbt = skull.getTagCompound();
        if (nbt.hasKey("SkullOwner", 10)) {
            return nbt.getCompoundTag("SkullOwner").getCompoundTag("Properties").getTagList("textures", 10).getCompoundTagAt(0).getString("Value");
        }
        return "";
    }

    public static void hasSkyBlockScoreboard() {
        if (mc.theWorld.getScoreboard() == null) return;
        Scoreboard scoreboard = mc.theWorld.getScoreboard();
        ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
        if (objective == null) return;
        String name = Utils.stripColor(objective.getDisplayName());
        for (String skyblock : SKYBLOCK_IN_ALL_LANGUAGES) {
            if (name.equalsIgnoreCase(skyblock) || name.startsWith(skyblock)) {
                Utils.isInSkyBlock = true;
                return;
            }
        }
        Utils.isInSkyBlock = false;
    }

    public static void scheduleCommand(long delay, String command) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mc.thePlayer.sendChatMessage(command);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, delay);
    }

    public static int parseRoman(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                break;
        }
        return -1;
    }

    public static int romanToInteger(String s) {
        if (isNum(s))
            return Integer.parseInt(s);
        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            int s1 = parseRoman(s.charAt(i));
            if (s1 == -1) return s1;
            if (i + 1 < s.length()) {
                int s2 = parseRoman(s.charAt(i + 1));
                if (s1 >= s2) {
                    total += s1;
                } else total -= s1;
            } else total += s1;
        }
        return total;
    }

    public static boolean isNum(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            //System.out.println("[KinesMod] Not an integer!");
            return false;
        }
        return true;
    }

    public static void drawStringOnSlot(FontRenderer fr, String s, Slot slot) {
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        mc.fontRendererObj.drawStringWithShadow(s, slot.xDisplayPosition + 19 - 2 - fr.getStringWidth(s), slot.yDisplayPosition + 6 + 3, 16777215);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    public static String formatTimer(int min, int sec) {
        String s = "";
        String m = "";
        if (sec < 10)
            s = "0";
        if (min < 10)
            m = "0";
        return m + min + ":" + s + sec;
    }
}
