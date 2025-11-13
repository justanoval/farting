package com.justanoval.farting

import com.justanoval.farting.attribute.FartingAttributes
import com.justanoval.farting.config.Fart
import com.justanoval.farting.config.ModConfig
import com.justanoval.farting.config.WEIGHTS_CONFIG_DEFAULT
import com.justanoval.farting.config.WeightData
import com.justanoval.farting.entity.effect.FartingStatusEffects
import com.justanoval.farting.FartHandler.tryFart
import com.justanoval.farting.config.FARTS_CONFIG_DEFAULT
import com.justanoval.farting.potion.FartingPotions
import com.justanoval.oval.config.ConfigManager
import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.string
import eu.pb4.polymer.core.api.entity.PolymerEntityUtils
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.command.argument.EntityArgumentType.getEntities
import net.minecraft.server.command.CommandManager.argument
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

// take a shot every time you see the word fart
class Farting : ModInitializer {
    companion object {
        const val MOD_ID: String = "farting"

        @JvmStatic
        lateinit var CONFIG: ModConfig

        @JvmStatic
        lateinit var FARTS: Map<String, Fart>

        @JvmStatic
        lateinit var WEIGHTS: List<WeightData>

        @JvmStatic
        val LOGGER: Logger = LoggerFactory.getLogger("Farting")

        fun id(path: String): Identifier {
            return Identifier.of(MOD_ID, path)
        }
    }

    fun loadConfigs() {
        CONFIG = ConfigManager.loadConfig(id("config"), ::ModConfig)
        FARTS = ConfigManager.loadMap(id("farts"), FARTS_CONFIG_DEFAULT)
        WEIGHTS = ConfigManager.loadList<WeightData>(id("weights"), WEIGHTS_CONFIG_DEFAULT)
    }

    override fun onInitialize() {
        loadConfigs()

        // initialization
        FartingStatusEffects.init()
        FartingAttributes.init()
        FartingPotions.init()

        // register attributes
        PolymerEntityUtils.registerAttribute(FartingAttributes.GASSINESS)
        PolymerEntityUtils.registerAttribute(FartingAttributes.FART_STRENGTH)
        PolymerEntityUtils.registerAttribute(FartingAttributes.FART_DAMAGE_MULTIPLIER)
        PolymerEntityUtils.registerAttribute(FartingAttributes.FART_RANGE_MULTIPLIER)

        // resource pack
        PolymerResourcePackUtils.addModAssets(MOD_ID)
        PolymerResourcePackUtils.markAsRequired()

        // command registration
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            // reload config command
            dispatcher.register(literal("farting")
                .requires { source -> source.hasPermissionLevel(3)}
                .then(literal("reload")
                    .executes { context ->
                        loadConfigs()
                        context.source.sendFeedback({ Text.literal("Reloaded Farting config!") }, true)
                        1
                    }
                )
            )

            // force people to fart command
            dispatcher.register(literal("fart")
                .requires { source -> source.hasPermissionLevel(3)}
                .then(argument("entity", EntityArgumentType.entities())
                    .then(argument("fart", string()).suggests { _, builder ->
                        FARTS.forEach { builder.suggest(it.key) }
                        builder.buildFuture()
                    }.executes { c ->
                        val entities = getEntities(c, "entity")
                        val fartKey = getString(c, "fart")
                        val fart = FARTS[fartKey]

                        if (fart != null) {
                            entities.forEach { it.tryFart(fart) }
                        }

                        return@executes 1
                    })
                )
            )
        }
    }
}
