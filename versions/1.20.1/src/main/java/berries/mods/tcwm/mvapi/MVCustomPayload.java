package berries.mods.tcwm.mvapi;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public interface MVCustomPayload {
    @NotNull Object type();
    void receive(Object platform, Player player);
}
