package berries.mods.tcwm.mvapi;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public interface MVNetwork {

    //@Environment(EnvType.SERVER)
    static <T extends MVCustomPayload> void registerS2C(MVPayloadType<T> type, MVPayloadCodec<T> streamCodec) {
        PayloadTypeRegistry.playS2C().register(type.type(), streamCodec.codec());
    }

    //@Environment(EnvType.CLIENT)
    static <T extends MVCustomPayload> void registerC2S(MVPayloadType<T> type, MVPayloadCodec<T> streamCodec) {
        PayloadTypeRegistry.playC2S().register(type.type(), streamCodec.codec());
    }

    @Environment(EnvType.CLIENT)
    static <T extends MVCustomPayload> void registerReceiverS2C(MVPayloadType<T> type, MVPayloadCodec<T> streamCodec) {
        ClientPlayNetworking.registerGlobalReceiver(type.type(), (p, c) -> {
            c.client().execute(() -> { p.receive(c.client(), c.player()); });
        });
    }

    //@Environment(EnvType.SERVER)
    static <T extends MVCustomPayload> void registerReceiverC2S(MVPayloadType<T> type, MVPayloadCodec<T> streamCodec) {
        ServerPlayNetworking.registerGlobalReceiver(type.type(), (p, c) -> {
            c.server().execute(() -> { p.receive(c.server(), c.player()); });
        });
    }

    //@Environment(EnvType.SERVER)
    static <T extends MVCustomPayload> void sendS2C(ServerPlayer player, T payload) {
        ServerPlayNetworking.send(player, payload);
    }

    @Environment(EnvType.CLIENT)
    static <T extends MVCustomPayload> void sendC2S(T payload) {
        ClientPlayNetworking.send(payload);
    }
}
