package com.kines.kinesmod.commands;

import com.kines.kinesmod.KinesMod;
import com.kines.kinesmod.features.performance.Tps;
import com.kines.kinesmod.utils.ScoreboardUtils;
import com.kines.kinesmod.utils.TitleUtils;
import com.kines.kinesmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class KinesCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "kines";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Minecraft mc = Minecraft.getMinecraft();
        if (args.length == 0)
            KinesMod.openGui = 1;
        else {
            if (args[0].equalsIgnoreCase("edit")) {
                KinesMod.editGui = 1;
            }
            else if (args[0].equalsIgnoreCase("tps")) {
                System.out.println(Tps.tpsList);
            }
            else if (args[0].equalsIgnoreCase("title")) {
                TitleUtils.sendTitleWithSub(EnumChatFormatting.WHITE + "God Potion", EnumChatFormatting.WHITE + "God Potion expires in 30 Minutes", 3);
            }
            else if (args[0].equalsIgnoreCase("score")) {
                for (String sidebarLine : ScoreboardUtils.getSidebarLines()) {
                    mc.thePlayer.addChatMessage(new ChatComponentText(Utils.stripColor(sidebarLine)));
                }
            }
        }
    }
}
