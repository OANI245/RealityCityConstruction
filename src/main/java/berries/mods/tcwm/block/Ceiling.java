package berries.mods.tcwm.block;

import berries.mods.tcwm.mvapi.MVSimpleCodecHorizontalDirectionalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Ceiling extends MVSimpleCodecHorizontalDirectionalBlock {

    public Ceiling(Properties properties, int lightLevel) {
        super(properties.lightLevel((state) -> lightLevel));
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)
        );
    }

    public Ceiling(Properties properties) {
        this(properties, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HorizontalDirectionalBlock.FACING);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.create(0.0F, 0.0F, 0.0F, 1.0F, 0.35F, 1.0F);
    }

    @Override
    //? < 1.20.5 {
    /*public
    *///? } else {
    protected
    //? }
    VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.create(0.0F, 0.0F, 0.0F, 1.0F, 0.3F, 1.0F);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }
}
