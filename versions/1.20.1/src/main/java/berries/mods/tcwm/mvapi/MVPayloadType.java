package berries.mods.tcwm.mvapi;

import net.minecraft.resources.ResourceLocation;

public class MVPayloadType<T extends MVCustomPayload> {
    public final ResourceLocation id;

    public MVPayloadType(ResourceLocation id) {
        this.id = id;
    }

    public Object type() {
        return null;
    }
}
