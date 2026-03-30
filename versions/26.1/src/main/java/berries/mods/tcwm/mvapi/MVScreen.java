package berries.mods.tcwm.mvapi;

import net.minecraft.client.gui.GuiGraphicsExtractor;
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
        super.extractRenderState(graphics.g, mouseX, mouseY, f);
    }

    @Override
    protected final void init() {
        initScreen();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        renderScreen(new GuiGraphicsData(graphics), mouseX, mouseY, a);
    }

    public record GuiGraphicsData(GuiGraphicsExtractor g) {
        public GuiGraphicsExtractor get() {
            return g;
        }
    }
}
