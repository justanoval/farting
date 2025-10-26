package com.justanoval.farting.effects

import com.justanoval.farting.Farting
import com.justanoval.farting.attribute.FartingAttributes
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.entry.RegistryEntry

object FartingEffects {
    val GASSY: RegistryEntry<StatusEffect> = register(
        "gassy",
        ModStatusEffect(StatusEffectCategory.NEUTRAL, 0x4a7319)
            .addAttributeModifier(
                FartingAttributes.GASSINESS,
                Farting.id("effect.gassiness"),
                Farting.CONFIG.effects.gassyAttributeModifier,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            )
    )

    val CRAMPING: RegistryEntry<StatusEffect> = register(
        "cramping",
        ModStatusEffect(StatusEffectCategory.NEUTRAL, 0x754719)
            .addAttributeModifier(
                FartingAttributes.FART_STRENGTH,
                Farting.id("effect.cramping"),
                Farting.CONFIG.effects.crampingAttributeModifier,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            )
    )

    val STINKY: RegistryEntry<StatusEffect> = register(
        "stinky", ModStatusEffect(StatusEffectCategory.NEUTRAL, 0x754719)
    )

    private fun register(id: String, statusEffect: StatusEffect): RegistryEntry<StatusEffect> {
        return Registry.registerReference(
            Registries.STATUS_EFFECT,
            Farting.id(id),
            statusEffect
        )
    }

    fun init() {

    }
}