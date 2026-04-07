package berries.mods.tcwm.mvapi;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public interface MVStateProperties {
    static BooleanProperty bool(String name) {
        return BooleanProperty.create(name);
    }

    static IntegerProperty integer(String name, int start, int end) {
        return IntegerProperty.create(name, start, end);
    }

    static <T extends Enum<T> & StringRepresentable> EnumProperty<T> fEnum(String name, Class<T> clazz) {
        return EnumProperty.create(name, clazz);
    }
}
