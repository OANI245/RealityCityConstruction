package berries.mods.tcwm.item;

import berries.mods.tcwm.block.Blocks;
import berries.mods.tcwm.block.StationBroadcaster;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StationBroadCasterBlockItem extends BlockItem {
    public StationBroadCasterBlockItem(Properties properties) {
        super(Blocks.STATION_BROADCASTER.get(), properties);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        MinecraftServer minecraftServer = level.getServer();
        if (minecraftServer == null) {
            return false;
        }

        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof StationBroadcaster.StationBroadcasterEntity) {
            Component name = stack.getComponents().get(DataComponents.CUSTOM_NAME);
            if (name != null) {
                String s = name.getString().trim();
                ((StationBroadcaster.StationBroadcasterEntity) entity).setName(s);
            }
        }

        return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
    }
}
