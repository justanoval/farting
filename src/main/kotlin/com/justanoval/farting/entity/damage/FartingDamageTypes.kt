package com.justanoval.farting.entity.damage

import com.justanoval.farting.Farting
import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys

object FartingDamageTypes {
    val FART_DAMAGE: RegistryKey<DamageType> =
        RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Farting.id("fart_damage"))
}