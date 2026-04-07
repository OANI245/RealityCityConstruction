package berries.mods.tcwm.mvapi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.function.Supplier;

public class MVScreenHelper {
    private final Supplier<Minecraft> minecraftGetter;

    public MVScreenHelper(Supplier<Minecraft> minecraftGetter) {
        this.minecraftGetter = minecraftGetter;
    }

    public void setScreen(Screen screen) {
        minecraftGetter.get().setScreenAndShow(screen);
    }
}
