package com.justanoval.farting.attribute

import com.justanoval.farting.Farting
import net.minecraft.entity.attribute.ClampedEntityAttribute
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.entry.RegistryEntry

object FartingAttributes {
    @JvmStatic
    val GASSINESS: RegistryEntry<EntityAttribute?>? = register(
        "gassiness", ClampedEntityAttribute("attribute.name.gassiness", 1.0, 1.0, 10.0).setTracked(true)
    )

    @JvmStatic
    val FART_STRENGTH: RegistryEntry<EntityAttribute?>? = register(
        "fart_strength", ClampedEntityAttribute("attribute.name.fart_strength", 1.0, 1.0, 10.0).setTracked(true)
    )

    @JvmStatic
    val FART_DAMAGE_MULTIPLIER: RegistryEntry<EntityAttribute?>? = register(
        "fart_damage_multiplier", ClampedEntityAttribute("attribute.name.fart_damage_multiplier", 1.0, 1.0, 50.0).setTracked(true)
    )

    @JvmStatic
    val FART_RANGE_MULTIPLIER: RegistryEntry<EntityAttribute?>? = register(
        "fart_range_multiplier", ClampedEntityAttribute("attribute.name.fart_range_multiplier", 1.0, 1.0, 16.0).setTracked(true)
    )

    private fun register(id: String, attribute: EntityAttribute?): RegistryEntry<EntityAttribute?>? {
        return Registry.registerReference<EntityAttribute?, EntityAttribute?>(
            Registries.ATTRIBUTE,
            Farting.id(id),
            attribute
        )
    }

    fun init() {

    }
}