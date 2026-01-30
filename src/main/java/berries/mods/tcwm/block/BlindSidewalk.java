package berries.mods.tcwm.block;


import berries.mods.tcwm.mvapi.MVSimpleCodecHorizontalDirectionalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.html.BlockView;
import java.util.Collections;
import java.util.List;

public class BlindSidewalk extends MVSimpleCodecHorizontalDirectionalBlock {
    public static final Property<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public BlindSidewalk(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockView view, BlockPos pos, CollisionContext ctx) {
        //? < 1.21.5 {
        /*Vec3 offset = state.getOffset(world, pos);
         *///? } else {
        Vec3 offset = state.getOffset(pos);
        //? }
        switch ((Direction) state.getValue(FACING)) {
            case SOUTH:
                return box(0, 0, 0, 16, 17, 16).move(offset.x, offset.y, offset.z);
            case NORTH:
                return box(0, 0, 0, 16, 17, 16).move(offset.x, offset.y, offset.z);
            case EAST:
                return box(0, 0, 0, 16, 17, 16).move(offset.x, offset.y, offset.z);
            case WEST:
                return box(0, 0, 0, 16, 17, 16).move(offset.x, offset.y, offset.z);
            default:
                return box(0, 0, 0, 16, 17, 16).move(offset.x, offset.y, offset.z);
        }
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return box(0, 0, 0, 16, 17f, 16);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ;
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public List<ItemStack> getDrops(BlockState state,  LootParams.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty()) {
            return dropsOriginal;
        }
        return Collections.singletonList(new ItemStack(net.minecraft.world.level.block.Blocks.STONE));
    }
}
