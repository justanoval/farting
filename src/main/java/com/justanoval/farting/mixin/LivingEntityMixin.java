package com.justanoval.farting.mixin;

import com.justanoval.farting.attribute.FartingAttributes;
import com.justanoval.farting.effects.FoodEffectHandler;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.waypoint.ServerWaypoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, ServerWaypoint {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public Hand getActiveHand() {
        return null;
    }

    @Shadow
    public ItemStack getStackInHand(Hand hand) {
        return null;
    }

    @Inject(method = "createLivingAttributes", require = 1, allow = 1, at = @At("RETURN"))
    private static void farting$addAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(FartingAttributes.getGASSINESS());
        cir.getReturnValue().add(FartingAttributes.getFART_STRENGTH());
    }

    @Inject(method = "consumeItem", at = @At("TAIL"))
    private void farting$consumeItem(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) ((Object) this);
        Hand hand = this.getActiveHand();

        FoodEffectHandler.INSTANCE.tryApplyFoodEffect(entity, getStackInHand(hand));
    }
}
