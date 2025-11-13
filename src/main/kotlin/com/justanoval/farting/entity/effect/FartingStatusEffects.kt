package com.justanoval.farting.entity.effect

import com.justanoval.farting.Farting
import com.justanoval.farting.attribute.FartingAttributes
import com.justanoval.farting.entity.effect.ModStatusEffect
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.entry.RegistryEntry

object FartingStatusEffects {
    val GASSY: RegistryEntry<StatusEffect> = register(
        "gassy", ModStatusEffect(StatusEffectCategory.NEUTRAL, 0x4a7319)
            .addAttributeModifier(
                FartingAttributes.GASSINESS,
                Farting.id("effect.gassiness"),
                Farting.CONFIG.effects.gassyAttributeModifier,
                EntityAttributeModifier.Operation.ADD_VALUE
            )
    )

    val CRAMPING: RegistryEntry<StatusEffect> = register(
        "cramping", ModStatusEffect(StatusEffectCategory.NEUTRAL, 0x754719)
            .addAttributeModifier(
                FartingAttributes.FART_STRENGTH,
                Farting.id("effect.cramping"),
                Farting.CONFIG.effects.crampingAttributeModifier,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            )
    )

    val INDIGESTION: RegistryEntry<StatusEffect> = register(
        "indigestion", ModStatusEffect(StatusEffectCategory.NEUTRAL, 0xd67513)
            .addAttributeModifier(
                FartingAttributes.FART_DAMAGE_MULTIPLIER,
                Farting.id("effect.indigestion"),
                Farting.CONFIG.effects.indigestionAttributeModifier,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            )
    )

    val INCONTINENCE: RegistryEntry<StatusEffect> = register(
        "incontinence", IncontinenceEffect(StatusEffectCategory.NEUTRAL, 0x471a05)
    )

    val STINKY: RegistryEntry<StatusEffect> = register(
        "stinky", ModStatusEffect(StatusEffectCategory.NEUTRAL, 0x754719)
    )

    private fun register(id: String, statusEffect: StatusEffect): RegistryEntry<StatusEffect> {
        return Registry.registerReference(
            Registries.STATUS_EFFECT,
            Farting.Companion.id(id),
            statusEffect
        )
    }

    fun init() {

    }
}