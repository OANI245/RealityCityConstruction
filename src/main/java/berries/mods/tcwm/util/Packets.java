package berries.mods.tcwm.util;

import berries.mods.tcwm.mvapi.MVCustomPayload;
import berries.mods.tcwm.mvapi.MVNetwork;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

public interface Packets {
    static void versionedPacketSendC2S(MVCustomPayload payload, Runnable onUsingLegacyVersion) {
        //? < 1.20.5 {
        /*onUsingLegacyVersion.run();
        *///? } else {
        MVNetwork.sendC2S(payload);
        //? }
    }

    static void versionedPacketSendS2C(ServerPlayer player, MVCustomPayload payload, Consumer<ServerPlayer> onUsingLegacyVersion) {
        //? < 1.20.5 {
        /*onUsingLegacyVersion.accept(player);
        *///? } else {
        MVNetwork.sendS2C(player, payload);
        //? }
    }

    @NotNull
    static <T> T getReadValue(Optional<T> input) {
        return input.orElseThrow();
    }

    @NotNull
    static <T> T getReadValue(T input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        return input;
    }

    @NotNull
    static <T> T getReadValue(Optional<T> input, T _else) {
        return input.orElse(_else);
    }

    @NotNull
    static <T> T getReadValue(T input, T _else) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        return input;
    }
}
