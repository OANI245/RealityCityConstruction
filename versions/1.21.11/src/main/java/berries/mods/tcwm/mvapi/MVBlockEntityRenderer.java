package berries.mods.tcwm.mvapi;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class MVBlockEntityRenderer<T extends MVBlockEntity, S extends MVBlockEntityRenderer.State> implements BlockEntityRenderer<T, S> {
    //protected S renderState;

    public MVBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        //this.renderState = createRenderState();
    }

    @Override
    public abstract S createRenderState();

    @Override
    public void extractRenderState(T blockEntity, S blockEntityRenderState, float f, Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        fMVExtractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
    }

    public abstract void fMVExtractRenderState(T blockEntity, S renderState, float f, Vec3 p, @Nullable Object crumblingOverlay);

    @Override
    public void submit(S blockEntityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        fMVRender(blockEntityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    public abstract void fMVRender(S renderState, PoseStack stack, @Nullable Object submitNodeCollector, @Nullable Object cameraRenderState);

    public static <T extends MVBlockEntity, S extends MVBlockEntityRenderer.State, U extends MVBlockEntityRenderer<T, S>> void register(BlockEntityType<T> blockEntityType, Function<BlockEntityRendererProvider.Context, U> instanceFunction) {
        BlockEntityRenderers.register(blockEntityType, instanceFunction::apply);
    }

    public static class State extends BlockEntityRenderState {
    }

    public void renderText(PoseStack stack, S renderState, @Nullable Object collector, float x, float y, Component text, boolean shadow, int displayMode, int color, int backgroundColor, int outlineColor, Object mbs) {
        if (collector instanceof SubmitNodeCollector) {
            ((SubmitNodeCollector) collector).submitText(stack, x, y, text.getVisualOrderText(), shadow, switch (displayMode) {
                case 1 -> Font.DisplayMode.SEE_THROUGH;
                case 2 -> Font.DisplayMode.POLYGON_OFFSET;
                default -> Font.DisplayMode.NORMAL;
            }, renderState.lightCoords, color, backgroundColor, outlineColor);
        }
    }

    public void renderText(PoseStack stack, S renderState, @Nullable Object collector, float x, float y, Component text, boolean shadow, int displayMode, int light, int color, int backgroundColor, int outlineColor, Object mbs) {
        if (collector instanceof SubmitNodeCollector) {
            ((SubmitNodeCollector) collector).submitText(stack, x, y, text.getVisualOrderText(), shadow, switch (displayMode) {
                case 1 -> Font.DisplayMode.SEE_THROUGH;
                case 2 -> Font.DisplayMode.POLYGON_OFFSET;
                default -> Font.DisplayMode.NORMAL;
            }, light, color, backgroundColor, outlineColor);
        }
    }
}
