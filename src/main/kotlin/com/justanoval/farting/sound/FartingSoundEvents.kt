package com.justanoval.farting.sound

import com.justanoval.farting.Farting
import net.minecraft.sound.SoundEvent

object FartingSoundEvents {
    val FART: SoundEvent = of("player.fart")

    fun of(path: String): SoundEvent {
        return SoundEvent.of(Farting.id(path))
    }
}