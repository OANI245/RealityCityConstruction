package berries.mods.tcwm.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public class ScreenReceiver {
    public static void open(Object... params) {
        Minecraft platform = Minecraft.getInstance();
        ((Minecraft) platform).execute(() -> {
            ((Minecraft) platform).setScreen(new berries.mods.tcwm.gui.screen.EditSoundPlayerScreen((BlockPos) params[0], (String) params[1], (String) params[2], (float) params[3], (float) params[4]));/*StationBroadcaster.propertiesScreen.apply(pos)*/
        });
    }
}
