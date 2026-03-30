package berries.mods.tcwm.mvapi;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class MVPayloadCodec<T extends MVCustomPayload> {
    public final StreamCodec<FriendlyByteBuf, T> streamCodec;

    public MVPayloadCodec(StreamCodec<FriendlyByteBuf, T> streamCodec) {
        this.streamCodec = streamCodec;
    }

    public StreamCodec<FriendlyByteBuf, T> codec() {
        return this.streamCodec;
    }
}
