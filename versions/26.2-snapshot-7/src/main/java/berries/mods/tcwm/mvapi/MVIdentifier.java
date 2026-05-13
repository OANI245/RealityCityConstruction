package berries.mods.tcwm.mvapi;

import net.minecraft.resources.Identifier;

public interface MVIdentifier {
    static Identifier get(String key) {
        return Identifier.parse(key);
    }

    static Identifier get(String namespace, String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }
}
