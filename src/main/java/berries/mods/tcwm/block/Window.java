package berries.mods.tcwm.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
//? <= 1.20.2 {
/*import net.minecraft.world.level.block.GlassBlock;
*///? } else {
import net.minecraft.world.level.block.TransparentBlock;
//? }

//? <= 1.20.2 {
/*public class Window extends GlassBlock {
*///? } else {
public class Window extends TransparentBlock {
    //? }
    public Window(BlockBehaviour.Properties settings) {
        super(settings);
    }
}
