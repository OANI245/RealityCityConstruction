package berries.mods.tcwm.block;

import berries.mods.tcwm.RealityCityConstruction;
import berries.mods.tcwm.item.Items;
import berries.mods.tcwm.mvapi.MVSimpleCodecHorizontalDirectionalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ConveyerBelt extends MVSimpleCodecHorizontalDirectionalBlock {//mtr.block.BlockEscalatorStep

    public ConveyerBelt(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        context.getLevel().scheduleTick(context.getClickedPos(), this, 20);
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
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
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Block.box(0, 0, 0, 16, 1.7, 16);
    }

    @Override
    public String toString() {
        return (
                "{" +
                        "\n\tBlock:" +
                        "\n\t\tConveyerBelt" +
                        "}"
        );
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.isHolding(Items.FORGE_TOOL.get())) {
            brushUse(state, pos, level);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.FAIL;
    }

    private void brushUse(BlockState state, BlockPos blockPos, Level level) {
        try {
            switch (state.getValue(FACING)) {
                case NORTH:
                    level.setBlock(blockPos, state.setValue(FACING, Direction.SOUTH), 18);
                    break;
                case SOUTH:
                    level.setBlock(blockPos, state.setValue(FACING, Direction.NORTH), 18);
                    break;
                case WEST:
                    level.setBlock(blockPos, state.setValue(FACING, Direction.EAST), 18);
                    break;
                case EAST:
                    level.setBlock(blockPos, state.setValue(FACING, Direction.WEST), 18);
                    break;
                default:
                    level.setBlock(blockPos, state.setValue(FACING, Direction.SOUTH), 18);
                    break;
            }
        } catch (Exception exception) {
            RealityCityConstruction.LOGGER.error("ERROR - ");
            System.out.print("ERROR!!! BlockState Edit Failed!");
            exception.printStackTrace();
        }
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        Direction facing = (Direction) state.getValue(FACING);
        float speed = 0.1F;
        switch (facing) {
            case SOUTH:
                entity.push(0.0, 0.0, -0.114);
                break;
            case WEST:
                entity.push(0.114, 0.0, 0.0);
                break;
            case NORTH:
                entity.push(0.0, 0.0, 0.114);
                break;
            case EAST:
                entity.push(-0.114, 0.0, 0.0);
        }
    }
}
