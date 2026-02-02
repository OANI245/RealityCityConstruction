package berries.mods.tcwm.block;

import berries.mods.tcwm.mvapi.MVBlockEntity;
import berries.mods.tcwm.mvapi.MVBlockEntityComponent;
import berries.mods.tcwm.mvapi.MVSimpleCodecHorizontalDirectionalBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AirConditioner extends MVSimpleCodecHorizontalDirectionalBlock implements EntityBlock {
    public static EnumProperty<EnumSide> SIDE = EnumProperty.create("side", EnumSide.class);

    public AirConditioner(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(SIDE, EnumSide.LEFT));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SIDE);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            default:
            case NORTH:
                return box(0, 0, 6, 16, 16, 16);
            case EAST:
                return box(0, 0, 0, 10, 16, 16);
            case SOUTH:
                return box(0, 0, 0, 16, 16, 10);
            case WEST:
                return box(6, 0, 0, 16, 16, 16);
        }
    }

    @Override
            //? >= 1.21.5 {
    /*protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        if (direction == getNeighbourDirection(state.getValue(SIDE), state.getValue(FACING))) {
            return neighborState.is(this) && neighborState.getValue(SIDE) != state.getValue(SIDE) ? state : Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(state, levelReader, scheduledTickAccess, blockPos, direction, neighborPos, neighborState, randomSource);
        }
    }
    *///? } else {
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (direction == getNeighbourDirection(state.getValue(SIDE), state.getValue(FACING))) {
            return neighborState.is(this) && neighborState.getValue(SIDE) != state.getValue(SIDE) ? state : Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
        }
    }
    //? }

    Direction getNeighbourDirection(EnumSide side, Direction direction) {
        return side == EnumSide.LEFT ? direction.getCounterClockWise() : direction.getClockWise();
    }

    //? < 1.20.5 {
    /*public @NotNull void
    *///? } else {
    public @NotNull BlockState
    //? }
    playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && player.isCreative()) {
            if (state.getValue(SIDE) == EnumSide.LEFT) {
                BlockPos rightPos = pos.relative(getNeighbourDirection(state.getValue(SIDE), state.getValue(FACING)));
                BlockState blockState = level.getBlockState(rightPos);
                if (blockState.getBlock() == state.getBlock() && blockState.getValue(SIDE) == EnumSide.RIGHT) {
                    level.setBlock(rightPos, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, rightPos, Block.getId(blockState));
                }
            }
        }
        //? >= 1.20.5
        return
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide()) {
            BlockPos blockPos = pos.relative((Direction) getNeighbourDirection(EnumSide.LEFT, state.getValue(FACING)));
            level.setBlock(blockPos, (BlockState) state.setValue(SIDE, EnumSide.RIGHT), 3);
            state.updateNeighbourShapes(level, pos, 3);
            BlockEntity entity = level.getBlockEntity(pos);
            BlockEntity neighborEntity = level.getBlockEntity(blockPos);
            if (entity instanceof AirConditionerEntity && neighborEntity instanceof AirConditionerEntity) {
                ((AirConditionerEntity) neighborEntity).setTemperatureOnly(((AirConditionerEntity) entity).getTemperature());
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        context.getLevel().scheduleTick(context.getClickedPos(), this, 20);
        return context
                .getLevel()
                .getBlockState(
                        context.getClickedPos()
                                .relative(
                                        getNeighbourDirection(
                                                EnumSide.LEFT,
                                                context.getHorizontalDirection().getOpposite()
                                        )
                                )
                )
                .canBeReplaced(context) ?
                this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()) : null;
    }

    @Environment(EnvType.CLIENT)
    public static DoubleBlockCombiner.BlockType getBlockType(BlockState state) {
        return state.getValue(SIDE) == EnumSide.LEFT ? DoubleBlockCombiner.BlockType.FIRST : DoubleBlockCombiner.BlockType.SECOND;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AirConditionerEntity(blockPos, blockState);
    }

    public enum EnumSide implements StringRepresentable {
        LEFT("left"), RIGHT("right");

        private final String name;

        private EnumSide(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }

    public static class AirConditionerEntity extends MVBlockEntity {
        protected float temperature = 26.0f;

        public AirConditionerEntity(BlockPos blockPos, BlockState blockState) {
            super(berries.mods.tcwm.block.Blocks.BlockEntityTypes.AIR_CONDITIONER, blockPos, blockState);
        }

        protected void setTemperatureOnly(float temperature) {
            this.temperature = temperature;
        }

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
            syncTwoSidesData(0);
        }

        @Override
        public void loadTag(MVBlockEntityComponent tag) {
            super.loadTag(tag);
            this.temperature = tag.getFloat("temperature");
            syncTwoSidesData(0);
        }

        @Override
        public void saveTag(MVBlockEntityComponent tag) {
            super.saveTag(tag);
            tag.putFloat("temperature", this.temperature);
        }

        public void syncTwoSidesData(int sideOnly) {
            if (this.getLevel() == null) return;
            Level level0 = this.getLevel();
            BlockState state0 = level0.getBlockState(this.getBlockPos());
            BlockPos neighborPos;
            if (state0.getValue(SIDE) == EnumSide.RIGHT && sideOnly != 0) {
                neighborPos = this.getBlockPos().relative(state0.getValue(FACING).getClockWise());
            } else if (sideOnly != 1) {
                neighborPos = this.getBlockPos().relative(state0.getValue(FACING).getCounterClockWise());
            } else {
                return;
            }
            BlockState neighborState = level0.getBlockState(neighborPos);
            BlockEntity neighborBlockEntity = level0.getBlockEntity(neighborPos);
            if (neighborState.getBlock() instanceof AirConditioner && neighborState.getValue(SIDE) != state0.getValue(SIDE) && neighborBlockEntity instanceof AirConditionerEntity) {
                ((AirConditionerEntity) neighborBlockEntity).setTemperatureOnly(this.temperature);
                neighborBlockEntity.setChanged();
                level0.blockEntityChanged(neighborPos);
            }
        }
    }
}
