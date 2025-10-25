package com.justanoval.farting.config

import com.justanoval.farting.Farting
import me.fzzyhmstrs.fzzy_config.config.Config

class FartingConfig : Config(Farting.id("config")) {
    var fartChance: Float = 0.001f
    var triggersSculkSensor: Boolean = true
}