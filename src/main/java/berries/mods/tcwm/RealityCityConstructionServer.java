package berries.mods.tcwm;

import net.fabricmc.api.DedicatedServerModInitializer;

public class RealityCityConstructionServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        RealityCityConstruction.server();
    }
}
