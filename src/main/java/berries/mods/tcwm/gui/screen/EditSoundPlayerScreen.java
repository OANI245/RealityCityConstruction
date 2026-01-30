package berries.mods.tcwm.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import berries.mods.tcwm.block.StationBroadcaster;
import berries.mods.tcwm.gui.Icons;
import berries.mods.tcwm.gui.FlueroUI;
import berries.mods.tcwm.gui.widget.Button;
import berries.mods.tcwm.gui.widget.ComboBox;
import berries.mods.tcwm.gui.widget.PropertiesList;
import berries.mods.tcwm.gui.widget.TransparentIconButton;
import berries.mods.tcwm.mvapi.MVComponent;
import berries.mods.tcwm.mvapi.MVScreen;
import berries.mods.tcwm.network.PacketUpdateBlockEntity;
import berries.mods.tcwm.util.TcwmBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
//? >= 1.21.9 {
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
//? }
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
//? < 1.21.6
//import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.joml.Matrix3x2fStack;

import java.util.List;

public class EditSoundPlayerScreen extends MVScreen {
    protected String name;
    protected BlockPos pos;
    protected BlockEntity blockEntity;
    protected String soundID = "";
    protected float range = 0;
    protected int pitch = 0;
    protected boolean fieldsLoaded = false;
    protected Runnable saveWidgetChanges = () -> {
    };

    protected Button finishButton;
    protected ComboBox pitchComboBox;

    protected PropertiesList list;

    public EditSoundPlayerScreen(BlockPos pos) {
        super(MVComponent.translatable("gui.tcwm.SSBAS.title"));
        this.pos = pos;
    }

    public EditSoundPlayerScreen(BlockPos pos, String name, String soundID, float range, float pitch) {
        this(pos);
        this.name = name;
        this.soundID = soundID;
        this.range = range;
        this.pitch = switch (StationBroadcaster.StationBroadcasterEntity.Pitch.getValue(pitch)) {
            case FAST -> {
                yield 1;
            }
            case VERY_FAST -> {
                yield 2;
            }
            case SLOW -> {
                yield 3;
            }
            case VERY_SLOW -> {
                yield 4;
            }
            default -> {
                yield 0;
            }
        };
    }

