package berries.mods.tcwm.network.legacynetwork;

import berries.mods.tcwm.RealityCityConstruction;
import berries.mods.tcwm.block.StationBroadcaster;
import berries.mods.tcwm.network.PacketUpdateBlockEntity;
import berries.mods.tcwm.util.TcwmBlockEntity;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class LegacyPacketUpdateBlockEntity implements LegacyC2SPacket {
    public static final LegacyPacketUpdateBlockEntity INSTANCE = new LegacyPacketUpdateBlockEntity();

    private LegacyPacketUpdateBlockEntity() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void send(Object... params) {
        if (params.length != 3) return;
        BlockEntityType<BlockEntity> type = (BlockEntityType<BlockEntity>) (params[0]);
        ResourceLocation key = BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(type);
        BlockPos pos = (BlockPos) (params[1]);
        CompoundTag tag = (CompoundTag) (params[2]);

        if (key == null) return;

        FriendlyByteBuf pkt = new FriendlyByteBuf(Unpooled.buffer());

        pkt.writeResourceLocation(key);
        pkt.writeBlockPos(pos);
        pkt.writeNbt(tag);

        ClientPlayNetworking.send(PacketUpdateBlockEntity.PACKET_UPDATE_BLOCK_ENTITY, pkt);
    }

    @Override
    public void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf pkt, PacketSender responseSender) {
        Level level = player.level();
        BlockEntityType<?> entityType = BuiltInRegistries.BLOCK_ENTITY_TYPE.get(pkt.readResourceLocation());
        BlockPos pos = pkt.readBlockPos();
        CompoundTag tag = pkt.readNbt();
        if (entityType == null) return;
        level.getBlockEntity(pos, entityType).ifPresent(blockEntity -> {
            if (tag != null) {
                RealityCityConstruction.LOGGER.info("A new tag received: " + tag.getAsString() + " at " + pos);
                if (blockEntity instanceof TcwmBlockEntity) {
                    RealityCityConstruction.LOGGER.info("Applying...");
                    if (blockEntity instanceof StationBroadcaster.StationBroadcasterEntity) {
                        ((StationBroadcaster.StationBroadcasterEntity) blockEntity).setName(tag.getString("name"));
                        ((StationBroadcaster.StationBroadcasterEntity) blockEntity).setSoundID(tag.getString("soundID"));
                        ((StationBroadcaster.StationBroadcasterEntity) blockEntity).setRange(tag.getFloat("range"));
                        ((StationBroadcaster.StationBroadcasterEntity) blockEntity).setPitch(StationBroadcaster.StationBroadcasterEntity.Pitch.getValue(tag.getFloat("pitch")));
                    }
                    blockEntity.setChanged();
                    level.blockEntityChanged(pos);
                }
            }
        });
    }
}
