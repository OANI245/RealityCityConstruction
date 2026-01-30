package berries.mods.tcwm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CeilingNoDirection extends Block {
    public CeilingNoDirection(Properties properties, int lightLevel) {
        super(properties.lightLevel((state) -> lightLevel));
    }

    public CeilingNoDirection(Properties properties) {
        this(properties, 0);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.create(0.0F, 0.0F, 0.0F, 1.0F, 0.35F, 1.0F);
    }

    @Override
            //? < 1.20.5 {
    /*public
            *///? } else {
    protected
    //? }
    VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.create(0.0F, 0.0F, 0.0F, 1.0F, 0.3F, 1.0F);
    }
}
