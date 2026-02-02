package berries.mods.tcwm.mixin;

import berries.mods.tcwm.mvapi.MVComponent;
import net.minecraft.CrashReport;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
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
    private @Nullable IntegratedServer singleplayerServer;

    @Shadow
    public abstract void disconnectWithProgressScreen();

    @Shadow
    public abstract @Nullable ClientPacketListener getConnection();

    @Shadow
    public abstract void disconnect(Screen par1, boolean par2);

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
                        setScreen(new GenericMessageScreen(Component.translatable("menu.savingLevel")));
                    }
                    this.level.disconnect(ClientLevel.DEFAULT_QUIT_MESSAGE);
                }

                this.disconnect(this.singleplayerServer != null ? this.screen : new ProgressScreen(true), false);
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
