package msnj.tcwm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
#if MC_VERSION < "12000"
import net.minecraft.world.level.storage.loot.LootContext;
#else
import net.minecraft.world.level.storage.loot.LootParams;
#endif
import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DeprecatedBlindSlab extends SlabBlock {

    public DeprecatedBlindSlab(Properties settings) {
        super(settings);
      this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false)
                .setValue(SlabBlock.TYPE, SlabType.BOTTOM));
    }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
      #if MC_VERSION <= "11800"
    context.getLevel().getBlockTicks().scheduleTick(context.getClickedPos(), this, 20);
    #else
    context.getLevel().scheduleTick(context.getClickedPos(), this, 20);
      #endif
    return this.defaultBlockState();
  }

  @Override
  public List<ItemStack> getDrops(BlockState state, #if MC_VERSION < "12000" LootContext #else LootParams #endif.Builder builder) {
    List<ItemStack> dropsOriginal = super.getDrops(state, builder);
    if (!dropsOriginal.isEmpty())
      return dropsOriginal;
    return Collections.singletonList(new ItemStack(net.minecraft.world.level.block.Blocks.STONE));
  }

  public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random randomSource) {
    serverLevel.setBlock(blockPos, Blocks.YELLOW_BLIND_SLAB.get().defaultBlockState(), 2);
  }

  public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
      #if MC_VERSION <= "11800"
    levelAccessor.getBlockTicks().scheduleTick(blockPos, this, 20);
      #else
    levelAccessor.scheduleTick(blockPos, this, 20);
      #endif
    return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
  }
}