    @Override
    public void initScreen() {
        if (minecraft == null || minecraft.level == null) return;

        if (!fieldsLoaded && name != null && soundID != null) {
            blockEntity = minecraft.level.getBlockEntity(pos);
            if (blockEntity instanceof StationBroadcaster.StationBroadcasterEntity) {
                ((StationBroadcaster.StationBroadcasterEntity) blockEntity).setName(name);
                ((StationBroadcaster.StationBroadcasterEntity) blockEntity).setSoundID(soundID);
                ((StationBroadcaster.StationBroadcasterEntity) blockEntity).setRange(range);
                ((StationBroadcaster.StationBroadcasterEntity) blockEntity).setPitch(
                        switch (pitch) {
                            case 1 -> StationBroadcaster.StationBroadcasterEntity.Pitch.FAST;
                            case 2 -> StationBroadcaster.StationBroadcasterEntity.Pitch.VERY_FAST;
                            case 3 -> StationBroadcaster.StationBroadcasterEntity.Pitch.SLOW;
                            case 4 -> StationBroadcaster.StationBroadcasterEntity.Pitch.VERY_SLOW;
                            default -> StationBroadcaster.StationBroadcasterEntity.Pitch.DEFAULT;
                        }
                );
            } else minecraft.setScreen(null);
        } else if (pos != null) {
            blockEntity = minecraft.level.getBlockEntity(pos);
            if (blockEntity instanceof StationBroadcaster.StationBroadcasterEntity) {
                this.name = ((StationBroadcaster.StationBroadcasterEntity) blockEntity).getName().trim();
                this.soundID = ((StationBroadcaster.StationBroadcasterEntity) blockEntity).getSoundID();
                this.range = ((StationBroadcaster.StationBroadcasterEntity) blockEntity).getRange();
                this.pitch = switch (((StationBroadcaster.StationBroadcasterEntity) blockEntity).getPitch()) {
                    case FAST -> {
                        yield 1;
                    }
                    case VERY_FAST -> {
                        yield 2;
                    }
                    case SLOW -> {
                        yield 3;
                    }
                    case VERY_SLOW -> {
                        yield 4;
                    }
                    default -> {
                        yield 0;
                    }
                };
            }

            fieldsLoaded = true;
        }

        EditBox rangeEditBox = new EditBox(minecraft.font, 0, 0, 75, 18, MVComponent.EMPTY) {
            @Override
                    //? < 1.21.9 {
            /*public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                var temp = super.keyPressed(keyCode, scanCode, modifiers);
                *///? } else {
            public boolean keyPressed(KeyEvent e) {
                var temp = super.keyPressed(e);
                //? }
                try {
                    range = Float.parseFloat(this.getValue());
                    this.setTextColor(FlueroUI.rgb(255, 255, 255));
                } catch (NumberFormatException en) {
                    this.setTextColor(FlueroUI.rgb(255, 160, 171));
                }
                return temp;
            }
        };
        rangeEditBox.setMaxLength(12);
        rangeEditBox.setValue(String.valueOf(range));

        EditBox idBox = new EditBox(minecraft.font, 0, 0, 85, 18, MVComponent.EMPTY) {
            @Override
                    //? < 1.21.9 {
            /*public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                var temp = super.keyPressed(keyCode, scanCode, modifiers);
                *///? } else {
            public boolean keyPressed(KeyEvent e) {
                var temp = super.keyPressed(e);
                //? }
                soundID = this.getValue();
                if (!soundID.matches("[a-z0-9_.]+:[a-z0-9_.]+$")) {
                    this.setTextColor(FlueroUI.rgb(255, 180, 171));
                } else {
                    this.setTextColor(FlueroUI.rgb(255, 255, 255));
                }
                return temp;
            }
        };
        idBox.setMaxLength(128);
        idBox.setValue(soundID);

        pitchComboBox = new ComboBox(0, 0, 75, 18, List.of(
                MVComponent.text("1.0×"),
                MVComponent.text("1.5×"),
                MVComponent.text("2.0×"),
                MVComponent.text("0.75×"),
                MVComponent.text("0.5×")
        ), pitch);

        pitchComboBox.onValueChanged((value) -> {
            String strValue = value.getString();
            pitch = switch (strValue.trim()) {
                case "1.5x" -> 1;
                case "2.0x" -> 2;
                case "0.75x" -> 3;
                case "0.5x" -> 4;
                default -> 0;
            };
        });

        list = PropertiesList.newList(width / 2 - 105, height / 2 - 49, 210, 100, List.of(
                PropertiesList.ItemProperties.newInstance(
                        MVComponent.text("音频 ID"),
                        new PropertiesList.StackPanel(
                                18,
                                idBox,
                                new Button(0, 0, 18, 18, MVComponent.text("..."), true, (button) -> {
                                    minecraft.setScreen(new SoundListScreen(EditSoundPlayerScreen.this));
                                })
                        )
                ),
                PropertiesList.ItemProperties.newInstance(MVComponent.text("音频范围（区块）"), rangeEditBox),
                PropertiesList.ItemProperties.newInstance(
                        MVComponent.text("速度"),
                        pitchComboBox
                )
        ));

        this.finishButton = addRenderableWidget(new Button(width / 2 + 34, height / 2 + 56, 60, 18, MVComponent.text("确定"), true, (button) -> {
            saveChanges();
            minecraft.setScreen(null);
        }));

        list.reload(this::addRenderableWidget, this::removeWidget, this.children()::contains);

        addRenderableWidget(new TransparentIconButton(width / 2 + 80, height / 2 - 69, 15, Icons.EDIT, (button) -> {
            minecraft.setScreen(new EditSoundPlayerNameScreen(EditSoundPlayerScreen.this));
        }));

        saveWidgetChanges = () -> {
            try {
                soundID = idBox.getValue();
                range = Float.parseFloat(rangeEditBox.getValue());
                pitch = pitchComboBox.getValue();
            } catch (NumberFormatException e) {
                if (minecraft.player != null) {
                    //? < 1.21.5 {
                    /*minecraft.player.sendSystemMessage(MVComponent.text("\u00A7l[音频播放器] \u00A7r\u00A7c范围数值输入有误，请重新编辑。"));
                    *///? } else {
                    minecraft.player.displayClientMessage(MVComponent.text("\u00A7l[音频播放器] \u00A7r\u00A7c范围数值输入有误，请重新编辑。"), true);
                     //? }
                }
            }
        };
    }

