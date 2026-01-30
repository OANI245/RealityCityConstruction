package berries.mods.tcwm.block;

import berries.mods.tcwm.mvapi.MVSimpleCodecHorizontalDirectionalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ExpwyCautionBar extends MVSimpleCodecHorizontalDirectionalBlock {
    public ExpwyCautionBar(Properties properties) {
        super(properties);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("all")
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        //? < 1.21.5 {
        Vec3 offset = state.getOffset(level, pos);
         //? } else {
        /*Vec3 offset = state.getOffset(pos);
        *///? }
        return Block.box(6, 0, 6, 10, 19, 10).move(offset.x, offset.y, offset.z);
    }

    @Override
    @SuppressWarnings("all")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        //? < 1.21.5 {
        Vec3 offset = state.getOffset(level, pos);
         //? } else {
        /*Vec3 offset = state.getOffset(pos);
        *///? }
        return Block.box(6, 0, 6, 10, 24, 10).move(offset.x, offset.y, offset.z);
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
