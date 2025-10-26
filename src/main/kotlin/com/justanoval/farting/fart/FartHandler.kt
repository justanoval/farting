package com.justanoval.farting.fart

import com.justanoval.farting.Farting.Companion.CONFIG
import com.justanoval.farting.Farting.Companion.FARTS
import com.justanoval.farting.attribute.FartingAttributes
import com.justanoval.farting.config.FartingConfigFarts
import com.justanoval.farting.effects.FartingEffects
import net.minecraft.particle.DustParticleEffect
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.world.event.GameEvent

object FartHandler {
    @JvmStatic
    fun tryFart(player: ServerPlayerEntity) {
        val fartChance = CONFIG.fartChance * ((player.getAttributeInstance(FartingAttributes.GASSINESS)?.value ?: 0.0) * 100.0)
        if (player.random.nextFloat() <= fartChance) {
            val weightMap = player.getFartWeightMap()

            val fartKey = weightMap.weightedRandom(player.random)
            val fart = FARTS.farts.get()[fartKey]

            if (fart != null) {
                player.tryFart(fart)
            }
        }
    }

    fun ServerPlayerEntity.tryFart(fart: FartingConfigFarts.Fart) {
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
                .multiply(this.getAttributeInstance(FartingAttributes.FART_STRENGTH)?.value ?: 0.0)
        }

        // trigger sculk sensor
        if (CONFIG.enableFartTriggersSculkSensor) {
            this.entityWorld.emitGameEvent(entity, GameEvent.NOTE_BLOCK_PLAY, pos)
        }
    }

    private fun ServerPlayerEntity.getFartWeightMap(): Map<String, Int> {
        return if (this.hasStatusEffect(FartingEffects.CRAMPING)) {
            CONFIG.fartWeights.cramping.get()
        } else if (this.hasStatusEffect(FartingEffects.GASSY)) {
            CONFIG.fartWeights.gassy.get()
        } else {
            CONFIG.fartWeights.natural.get()
        }
    }

    private fun Map<String, Int>.weightedRandom(random: Random): String? {
        val list = this.toList()
        val totalWeight = list.sumOf { it.second }

        if (totalWeight <= 0) {
            return null
        }

        var randomValue = random.nextInt(totalWeight)

        for (weightedItem in this) {
            randomValue -= weightedItem.value
            if (randomValue < 0) {
                return weightedItem.key
            }
        }

        return list.lastOrNull()?.first
    }
}
