package com.justanoval.farting.config

import com.google.gson.annotations.SerializedName
import net.minecraft.util.Identifier

data class ModConfig(
    @SerializedName("fart_chance")
    val fartChance: Float = 0.001f,
    @SerializedName("enable_fart_triggers_sculk_sensor")
    val enableFartTriggersSculkSensor: Boolean = true,
    val effects: Effects = Effects(),
    @SerializedName("gassy_foods")
    val gassyFoods: Map<Identifier, FoodEffect> = mapOf(
        Identifier.of("beetroot") to FoodEffect(),
        Identifier.of("beetroot_soup") to FoodEffect(),
        Identifier.of("milk_bucket") to FoodEffect(),
        Identifier.of("chicken") to FoodEffect(amplifier = 1),
        Identifier.of("rabbit") to FoodEffect(amplifier = 1),
        Identifier.of("porkchop") to FoodEffect(amplifier = 1),
        Identifier.of("beef") to FoodEffect(amplifier = 1),
        Identifier.of("mutton") to FoodEffect(amplifier = 1),
        Identifier.of("suspicious_stew") to FoodEffect(amplifier = 1),
        Identifier.of("pufferfish") to FoodEffect(amplifier = 2),
        Identifier.of("rotten_flesh") to FoodEffect(amplifier = 2),
        Identifier.of("spider_eye") to FoodEffect(amplifier = 2),
        Identifier.of("poisonous_potato") to FoodEffect(amplifier = 2),
    )
)

data class Effects(
    @SerializedName("gassy_attribute_modifier")
    val gassyAttributeModifier: Double = 0.6,
    @SerializedName("cramping_attribute_modifier")
    val crampingAttributeModifier: Double = 0.6,
    @SerializedName("indigestion_attribute_modifier")
    val indigestionAttributeModifier: Double = 1.8,
    @SerializedName("incontinence_fart_chance")
    val incontinenceFartChance: Float = 0.2f,
    @SerializedName("incontinence_amplifier_modifier")
    val incontinenceAmplifierMultiplier: Float = 1.2f
)

data class FoodEffect(
    val length: Int = 30,
    val amplifier: Int = 0
)