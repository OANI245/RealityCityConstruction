package berries.mods.tcwm.mvapi;

import net.minecraft.network.chat.Component;

public interface MVComponent {
    Component EMPTY = Component.empty().copy();

    static Component text(String v) {
        return Component.literal(v);
    }

    static Component translatable(String d) {
        return Component.translatable(d);
    }

    static Component translatable(String d, Object... f) {
        return Component.translatable(d, f);
    }
}
