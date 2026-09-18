package com.hackfire.poxyneoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NeoForge port of Poxy (https://github.com/Jokypond/Poxy).
 *
 * Poxy itself does no active work at runtime beyond its mixins — this class
 * just exists so the mod shows up in the mods list and so we have a place to
 * log which Android compat patches actually applied (native lib present,
 * storage backend swapped, etc.) for debugging on-device.
 */
@Mod(PoxyNeoForge.MOD_ID)
public class PoxyNeoForge {
    public static final String MOD_ID = "poxyneoforge";
    public static final Logger LOGGER = LoggerFactory.getLogger("PoxyNeoForge");

    public PoxyNeoForge() {
        LOGGER.info("Poxy (NeoForge) loaded — Android patches for Voxy will apply via mixin.");
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("Poxy (NeoForge): client setup complete.");
    }
}
