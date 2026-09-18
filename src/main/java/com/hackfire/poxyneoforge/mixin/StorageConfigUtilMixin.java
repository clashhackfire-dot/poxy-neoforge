package com.hackfire.poxyneoforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.cortex.voxy.common.config.storage.lmdb.LMDBStorageBackend;
import me.cortex.voxy.common.config.storage.other.CompressionStorageAdaptor;
import me.cortex.voxy.common.config.section.SectionSerializationStorage;

/**
 * Direct NeoForge port of Poxy's StorageConfigUtilMixin (originally Fabric).
 * Only the package changed — the target (me.cortex.voxy.common.StorageConfigUtil)
 * is Voxy's own class, unchanged whether Voxy is loaded via Roxy or natively.
 */
@Mixin(targets = "me.cortex.voxy.common.StorageConfigUtil")
public class StorageConfigUtilMixin {

    @Inject(method = "createDefaultSerializer", at = @At("TAIL"))
    private static void createDefaultSerializer(CallbackInfoReturnable<SectionSerializationStorage.Config> cir, @Local(name = "compression") CompressionStorageAdaptor.Config compression) {
        compression.delegate = new LMDBStorageBackend.Config();
    }
}
