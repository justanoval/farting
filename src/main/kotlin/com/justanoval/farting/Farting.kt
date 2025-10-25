package com.justanoval.farting

import com.justanoval.farting.config.FartingConfig
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils
import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Farting : ModInitializer {
    companion object {
        const val MOD_ID: String = "farting"

        @JvmStatic
        var CONFIG = ConfigApi.registerAndLoadConfig(::FartingConfig, RegisterType.SERVER)

        @JvmStatic
        val LOGGER: Logger = LoggerFactory.getLogger("Farting")

        fun id(path: String): Identifier {
            return Identifier.of(MOD_ID, path)
        }
    }

    override fun onInitialize() {
        PolymerResourcePackUtils.addModAssets(MOD_ID)
        PolymerResourcePackUtils.markAsRequired()

        // reload config command
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(literal("farting")
                .requires { source -> source.hasPermissionLevel(3)}
                .then(literal("reload")
                    .executes { context ->
                        CONFIG = ConfigApi.registerAndLoadConfig(::FartingConfig, RegisterType.SERVER)
                        context.source.sendFeedback({ Text.literal("Reloaded Farting config!") }, true)
                        1
                    }
                )
            )
        }
    }
}
