package berries.mods.tcwm.gui.widget;

import berries.mods.tcwm.gui.FlueroUI;
import com.mojang.blaze3d.systems.RenderSystem;
import berries.mods.tcwm.mvapi.MVComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
//? >= 1.21.9 {
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.input.MouseButtonEvent;
//? }
import net.minecraft.client.renderer.GameRenderer;
//? >= 1.21.6
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
//? < 1.21.6
//import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TransparentIconButton extends AbstractButton {
    public Identifier icon;
    protected Consumer<TransparentIconButton> onPress;

    public TransparentIconButton(int x, int y, int scale, @NotNull Identifier icon, Consumer<TransparentIconButton> onPress) {
        super(x, y, scale, scale, MVComponent.EMPTY);
        this.icon = icon;
        this.onPress = onPress;
    }

    //? < 1.21.9 {
    /*@Override
    public void onPress() {
        this.onPress.accept(this);
    }
    *///? } else {
    @Override
    public void onPress(InputWithModifiers inputWithModifiers) {
        this.onPress.accept(this);
    }
    //? }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }

    @Override
            //? < 1.21.11 {
    /*protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            *///? } else {
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        //? }
        //? < 1.21.6 {
        /*RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(icon, getX(), getY(), 0, 0, width, height, width, height);
        *///? } else {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, icon, getX(), getY(), 0, 0, width, height, width, height);
        //? }
        if (isHoveredOrFocused()) {
            guiGraphics.fill(getX(), getY(), getX() + width, getY() + height, FlueroUI.argb(isFocused() ? 6 : 18, 255, 255, 255));
        }
    }
}
