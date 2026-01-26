package berries.mods.tcwm.block;

import berries.mods.tcwm.MCText;
import berries.mods.tcwm.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class TunnelLight extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty ISOLD = BooleanProperty.create("isold");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TunnelLight(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(ISOLD, false));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext ctx, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, ctx, list, flag);
        list.add(MCText.translatable("tooltip.tunnel_light"));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.isHolding(Items.FORGE_TOOL.get())) {
            if (state.getValue(ISOLD)) {
                level.setBlock(pos, state.setValue(ISOLD, false), 18);
            } else {
                level.setBlock(pos, state.setValue(ISOLD, true), 18);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.FAIL;
    }

    //deprecated
    /*@Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (player.isHolding(Items.FORGE_TOOL.get())) {
            if (blockState.getValue(ISOLD)) {
                level.setBlock(blockPos, blockState.setValue(ISOLD, false), 18);
            } else {
                level.setBlock(blockPos, blockState.setValue(ISOLD, true), 18);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }*/

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(world, pos);
        switch ((Direction) state.getValue(FACING)) {
            case SOUTH:
            default:
                return box(0, 0, 0, 16, 16, 2).move(offset.x, offset.y, offset.z);
            case NORTH:
                return box(0, 0, 14, 16, 16, 16).move(offset.x, offset.y, offset.z);
            case EAST:
                return box(0, 0, 0, 2, 16, 16).move(offset.x, offset.y, offset.z);
            case WEST:
                return box(14, 0, 0, 16, 16, 16).move(offset.x, offset.y, offset.z);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, ISOLD);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        ;
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos,
                                  BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {

        }
        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }
}
