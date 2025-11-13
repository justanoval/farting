package com.justanoval.farting.potion

import com.justanoval.farting.Farting
import com.justanoval.farting.entity.effect.FartingStatusEffects
import eu.pb4.polymer.core.api.other.SimplePolymerPotion
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.potion.Potion
import net.minecraft.potion.Potions
import net.minecraft.recipe.BrewingRecipeRegistry
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.entry.RegistryEntry

object FartingPotions {
    val FLATULENCE: RegistryEntry<Potion> = register(
        "flatulence",
        "flatulence",
        StatusEffectInstance(
            FartingStatusEffects.GASSY,
            3600,
            0
        )
    )

    val LONG_FLATULENCE: RegistryEntry<Potion> = register(
        "long_flatulence",
        "flatulence",
        StatusEffectInstance(
            FartingStatusEffects.GASSY,
            9600,
            0
        )
    )

    val STRONG_FLATULENCE: RegistryEntry<Potion> = register(
        "strong_flatulence",
        "flatulence",
        StatusEffectInstance(
            FartingStatusEffects.GASSY,
            1800,
            1
        )
    )

    val CRAMPING: RegistryEntry<Potion> = register(
        "cramping",
        "cramping",
        StatusEffectInstance(
            FartingStatusEffects.CRAMPING,
            3600,
            0
        )
    )

    val LONG_CRAMPING: RegistryEntry<Potion> = register(
        "long_cramping",
        "cramping",
        StatusEffectInstance(
            FartingStatusEffects.CRAMPING,
            9600,
            0
        )
    )

    val STRONG_CRAMPING: RegistryEntry<Potion> = register(
        "strong_cramping",
        "cramping",
        StatusEffectInstance(
            FartingStatusEffects.CRAMPING,
            1800,
            1
        )
    )

    val LAXATION: RegistryEntry<Potion> = register(
        "laxation",
        "laxation",
        StatusEffectInstance(
            FartingStatusEffects.CRAMPING,
            1800,
            2
        ),
        StatusEffectInstance(
            FartingStatusEffects.GASSY,
            1800,
            3
        )
    )

    val INDIGESTION: RegistryEntry<Potion> = register(
        "indigestion",
        "indigestion",
        StatusEffectInstance(
            FartingStatusEffects.INDIGESTION,
            3600,
            0
        )
    )

    val LONG_INDIGESTION: RegistryEntry<Potion> = register(
        "long_indigestion",
        "indigestion",
        StatusEffectInstance(
            FartingStatusEffects.INDIGESTION,
            9600,
            0
        )
    )

    val STRONG_INDIGESTION: RegistryEntry<Potion> = register(
        "strong_indigestion",
        "indigestion",
        StatusEffectInstance(
            FartingStatusEffects.INDIGESTION,
            1800,
            1
        )
    )

    val INCONTINENCE: RegistryEntry<Potion> = register(
        "incontinence",
        "incontinence",
        StatusEffectInstance(
            FartingStatusEffects.INCONTINENCE,
            3600,
            0
        )
    )

    val LONG_INCONTINENCE: RegistryEntry<Potion> = register(
        "long_incontinence",
        "incontinence",
        StatusEffectInstance(
            FartingStatusEffects.INCONTINENCE,
            9600,
            0
        )
    )

    val STRONG_INCONTINENCE: RegistryEntry<Potion> = register(
        "strong_incontinence",
        "incontinence",
        StatusEffectInstance(
            FartingStatusEffects.INCONTINENCE,
            1800,
            1
        )
    )

    fun register(id: String, potionId: String, vararg effects: StatusEffectInstance): RegistryEntry<Potion> {
        return Registry.registerReference(
            Registries.POTION,
            Farting.id(id),
            SimplePolymerPotion(potionId, *effects)
        )
    }

    fun BrewingRecipeRegistry.Builder.registerRecipe(input: RegistryEntry<Potion>, ingredient: Item, potion: Potion) {
        this.registerPotionRecipe(
            input,
            ingredient,
            Registries.POTION.getEntry(potion)
        )
    }

    fun init() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(FabricBrewingRecipeRegistryBuilder.BuildCallback { builder: BrewingRecipeRegistry.Builder ->
            // flatulence
            builder.registerRecipe(Potions.AWKWARD, Items.BEETROOT, FLATULENCE.value())
            builder.registerRecipe(FLATULENCE, Items.REDSTONE, LONG_FLATULENCE.value())
            builder.registerRecipe(FLATULENCE, Items.GLOWSTONE_DUST, STRONG_FLATULENCE.value())

            // cramping
            builder.registerRecipe(Potions.AWKWARD, Items.MILK_BUCKET, CRAMPING.value())
            builder.registerRecipe(CRAMPING, Items.REDSTONE, LONG_CRAMPING.value())
            builder.registerRecipe(CRAMPING, Items.GLOWSTONE_DUST, STRONG_CRAMPING.value())

            // laxation
            builder.registerRecipe(Potions.AWKWARD, Items.SUSPICIOUS_STEW, LAXATION.value())

            // indigestion
            builder.registerRecipe(Potions.AWKWARD, Items.CHICKEN, INDIGESTION.value())
            builder.registerRecipe(INDIGESTION, Items.REDSTONE, LONG_INDIGESTION.value())
            builder.registerRecipe(INDIGESTION, Items.GLOWSTONE_DUST, STRONG_INDIGESTION.value())

            // incontinence
            builder.registerRecipe(Potions.AWKWARD, Items.POISONOUS_POTATO, INCONTINENCE.value())
            builder.registerRecipe(INCONTINENCE, Items.REDSTONE, LONG_INCONTINENCE.value())
            builder.registerRecipe(INCONTINENCE, Items.GLOWSTONE_DUST, STRONG_INCONTINENCE.value())
        })
    }
}