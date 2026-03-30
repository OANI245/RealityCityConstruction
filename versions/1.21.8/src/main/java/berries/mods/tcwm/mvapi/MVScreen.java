package berries.mods.tcwm.mvapi;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Field;
import java.util.List;

public abstract class MVScreen extends Screen {
    public MVScreen(Component title) {
        super(title);
    }

    public MVScreen() {
        this(MVComponent.EMPTY);
    }

    public abstract void initScreen();

    public void renderScreen(GuiGraphicsData graphics, int mouseX, int mouseY, float f) {
        super.render(graphics.g, mouseX, mouseY, f);
    }

    @Override
    protected final void init() {
        initScreen();
    }

    @Override
    public final void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderScreen(new GuiGraphicsData(guiGraphics), mouseX, mouseY, partialTick);
    }

    public record GuiGraphicsData(GuiGraphics g) {
        public GuiGraphics get() {
            return g;
        }
    }
}
