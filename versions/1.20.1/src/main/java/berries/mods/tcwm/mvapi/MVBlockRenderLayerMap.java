package berries.mods.tcwm.mvapi;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

public class MVBlockRenderLayerMap {
    public static void put(RenderType renderType, Block block) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, renderType);
    }
}
