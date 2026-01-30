package berries.mods.tcwm.block;

import berries.mods.tcwm.mvapi.MVSimpleCodecHorizontalDirectionalBlock;
import berries.mods.tcwm.mvapi.MVStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class TunnelWires1 extends MVSimpleCodecHorizontalDirectionalBlock {
    public static final Property<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty TOP = MVStateProperties.bool("top");
    public static final BooleanProperty BOTTOM = MVStateProperties.bool("bottom");
    public static final BooleanProperty LEFT = MVStateProperties.bool("left");
    public static final BooleanProperty RIGHT = MVStateProperties.bool("right");

    public TunnelWires1(Properties settings) {
        super(settings);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(TOP, false)
                        .setValue(BOTTOM, false)
                        .setValue(LEFT, false)
                        .setValue(RIGHT, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TOP, BOTTOM, LEFT, RIGHT);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
}
