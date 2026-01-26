package berries.mods.tcwm.block;

import berries.mods.tcwm.mvapi.MVSimpleCodecHorizontalDirectionalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class AntiGlareBoard extends MVSimpleCodecHorizontalDirectionalBlock {
    public static final BooleanProperty LOW = BooleanProperty.create("low");

    public AntiGlareBoard(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LOW, false));
    }

    public AntiGlareBoard() {
        this(berries.mods.tcwm.block.Blocks.copyProperties(Blocks.GLASS).noOcclusion());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        context.getLevel().scheduleTick(context.getClickedPos(), this, 20);
        BlockPos pos = context.getClickedPos();
        BlockPos downPos = pos.below();
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(LOW, context.getLevel().getBlockState(downPos).getBlock() instanceof CrossCollisionBlock);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LOW);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        return (
                state.setValue(LOW, level.getBlockState(currentPos.below()).getBlock() instanceof CrossCollisionBlock)
        );
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }
}
