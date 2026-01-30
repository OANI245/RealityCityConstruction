package berries.mods.tcwm.gui;

import net.minecraft.client.gui.GuiGraphics;
//? >= 1.21.6 {
import net.minecraft.util.ARGB;
//? } else {
/*import net.minecraft.util.FastColor;
*///? }

public class FlueroUI {
    public static void renderCenteredDialog(GuiGraphics graphics, int screenWidth, int screenHeight, int width, int height) {
        int minX = screenWidth / 2 - width / 2;
        int minY = screenHeight / 2 - height / 2;
        int maxX = screenWidth / 2 + width / 2;
        int maxY = screenHeight / 2 + height / 2;
        graphics.fill(minX, minY, maxX, maxY, rgb(32, 32, 32));
        graphics.fill(minX, minY, maxX, minY + 1, rgb(59, 59, 59));
        graphics.fill(minX, minY, minX + 1, maxY, rgb(59, 59, 59));
        graphics.fill(minX, maxY - 1, maxX, maxY, rgb(59, 59, 59));
        graphics.fill(maxX - 1, minY, maxX, maxY, rgb(59, 59, 59));
    }

    public static int rgb(int r, int g, int b) {
        //? < 1.21.6 {
        /*return FastColor.ARGB32.color(255, r, g, b);
        *///? } else {
        return ARGB.color(r, g, b);
        //? }
    }

    public static int argb(int a, int r, int g, int b) {
        //? < 1.21.6 {
        /*return FastColor.ARGB32.color(a, r, g, b);
         *///? } else {
        return ARGB.color(a, r, g, b);
        //? }
    }

    public static int textColor(int hex) {
        //? < 1.21.6 {
        /*return hex;
        *///? } else {
        int r = hex >> 16 & 0xff;
        int g = hex >> 8 & 0xff;
        int b = hex & 0xff;

        return rgb(r, g, b);
        //? }
    }
}
