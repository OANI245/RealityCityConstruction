package berries.mods.tcwm.mvapi;

import net.minecraft.nbt.CompoundTag;

public class MVBlockEntityComponent {
    protected CompoundTag tag;

    public MVBlockEntityComponent(CompoundTag tag) {
        this.tag = tag;
    }

    protected void checkInputMode() {
    }

    protected void checkOutputMode() {
    }

    public String getString(String k) {
        checkInputMode();
        return tag.getString(k);
    }

    public boolean getBoolean(String k) {
        checkInputMode();
        return tag.getBoolean(k);
    }

    public int getInt(String k) {
        checkInputMode();
        return tag.getInt(k);
    }

    public float getFloat(String k) {
        checkInputMode();
        return tag.getFloat(k);
    }

    public double getDouble(String k) {
        checkInputMode();
        return tag.getDouble(k);
    }

    public void putString(String k, String v) {
        checkOutputMode();
        tag.putString(k, v);
    }

    public void putBoolean(String k, boolean v) {
        checkOutputMode();
        tag.putBoolean(k, v);
    }

    public void putInt(String k, int v) {
        checkOutputMode();
        tag.putInt(k, v);
    }

    public void putFloat(String k, float v) {
        checkOutputMode();
        tag.putFloat(k, v);
    }

    public void putDouble(String k, double v) {
        checkOutputMode();
        tag.putDouble(k, v);
    }

    public boolean contains(String k) {
        if (tag != null) return tag.contains(k);
        return false;
    }

    public CompoundTag getTag() {
        return tag;
    }
}
