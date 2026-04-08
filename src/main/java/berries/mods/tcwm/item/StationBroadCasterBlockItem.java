package berries.mods.tcwm.item;

import berries.mods.tcwm.block.Blocks;
import berries.mods.tcwm.block.StationBroadcaster;
import berries.mods.tcwm.mvapi.MVBlockEntity;
import berries.mods.tcwm.mvapi.MVComponent;
import berries.mods.tcwm.util.Packets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.BlockPos;
//? >= 1.20.5 {
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
//? }
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StationBroadCasterBlockItem extends BlockItem {
    public StationBroadCasterBlockItem(StationBroadcaster block, Properties properties) {
        super(block, properties);
    }

    @Override
    @SuppressWarnings("all")
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        MinecraftServer minecraftServer = level.getServer();
        if (minecraftServer == null) {
            return false;
        }

        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof StationBroadcaster.StationBroadcasterEntity) {
            //? < 1.20.5 {
            /*Component name = null;
            try {
                JsonElement ec = (JsonParser.parseString(((CompoundTag) stack.getTag().get("display")).getString("Name")));
                if (ec instanceof JsonObject) {
                    name = MVComponent.text(((JsonObject) ec).get("text").getAsString());
                } else if (ec instanceof JsonArray) {
                    name = MVComponent.EMPTY.copy();
                    for (int i = 0; i < ((JsonArray) ec).size(); ++i) {
                        var item = ((JsonArray) ec).get(i);
                        if (item instanceof JsonObject) {
                            name.getSiblings().add(MVComponent.text(((JsonObject) item).get("text").getAsString()));
                        } else {
                            name.getSiblings().add(MVComponent.text(item.getAsString()));
                        }
                    }
                }
            } catch (Throwable ignored) {}
            *///? } else {
            Component name = stack.getComponents().get(DataComponents.CUSTOM_NAME);
            //? }
            if (name != null) {
                String s = name.getString().trim();
                ((StationBroadcaster.StationBroadcasterEntity) entity).setName(s);
            }
        }

        if (true//? >= 1.20.5
                && stack.has(DataComponents.CUSTOM_DATA) && stack.get(DataComponents.CUSTOM_DATA).copyTag().contains("station_broadcaster_entity_data")
                //? <= 1.20.5
                //&& stack.getOrCreateTag().contains("station_broadcaster_entity_data")
        ) {
            return updateCustomBlockEntityTagFromCustomData(level, player, pos, stack);
        } else {
            return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        }
    }

    public static boolean updateCustomBlockEntityTagFromCustomData(final Level level, final @Nullable Player player, final BlockPos pos, final ItemStack itemStack) {
        if (level.isClientSide()) {
            return false;
        } else {
            var customData = //? >= 1.20.5 {
                    itemStack.get(DataComponents.CUSTOM_DATA)//? } else {
                    /*itemStack.getOrCreateTag()*///? }
            ;
            if (customData != null && !customData.isEmpty()) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (!(blockEntity instanceof MVBlockEntity)) {
                    return false;
                }

                BlockEntityType<?> type = blockEntity.getType();
                if (type != Blocks.BlockEntityTypes.HOMO_STATION_BROADCASTER) {
                    return false;
                }

                if (player != null) {
                    try {
                        ((MVBlockEntity) blockEntity).setTag(Packets.getReadValue(customData
                                //? >= 1.20.5
                                .copyTag()
                                .getCompound("station_broadcaster_entity_data")));
                        return true;
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }

                return false;
            }

            return false;
        }
    }
}
