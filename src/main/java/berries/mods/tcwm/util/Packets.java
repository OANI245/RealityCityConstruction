package berries.mods.tcwm.util;

import berries.mods.tcwm.mvapi.MVCustomPayload;
import berries.mods.tcwm.mvapi.MVNetwork;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public interface Packets {
    static void versionedPacketSendC2S(MVCustomPayload payload, Runnable onUsingLegacyVersion) {
        //? < 1.20.5 {
        onUsingLegacyVersion.run();
        //? } else {
        /*MVNetwork.sendC2S(payload);
        *///? }
    }

    static void versionedPacketSendS2C(ServerPlayer player, MVCustomPayload payload, Consumer<ServerPlayer> onUsingLegacyVersion) {
        //? < 1.20.5 {
        onUsingLegacyVersion.accept(player);
        //? } else {
        /*MVNetwork.sendS2C(player, payload);
        *///? }
    }
}
