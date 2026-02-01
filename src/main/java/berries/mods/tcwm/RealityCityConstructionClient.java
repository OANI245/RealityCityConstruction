package berries.mods.tcwm;

import berries.mods.tcwm.block.Blocks;
import berries.mods.tcwm.mvapi.MVBlockRenderLayerMap;
import berries.mods.tcwm.mvapi.MVComponent;
import berries.mods.tcwm.mvapi.MVIdentifier;
import berries.mods.tcwm.mvapi.MVNetwork;
import berries.mods.tcwm.network.PacketOpenSoundPlayerScreen;
import berries.mods.tcwm.network.PacketUpdateBlockEntity;
import berries.mods.tcwm.util.settings.Settings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.Minecraft;
import org.slf4j.LoggerFactory;
//? < 1.20.5 {
/*import berries.mods.tcwm.network.legacynetwork.LegacyPacketOpenSoundPlayerScreen;
 *///? }
//? < 1.21.6
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

        ModContainer container = FabricLoader.getInstance().getModContainer(UFEInfo.MOD_ID).get();
        ResourceManagerHelper.registerBuiltinResourcePack(
                MVIdentifier.get(UFEInfo.MOD_ID, "tcwm_pbr"),
                container, MVComponent.translatable("pack.tcwm.pbr_texture"),
                ResourcePackActivationType.NORMAL
        );

            /*MVBlockRenderLayerMap.put(RenderType.cutoutMipped(), Blocks.PLATFORM);
            MVBlockRenderLayerMap.put(RenderType.cutoutMipped(), Blocks.PLATFORM_TYP_QUARTZ);
            MVBlockRenderLayerMap.put(RenderType.cutoutMipped(), Blocks.PLATFORM_INDENTED);*/
        MVBlockRenderLayerMap.put(2, Blocks.WINDOW);
        MVBlockRenderLayerMap.put(2, Blocks.WINDOW_PANE);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1_BEIJING_4);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1_OC_NEW);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1_OC_NEW_REF);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1_OC_NEW_2);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1_OC_NEW_2_REF);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1_SHANGHAI);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_PLACEHOLDER);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_PLACEHOLDER_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_DOUBLE);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_DOUBLE_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_3);
        MVBlockRenderLayerMap.put(1, Blocks.BLACK_BLIND_SIDEWALK);
        MVBlockRenderLayerMap.put(1, Blocks.YELLOW_BLIND_SIDEWALK);
        MVBlockRenderLayerMap.put(1, Blocks.AIR_CONDITIONER);
        MVBlockRenderLayerMap.put(1, Blocks.AIR_CONDITIONER_EXTERNAL_UNIT);
        //MVBlockRenderLayerMap.put(1, Blocks.BOAT_DOCK);
        //MVBlockRenderLayerMap.put(1, Blocks.AIR_CONDITIONER);
        MVNetwork.registerC2S(PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.TYPE, PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.CODEC);
        MVNetwork.registerS2C(PacketOpenSoundPlayerScreen.PacketScreenPayload.TYPE, PacketOpenSoundPlayerScreen.PacketScreenPayload.CODEC);
        MVNetwork.registerReceiverS2C(PacketOpenSoundPlayerScreen.PacketScreenPayload.TYPE, PacketOpenSoundPlayerScreen.PacketScreenPayload.CODEC);
        MVNetwork.registerReceiverC2S(PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.TYPE, PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.CODEC);
        //? < 1.20.5 {
        /*ClientPlayNetworking.registerGlobalReceiver(PacketOpenSoundPlayerScreen.PACKET_SHOW_SCREEN, LegacyPacketOpenSoundPlayerScreen.INSTANCE::receive);
         *///? }
        //nrr.accept(LegacyPacketScreen.PACKET_SHOW_SCREEN, LegacyPacketScreen::receiveScreenS2C);
    }
}
