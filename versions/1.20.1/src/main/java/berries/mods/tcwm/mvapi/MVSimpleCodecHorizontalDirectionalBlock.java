package berries.mods.tcwm.mvapi;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import org.jetbrains.annotations.NotNull;

public class MVSimpleCodecHorizontalDirectionalBlock extends HorizontalDirectionalBlock {
    //public static final MapCodec<MVSimpleCodecHorizontalDirectionalBlock> CODEC = simpleCodec(MVSimpleCodecHorizontalDirectionalBlock::new);

    protected MVSimpleCodecHorizontalDirectionalBlock(Properties properties) {
        super(properties);
    }

    /*@Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }*/
}
