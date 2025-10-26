package com.justanoval.farting.effects

import com.justanoval.farting.Farting
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries

object FoodEffectHandler {
    fun tryApplyFoodEffect(entity: LivingEntity, itemStack: ItemStack) {
        Farting.LOGGER.info("tryApplyFoodEffect")
        Farting.CONFIG.gassyFood.gassyFoods.get().forEach { (id, strength) ->
            val item = Registries.ITEM.get(id)
            Farting.LOGGER.info("item $item ?= item ${itemStack.item}")
            if (item == itemStack.item) {
                val instance = StatusEffectInstance(
                    FartingEffects.GASSY,
                    20 * Farting.CONFIG.gassyFood.length,
                    strength,
                    false, true, true)

                entity.addStatusEffect(instance)
            }
        }
    }
}