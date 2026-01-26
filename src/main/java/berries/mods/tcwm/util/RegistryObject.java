package berries.mods.tcwm.util;

import java.util.function.Supplier;

public class RegistryObject<T> {
    private T data = null;
    private final Supplier<T> supplier;

    public RegistryObject(Supplier<T> st) {
        this.supplier = st;
    }

    public T get() {
        if (data == null) {
            data = supplier.get();
        }

        return data;
    }
}
