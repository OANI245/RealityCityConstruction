package berries.mods.tcwm.util;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum EnumExpwyBarRotateType implements StringRepresentable {
  R22_5("rotate_22_5"), R45("rotate_45"),
  DISABLE("disabledrotate");
  private final String name;
  private EnumExpwyBarRotateType(String name){
    this.name = name;
  }

  @Override
  public @NotNull String getSerializedName() {
    return name;
  }
}
