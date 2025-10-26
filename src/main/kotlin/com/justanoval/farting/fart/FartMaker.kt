package com.justanoval.farting.fart

import com.justanoval.farting.Farting
import com.justanoval.farting.Farting.Companion.CONFIG
import com.justanoval.farting.config.FartingConfig
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList
import net.minecraft.particle.DustParticleEffect
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.world.event.GameEvent

object FartMaker {
    @JvmStatic
    fun fart(player: ServerPlayerEntity) {
        val fart = CONFIG.farts.weightedRandom(player.random)
        if (fart != null) {
            player.fart(fart)
        }
    }
}

fun ValidatedList<FartingConfig.Fart>.weightedRandom(random: Random): FartingConfig.Fart? {
    val list = this.get()
    val totalWeight = list.sumOf { it.weight.get() }

    if (totalWeight <= 0) {
        return null
    }

    var randomValue = random.nextInt(totalWeight)

    for (weightedItem in this) {
        randomValue -= weightedItem.weight.get()
        if (randomValue < 0) {
            return weightedItem
        }
    }

    return this.lastOrNull()
}

fun ServerPlayerEntity.fart(fart: FartingConfig.Fart) {
    val pos = this.entityPos

    // play sound
    val sound = fart.sound.get()
    val minVolume = sound.volume.get().left
    val maxVolume = sound.volume.get().right
    val volume = minVolume + (maxVolume - minVolume) * random.nextFloat()
    this.entityWorld.playSound(
        null,
        pos.x,
        pos.y,
        pos.z,
        SoundEvent.of(Identifier.of(sound.event)),
        SoundCategory.PLAYERS,
        volume,
        1.0f
    )

    // show particles
    val particle = fart.particle.get()
    if (particle.enabled) {
        this.entityWorld.spawnParticles(
            DustParticleEffect(
                particle.color.get().toInt(),
                particle.scale
            ),
            false,
            false,
            x,
            y + 0.5,
            z,
            particle.count,
            particle.offset.get().x,
            particle.offset.get().y,
            particle.offset.get().z,
            particle.speed
        )
    }

    // add velocity
    val velocity = fart.velocity.get()
    if (velocity.enabled) {
        val playerLook: Vec3d = this.rotationVector

        this.velocityModified = true
        this.velocity = Vec3d(
            playerLook.x,
            velocity.verticalMultiplier,
            playerLook.z
        ).multiply(velocity.horizontalMultiplier)
    }

    // trigger sculk
    if (CONFIG.enableFartTriggersSculkSensor) {
        this.entityWorld.emitGameEvent(entity, GameEvent.NOTE_BLOCK_PLAY, pos)
    }
}

