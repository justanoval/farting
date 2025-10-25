package com.justanoval.farting.mixin;

import com.justanoval.farting.Farting;
import com.justanoval.farting.sound.FartingSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class SneakDetectMixin {
    @Final
    @Shadow
    protected Random random;

    @Shadow
    private World world;

    @Shadow
    private Vec3d pos;

    @Inject(method = "setSneaking", at = @At("HEAD"))
    public void onSneakStart(boolean sneaking, CallbackInfo ci) {
        Entity entity = ((Entity)((Object) this));
        if (entity instanceof ServerPlayerEntity && sneaking && !entity.isSpectator()) {
            float fartChance = Farting.getCONFIG().getFartChance();
            float v = random.nextFloat();
            Farting.getLOGGER().info("{}", v);
            if (v <= fartChance) {
                world.playSound(null, pos.x, pos.y, pos.z, FartingSoundEvents.INSTANCE.getFART(), SoundCategory.PLAYERS, 1.0f, 1.0f);

                if (Farting.getCONFIG().getTriggersSculkSensor()) {
                    world.emitGameEvent(entity, GameEvent.NOTE_BLOCK_PLAY, pos);
                }
            }
        }
    }
}
