package berries.mods.tcwm.gui.screen;

import berries.mods.tcwm.gui.FlueroUI;
import berries.mods.tcwm.gui.widget.Button;
import berries.mods.tcwm.mvapi.MVComponent;
import berries.mods.tcwm.mvapi.MVScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.FastColor;

public class EditSoundPlayerNameScreen extends MVScreen {
    protected final EditSoundPlayerScreen previous;
    protected EditBox nameEditBox;

    public EditSoundPlayerNameScreen(EditSoundPlayerScreen previousScreen) {
        super(MVComponent.translatable("gui.tcwm.ESPNS.title"));
        this.previous = previousScreen;
    }

    @Override
    public void initScreen() {
        if (minecraft == null) return;

        nameEditBox = new EditBox(minecraft.font, width / 2 - 50, height / 2 - 13, 100, 18, MVComponent.EMPTY);
        nameEditBox.setValue(previous.name.trim().isEmpty() ? I18n.get("block.tcwm.homo_station_broadcaster.default_name") : previous.name.trim());
        nameEditBox.setTextColor(FastColor.ARGB32.color(16, 192, 16));
        addRenderableWidget(nameEditBox);
        addRenderableWidget(new Button(width / 2 - 25, height / 2 + 11, 50, 18, MVComponent.text("确定"), true, (button) -> saveChangesAndBack()));
    }

    @Override
    public void onClose() {
        saveChangesAndBack();
    }

    public void saveChangesAndBack() {
        this.previous.name = nameEditBox.getValue();
        if (minecraft != null) {
            minecraft.setScreen(previous);
        }
    }

    @Override
    public void renderScreen(GuiGraphics graphics, int mouseX, int mouseY, float f) {
        if (minecraft == null) return;
        renderBackground(graphics, mouseX, mouseY, f);
        FlueroUI.renderCenteredDialog(graphics, this.width, this.height, 120, 74);
        super.renderScreen(graphics, mouseX, mouseY, f);
        graphics.drawCenteredString(minecraft.font, this.getTitle(), width / 2, height / 2 - 29, 0xFFFFFF);
    }
}
