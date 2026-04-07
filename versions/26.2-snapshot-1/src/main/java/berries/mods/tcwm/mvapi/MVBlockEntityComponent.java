package berries.mods.tcwm.mvapi;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class MVBlockEntityComponent {
    protected ValueInput vi;
    protected ValueOutput vo;
    protected boolean isOutput = false;

    public MVBlockEntityComponent(ValueInput vi) {
        this(vi, null, false);
    }

    public MVBlockEntityComponent(ValueOutput vo) {
        this(null, vo, true);
    }

    protected MVBlockEntityComponent(ValueInput vi, ValueOutput vo, boolean mode) {
        this.vi = vi;
        this.vo = vo;
        this.isOutput = vo != null || mode;
    }

    protected void checkInputMode() {
        if (isOutput) {
            throw new IllegalCallerException();
        }
    }

    protected void checkOutputMode() {
        if (!isOutput) {
            throw new IllegalCallerException();
        }
    }

    public String getString(String k) {
        checkInputMode();
        return vi.getStringOr(k, null);
    }

    public boolean getBoolean(String k) {
        checkInputMode();
        return vi.getBooleanOr(k, false);
    }

    public int getInt(String k) {
        checkInputMode();
        return vi.getIntOr(k, 0);
    }

    public float getFloat(String k) {
        checkInputMode();
        return vi.getFloatOr(k, 0);
    }

    public double getDouble(String k) {
        checkInputMode();
        return vi.getDoubleOr(k, 0);
    }

    public void putString(String k, String v) {
        checkOutputMode();
        vo.putString(k, v);
    }

    public void putBoolean(String k, boolean v) {
        checkOutputMode();
        vo.putBoolean(k, v);
    }

    public void putInt(String k, int v) {
        checkOutputMode();
        vo.putInt(k, v);
    }

    public void putFloat(String k, float v) {
        checkOutputMode();
        vo.putFloat(k, v);
    }

    public void putDouble(String k, double v) {
        checkOutputMode();
        vo.putDouble(k, v);
    }

    public boolean contains(String k) {
        if (vi != null) return vi.contains(k);
        return false;
    }

    public ValueInput getValueInput() {
        checkInputMode();
        return vi;
    }

    public ValueOutput getValueOutput() {
        checkOutputMode();
        return vo;
    }
}
