package berries.mods.tcwm.network.legacynetwork;

import berries.mods.tcwm.gui.screen.EditSoundPlayerScreen;
import berries.mods.tcwm.network.PacketScreen;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class LegacyPacketScreen implements LegacyS2CPacket {
    public static final LegacyPacketScreen INSTANCE = new LegacyPacketScreen();

    private LegacyPacketScreen() {}

    @Override
    public void send(ServerPlayer player, Object... params) {
        if (params.length != 2) return;
        String screenName = (String) (params[0]);
        BlockPos pos = (BlockPos) (params[1]);

        FriendlyByteBuf pkt = new FriendlyByteBuf(Unpooled.buffer());
        pkt.writeUtf(screenName);
        pkt.writeBlockPos(pos);

        ServerPlayNetworking.send(player, PacketScreen.PACKET_SHOW_SCREEN, pkt);
    }

    @Override
    public void receive(Minecraft mc, ClientPacketListener ignored, FriendlyByteBuf pkt, PacketSender ignored_1) {
        mc.execute(() -> {
            switch (pkt.readUtf()) {
                case "SSBAS":
                    mc.setScreen(new EditSoundPlayerScreen(pkt.readBlockPos()));/*StationBroadcaster.propertiesScreen.apply(pos)*/
                    break;
            }
        });
    }
}
