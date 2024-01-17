package com.kines.kinesmod.mixins;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.kines.kinesmod.config.Config;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame extends Gui {

    @Shadow public abstract FontRenderer getFontRenderer();

    @Inject(method = "renderScoreboard", at = @At("HEAD"), cancellable = true)
    protected void renderScoreboard(ScoreObjective objective, ScaledResolution scaledRes, CallbackInfo ci) {
        if (!Config.removeScores) return;
        ci.cancel();
        Scoreboard scoreboard = objective.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(objective);
        List<Score> list = Lists.newArrayList(Iterables.filter(collection, new Predicate<Score>() {
            public boolean apply(Score score) {
                return score.getPlayerName() != null && !score.getPlayerName().startsWith("#");
            }
        }));
        //ArrayList collection;
        if (list.size() > 15) {
            collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        } else {
            collection = list;
        }

        int i = this.getFontRenderer().getStringWidth(objective.getDisplayName());

        String string;
        for(Iterator iterator = collection.iterator(); iterator.hasNext(); i = Math.max(i, this.getFontRenderer().getStringWidth(string))) {
            Score score = (Score)iterator.next();
            ScorePlayerTeam scorePlayerTeam = scoreboard.getPlayersTeam(score.getPlayerName());
            string = ScorePlayerTeam.formatPlayerName(scorePlayerTeam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
        }

        int j = collection.size() * this.getFontRenderer().FONT_HEIGHT;
        int k = scaledRes.getScaledHeight() / 2 + j / 3;
        int l = 3;
        int m = scaledRes.getScaledWidth() - i - l;
        int n = 0;
        Iterator iterator2 = collection.iterator();

        while(iterator2.hasNext()) {
            Score score2 = (Score)iterator2.next();
            ++n;
            ScorePlayerTeam scorePlayerTeam2 = scoreboard.getPlayersTeam(score2.getPlayerName());
            String string2 = ScorePlayerTeam.formatPlayerName(scorePlayerTeam2, score2.getPlayerName());
            String string3 = EnumChatFormatting.RED + "" + score2.getScorePoints();
            int p = k - n * this.getFontRenderer().FONT_HEIGHT;
            int q = scaledRes.getScaledWidth() - l + 2;
            drawRect(m - 2, p, q, p + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString(string2, m, p, 553648127);
            //this.getFontRenderer().drawString(string3, q - this.getFontRenderer().getStringWidth(string3), p, 553648127);
            if (n == collection.size()) {
                String string4 = objective.getDisplayName();
                drawRect(m - 2, p - this.getFontRenderer().FONT_HEIGHT - 1, q, p - 1, 1610612736);
                drawRect(m - 2, p - 1, q, p, 1342177280);
                this.getFontRenderer().drawString(string4, m + i / 2 - this.getFontRenderer().getStringWidth(string4) / 2, p - this.getFontRenderer().FONT_HEIGHT, 553648127);
            }
        }
    }
}
