package berries.mods.tcwm.util;

import berries.mods.tcwm.mvapi.MVBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TcwmBlockEntity extends MVBlockEntity {
    public TcwmBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
}
