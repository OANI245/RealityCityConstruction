package berries.mods.tcwm.block;

import berries.mods.tcwm.MCText;
import berries.mods.tcwm.item.Items;
import berries.mods.tcwm.mvapi.MVSimpleCodecHorizontalDirectionalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
//? >= 1.20.5 && < 1.21.5 {
import net.minecraft.world.ItemInteractionResult;
//? }
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExpwyBarType2Placeholder extends MVSimpleCodecHorizontalDirectionalBlock {
    public static final Property<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public ExpwyBarType2Placeholder(Direction defaultDirectional) {
        super(Blocks.copyProperties(Blocks.EXPWY_BAR_TYPE_2));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, defaultDirectional));
    }

    public ExpwyBarType2Placeholder(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    //? < 1.20.5 {
    /*@Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (player.isHolding(Items.FORGE_TOOL)) {
            level.setBlock(blockPos, Blocks.EXPWY_BAR_TYPE_2.defaultBlockState().setValue(FACING, blockState.getValue(FACING)), 18);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
    *///? } else if < 1.21.5 {
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.isHolding(Items.FORGE_TOOL)) {
            level.setBlock(pos, Blocks.EXPWY_BAR_TYPE_2.defaultBlockState().setValue(FACING, state.getValue(FACING)), 18);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.FAIL;
    }
    //? } else {
    /*@Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.isHolding(Items.FORGE_TOOL)) {
            level.setBlock(pos, Blocks.EXPWY_BAR_TYPE_2.defaultBlockState().setValue(FACING, state.getValue(FACING)), 18);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
    *///? }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        //? < 1.21.5 {
        Vec3 offset = blockState.getOffset(blockGetter, blockPos);
         //? } else {
        /*Vec3 offset = blockState.getOffset(blockPos);
        *///? }
        switch ((Direction) blockState.getValue(FACING)) {
            case SOUTH:
                return Block.box(0, 8, 0, 16, 16, 10).move(offset.x, offset.y, offset.z);
            case WEST:
                return Block.box(6, 8, 0, 16, 16, 16).move(offset.x, offset.y, offset.z);
            case EAST:
                return Block.box(0, 8, 0, 10, 16, 16).move(offset.x, offset.y, offset.z);
            case NORTH:
            default:
                return Block.box(0, 8, 9, 16, 16, 16).move(offset.x, offset.y, offset.z);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        //? < 1.21.5 {
        Vec3 offset = blockState.getOffset(blockGetter, blockPos);
         //? } else {
        /*Vec3 offset = blockState.getOffset(blockPos);
        *///? }
        switch ((Direction) blockState.getValue(FACING)) {
            case SOUTH:
                return Block.box(0, 8, 0, 16, 24, 10).move(offset.x, offset.y, offset.z);
            case WEST:
                return Block.box(6, 8, 0, 16, 24, 16).move(offset.x, offset.y, offset.z);
            case EAST:
                return Block.box(0, 8, 0, 10, 24, 16).move(offset.x, offset.y, offset.z);
            case NORTH:
            default:
                return Block.box(0, 0, 9, 16, 24, 16).move(offset.x, offset.y, offset.z);
        }
    }

    //? < 1.21.5 {
    @Override
            //? < 1.20.5 {
    /*public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter ctx, List<Component> list, TooltipFlag tooltipFlag) {
        *///? } else {
        public void appendHoverText(ItemStack itemStack, @Nullable Item.TooltipContext ctx, List<Component> list, TooltipFlag tooltipFlag) {
         //? }
        super.appendHoverText(itemStack, ctx, list, tooltipFlag);
        list.add(MCText.translatable("tooltip.expwy_bar_type_2_placeholder"));
    }
    //? }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        context.getLevel().scheduleTick(context.getClickedPos(), this, 20);
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
