package berries.mods.tcwm.render;

import berries.mods.tcwm.block.AirConditioner;
import berries.mods.tcwm.mvapi.MVBlockEntityRenderer;
import berries.mods.tcwm.mvapi.MVComponent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AirConditionerRenderer extends MVBlockEntityRenderer<AirConditioner.AirConditionerEntity, AirConditionerRenderer.AirConditionerRenderState> {
    public AirConditionerRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public AirConditionerRenderState createRenderState() {
        return new AirConditionerRenderState();
    }

    @Override
    public void fMVExtractRenderState(AirConditioner.AirConditionerEntity blockEntity, AirConditionerRenderState renderState, float f, Vec3 p, @Nullable Object crumblingOverlay) {
        if (blockEntity == null) return;

        renderState.setTemperature(blockEntity.getTemperature());
        if (blockEntity.getLevel() != null) {
            BlockState state = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos());
            if (state.hasProperty(AirConditioner.SIDE) && state.hasProperty(AirConditioner.OPEN)) {
                renderState.setRightSide(state.getValue(AirConditioner.SIDE) == AirConditioner.EnumSide.RIGHT);
                renderState.setOpen(state.getValue(AirConditioner.OPEN) > 0);
                renderState.setFacing(switch (state.getValue(AirConditioner.FACING)) {
                    case EAST -> 1;
                    case SOUTH -> 2;
                    case WEST -> 3;
                    default -> 0;
                });
            }
        }
    }

    @Override
    public void fMVRender(AirConditionerRenderState renderState, PoseStack stack, @Nullable Object submitNodeCollector, @Nullable Object cameraRenderState) {
        if (!renderState.isRightSide() || !renderState.isOpen()) return;

        stack.pushPose();
        switch (renderState.facing) {
            case 1 -> stack.translate(0.565, 7.7 / 16.0, 6.25 / 16.0);
            case 2 -> stack.translate(9.75 / 16.0, 7.7 / 16.0, 0.565);
            case 3 -> stack.translate(0.435, 7.7 / 16.0, 9.75 / 16.0);
            default -> stack.translate(6.25 / 16.0, 7.7 / 16.0, 0.435);
        }
        stack.scale(1 / 85f, 1 / 85f, 1 / 85f);
        stack.mulPose(Axis.XP.rotationDegrees(180));
        stack.mulPose(Axis.YP.rotationDegrees(90 * renderState.facing + 180));
        Component text = MVComponent.text(renderState.getTemperature() + "℃");
        float width = Minecraft.getInstance().font.width(text);

        if (submitNodeCollector != null) {
            renderText(stack, renderState,
                    //? < 1.21.9 {
                    Minecraft.getInstance().font
                    //? } else {
                    /*submitNodeCollector
                     *///? }
                    , -width / 2, -4f, text, false, 0, 0xffffffff, 0, 0, submitNodeCollector);
        }
        stack.popPose();
    }

    public static class AirConditionerRenderState extends State {
        protected float temperature;
        protected boolean isRightSide = false;
        protected boolean isOpen = false;
        protected int facing = 0; //0: north; 1: east; 2: south; 3: west.

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }

        public float getTemperature() {
            return temperature;
        }

        public void setRightSide(boolean v) {
            this.isRightSide = v;
        }

        public boolean isRightSide() {
            return isRightSide;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setFacing(int facing) {
            this.facing = facing;
        }

        public int getFacing() {
            return facing;
        }
    }
}
