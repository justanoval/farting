package com.justanoval.farting.config

import com.justanoval.farting.Farting
import me.fzzyhmstrs.fzzy_config.annotations.Comment
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.util.Walkable
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedPair
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt

class FartingConfig: Config(Farting.id("config")) {
    companion object {
        val NORMAL_FART = Fart()

        val BIG_FART = Fart(
            weight = ValidatedInt(1),
            sound = ValidatedAny(
                Sound(
                    "farting:player.big_fart"
                )
            ),
            particle = ValidatedAny(
                Particle(
                    true,
                    2.5f,
                    1.5,
                    40,
                    ValidatedColor(150, 78, 20),
                    ValidatedAny(Offset(0.3, 0.3, 0.3))
                )
            ),
            velocity = ValidatedAny(
                Velocity(
                    true, 0.2, 2.0
                )
            )
        )
    }

    @Comment("The chance for the fart to happen when a player crouches. Between 0 and 1, 0 being never and 1 being always.")
    var fartChance: Float = 0.001f

    @Comment("Whether the fart sound is picked up by a sculk sensor.")
    var enableFartTriggersSculkSensor: Boolean = true

    var farts: ValidatedList<Fart> = ValidatedAny<Fart>(Fart())
        .toList(NORMAL_FART, BIG_FART)

    class Fart(
        @Comment("Leave this at 0 if you don't want it to happen naturally.")
        var weight: ValidatedInt = ValidatedInt(10, 100, 0),
        var sound: ValidatedAny<Sound> = ValidatedAny(Sound("farting:player.normal_fart")),
        var particle: ValidatedAny<Particle> = ValidatedAny(Particle()),
        var velocity: ValidatedAny<Velocity> = ValidatedAny(Velocity())
    ): Walkable

    class Sound(
        var event: String,
        @Comment("A number will be picked randomly between these values. Make them the same to make it only one value.")
        var volume: ValidatedPair<Float, Float> = ValidatedPair.of(ValidatedFloat(1.0f))
    ): Walkable

    class Particle(
        var enabled: Boolean = true,
        var scale: Float = 2.5f,
        var speed: Double = 1.5,
        var count: Int = 20,
        var color: ValidatedColor = ValidatedColor(157, 194, 64),
        var offset: ValidatedAny<Offset> = ValidatedAny(Offset(0.2, 0.2, 0.2))
    ): Walkable

    class Velocity(
        var enabled: Boolean = true,
        var horizontalMultiplier: Double = 0.2,
        var verticalMultiplier: Double = 1.0
    ): Walkable

    class Offset(
        var x: Double,
        var y: Double,
        var z: Double
    ): Walkable
}