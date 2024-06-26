/*
 * Curious Curios — A Minecraft mod
 * Copyright (C) 2024  Cypheriel
 */

package dev.cypheriel.curious_curios

import dev.cypheriel.curious_curios.common.datagen.ModelGenerator
import dev.cypheriel.curious_curios.common.registries.ItemRegistry
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.InterModComms
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import top.theillusivec4.curios.api.CuriosApi
import top.theillusivec4.curios.api.SlotTypeMessage

@Mod(CuriousCurios.MOD_ID)
@EventBusSubscriber(modid = CuriousCurios.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
object CuriousCurios {
    const val MOD_ID = "curious_curios"

    private val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    init {
        LOGGER.log(Level.INFO, "Howdy from Curious Curios!")

        ItemRegistry.register(MOD_BUS)

        MOD_BUS.addListener(this::enqueueIMC)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun enqueueIMC(event: InterModEnqueueEvent) {
        // Register Curio slot types
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE) {
            SlotTypeMessage.Builder("ring").size(2).build()
        }
    }

    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        val existingFileHelper = event.existingFileHelper

        generator.addProvider(event.includeClient(), ModelGenerator(generator, existingFileHelper))
    }
}