    @Override
    public void renderScreen(GuiGraphics graphics, int mouseX, int mouseY, float f) {
        if (minecraft == null) return;
        if (this.name == null || this.soundID == null) minecraft.setScreen(null);

        if (finishButton != null) {
            finishButton.active = !pitchComboBox.isOpened();
        }

        //? < 1.20.5 {
        /*renderBackground(graphics);
         *///? } else if < 1.21.6 {
        /*this.renderBackground(graphics, mouseX, mouseY, f);
        *///? }
        int dialogWidth = 225;
        int dialogHeight = 182;
        FlueroUI.renderCenteredDialog(graphics, this.width, this.height, dialogWidth, dialogHeight);
        Component title = this.name.isEmpty() ? MVComponent.translatable("block.tcwm.homo_station_broadcaster.default_name") : MVComponent.text(this.name);
        FormattedCharSequence formattedCharSequence = title.getVisualOrderText();
        float scale =
                minecraft.font.width(formattedCharSequence) >= (dialogWidth - 50) / 2f ?
                        minecraft.font.width(formattedCharSequence) >= (dialogWidth - 68) / 1.5f ?
                                minecraft.font.width(formattedCharSequence) >= (dialogWidth - 80) / 1.25f ?
                                        1.0f : 1.25f
                                : 1.5f
                        : 2.0f;
        //? < 1.21.6 {
        /*PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        graphics.drawString(minecraft.font, title, (int) Math.floor((width / 2f - 95) / scale), (int) Math.ceil((height / 2f - 71) / scale), FlueroUI.textColor(0xFFFFFF), false);
        poseStack.popPose();
        *///? } else {
        graphics.nextStratum();
        Matrix3x2fStack poseStack = graphics.pose();
        poseStack.pushMatrix();
        poseStack.scale(scale, scale);
        graphics.drawString(minecraft.font, title, (int)Math.floor((width / 2f - 95) / scale), (int)Math.ceil((height / 2f - 71) / scale), FlueroUI.textColor(0xFFFFFF), false);
        poseStack.popMatrix();
        graphics.nextStratum();
        //? }

        for (PropertiesList.Item<AbstractWidget> item : list.items) {
            item.render(graphics, mouseX, mouseY, f);
        }
        for (PropertiesList.Item<PropertiesList.StackPanel> item : list.stackPanels) {
            item.render(graphics, mouseX, mouseY, f);
        }
        super.renderScreen(graphics, mouseX, mouseY, f);
    }

    public void saveChanges() {
        saveWidgetChanges.run();
        if (blockEntity instanceof StationBroadcaster.StationBroadcasterEntity) {
            ((StationBroadcaster.StationBroadcasterEntity) blockEntity).setName(name);
            ((StationBroadcaster.StationBroadcasterEntity) blockEntity).setSoundID(soundID);
            ((StationBroadcaster.StationBroadcasterEntity) blockEntity).setRange(range);
            ((StationBroadcaster.StationBroadcasterEntity) blockEntity).setPitch(
                    switch (pitch) {
                        case 1 -> StationBroadcaster.StationBroadcasterEntity.Pitch.FAST;
                        case 2 -> StationBroadcaster.StationBroadcasterEntity.Pitch.VERY_FAST;
                        case 3 -> StationBroadcaster.StationBroadcasterEntity.Pitch.SLOW;
                        case 4 -> StationBroadcaster.StationBroadcasterEntity.Pitch.VERY_SLOW;
                        default -> StationBroadcaster.StationBroadcasterEntity.Pitch.DEFAULT;
                    }
            );

            PacketUpdateBlockEntity.sendUpdateC2S((TcwmBlockEntity) blockEntity, pos);
        }
    }

    //? >= 1.21.6 && < 1.21.9 {
    /*@Override public boolean mouseClicked(double d, double e, int i) {
        return pitchComboBox.mouseClicked(d, e, i) || super.mouseClicked(d, e, i);
    }*/
    //? } else if >= 1.21.9 {
    @Override public boolean mouseClicked(MouseButtonEvent e, boolean bl) {
        return pitchComboBox.mouseClicked(e, bl) || super.mouseClicked(e, bl);
    }
    //? }

    @Override
    public void onClose() {
        saveChanges();
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
