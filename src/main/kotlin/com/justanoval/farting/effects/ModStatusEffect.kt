package com.justanoval.farting.effects

import eu.pb4.polymer.core.api.other.PolymerStatusEffect
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory

class ModStatusEffect(
    category: StatusEffectCategory,
    color: Int
): StatusEffect(category, color), PolymerStatusEffect