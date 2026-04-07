package berries.mods.tcwm.util;

public record Tuple<T, U>(T a, U b) {
    public T getA() {
        return a;
    }

    public U getB() {
        return b;
    }
}
