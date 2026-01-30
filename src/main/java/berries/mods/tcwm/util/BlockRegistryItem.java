package berries.mods.tcwm.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class BlockRegistryItem {
    protected final Function<BlockBehaviour.Properties, Block> factory;

    public BlockRegistryItem(Function<BlockBehaviour.Properties, Block> factory) {
        this.factory = factory;
    }

    public Block get(BlockBehaviour.Properties p) {
        return factory.apply(p);
    }
}
