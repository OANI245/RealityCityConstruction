package berries.mods.tcwm.network;

import io.netty.buffer.ByteBuf;
import berries.mods.tcwm.RealityCityConstruction;
//import msnj.tcwm.mappings.NetworkUtilities;
//import mtr.Registry;
import berries.mods.tcwm.gui.screen.EditSoundPlayerScreen;
import berries.mods.tcwm.mvapi.MVCustomPayload;
import berries.mods.tcwm.mvapi.MVNetwork;
import berries.mods.tcwm.mvapi.MVPayloadCodec;
import berries.mods.tcwm.mvapi.MVPayloadType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PacketScreen {
    public static ResourceLocation PACKET_SHOW_SCREEN = ResourceLocation.fromNamespaceAndPath(RealityCityConstruction.MOD_ID, "show_screen");

    public record PacketScreenPayload(String screenName, BlockPos pos) implements MVCustomPayload {
        public static final MVPayloadType<PacketScreenPayload> TYPE = new MVPayloadType<>(PACKET_SHOW_SCREEN);
        public static final MVPayloadCodec<PacketScreenPayload> CODEC = new MVPayloadCodec<>(
                StreamCodec.composite(
                        new StreamCodec<ByteBuf, String>() {
                            @Override
                            public @NotNull String decode(ByteBuf object) {
                                return Objects.requireNonNull(FriendlyByteBuf.readNbt(object)).getString("value");
                            }

                            @Override
                            public void encode(ByteBuf object, String object2) {
                                CompoundTag compoundTag = new CompoundTag();
                                compoundTag.putString("value", object2);
                                FriendlyByteBuf.writeNbt(object, compoundTag);
                            }
                        },
                        PacketScreenPayload::screenName,
                        BlockPos.STREAM_CODEC,
                        PacketScreenPayload::pos,
                        PacketScreenPayload::new
                ));

        @Override
        public @NotNull Type<? extends MVCustomPayload> type() {
            return TYPE.type();
        }

        @Override
        public void receive(Object platform, Player player) {
            if (platform instanceof Minecraft) {
                ((Minecraft) platform).execute(() -> {
                    switch (screenName) {
                        case "SSBAS":
                            ((Minecraft) platform).setScreen(new EditSoundPlayerScreen(pos));/*StationBroadcaster.propertiesScreen.apply(pos)*/
                            break;
                    }
                });
            }
        }
    }

    public static void sendScreenBlockS2C(ServerPlayer player, String screenName, BlockPos pos) {
        MVNetwork.sendS2C(player, new PacketScreenPayload(screenName, pos));
    }
}
