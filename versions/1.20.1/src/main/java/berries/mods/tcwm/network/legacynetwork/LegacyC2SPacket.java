package berries.mods.tcwm.network.legacynetwork;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public interface LegacyC2SPacket {
    void send(Object... params);
    void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender);
}
