package com.justanoval.farting.config

import com.google.gson.annotations.SerializedName
import com.justanoval.farting.entity.damage.FartingDamageTypes
import com.justanoval.oval.data.action.AoeAction
import com.justanoval.oval.data.action.DamageAction
import com.justanoval.oval.data.action.EntityAction
import com.justanoval.oval.data.component.ModifiableValue
import java.awt.Color

val FARTS_CONFIG_DEFAULT: Map<String, Fart> = mapOf(
    "normal_fart" to Fart(),
    "big_fart" to Fart(
        sound = Sound(
            "farting:player.big_fart"
        ),
        particle = Particle(
            true,
            2.5f,
            1.5,
            40,
            Color(150, 78, 20),
            0.3, 0.3, 0.3
        ),
        velocity = Velocity(
                true, 0.2, 2.0
        )
    ),
    "deadly_fart" to Fart(
        sound = Sound(
            "farting:player.big_fart"
        ),
        particle = Particle(
            true,
            4.0f,
            1.5,
            50,
            Color(59, 20, 5),
            1.0, 1.0, 1.0
        ),
        velocity = Velocity(
            true, 0.2, 2.0
        ),
        actions = listOf(
            AoeAction(
                ModifiableValue(2.0),
                false,
                DamageAction(
                    ModifiableValue(1.5),
                    FartingDamageTypes.FART_DAMAGE
                )
            )
        )
    ),
    "super_fart" to Fart(
        sound = Sound(
            "farting:player.super_fart",
            global = true
        ),
        particle = Particle(
            true,
            30.0f,
            1.5,
            100,
            Color(150, 78, 20),
            0.8, 0.8, 0.8
        ),
        velocity = Velocity(
            true, 0.2, 7.0
        ),
        actions = listOf()
    )
)

data class Fart(
    val sound: Sound = Sound("farting:player.normal_fart"),
    val particle: Particle = Particle(),
    val velocity: Velocity = Velocity(),
    val actions: List<EntityAction>? = listOf()
)

data class Sound(
    val event: String,
    val global: Boolean = false,
    @SerializedName("min_volume")
    val minVolume: Float = 1.0f,
    @SerializedName("max_volume")
    val maxVolume: Float = 1.0f
)

data class Particle(
    val enabled: Boolean = true,
    val scale: Float = 2.5f,
    val speed: Double = 1.5,
    val count: Int = 20,
    val color: Color = Color(157, 194, 64),
    @SerializedName("offset_x")
    val offsetX: Double = 0.3,
    @SerializedName("offset_y")
    val offsetY: Double = 0.3,
    @SerializedName("offset_z")
    val offsetZ: Double = 0.3
)

data class Velocity(
    val enabled: Boolean = true,
    @SerializedName("horizontal_multiplier")
    val horizontalMultiplier: Double = 0.2,
    @SerializedName("vertical_multiplier")
    val verticalMultiplier: Double = 1.0
)