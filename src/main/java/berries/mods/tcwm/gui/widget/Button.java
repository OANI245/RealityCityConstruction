package berries.mods.tcwm.gui.widget;

import berries.mods.tcwm.gui.FlueroUI;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
//? >= 1.21.6
import net.minecraft.client.renderer.RenderPipelines;
//? >= 1.21.9
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
//? < 1.21.6
//import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;

import java.util.function.Consumer;

public class Button extends AbstractButton {
    protected final Consumer<Button> onClick;
    protected boolean isAccentStyle = false;
    protected Identifier icon;

    public Button(int x, int y, int width, int height, Component message, Consumer<Button> onClick) {
        super(x, y, width, height, message);
        this.onClick = onClick;
    }

    public Button(int x, int y, int width, int height, Identifier icon, Component message, Consumer<Button> onClick) {
        this(x, y, width, height, message, onClick);
        this.icon = icon;
    }

    public Button(int x, int y, int width, int height, Component message, boolean isAccentStyle, Consumer<Button> onClick) {
        this(x, y, width, height, message, onClick);
        this.isAccentStyle = isAccentStyle;
    }

    public Button(int x, int y, int width, int height, Identifier icon, Component message, boolean isAccentStyle, Consumer<Button> onClick) {
        this(x, y, width, height, message, isAccentStyle, onClick);
        this.icon = icon;
    }

    public Button(int x, int y, Component message, Consumer<Button> onClick) {
        this(x, y, 36, 18, message, onClick);
    }

    public Button(int x, int y, Identifier icon, Component message, Consumer<Button> onClick) {
        this(x, y, 36, 18, icon, message, onClick);
    }

    public void setIcon(Identifier icon) {
        this.icon = icon;
    }

    //? < 1.21.9 {
    /*@Override
    public void onPress() {
        this.onClick.accept(this);
    }
    *///? } else {
    @Override
    public void onPress(InputWithModifiers inputWithModifiers) {
        this.onClick.accept(this);
    }
    //? }

    public void setAccent(boolean bl) {
        isAccentStyle = bl;
    }

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
        Minecraft minecraft = Minecraft.getInstance();
        int buttonBackground = (this.isAccentStyle ?
                (!this.isHovered() ? FlueroUI.rgb(210,188,152) :
                        FlueroUI.rgb(192,172,139)) :
                (!this.isHovered() ? FlueroUI.rgb(45,45,45) : FlueroUI.rgb(50,50,50)));
        int buttonBorder = (this.isAccentStyle ?
                (!this.isHovered() ? FlueroUI.rgb(181,162,131) : FlueroUI.rgb(166,148,120)):
                (!this.isHovered() ? FlueroUI.rgb(48,48,48) : FlueroUI.rgb(53,53,53)));
        guiGraphics.fill(getX(), getY(), getX() + width, getY() + height, buttonBackground);
        guiGraphics.fill(getX(), getY() + height - 1, getX() + width, getY() + height, buttonBorder);
        guiGraphics.fill(getX(), getY(), getX() + 1, getY() + height, buttonBorder);
        guiGraphics.fill(getX(), getY(), getX() + width, getY() + 1, buttonBorder);
        guiGraphics.fill(getX() + width - 1, getY(), getX() + width, getY() + height, buttonBorder);
        if (this.isFocused()) {
            guiGraphics.fill(getX(), getY() + height, getX() + width, getY() + height + 1, FlueroUI.rgb(255, 255, 255));
            guiGraphics.fill(getX() - 1, getY(), getX(), getY() + height, FlueroUI.rgb(255, 255, 255));
            guiGraphics.fill(getX(), getY() - 1, getX() + width, getY(), FlueroUI.rgb(255, 255, 255));
            guiGraphics.fill(getX() + width, getY(), getX() + width + 1, getY() + height, FlueroUI.rgb(255, 255, 255));
        }
        FormattedCharSequence formattedCharSequence = this.getMessage().getVisualOrderText();
        int fontWidth = minecraft.font.width(formattedCharSequence) / 2;
        if (icon != null) {
            fontWidth -= this.getHeight() / 2;
            //? < 1.21.6 {
            /*RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            guiGraphics.blit(icon, this.getX() + width / 2 - minecraft.font.width(formattedCharSequence) / 2 - this.getHeight() / 2, this.getY(), 0, 0, height, height, height, height);
            *///? } else {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, icon, this.getX() + width / 2 - minecraft.font.width(formattedCharSequence) / 2 - this.getHeight() / 2, this.getY(), 0, 0, height, height, height, height);
            //? }
        }
        guiGraphics.drawString(minecraft.font, this.getMessage(), (this.getX() + width / 2) - fontWidth, (this.getY() + height / 2 - minecraft.font.lineHeight / 2), this.isAccentStyle ? FlueroUI.textColor(0) : FlueroUI.textColor(0xFFFFFF), false);
        //this.renderString(guiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }
}
