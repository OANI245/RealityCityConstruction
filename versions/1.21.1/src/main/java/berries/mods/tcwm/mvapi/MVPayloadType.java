package berries.mods.tcwm.mvapi;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class MVPayloadType<T extends MVCustomPayload> {
    public final ResourceLocation id;
    public final CustomPacketPayload.Type<T> type;

    public MVPayloadType(ResourceLocation id) {
        this.id = id;
        this.type = new CustomPacketPayload.Type<>(id);
    }

    public CustomPacketPayload.Type<T> type() {
        return this.type;
    }
}
