package com.justanoval.farting.config

import com.justanoval.farting.Farting
import me.fzzyhmstrs.fzzy_config.annotations.Comment
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigSection
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedIdentifierMap
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedStringMap
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier

class FartingConfigMain: Config(Farting.id("config")) {
    @Comment("The chance for the fart to happen when a player crouches. Between 0 and 1, 0 being never and 1 being always.")
    var fartChance: Float = 0.001f

    @Comment("Whether the fart sound is picked up by a sculk sensor.")
    var enableFartTriggersSculkSensor: Boolean = true

    @Comment("Changing these settings require a server restart")
    var effects = Effects()
    var fartWeights = FartWeights()
    var gassyFood = GassyFood()

    class Effects: ConfigSection() {
        var gassyAttributeModifier: Double = 0.6
        var crampingAttributeModifier: Double = 0.6
    }

    class FartWeights: ConfigSection() {
        @Comment("Farts that happen when the player crouches")
        var natural: ValidatedStringMap<Int> = createWeightMap(mapOf(
            "normal_fart" to 10,
            "big_fart" to 1
        ))

        @Comment("Farts that happen when the player crouches and has the Gassy effect applied to them")
        var gassy: ValidatedStringMap<Int> = createWeightMap(mapOf(
            "normal_fart" to 10,
            "big_fart" to 4
        ))

        @Comment("Farts that happen when the player crouches and has the Cramping effect applied to them")
        var cramping: ValidatedStringMap<Int> = createWeightMap(mapOf(
            "big_fart" to 1
        ))

        fun createWeightMap(defaults: Map<String, Int>): ValidatedStringMap<Int> {
            return ValidatedStringMap.Builder<Int>()
                .keyHandler(ValidatedString(""))
                .valueHandler(ValidatedInt(0))
                .defaults(defaults)
                .build()
        }
    }

    class GassyFood: ConfigSection() {
        @Comment("Length of the gassy effect in seconds when caused by food")
        var length: Int = 30

        @Comment("A map of foods and the strength of their gassiness")
        var gassyFoods: ValidatedIdentifierMap<Int> = ValidatedIdentifierMap.Builder<Int>()
            .keyHandler(ValidatedIdentifier.ofRegistry(Identifier.of("cobblestone"), Registries.ITEM))
            .valueHandler(ValidatedInt(1, 5, 0))
            .defaults(mapOf(
                Identifier.of("beetroot_soup") to 0,
                Identifier.of("milk_bucket") to 0,
                Identifier.of("chicken") to 1,
                Identifier.of("rabbit") to 1,
                Identifier.of("porkchop") to 1,
                Identifier.of("beef") to 1,
                Identifier.of("mutton") to 1,
                Identifier.of("suspicious_stew") to 1,
                Identifier.of("pufferfish") to 2,
                Identifier.of("rotten_flesh") to 2,
                Identifier.of("spider_eye") to 2,
                Identifier.of("poisonous_potato") to 2,
            ))
            .build()
    }
}