/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */
package dev.cypheriel.curious_curios.common.datagen

import dev.cypheriel.curious_curios.CuriousCurios
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

class ModelGenerator(
    dataGenerator: DataGenerator,
    existingFileHelper: ExistingFileHelper,
) : ItemModelProvider(dataGenerator, CuriousCurios.MOD_ID, existingFileHelper) {
    private fun basicModel(name: String) {
        this.singleTexture(
            name,
            mcLoc("item/generated"),
            "layer0",
            modLoc("item/$name"),
        )
    }

    override fun registerModels() {
        basicModel("glowing_powder")
        basicModel("glowing_ingot")
        basicModel("glowing_gem")
        basicModel("ring_of_enchanted_eyes")
        basicModel("stone_of_inertia_null")
        basicModel("stone_of_the_sea")
        basicModel("stone_of_greater_inertia")
    }
}
