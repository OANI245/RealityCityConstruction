package berries.mods.tcwm.mixin;

import berries.mods.tcwm.RealityCityConstruction;
import berries.mods.tcwm.mvapi.MVComponent;
import berries.mods.tcwm.mvapi.MVRegistry;
import net.minecraft.CrashReport;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.function.Supplier;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(
            method = "onGameLoadFinished",
            at = @At("HEAD")
    )
    public void onGameLoadFinished(CallbackInfo ci) {
        RealityCityConstruction.LOGGER.info(Arrays.toString(MVRegistry.BLOCK.registryObjects.stream().map((v) -> "\"" + BuiltInRegistries.BLOCK.getKey(v).toString() + "\"").toArray(String[]::new)));
    }
}
