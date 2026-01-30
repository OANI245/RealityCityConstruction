package berries.mods.tcwm.block;

import berries.mods.tcwm.mvapi.MVStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
//? >= 1.21.5
//import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TunnelWires2 extends Block implements SimpleWaterloggedBlock {
    public static final Property<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty TOP = MVStateProperties.bool("top");
    public static final BooleanProperty BOTTOM = MVStateProperties.bool("bottom");
    public static final BooleanProperty LEFT = MVStateProperties.bool("left");
    public static final BooleanProperty RIGHT = MVStateProperties.bool("right");
    public static final BooleanProperty CORNER_LEFT = MVStateProperties.bool("corner_left");
    public static final BooleanProperty CORNER_RIGHT = MVStateProperties.bool("corner_right");

    public TunnelWires2(Properties settings) {
        super(settings);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(WATERLOGGED, false)
                        .setValue(TOP, false)
                        .setValue(BOTTOM, false)
                        .setValue(LEFT, false)
                        .setValue(RIGHT, false)
                        .setValue(CORNER_LEFT, false)
                        .setValue(CORNER_RIGHT, false)
        );
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        //? < 1.21.5 {
        Vec3 offset = state.getOffset(world, pos);
        //? } else {
        /*Vec3 offset = state.getOffset(pos);
        *///? }
        switch ((Direction) state.getValue(FACING)) {
            case SOUTH -> {
                VoxelShape vs0 = box(0, 0, 0, 16, 16, 2);
                if (state.getValue(CORNER_LEFT)) {
                    vs0 = Shapes.or(vs0, box(0, 0, 0, 2, 16, 16));
                }
                if (state.getValue(CORNER_RIGHT)) {
                    vs0 = Shapes.or(vs0, box(14, 0, 0, 16, 16, 16));
                }
                return vs0.move(offset.x, offset.y, offset.z);
            }
            case EAST -> {
                VoxelShape vs1 = box(0, 0, 0, 2, 16, 16);
                if (state.getValue(CORNER_LEFT)) {
                    vs1 = Shapes.or(vs1, box(0, 0, 14, 16, 16, 16));
                }
                if (state.getValue(CORNER_RIGHT)) {
                    vs1 = Shapes.or(vs1, box(0, 0, 0, 16, 16, 2));
                }
                return vs1.move(offset.x, offset.y, offset.z);
            }
            case WEST -> {
                VoxelShape vs2 = box(14, 0, 0, 16, 16, 16);
                if (state.getValue(CORNER_LEFT)) {
                    vs2 = Shapes.or(vs2, box(0, 0, 0, 16, 16, 2));
                }
                if (state.getValue(CORNER_RIGHT)) {
                    vs2 = Shapes.or(vs2, box(0, 0, 14, 16, 16, 16));
                }
                return vs2.move(offset.x, offset.y, offset.z);
            }
            default -> {
                VoxelShape vsd = box(0, 0, 14, 16, 16, 16);
                if (state.getValue(CORNER_LEFT)) {
                    vsd = Shapes.or(vsd, box(14, 0, 0, 16, 16, 16));
                }
                if (state.getValue(CORNER_RIGHT)) {
                    vsd = Shapes.or(vsd, box(0, 0, 0, 2, 16, 16));
                }
                return vsd.move(offset.x, offset.y, offset.z);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, TOP, BOTTOM, LEFT, RIGHT, CORNER_LEFT, CORNER_RIGHT);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
            //? < 1.20.5 {
    /*public
            *///? } else {
    protected
        //? }
    //? < 1.21.5 {
    void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl)
    //? } else {
    /*void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, @Nullable Orientation orientation, boolean bl)
    *///? }
    {
        BlockPos bottomNeighborPos = blockPos.below();
        BlockPos topNeighborPos = blockPos.above();
        BlockState bottomNeighborState = level.getBlockState(bottomNeighborPos);
        BlockState topNeighborState = level.getBlockState(topNeighborPos);
        Direction direction0 = blockState.getValue(FACING);
        BlockPos leftNeighborPos = blockPos.relative(direction0.getClockWise());
        BlockState leftNeighborState = level.getBlockState(leftNeighborPos);
        if (leftNeighborState.getBlock() instanceof TunnelWires2) {
            leftNeighborState = leftNeighborState.setValue(RIGHT, true);
            level.setBlockAndUpdate(leftNeighborPos, leftNeighborState);
        }
        BlockPos leftDiagonalPos = leftNeighborPos.relative(direction0);
        BlockState leftDiagonalState = level.getBlockState(leftDiagonalPos);
        BlockPos leftDiagonalAPos = leftNeighborPos.relative(direction0.getOpposite());
        BlockState leftDiagonalAState = level.getBlockState(leftDiagonalAPos);
        blockState = blockState.setValue(LEFT,  (leftDiagonalAState.getBlock() instanceof TunnelWires2 && leftDiagonalAState.getValue(FACING) == direction0) || !(leftNeighborState.getBlock() instanceof AirBlock) && !(blockState.getValue(TOP) || blockState.getValue(BOTTOM)) || leftNeighborState.getBlock() instanceof TunnelWires2);
        if (leftDiagonalState.getBlock() instanceof TunnelWires2) {
            blockState = blockState.setValue(LEFT, true).setValue(CORNER_LEFT, true);
            leftDiagonalState = leftDiagonalState.setValue(RIGHT, true);
            level.setBlockAndUpdate(leftDiagonalPos, leftDiagonalState);
        }
        BlockPos rightNeighborPos = blockPos.relative(direction0.getCounterClockWise());
        BlockState rightNeighborState = level.getBlockState(rightNeighborPos);
        if (rightNeighborState.getBlock() instanceof TunnelWires2) {
            rightNeighborState = rightNeighborState.setValue(LEFT, true);
            level.setBlockAndUpdate(rightNeighborPos, rightNeighborState);
        }
        BlockPos rightDiagonalPos = rightNeighborPos.relative(direction0);
        BlockState rightDiagonalState = level.getBlockState(rightDiagonalPos);
        BlockPos rightDiagonalAPos = rightNeighborPos.relative(direction0.getOpposite());
        BlockState rightDiagonalAState = level.getBlockState(rightDiagonalAPos);
        blockState = blockState.setValue(RIGHT, (rightDiagonalAState.getBlock() instanceof TunnelWires2 && rightDiagonalAState.getValue(FACING) == direction0) || !(rightNeighborState.getBlock() instanceof AirBlock) && !(blockState.getValue(TOP) || blockState.getValue(BOTTOM)) || rightNeighborState.getBlock() instanceof TunnelWires2);
        if (rightDiagonalState.getBlock() instanceof TunnelWires2) {
            blockState = blockState.setValue(RIGHT, true).setValue(CORNER_RIGHT, true);
            rightDiagonalState = rightDiagonalState.setValue(LEFT, true);
            level.setBlockAndUpdate(rightDiagonalPos, rightDiagonalState);
        }
        if (bottomNeighborState.getBlock() instanceof TunnelWires2) {
            boolean bl0 = !(blockState.getValue(LEFT) && blockState.getValue(RIGHT)) || !(bottomNeighborState.getValue(LEFT) && bottomNeighborState.getValue(RIGHT));
            bottomNeighborState = bottomNeighborState.setValue(TOP, bl0);
            blockState = blockState.setValue(BOTTOM, bl0);
            level.setBlockAndUpdate(bottomNeighborPos, bottomNeighborState);
        } else {
            blockState = blockState.setValue(BOTTOM, false);
        }
        if (topNeighborState.getBlock() instanceof TunnelWires2) {
            boolean bl1 = !(blockState.getValue(LEFT) && blockState.getValue(RIGHT)) || !(topNeighborState.getValue(LEFT) && topNeighborState.getValue(RIGHT));
            topNeighborState = topNeighborState.setValue(BOTTOM, bl1);
            blockState = blockState.setValue(TOP, bl1);
            level.setBlockAndUpdate(topNeighborPos, topNeighborState);
        } else {
            blockState = blockState.setValue(TOP, false);
        }
        level.setBlockAndUpdate(blockPos, blockState);
        //? < 1.21.5 {
        super.neighborChanged(blockState, level, blockPos, block, blockPos2, bl);
        //? } else {
        /*super.neighborChanged(blockState, level, blockPos, block, orientation, bl);
        *///? }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        BlockPos pos0 = context.getClickedPos()/*.above()*/;
        Direction direction0 = context.getHorizontalDirection().getOpposite();
        BlockState newState = this.defaultBlockState();
        Level level = context.getLevel();
        BlockPos leftDiagonalPos = pos0.relative(direction0.getClockWise()).relative(direction0);
        BlockState leftDiagonalState = level.getBlockState(leftDiagonalPos);
        if (leftDiagonalState.getBlock() instanceof TunnelWires2) {
            newState = newState.setValue(CORNER_LEFT, true);
        }
        BlockPos rightDiagonalPos = pos0.relative(direction0.getCounterClockWise()).relative(direction0);
        BlockState rightDiagonalState = level.getBlockState(rightDiagonalPos);
        if (rightDiagonalState.getBlock() instanceof TunnelWires2) {
            newState = newState.setValue(CORNER_RIGHT, true);
        }
        return newState.setValue(FACING, direction0).setValue(WATERLOGGED, flag);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
