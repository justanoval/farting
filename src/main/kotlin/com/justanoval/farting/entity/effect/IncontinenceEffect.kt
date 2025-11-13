package com.justanoval.farting.entity.effect

import com.justanoval.farting.Farting
import com.justanoval.farting.attribute.FartingAttributes
import com.justanoval.farting.FartHandler
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.server.world.ServerWorld

class IncontinenceEffect(
    category: StatusEffectCategory,
    color: Int
): ModStatusEffect(category, color) {
    override fun applyUpdateEffect(world: ServerWorld, entity: LivingEntity, amplifier: Int): Boolean {
        val incontinenceFartChance = Farting.CONFIG.effects.incontinenceFartChance
        val incontinenceAmplifierMultiplier = Farting.CONFIG.effects.incontinenceAmplifierMultiplier
        val fartChance = (incontinenceFartChance + (amplifier * incontinenceAmplifierMultiplier)) * ((entity.getAttributeInstance(FartingAttributes.GASSINESS)?.value ?: 1.0) * 100.0)
        if (entity.random.nextFloat() <= fartChance) {
            FartHandler.tryFart(entity)
        }

        return true
    }

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        val i = 4 shr amplifier
        return if (i > 0) duration % i == 0 else true
    }
}