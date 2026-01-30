package berries.mods.tcwm.network;

import berries.mods.tcwm.block.StationBroadcaster;
import berries.mods.tcwm.mvapi.*;
//? < 1.20.5
//import berries.mods.tcwm.network.legacynetwork.LegacyPacketOpenSoundPlayerScreen;
import berries.mods.tcwm.util.Packets;
import berries.mods.tcwm.RealityCityConstruction;
//import msnj.tcwm.mappings.NetworkUtilities;
//import mtr.Registry;
import berries.mods.tcwm.gui.screen.EditSoundPlayerScreen;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
//? >= 1.20.5 {
import net.minecraft.nbt.Tag;
import net.minecraft.network.codec.StreamCodec;
//? }
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Objects;

public class PacketOpenSoundPlayerScreen {
    public static Identifier PACKET_SHOW_SCREEN = MVIdentifier.get(RealityCityConstruction.MOD_ID, "show_screen");

    public record PacketScreenPayload(BlockPos pos, String blockName, String soundID, float range, float pitch) implements MVCustomPayload {
        public static final MVPayloadType<PacketScreenPayload> TYPE = new MVPayloadType<>(PACKET_SHOW_SCREEN);
        public static final MVPayloadCodec<PacketScreenPayload> CODEC = new MVPayloadCodec<>(
                //? >= 1.20.5 {
                StreamCodec.composite(
                        BlockPos.STREAM_CODEC,
                        PacketScreenPayload::pos,
                        new StreamCodec<ByteBuf, String>() {
                            @Override
                            public void encode(ByteBuf object, String object2) {
                                CompoundTag t = new CompoundTag();
                                t.putString("value", object2);
                                FriendlyByteBuf.writeNbt(object, t);
                            }

                            @Override
                            public String decode(ByteBuf object) {
                                return Packets.getReadValue(FriendlyByteBuf.readNbt(object).getString("value"));
                            }
                        },
                        PacketScreenPayload::blockName,
                        new StreamCodec<ByteBuf, String>() {
                            @Override
                            public void encode(ByteBuf object, String object2) {
                                CompoundTag t = new CompoundTag();
                                t.putString("value", object2);
                                FriendlyByteBuf.writeNbt(object, t);
                            }

                            @Override
                            public String decode(ByteBuf object) {
                                return Packets.getReadValue(FriendlyByteBuf.readNbt(object).getString("value"));
                            }
                        },
                        PacketScreenPayload::soundID,
                        new StreamCodec<ByteBuf, Float>() {
                            @Override
                            public void encode(ByteBuf object, Float object2) {
                                CompoundTag t = new CompoundTag();
                                t.putFloat("value", object2);
                                FriendlyByteBuf.writeNbt(object, t);
                            }

                            @Override
                            public Float decode(ByteBuf object) {
                                return Packets.getReadValue(FriendlyByteBuf.readNbt(object).getFloat("value"));
                            }
                        },
                        PacketScreenPayload::range,
                        new StreamCodec<ByteBuf, Float>() {
                            @Override
                            public void encode(ByteBuf object, Float object2) {
                                CompoundTag t = new CompoundTag();
                                t.putFloat("value", object2);
                                FriendlyByteBuf.writeNbt(object, t);
                            }

                            @Override
                            public Float decode(ByteBuf object) {
                                return Packets.getReadValue(FriendlyByteBuf.readNbt(object).getFloat("value"));
                            }
                        },
                        PacketScreenPayload::pitch,
                        PacketScreenPayload::new
                )
                //? }
        );

        @Override
        public @NotNull
        //? < 1.20.5 {
        /*Object
        *///? } else {
        Type<? extends MVCustomPayload>
        //? }
        type() {
            return TYPE.type();
        }

        @Override
        public void receive(Object platform, Player player) {
            if (platform instanceof Minecraft) {
                ((Minecraft) platform).execute(() -> {
                    ((Minecraft) platform).setScreen(new EditSoundPlayerScreen(pos, blockName, soundID, range, pitch));/*StationBroadcaster.propertiesScreen.apply(pos)*/
                });
            }
        }
    }

    public static void sendScreenBlockS2C(ServerPlayer player, BlockPos pos) {
        Level level = player.level();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof StationBroadcaster.StationBroadcasterEntity) {
            Packets.versionedPacketSendS2C(player, new PacketScreenPayload(pos, ((StationBroadcaster.StationBroadcasterEntity) blockEntity).getName(), ((StationBroadcaster.StationBroadcasterEntity) blockEntity).getSoundID(), ((StationBroadcaster.StationBroadcasterEntity) blockEntity).getRange(), ((StationBroadcaster.StationBroadcasterEntity) blockEntity).getPitch().getFloat()), (p) -> {
                //? < 1.20.5 {
                /*LegacyPacketOpenSoundPlayerScreen.INSTANCE.send(p, pos, ((StationBroadcaster.StationBroadcasterEntity) blockEntity).getName(), ((StationBroadcaster.StationBroadcasterEntity) blockEntity).getSoundID(), ((StationBroadcaster.StationBroadcasterEntity) blockEntity).getRange(), ((StationBroadcaster.StationBroadcasterEntity) blockEntity).getPitch().getFloat());
                 *///? }
            });
        }
    }
}
