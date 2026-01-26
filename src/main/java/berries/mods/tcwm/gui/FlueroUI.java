package berries.mods.tcwm.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FastColor;

public class FlueroUI {
    public static void renderCenteredDialog(GuiGraphics graphics, int screenWidth, int screenHeight, int width, int height) {
        int minX = screenWidth / 2 - width / 2;
        int minY = screenHeight / 2 - height / 2;
        int maxX = screenWidth / 2 + width / 2;
        int maxY = screenHeight / 2 + height / 2;
        graphics.fill(minX, minY, maxX, maxY, FastColor.ARGB32.color(32, 32, 32));
        graphics.fill(minX, minY, maxX, minY + 1, FastColor.ARGB32.color(59, 59, 59));
        graphics.fill(minX, minY, minX + 1, maxY, FastColor.ARGB32.color(59, 59, 59));
        graphics.fill(minX, maxY - 1, maxX, maxY, FastColor.ARGB32.color(59, 59, 59));
        graphics.fill(maxX - 1, minY, maxX, maxY, FastColor.ARGB32.color(59, 59, 59));
    }
}
