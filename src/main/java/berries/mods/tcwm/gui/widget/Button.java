package berries.mods.tcwm.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;

import java.util.function.Consumer;

public class Button extends AbstractButton {
    protected final Consumer<Button> onClick;
    protected boolean isAccentStyle = false;
    protected ResourceLocation icon;

    public Button(int x, int y, int width, int height, Component message, Consumer<Button> onClick) {
        super(x, y, width, height, message);
        this.onClick = onClick;
    }

    public Button(int x, int y, int width, int height, ResourceLocation icon, Component message, Consumer<Button> onClick) {
        this(x, y, width, height, message, onClick);
        this.icon = icon;
    }

    public Button(int x, int y, int width, int height, Component message, boolean isAccentStyle, Consumer<Button> onClick) {
        this(x, y, width, height, message, onClick);
        this.isAccentStyle = isAccentStyle;
    }

    public Button(int x, int y, int width, int height, ResourceLocation icon, Component message, boolean isAccentStyle, Consumer<Button> onClick) {
        this(x, y, width, height, message, isAccentStyle, onClick);
        this.icon = icon;
    }

    public Button(int x, int y, Component message, Consumer<Button> onClick) {
        this(x, y, 36, 18, message, onClick);
    }

    public Button(int x, int y, ResourceLocation icon, Component message, Consumer<Button> onClick) {
        this(x, y, 36, 18, icon, message, onClick);
    }

    public void setIcon(ResourceLocation icon) {
        this.icon = icon;
    }

    @Override
    public void onPress() {
        this.onClick.accept(this);
    }

    public void setAccent(boolean bl) {
        isAccentStyle = bl;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        int buttonBackground = (this.isAccentStyle ?
                (!this.isHovered() ? FastColor.ARGB32.color(210,188,152) :
                        FastColor.ARGB32.color(192,172,139)) :
                (!this.isHovered() ? FastColor.ARGB32.color(45,45,45) : FastColor.ARGB32.color(50,50,50)));
        int buttonBorder = (this.isAccentStyle ?
                !this.isHovered() ? FastColor.ARGB32.color(181,162,131) : FastColor.ARGB32.color(166,148,120) :
                !this.isHovered() ? FastColor.ARGB32.color(48,48,48) : FastColor.ARGB32.color(53,53,53));
        guiGraphics.fill(getX(), getY(), getX() + width, getY() + height, buttonBackground);
        guiGraphics.fill(getX(), getY() + height - 1, getX() + width, getY() + height, buttonBorder);
        guiGraphics.fill(getX(), getY(), getX() + 1, getY() + height, buttonBorder);
        guiGraphics.fill(getX(), getY(), getX() + width, getY() + 1, buttonBorder);
        guiGraphics.fill(getX() + width - 1, getY(), getX() + width, getY() + height, buttonBorder);
        if (this.isFocused()) {
            guiGraphics.fill(getX(), getY() + height, getX() + width, getY() + height + 1, FastColor.ARGB32.color(255, 255, 255));
            guiGraphics.fill(getX() - 1, getY(), getX(), getY() + height, FastColor.ARGB32.color(255, 255, 255));
            guiGraphics.fill(getX(), getY() - 1, getX() + width, getY(), FastColor.ARGB32.color(255, 255, 255));
            guiGraphics.fill(getX() + width, getY(), getX() + width + 1, getY() + height, FastColor.ARGB32.color(255, 255, 255));
        }
        FormattedCharSequence formattedCharSequence = this.getMessage().getVisualOrderText();
        int fontWidth = minecraft.font.width(formattedCharSequence) / 2;
        if (icon != null) {
            fontWidth -= this.getHeight() / 2;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            guiGraphics.blit(icon, this.getX() + width / 2 - minecraft.font.width(formattedCharSequence) / 2 - this.getHeight() / 2, this.getY(), 0, 0, height, height, height, height);
        }
        guiGraphics.drawString(minecraft.font, this.getMessage(), (this.getX() + width / 2) - fontWidth, (this.getY() + height / 2 - minecraft.font.lineHeight / 2), this.isAccentStyle ? 0 : 0xFFFFFF, false);
        //this.renderString(guiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }
}
