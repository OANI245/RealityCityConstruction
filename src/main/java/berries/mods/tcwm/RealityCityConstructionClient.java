package berries.mods.tcwm;

import berries.mods.tcwm.block.Blocks;
import berries.mods.tcwm.mvapi.MVBlockRenderLayerMap;
import berries.mods.tcwm.mvapi.MVNetwork;
import berries.mods.tcwm.network.PacketScreen;
import berries.mods.tcwm.network.PacketUpdateBlockEntity;
import berries.mods.tcwm.util.settings.Settings;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import org.slf4j.LoggerFactory;

public class RealityCityConstructionClient implements ClientModInitializer {
    public String MIN_MTRVERSION = "3.2.0";
    public final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RealityCityConstruction.MOD_ID);
    private Runtime rn = Runtime.getRuntime();

    @SuppressWarnings("all")
    public void onInitializeClient() {
        LOGGER.info("RCCmod Client Task Loading");
        if (!Minecraft.getInstance().gameDirectory.toPath().resolve("config").resolve("tcwm.json").toFile().exists()) {
            Settings io = Settings.get(Minecraft.getInstance().gameDirectory.toPath().resolve("config").resolve("tcwm.json"));
            Settings.write(io);
        }
            /*MVBlockRenderLayerMap.put(RenderType.cutoutMipped(), Blocks.PLATFORM.get());
            MVBlockRenderLayerMap.put(RenderType.cutoutMipped(), Blocks.PLATFORM_TYP_QUARTZ.get());
            MVBlockRenderLayerMap.put(RenderType.cutoutMipped(), Blocks.PLATFORM_INDENTED.get());*/
        MVBlockRenderLayerMap.put(RenderType.translucent(), Blocks.WINDOW.get());
        MVBlockRenderLayerMap.put(RenderType.translucent(), Blocks.WINDOW_PANE.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_1.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_1_BEIJING_4.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_1_OC_NEW.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_1_OC_NEW_REF.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_1_OC_NEW_2.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_1_OC_NEW_2_REF.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_1_SHANGHAI.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_GR.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_PLACEHOLDER.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_PLACEHOLDER_GR.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_DOUBLE.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_DOUBLE_GR.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER_GR.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_NEW.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_NEW_GR.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER_GR.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_GR.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER_GR.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.EXPWY_BAR_TYPE_3.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.BLACK_BLIND_SIDEWALK.get());
        MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.YELLOW_BLIND_SIDEWALK.get());
        //MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.BOAT_DOCK.get());
        //MVBlockRenderLayerMap.put(RenderType.cutout(), Blocks.AIR_CONDITIONER.get());
        MVNetwork.registerC2S(PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.TYPE, PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.CODEC);
        MVNetwork.registerS2C(PacketScreen.PacketScreenPayload.TYPE, PacketScreen.PacketScreenPayload.CODEC);
        MVNetwork.registerReceiverS2C(PacketScreen.PacketScreenPayload.TYPE, PacketScreen.PacketScreenPayload.CODEC);
        MVNetwork.registerReceiverC2S(PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.TYPE, PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.CODEC);
        //nrr.accept(LegacyPacketScreen.PACKET_SHOW_SCREEN, LegacyPacketScreen::receiveScreenS2C);
    }
}
