package berries.mods.tcwm.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import berries.mods.tcwm.gui.Icons;
import berries.mods.tcwm.mvapi.MVComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ComboBox extends Button {
    protected List<Component> items;
    protected int value;
    protected boolean opened = false;
    protected Consumer<Component> onValueChanged;

    public ComboBox(int x, int y, int width, int height, List<Component> items, int value) {
        super(x, y, width, height, MVComponent.EMPTY, ((cb) -> {}));
        if (items.isEmpty()) {
            throw new IllegalArgumentException("items must not be empty");
        }
        this.items = new ArrayList<>(items);
        this.value = Math.min(value, items.size() - 1);
        this.setMessage(items.get(this.value));
    }

    public ComboBox(int x, int y, int width, int height, List<Component> items) {
        this(x, y, width, height, items, 0);
    }

    public boolean isOpened() {
        return opened;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (opened && mouseY > (this.getY() + this.getHeight()) && mouseX >= (this.getX()) && mouseX < (this.getX() + this.getWidth())) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void onPress() {
        if (opened) {
            closeList();
        } else {
            openList();
        }
    }

    public void openList() {
        this.opened = true;
    }

    public void closeList() {
        this.opened = false;
    }

    public Component getCurrentValue() {
        return this.items.get(this.value);
    }

    public int getValue() {
        return value;
    }

    public void onValueChanged(Consumer<Component> listener) {
        this.onValueChanged = listener;
    }

    @Override
    @Deprecated(forRemoval = true)
    public void setAccent(boolean bl) {
        throw new RuntimeException("Not supported");
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        int buttonBackground = (
                (!this.isHovered() ? FastColor.ARGB32.color(45,45,45) : FastColor.ARGB32.color(50,50,50)));
        int buttonBorder = (
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
        guiGraphics.drawString(minecraft.font, this.getMessage(), (this.getX() + 5), (this.getY() + height / 2 - minecraft.font.lineHeight / 2), 0xFFFFFF, false);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(opened ? Icons.ARROW_UP : Icons.ARROW_DOWN,
                getX() + width - 12, getY() + 5, 0, 0, 9, 9, 9, 9);

        if (opened) {
            renderComboBoxList(guiGraphics, mouseX, mouseY);
        }
    }

    protected void renderComboBoxList(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }
}
