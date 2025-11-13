package com.justanoval.farting.mixin;

import com.justanoval.farting.FartHandler;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(Entity.class)
public abstract class SneakDetectMixin {
    @Inject(method = "setSneaking(Z)V", at = @At("HEAD"))
    public void farting$onSneakStart(boolean sneaking, CallbackInfo ci) {
        Entity entity = ((Entity)((Object) this));
        if (entity instanceof ServerPlayerEntity && sneaking && !entity.isSpectator() && !entity.isSneaking()) {
            FartHandler.tryFart(((ServerPlayerEntity) entity));
        }
    }
}
