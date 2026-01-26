package berries.mods.tcwm.mvapi;

import net.minecraft.resources.ResourceLocation;

public interface MVIdentifier {
    static ResourceLocation get(String key) {
        return new ResourceLocation(key);
    }

    static ResourceLocation get(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }
}
