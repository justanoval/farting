package com.justanoval.farting.mixin;

import com.justanoval.farting.Farting;
import com.justanoval.farting.fart.FartMaker;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(Entity.class)
public abstract class SneakDetectMixin {
    @Final
    @Shadow
    protected Random random;

    @Inject(method = "setSneaking(Z)V", at = @At("HEAD"))
    public void farting$onSneakStart(boolean sneaking, CallbackInfo ci) {
        Entity entity = ((Entity)((Object) this));
        if (entity instanceof ServerPlayerEntity && sneaking && !entity.isSpectator()) {
            float fartChance = Farting.getCONFIG().getFartChance();
            if (random.nextFloat() <= fartChance) {
                FartMaker.fart(((ServerPlayerEntity) entity));
            }
        }
    }
}
