package berries.mods.tcwm.mvapi;

import net.minecraft.CrashReport;
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

    public void renderScreen(GuiGraphics graphics, int mouseX, int mouseY, float f) {
        //super.render(graphics, mouseX, mouseY, f);
        Field field = null;
        try {
            field = Screen.class.getDeclaredField("field_33816");
            field.setAccessible(true);
            List<Renderable> lr = (List<Renderable>) (field.get(this));
            for (Renderable r : lr) {
                r.render(graphics, mouseX, mouseY, f);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            if (minecraft != null) {
                minecraft.emergencySaveAndCrash(new CrashReport("Screen Render Failed", e));
            }
        }
    }

    @Override
    protected final void init() {
        initScreen();
    }

    @Override
    public final void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderScreen(guiGraphics, mouseX, mouseY, partialTick);
    }
}
