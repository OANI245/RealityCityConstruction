package berries.mods.tcwm;

import berries.mods.tcwm.mvapi.MVComponent;
import berries.mods.tcwm.util.ObjectFunction;
import net.minecraft.network.chat.Component;

public class MCText {
  public static Component translatable(String s) {return MVComponent.translatable(s);};
  public static Component translatable(String s, Object... j) {return MVComponent.translatable(s,j);};
  public static Component translatable(String s, ObjectFunction f) {return MVComponent.translatable(s,f.get());};
  public static Component text(String s) {return MVComponent.text(s);};
}
