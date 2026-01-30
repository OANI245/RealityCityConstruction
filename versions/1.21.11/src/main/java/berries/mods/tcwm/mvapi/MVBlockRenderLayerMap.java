package berries.mods.tcwm.mvapi;

import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;

public class MVBlockRenderLayerMap {
    public static void put(int type, Block block) {
        ChunkSectionLayer ly;
        switch (type) {
            case 1, 3 -> ly = ChunkSectionLayer.CUTOUT;
            case 2 -> ly = ChunkSectionLayer.TRANSLUCENT;
            default -> ly = ChunkSectionLayer.SOLID;
        }
        BlockRenderLayerMap.putBlock(block, ly);
    }
}
