package com.hackfire.poxyneoforge;

import com.google.common.collect.ImmutableMap;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Direct port of Poxy's PoxyMixinPlugin. IMixinConfigPlugin is part of
 * SpongePowered Mixin itself (not Fabric-specific), so this needed no
 * changes beyond the package rename and the mixin class names below
 * matching this project's package.
 *
 * Skips applying either mixin unless /system/lib64/libandroid.so exists,
 * i.e. unless we're actually running on Android.
 */
public final class PoxyMixinPlugin implements IMixinConfigPlugin {
    private static final Supplier<Boolean> TRUE = () -> true;

    public static boolean isAndroid() {
        File f = new File("/system/lib64/libandroid.so");
        return f.exists();
    }

    private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.of(
            "com.hackfire.poxyneoforge.mixin.StorageConfigUtilMixin", PoxyMixinPlugin::isAndroid,
            "com.hackfire.poxyneoforge.mixin.ThreadUtilsMixin", PoxyMixinPlugin::isAndroid
    );

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return CONDITIONS.getOrDefault(mixinClassName, TRUE).get();
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
