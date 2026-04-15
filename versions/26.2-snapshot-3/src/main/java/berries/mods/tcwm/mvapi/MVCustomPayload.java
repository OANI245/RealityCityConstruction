package berries.mods.tcwm.mvapi;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public interface MVCustomPayload extends CustomPacketPayload {
    @NotNull Type<? extends @NotNull MVCustomPayload> type();
    void receive(Object platform, Player player);
}
