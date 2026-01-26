package berries.mods.tcwm.mvapi;

import net.minecraft.resources.ResourceLocation;

public interface MVIdentifier {
    static ResourceLocation get(String key) {
        return ResourceLocation.parse(key);
    }

    static ResourceLocation get(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
