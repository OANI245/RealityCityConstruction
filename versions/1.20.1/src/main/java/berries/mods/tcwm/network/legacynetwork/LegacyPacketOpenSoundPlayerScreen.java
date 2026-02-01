package berries.mods.tcwm.network.legacynetwork;

import berries.mods.tcwm.network.PacketOpenSoundPlayerScreen;
import berries.mods.tcwm.network.ScreenReceiver;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class LegacyPacketOpenSoundPlayerScreen implements LegacyS2CPacket {
    public static final LegacyPacketOpenSoundPlayerScreen INSTANCE = new LegacyPacketOpenSoundPlayerScreen();

    private LegacyPacketOpenSoundPlayerScreen() {}

    @Override
    public void send(ServerPlayer player, Object... params) {
        if (params.length != 5) return;
        BlockPos pos = (BlockPos) (params[0]);
        String name = (String) (params[1]);
        String soundID = (String) (params[2]);
        float range = (float) (params[3]);
        float pitch = (float) (params[4]);

        FriendlyByteBuf pkt = new FriendlyByteBuf(Unpooled.buffer());
        pkt.writeBlockPos(pos);
        pkt.writeUtf(name);
        pkt.writeUtf(soundID);
        pkt.writeFloat(range);
        pkt.writeFloat(pitch);

        ServerPlayNetworking.send(player, PacketOpenSoundPlayerScreen.PACKET_SHOW_SCREEN, pkt);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void receive(Minecraft mc, ClientPacketListener ignored, FriendlyByteBuf pkt, PacketSender ignored_1) {
        BlockPos pos = pkt.readBlockPos();
        String name = pkt.readUtf();
        String soundID = pkt.readUtf();
        float range = pkt.readFloat();
        float pitch = pkt.readFloat();
        ScreenReceiver.open(pos, name, soundID, range, pitch);
    }
}
