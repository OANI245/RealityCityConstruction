package berries.mods.tcwm.mvapi;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.level.ServerPlayer;

public interface MVNetwork {

    //@Environment(EnvType.SERVER)
    static <T extends MVCustomPayload> void registerS2C(MVPayloadType<T> type, MVPayloadCodec<T> streamCodec) {
        //do nothing.
    }

    //@Environment(EnvType.CLIENT)
    static <T extends MVCustomPayload> void registerC2S(MVPayloadType<T> type, MVPayloadCodec<T> streamCodec) {
        //do nothing.
    }

    @Environment(EnvType.CLIENT)
    static <T extends MVCustomPayload> void registerReceiverS2C(MVPayloadType<T> type, MVPayloadCodec<T> streamCodec) {
        //do nothing.
    }

    //@Environment(EnvType.SERVER)
    static <T extends MVCustomPayload> void registerReceiverC2S(MVPayloadType<T> type, MVPayloadCodec<T> streamCodec) {
        //do nothing.
    }

    //@Environment(EnvType.SERVER)
    static <T extends MVCustomPayload> void sendS2C(ServerPlayer player, T payload) {
        //do nothing.
    }

    @Environment(EnvType.CLIENT)
    static <T extends MVCustomPayload> void sendC2S(T payload) {
        //do nothing.
    }
}
