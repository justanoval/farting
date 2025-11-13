package com.justanoval.farting.config

import com.justanoval.farting.entity.effect.FartingStatusEffects
import com.justanoval.oval.data.condition.ActionCondition
import com.justanoval.oval.data.condition.StatusEffectCondition
import com.justanoval.oval.data.condition.TrueCondition

val WEIGHTS_CONFIG_DEFAULT: List<WeightData> = listOf(
    WeightData(
        StatusEffectCondition(FartingStatusEffects.INDIGESTION),
        mapOf(
            "deadly_fart" to 1
        )
    ),
    WeightData(
        StatusEffectCondition(FartingStatusEffects.CRAMPING),
        mapOf(
            "big_fart" to 1
        )
    ),
    WeightData(
        StatusEffectCondition(FartingStatusEffects.GASSY),
        mapOf(
            "normal_fart" to 10,
            "big_fart" to 4
        )
    ),
    WeightData(
        TrueCondition(),
        mapOf(
            "normal_fart" to 10,
            "big_fart" to 1
        )
    )
)

data class WeightData(
    val condition: ActionCondition,
    val weights: Map<String, Int>
)