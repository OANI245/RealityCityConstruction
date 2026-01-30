package berries.mods.tcwm.mvapi;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

public class MVBlockRenderLayerMap {
    public static void put(int type, Block block) {
        RenderType ly;
        switch (type) {
            case 1 -> ly = RenderType.cutout();
            case 2 -> ly = RenderType.translucent();
            case 3 -> ly = RenderType.cutoutMipped();
            default -> ly = RenderType.solid();
        }
        BlockRenderLayerMap.INSTANCE.putBlock(block, ly);
    }
}
