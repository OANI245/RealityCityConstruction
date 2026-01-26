package berries.mods.tcwm.util.settings;

import net.minecraft.Util;

public class Afdian {
  public static void open(){
    Util.OS os = Util.getPlatform();
    os.openUri("https://afdian.net/a/RM_Project");
  }

  public static String getUrl(){
    return "https://afdian.net/a/RM_Project";
  }
}
