package berries.mods.tcwm.mvapi;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public abstract class MVBlockEntityRenderer<T extends MVBlockEntity, S extends MVBlockEntityRenderer.State> implements BlockEntityRenderer<T> {
    protected S renderState;

    public MVBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.renderState = createRenderState();
    }
    public abstract S createRenderState();

    @Override
    public void render(T blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, Vec3 vec3) {
        fMVExtractRenderState(blockEntity, this.renderState, f, vec3, null);
        State.extractBase(blockEntity, this.renderState);
        fMVRender(this.renderState, poseStack, multiBufferSource, null);
    }

    public abstract void fMVExtractRenderState(T blockEntity, S renderState, float f, Vec3 p, @Nullable Object crumblingOverlay);

    public abstract void fMVRender(S renderState, PoseStack stack, @Nullable Object multiBufferSource, @Nullable Object cameraRenderState);

    public static <T extends MVBlockEntity, S extends State, U extends MVBlockEntityRenderer<T, S>> void register(BlockEntityType<T> blockEntityType, Function<BlockEntityRendererProvider.Context, U> instanceFunction) {
        BlockEntityRenderers.register(blockEntityType, instanceFunction::apply);
    }

    public static class State {
        public BlockPos blockPos = BlockPos.ZERO;
        public BlockState blockState = Blocks.AIR.defaultBlockState();
        public BlockEntityType<?> blockEntityType = BlockEntityType.TEST_BLOCK;
        public int lightCoords;

        public static void extractBase(BlockEntity blockEntity, State blockEntityRenderState) {
            blockEntityRenderState.blockPos = blockEntity.getBlockPos();
            blockEntityRenderState.blockState = blockEntity.getBlockState();
            blockEntityRenderState.blockEntityType = blockEntity.getType();
            blockEntityRenderState.lightCoords = blockEntity.getLevel() != null ? LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos()) : 0xF000F0;
        }
    }

    public void renderText(PoseStack stack, S renderState, @Nullable Object renderer, float x, float y, Component text, boolean shadow, int displayMode, int color, int backgroundColor, int outlineColor, Object mbs) {
        if (renderer instanceof Font) {
            ((Font) renderer).drawInBatch(text, x, y, color, shadow, stack.last().pose(), (MultiBufferSource) mbs, switch (displayMode) {
                case 1 -> Font.DisplayMode.SEE_THROUGH;
                case 2 -> Font.DisplayMode.POLYGON_OFFSET;
                default -> Font.DisplayMode.NORMAL;
            }, backgroundColor, renderState.lightCoords);
        }
    }

    public void renderText(PoseStack stack, S renderState, @Nullable Object renderer, float x, float y, Component text, boolean shadow, int displayMode, int light, int color, int backgroundColor, int outlineColor, Object mbs) {
        if (renderer instanceof Font) {
            ((Font) renderer).drawInBatch(text, x, y, color, shadow, stack.last().pose(), (MultiBufferSource) mbs, switch (displayMode) {
                case 1 -> Font.DisplayMode.SEE_THROUGH;
                case 2 -> Font.DisplayMode.POLYGON_OFFSET;
                default -> Font.DisplayMode.NORMAL;
            }, backgroundColor, light);
        }
    }
}
