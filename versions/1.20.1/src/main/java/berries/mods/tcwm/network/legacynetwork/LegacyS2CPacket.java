package berries.mods.tcwm.network.legacynetwork;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public interface LegacyS2CPacket {
    void send(ServerPlayer player, Object... params);
    void receive(Minecraft var1, ClientPacketListener var2, FriendlyByteBuf var3, PacketSender var4);
}
