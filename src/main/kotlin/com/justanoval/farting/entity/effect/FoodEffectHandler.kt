package com.justanoval.farting.entity.effect

import com.justanoval.farting.Farting
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries

object FoodEffectHandler {
    fun tryApplyFoodEffect(entity: LivingEntity, itemStack: ItemStack) {
        Farting.CONFIG.gassyFoods.forEach { (id, effect) ->
            val item = Registries.ITEM.get(id)
            if (item == itemStack.item) {
                val instance = StatusEffectInstance(
                    FartingStatusEffects.GASSY,
                    20 * effect.length,
                    effect.amplifier,
                    false, true, true
                )

                entity.addStatusEffect(instance)
            }
        }
    }
}