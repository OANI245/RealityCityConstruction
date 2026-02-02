package berries.mods.tcwm.mixin;

import net.minecraft.CrashReport;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow
    @Nullable
    public ClientLevel level;

    @Shadow
    public abstract void setScreen(@Nullable Screen screen);

    @Shadow
    @Final
    private static Logger LOGGER;

    @Shadow
    @Final
    private GameNarrator narrator;

    @Shadow
    @Nullable
    public Screen screen;

    @Shadow
    public abstract void close();

    @Shadow
    @Nullable
    private Supplier<CrashReport> delayedCrash;

    @Shadow
    public abstract void clearLevel(Screen s0);

    @Shadow
    private @Nullable IntegratedServer singleplayerServer;

    @Inject(
            method = "destroy",
            at = @At("HEAD"),
            cancellable = true)
    public void destory(CallbackInfo ci) {
        try {
            LOGGER.info("Stopping!");

            try {
                this.narrator.destroy();
            } catch (Throwable ignored) {
            }

            try {
                if (this.level != null) {
                    if (this.singleplayerServer != null) {
                        setScreen(new GenericDirtMessageScreen(Component.translatable("menu.savingLevel")));
                    }
                    this.level.disconnect();
                }

                this.clearLevel(this.singleplayerServer != null ? this.screen : new ProgressScreen(true));
            } catch (Throwable ignored) {
            }

            if (this.screen != null) {
                this.screen.removed();
            }

            this.close();
        } finally {
            Util.timeSource = System::nanoTime;
            if (this.delayedCrash == null) {
                System.exit(0);
            }
        }

        ci.cancel();
    }
}
