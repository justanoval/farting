package com.justanoval.farting

import com.justanoval.farting.attribute.FartingAttributes
import com.justanoval.farting.config.Fart
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.particle.DustParticleEffect
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.world.event.GameEvent
import kotlin.collections.get
import kotlin.collections.iterator

object FartHandler {
    @JvmStatic
    fun tryFart(entity: LivingEntity) {
        val fartChance = Farting.CONFIG.fartChance * ((entity.getAttributeInstance(FartingAttributes.GASSINESS)?.value ?: 1.0) * 100.0)
        if (entity.random.nextFloat() <= fartChance) {
            val weightMap = entity.getFartWeightMap()

            val fartKey = weightMap.weightedRandom(entity.random)
            val fart = Farting.FARTS[fartKey]

            if (fart != null) {
                entity.tryFart(fart)
            }
        }
    }

    fun Entity.tryFart(fart: Fart) {
        val pos = this.entityPos

        if (entityWorld !is ServerWorld) return

        val world = entityWorld as ServerWorld

        // play sound
        val sound = fart.sound
        val minVolume = sound.minVolume
        val maxVolume = sound.maxVolume
        val volume = minVolume + (maxVolume - minVolume) * random.nextFloat()

        val soundEvent = SoundEvent.of(Identifier.of(sound.event))
        val soundCategory = SoundCategory.PLAYERS

        if (sound.global) {
            world.server.playerManager.playerList.forEach { player ->
                player.playSoundToPlayer(soundEvent, soundCategory, volume, 1.0f)
            }
        } else {
            world.playSound(
                null,
                pos.x,
                pos.y,
                pos.z,
                soundEvent,
                soundCategory,
                volume,
                1.0f
            )
        }

        // show particles
        val particle = fart.particle
        if (particle.enabled) {
            world.spawnParticles(
                DustParticleEffect(
                    particle.color?.rgb ?: 0x0,
                    particle.scale
                ),
                false,
                false,
                x,
                y + 0.5,
                z,
                particle.count,
                particle.offsetX,
                particle.offsetY,
                particle.offsetZ,
                particle.speed
            )
        }

        // add velocity
        val velocity = fart.velocity
        if (velocity.enabled) {
            val playerLook: Vec3d = this.rotationVector

            this.velocityModified = true
            this.velocity = Vec3d(
                playerLook.x,
                velocity.verticalMultiplier,
                playerLook.z
            ).multiply(velocity.horizontalMultiplier)

            if (this is LivingEntity) {
                this.velocity = this.velocity
                    .multiply(this.getAttributeInstance(FartingAttributes.FART_STRENGTH)?.value ?: 0.0)
            }
        }

        // trigger sculk sensor
        if (Farting.CONFIG.enableFartTriggersSculkSensor) {
            this.entityWorld.emitGameEvent(entity, GameEvent.NOTE_BLOCK_PLAY, pos)
        }

        // trigger fart actions
        if (fart.actions != null ) {
            for (action in fart.actions) {
                action.execute(this, world)
            }
        }
    }

    private fun LivingEntity.getFartWeightMap(): Map<String, Int> {
        for (data in Farting.WEIGHTS) {
            if (data.condition.passes(this)) {
                return data.weights
            }
        }

        return mapOf()
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