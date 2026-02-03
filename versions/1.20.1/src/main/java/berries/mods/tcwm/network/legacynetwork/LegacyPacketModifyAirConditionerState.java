package berries.mods.tcwm.network.legacynetwork;

import berries.mods.tcwm.network.PacketModifyAirConditionerState;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class LegacyPacketModifyAirConditionerState implements LegacyC2SPacket {
    public static final LegacyPacketModifyAirConditionerState INSTANCE = new LegacyPacketModifyAirConditionerState();

    private LegacyPacketModifyAirConditionerState() {}

    @Override
    public void send(Object... params) {
        if (params.length != 2) return;

        FriendlyByteBuf pkt = new FriendlyByteBuf(Unpooled.buffer());
        pkt.writeInt((Integer) params[0]);
        pkt.writeBlockPos((BlockPos) params[1]);

        ClientPlayNetworking.send(PacketModifyAirConditionerState.PACKET_MODIFY_AIR_CONDITIONER_STATE, pkt);
    }

    @Override
    public void receive(MinecraftServer s, ServerPlayer player, ServerGamePacketListenerImpl ignored, FriendlyByteBuf pkt, PacketSender ignored_) {
        int type = pkt.readInt();
        BlockPos linkedPos = pkt.readBlockPos();

        PacketModifyAirConditionerState.modifyState(s, player, type, linkedPos);
    }
}
