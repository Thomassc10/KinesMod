package com.kines.kinesmod.mixins;

import com.kines.kinesmod.events.CheckRenderEntityEvent;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldClient.class)
public class MixinWorldClient {

    @Inject(method = "addEntityToWorld", at = @At("HEAD"), cancellable = true)
    public void addEntityToWorld(int entityID, Entity entityToSpawn, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new CheckRenderEntityEvent(entityToSpawn)))
            ci.cancel();
    }
}
