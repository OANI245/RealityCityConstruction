package berries.mods.tcwm.gui.widget;

import berries.mods.tcwm.gui.FlueroUI;
import com.mojang.blaze3d.systems.RenderSystem;
import berries.mods.tcwm.gui.Icons;
import berries.mods.tcwm.mvapi.MVComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
//? >= 1.21.9 {
/*import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.input.MouseButtonEvent;
*///? }
import net.minecraft.client.renderer.GameRenderer;
//? >= 1.21.6 {
/*import net.minecraft.client.renderer.RenderPipelines;
*///? } else {
import net.minecraft.util.FastColor;
//? }
import net.minecraft.network.chat.Component;

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

    //? < 1.21.9 {
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (int i = 0; i < items.size(); i++) {
            if (opened && mouseY > (this.getY() + 2 + this.getHeight()) && mouseX >= (this.getX()) && mouseX < (this.getX() + this.getWidth()) && mouseY < getY() + 3 + this.height + (16 * (i + 1))) {
                this.value = i;
                this.setMessage(items.get(i));
                closeList();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    //? } else {
    /*@Override
    public boolean mouseClicked(MouseButtonEvent e, boolean bl) {
        for (int i = 0; i < items.size(); i++) {
            if (opened && e.y() > (this.getY() + 2 + this.getHeight()) && e.x() >= (this.getX()) && e.x() < (this.getX() + this.getWidth()) && e.y() < getY() + 3 + this.height + (16 * (i + 1))) {
                this.value = i;
                this.setMessage(items.get(i));
                closeList();
                return true;
            }
        }
        return super.mouseClicked(e, bl);
    }
    *///? }

    @Override
    //? < 1.21.9 {
    
    public void onPress() {//? } else {
    /*public void onPress(InputWithModifiers inputWithModifiers) {
    *///? }
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
            //? < 1.21.11 {
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            //? } else {
    /*protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        *///? }
        Minecraft minecraft = Minecraft.getInstance();
        int buttonBackground = (
                (!this.isHovered() ? FlueroUI.rgb(45,45,45) : FlueroUI.rgb(50,50,50)));
        int buttonBorder = (
                !this.isHovered() ? FlueroUI.rgb(48,48,48) : FlueroUI.rgb(53,53,53));
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
        guiGraphics.drawString(minecraft.font, this.getMessage(), (this.getX() + 5), (this.getY() + height / 2 - minecraft.font.lineHeight / 2), FlueroUI.textColor(0xFFFFFF), false);
        //? < 1.21.6 {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                guiGraphics.blit(opened ? Icons.ARROW_UP : Icons.ARROW_DOWN,
                getX() + width - 12, getY() + 5, 0, 0, 9, 9, 9, 9);
        //? } else {
        /*guiGraphics.blit(RenderPipelines.GUI_TEXTURED, opened ? Icons.ARROW_UP : Icons.ARROW_DOWN,
                getX() + width - 12, getY() + 5, 0, 0, 9, 9, 9, 9);
        *///? }

        if (opened) {
            renderComboBoxList(guiGraphics, mouseX, mouseY);
        }
    }

    protected void renderComboBoxList(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.fill(getX(), getY() + 2 + this.height, getX() + this.width, getY() + 3 + this.height + items.size() * 16, FlueroUI.rgb(60, 60, 60));
        for (int i = 0; i < items.size(); i++) {
            var item = items.get(i);
            if (mouseX > getX() && mouseY > getY() + 2 + this.height + (16 * i) && mouseX < getX() + this.width && mouseY < getY() + 3 + this.height + (16 * (i + 1))) {
                guiGraphics.fill(getX(), getY() + 2 + this.height + (16 * i), getX() + this.width, getY() + 2 + this.height + (16 * (i + 1)), FlueroUI.rgb(72, 72, 72));
            }
            guiGraphics.drawString(Minecraft.getInstance().font, item, getX() + 5, getY() + 2 + this.height + (8 - Minecraft.getInstance().font.lineHeight / 2) + 16 * i, FlueroUI.textColor(0xFFFFFF), false);
        }
    }
}
