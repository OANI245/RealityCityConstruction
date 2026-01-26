package berries.mods.tcwm.network;

import berries.mods.tcwm.mvapi.*;
//? < 1.20.5
import berries.mods.tcwm.network.legacynetwork.LegacyPacketUpdateBlockEntity;
import berries.mods.tcwm.util.Packets;
import io.netty.buffer.ByteBuf;
import berries.mods.tcwm.RealityCityConstruction;
import berries.mods.tcwm.block.StationBroadcaster;
//import msnj.tcwm.mappings.BlockEntityClientSerializableMapper;
import berries.mods.tcwm.util.TcwmBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
//? >= 1.20.5
//import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PacketUpdateBlockEntity {
    public static ResourceLocation PACKET_UPDATE_BLOCK_ENTITY = MVIdentifier.get(RealityCityConstruction.MOD_ID, "update_block_entity");

    public record PacketUpdateBlockEntityPayload(BlockEntityType<? extends BlockEntity> entityType,
                                                 BlockPos pos, CompoundTag tag) implements MVCustomPayload {
        public static final MVPayloadType<PacketUpdateBlockEntityPayload> TYPE = new MVPayloadType<>(PACKET_UPDATE_BLOCK_ENTITY);
        public static final MVPayloadCodec<PacketUpdateBlockEntityPayload> CODEC =
                new MVPayloadCodec<>(
                        //? >= 1.20.5 {
                        /*StreamCodec.composite(
                                new StreamCodec<ByteBuf, BlockEntityType<? extends BlockEntity>>() {
                                    @Override
                                    public @NotNull BlockEntityType<? extends BlockEntity> decode(ByteBuf object) {
                                        return Objects.requireNonNull(BuiltInRegistries.BLOCK_ENTITY_TYPE.get(ResourceLocation.parse(FriendlyByteBuf.readNbt(object).getString("value"))));
                                    }

                                    @Override
                                    public void encode(ByteBuf object, BlockEntityType<? extends BlockEntity> object2) {
                                        CompoundTag tag = new CompoundTag();
                                        tag.putString("value", BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(object2).toString());
                                        FriendlyByteBuf.writeNbt(object, tag);
                                    }
                                },
                                PacketUpdateBlockEntityPayload::entityType,
                                BlockPos.STREAM_CODEC,
                                PacketUpdateBlockEntityPayload::pos,
                                new StreamCodec<ByteBuf, CompoundTag>() {
                                    @Override
                                    public void encode(ByteBuf object, CompoundTag object2) {
                                        FriendlyByteBuf.writeNbt(object, object2);
                                    }

                                    @Override
                                    public @NotNull CompoundTag decode(ByteBuf object) {
                                        return Objects.requireNonNull(FriendlyByteBuf.readNbt(object)).copy();
                                    }
                                },
                                PacketUpdateBlockEntityPayload::tag,
                                PacketUpdateBlockEntityPayload::new
                        )
                        *///? }
                );

        @Override
        public @NotNull
            //? < 1.20.5 {
        Object
        //? } else {
        /*Type<? extends MVCustomPayload>
         *///? }
        type() {
            return TYPE.type();
        }

        @Override
        public void receive(Object platform, Player player) {
            if (!(platform instanceof MinecraftServer)) {
                RealityCityConstruction.LOGGER.warn("Wrong receiver: " + platform.getClass().getName());
                return;
            }
            Level level = ((ServerPlayer) player).level();
            if (entityType == null) return;
            level.getBlockEntity(pos, entityType).ifPresent(blockEntity -> {
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
            });
        }
    }

    public static void sendUpdateC2S(TcwmBlockEntity blockEntity, BlockPos pos) {
        Level level = blockEntity.getLevel();
        if (level == null) return;
        CompoundTag tag = blockEntity.getTag();
        Packets.versionedPacketSendC2S(new PacketUpdateBlockEntityPayload(blockEntity.getType(), pos, tag), () -> {
            //? < 1.20.5 {
            LegacyPacketUpdateBlockEntity.INSTANCE.send(blockEntity.getType(), pos, tag);
            //? }
        });
    }

   /* public static void receiveUpdateC2S(MinecraftServer server, ServerPlayer player, FriendlyByteBuf packet) {
        ResourceKey<Level> levelKey = packet.readResourceKey(net.minecraft.core.registries.Registries.DIMENSION);
        BlockPos blockPos = packet.readBlockPos();
        //BlockEntityType<?> blockEntityType = packet.readById(net.minecraft.core.registries.BuiltInRegistries.BLOCK_ENTITY_TYPE);

        CompoundTag compoundTag = packet.readNbt();
        new Logger(System.out).info("[D    E    B    U    G] " + compoundTag);

    #if MC_VERSION <= "11700"
    server.execute(() -> {
      ServerLevel level = server.getLevel(levelKey);
      if (level == null || blockEntityType == null) return;
      BlockEntity blockEntity = level.getBlockEntity(blockPos);
      if(blockEntity.getType().equals(blockEntityType)) {
        if (compoundTag == null) return;
        blockEntity.load(blockEntity.getBlockState(), compoundTag);
        blockEntity.setPosition(blockPos);
        blockEntity.setChanged();
        level.getChunkSource().blockChanged(blockPos);
      }
    });
    #else
        server.execute(() -> {
            ServerLevel level = server.getLevel(levelKey);
            if (level == null || blockEntityType == null) return;
            level.getBlockEntity(blockPos, blockEntityType).ifPresent(blockEntity -> {
                if (compoundTag != null) {
                    blockEntity.load(compoundTag);
                    blockEntity.setChanged();
                    level.getChunkSource().blockChanged(blockPos);
                }
            });
        });
    #endif
    }*/
}
