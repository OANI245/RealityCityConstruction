package berries.mods.tcwm.block;

import berries.mods.tcwm.mvapi.MVSimpleCodecHorizontalDirectionalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ExpwyBarType3 extends MVSimpleCodecHorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected ExpwyBarType3(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Vec3 offset = blockState.getOffset(blockGetter, blockPos);
        switch ((Direction) blockState.getValue(FACING)) {
            case NORTH:
                return Block.box(0, 0, 9, 16, 16, 16).move(offset.x, offset.y, offset.z);
            case SOUTH:
                return Block.box(0, 0, 0, 16, 16, 7).move(offset.x, offset.y, offset.z);
            case WEST:
                return Block.box(9, 0, 0, 16, 16, 16).move(offset.x, offset.y, offset.z);
            case EAST:
                return Block.box(0, 0, 0, 7, 16, 16).move(offset.x, offset.y, offset.z);
            default:
                return Block.box(0, 0, 9, 16, 16, 16).move(offset.x, offset.y, offset.z);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Vec3 offset = blockState.getOffset(blockGetter, blockPos);
        switch ((Direction) blockState.getValue(FACING)) {
            case NORTH:
                return Block.box(0, 0, 9, 16, 24, 16).move(offset.x, offset.y, offset.z);
            case SOUTH:
                return Block.box(0, 0, 0, 16, 24, 7).move(offset.x, offset.y, offset.z);
            case WEST:
                return Block.box(9, 0, 0, 16, 24, 16).move(offset.x, offset.y, offset.z);
            case EAST:
                return Block.box(0, 0, 0, 7, 24, 16).move(offset.x, offset.y, offset.z);
            default:
                return Block.box(0, 0, 9, 16, 24, 16).move(offset.x, offset.y, offset.z);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        context.getLevel().scheduleTick(context.getClickedPos(), this, 20);
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
