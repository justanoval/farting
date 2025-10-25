package com.justanoval.farting

import com.justanoval.farting.config.FartingConfig
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils
import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Farting : ModInitializer {
    companion object {
        const val MOD_ID: String = "farting"

        @JvmStatic
        var CONFIG = ConfigApi.registerAndLoadConfig(::FartingConfig, RegisterType.SERVER)

        @JvmStatic
        val LOGGER: Logger = LoggerFactory.getLogger("Geep Craft")

        fun id(path: String): Identifier {
            return Identifier.of(MOD_ID, path)
        }
    }

    override fun onInitialize() {
        PolymerResourcePackUtils.addModAssets(MOD_ID)
        PolymerResourcePackUtils.markAsRequired()
    }
}
