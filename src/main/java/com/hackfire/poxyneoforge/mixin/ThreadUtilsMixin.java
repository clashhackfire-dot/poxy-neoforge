package com.hackfire.poxyneoforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Direct NeoForge port of Poxy's ThreadUtilsMixin (originally Fabric).
 * Same target, same fix: Android has no libc.so.6, only libc.so.
 */
@Mixin(targets = "me.cortex.voxy.common.util.ThreadUtils")
public class ThreadUtilsMixin {

    @ModifyConstant(method = "<clinit>", constant = @Constant(stringValue = "libc.so.6"))
    private static String init(String original) {
        return "libc.so";
    }
}
