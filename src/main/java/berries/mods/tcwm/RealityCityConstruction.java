package berries.mods.tcwm;

import berries.mods.tcwm.block.Blocks;
import berries.mods.tcwm.item.StationBroadCasterBlockItem;
import berries.mods.tcwm.mvapi.MVIdentifier;
import berries.mods.tcwm.mvapi.MVNetwork;
import berries.mods.tcwm.mvapi.MVRegistry;
import berries.mods.tcwm.network.PacketScreen;
import berries.mods.tcwm.network.PacketUpdateBlockEntity;
import berries.mods.tcwm.item.Items;
import berries.mods.tcwm.item.TcwmCreativeModeTab;
import berries.mods.tcwm.network.legacynetwork.LegacyPacketScreen;
import berries.mods.tcwm.network.legacynetwork.LegacyPacketUpdateBlockEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RealityCityConstruction implements ModInitializer {
    public static final String MOD_ID = "tcwm";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Map<String, String> REPLACE_BLOCKS = new HashMap<>();

    @SuppressWarnings("all")
    public void onInitialize() {
        MVRegistry.BLOCK.register(modIdLocation("logo"), Blocks.LOGO_BLOCK.get());
        MVRegistry.ITEM.register(modIdLocation("logo"), defaultBlockItem(Blocks.LOGO_BLOCK.get()));
        MVRegistry.BLOCK.register(modIdLocation("marble"), Blocks.MARBLE.get());
        MVRegistry.ITEM.register(modIdLocation("marble"), defaultBlockItem(Blocks.MARBLE.get()));
        MVRegistry.BLOCK.register(modIdLocation("white_marble"), Blocks.WHITE_MARBLE.get());
        MVRegistry.ITEM.register(modIdLocation("white_marble"), defaultBlockItem(Blocks.WHITE_MARBLE.get()));
        MVRegistry.BLOCK.register(modIdLocation("yellow_blind"), Blocks.YELLOW_BLIND_SIDEWALK.get());
        MVRegistry.ITEM.register(modIdLocation("yellow_blind"), defaultBlockItem(Blocks.YELLOW_BLIND_SIDEWALK.get()));
        MVRegistry.BLOCK.register(modIdLocation("black_blind"), Blocks.BLACK_BLIND_SIDEWALK.get());
        MVRegistry.ITEM.register(modIdLocation("black_blind"), defaultBlockItem(Blocks.BLACK_BLIND_SIDEWALK.get()));
        MVRegistry.BLOCK.register(modIdLocation("andesite_blind"), Blocks.ANDESITE_BLIND_SIDEWALK.get());
        MVRegistry.ITEM.register(modIdLocation("andesite_blind"), defaultBlockItem(Blocks.ANDESITE_BLIND_SIDEWALK.get()));
        MVRegistry.BLOCK.register(modIdLocation("yellow_blind_corner"), Blocks.YELLOW_BLIND_SIDEWALK_CORNER.get());
        MVRegistry.ITEM.register(modIdLocation("yellow_blind_corner"), defaultBlockItem(Blocks.YELLOW_BLIND_SIDEWALK_CORNER.get()));
        MVRegistry.BLOCK.register(modIdLocation("black_blind_corner"), Blocks.BLACK_BLIND_SIDEWALK_CORNER.get());
        MVRegistry.ITEM.register(modIdLocation("black_blind_corner"), defaultBlockItem(Blocks.BLACK_BLIND_SIDEWALK_CORNER.get()));
        MVRegistry.BLOCK.register(modIdLocation("andesite_blind_corner"), Blocks.ANDESITE_BLIND_SIDEWALK_CORNER.get());
        MVRegistry.ITEM.register(modIdLocation("andesite_blind_corner"), defaultBlockItem(Blocks.ANDESITE_BLIND_SIDEWALK_CORNER.get()));
        MVRegistry.BLOCK.register(modIdLocation("yellow_blind_slab"), Blocks.YELLOW_BLIND_SIDEWALK_SLAB.get());
        MVRegistry.ITEM.register(modIdLocation("yellow_blind_slab"), defaultBlockItem(Blocks.YELLOW_BLIND_SIDEWALK_SLAB.get()));
        MVRegistry.BLOCK.register(modIdLocation("black_blind_slab"), Blocks.BLACK_BLIND_SIDEWALK_SLAB.get());
        MVRegistry.ITEM.register(modIdLocation("black_blind_slab"), defaultBlockItem(Blocks.BLACK_BLIND_SIDEWALK_SLAB.get()));
        MVRegistry.BLOCK.register(modIdLocation("andesite_blind_slab"), Blocks.ANDESITE_BLIND_SIDEWALK_SLAB.get());
        MVRegistry.ITEM.register(modIdLocation("andesite_blind_slab"), defaultBlockItem(Blocks.ANDESITE_BLIND_SIDEWALK_SLAB.get()));
        MVRegistry.BLOCK.register(modIdLocation("tunnelline"), Blocks.TUNNEL_WIRES_STYLE_1.get());
        MVRegistry.ITEM.register(modIdLocation("tunnelline"), defaultBlockItem(Blocks.TUNNEL_WIRES_STYLE_1.get()));
        MVRegistry.BLOCK.register(modIdLocation("tunnellinenoblock"), Blocks.TUNNEL_WIRES_STYLE_2.get());
        MVRegistry.ITEM.register(modIdLocation("tunnellinenoblock"), defaultBlockItem(Blocks.TUNNEL_WIRES_STYLE_2.get()));
        MVRegistry.BLOCK.register(modIdLocation("tunnellight"), Blocks.TUNNEL_LIGHT.get());
        MVRegistry.ITEM.register(modIdLocation("tunnellight"), defaultBlockItem(Blocks.TUNNEL_LIGHT.get()));
        MVRegistry.BLOCK.register(modIdLocation("window"), Blocks.WINDOW.get());
        MVRegistry.ITEM.register(modIdLocation("window"), defaultBlockItem(Blocks.WINDOW.get()));
        MVRegistry.BLOCK.register(modIdLocation("windowpane"), Blocks.WINDOW_PANE.get());
        MVRegistry.ITEM.register(modIdLocation("windowpane"), defaultBlockItem(Blocks.WINDOW_PANE.get()));
        /*MVRegistry.BLOCK.register(modIdLocation("platform"), Blocks.PLATFORM.get());
        MVRegistry.ITEM.register(modIdLocation("platform"), defaultBlockItem(Blocks.PLATFORM.get()));
        MVRegistry.BLOCK.register(modIdLocation("platform_type_quartz"), Blocks.PLATFORM_TYP_QUARTZ.get());
        MVRegistry.ITEM.register(modIdLocation("platform_type_quartz"), defaultBlockItem(Blocks.PLATFORM_TYP_QUARTZ.get()));
        MVRegistry.BLOCK.register(modIdLocation("platform_indented"), Blocks.PLATFORM_INDENTED.get());
        MVRegistry.ITEM.register(modIdLocation("platform_indented"), defaultBlockItem(Blocks.PLATFORM_INDENTED.get()));*/
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_1"), Blocks.EXPWY_BAR_TYPE_1.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_1"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_1.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_1_bj_4"), Blocks.EXPWY_BAR_TYPE_1_BEIJING_4.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_1_bj_4"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_1_BEIJING_4.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_1_sh"), Blocks.EXPWY_BAR_TYPE_1_SHANGHAI.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_1_sh"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_1_SHANGHAI.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_1_oc_new"), Blocks.EXPWY_BAR_TYPE_1_OC_NEW.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_1_oc_new"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_1_OC_NEW.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_1_oc_new_ref"), Blocks.EXPWY_BAR_TYPE_1_OC_NEW_REF.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_1_oc_new_ref"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_1_OC_NEW_REF.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_1_oc_new_2"), Blocks.EXPWY_BAR_TYPE_1_OC_NEW_2.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_1_oc_new_2"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_1_OC_NEW_2.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_1_oc_new_2_ref"), Blocks.EXPWY_BAR_TYPE_1_OC_NEW_2_REF.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_1_oc_new_2_ref"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_1_OC_NEW_2_REF.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2"), Blocks.EXPWY_BAR_TYPE_2.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_placeholder"), Blocks.EXPWY_BAR_TYPE_2_PLACEHOLDER.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_placeholder"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_PLACEHOLDER.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_green"), Blocks.EXPWY_BAR_TYPE_2_GR.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_green"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_GR.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_placeholder_green"), Blocks.EXPWY_BAR_TYPE_2_PLACEHOLDER_GR.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_placeholder_green"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_PLACEHOLDER_GR.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_new_gray"), Blocks.EXPWY_BAR_TYPE_2_NEW.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_new_gray"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_NEW.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_new_placeholder_gray"), Blocks.EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_new_placeholder_gray"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_new_green"), Blocks.EXPWY_BAR_TYPE_2_NEW_GR.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_new_green"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_NEW_GR.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_new_placeholder_green"), Blocks.EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER_GR.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_new_placeholder_green"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER_GR.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_double_gray"), Blocks.EXPWY_BAR_TYPE_2_DOUBLE.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_double_gray"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_DOUBLE.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_double_placeholder_gray"), Blocks.EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_double_placeholder_gray"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_double_green"), Blocks.EXPWY_BAR_TYPE_2_DOUBLE_GR.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_double_green"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_DOUBLE_GR.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_double_placeholder_green"), Blocks.EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER_GR.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_double_placeholder_green"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER_GR.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_new_double_gray"), Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_new_double_gray"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_new_double_placeholder_gray"), Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_new_double_placeholder_gray"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_new_double_green"), Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_GR.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_new_double_green"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_GR.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_2_new_double_placeholder_green"), Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER_GR.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_2_new_double_placeholder_green"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER_GR.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_bar_type_3"), Blocks.EXPWY_BAR_TYPE_3.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_bar_type_3"), defaultBlockItem(Blocks.EXPWY_BAR_TYPE_3.get()));
        MVRegistry.BLOCK.register(modIdLocation("homo_station_broadcaster"), Blocks.STATION_BROADCASTER.get());
        MVRegistry.ITEM.register(modIdLocation("homo_station_broadcaster"), new StationBroadCasterBlockItem(new Item.Properties()));
        MVRegistry.BLOCK_ENTITY_TYPE.register(modIdLocation("homo_station_broadcaster_entity"), Blocks.BlockEntityTypes.HOMO_STATION_BROADCASTER.get());
        MVRegistry.BLOCK.register(modIdLocation("conveyer_belt"), Blocks.CONVEYER_BELT.get());
        MVRegistry.ITEM.register(modIdLocation("conveyer_belt"), defaultBlockItem(Blocks.CONVEYER_BELT.get()));
        MVRegistry.BLOCK.register(modIdLocation("expwy_caution_bar"), Blocks.EXPWY_CAUTION_BAR.get());
        MVRegistry.ITEM.register(modIdLocation("expwy_caution_bar"), defaultBlockItem(Blocks.EXPWY_CAUTION_BAR.get()));
        MVRegistry.BLOCK.register(modIdLocation("anti_glare_board_type_1"), Blocks.ANTI_GLARE_BOARD_1.get());
        MVRegistry.ITEM.register(modIdLocation("anti_glare_board_type_1"), defaultBlockItem(Blocks.ANTI_GLARE_BOARD_1.get()));
        REPLACE_BLOCKS.put("tcwm:shading_panel_type_1", "tcwm:anti_glare_board_type_1");
        MVRegistry.BLOCK.register(modIdLocation("anti_glare_board_type_2"), Blocks.ANTI_GLARE_BOARD_2.get());
        MVRegistry.ITEM.register(modIdLocation("anti_glare_board_type_2"), defaultBlockItem(Blocks.ANTI_GLARE_BOARD_2.get()));
        REPLACE_BLOCKS.put("tcwm:shading_panel_type_2", "tcwm:anti_glare_board_type_2");
        //sys.bi("air_conditioner", Blocks.AIR_CONDITIONER);
        //sys.bi("air_conditioner_external_unit", Blocks.AIR_CONDITIONER_EU);

        MVRegistry.ITEM.register(modIdLocation("forge_tool"), Items.FORGE_TOOL.get());
        LOGGER.info("Reality City Construction Mod Items and Blocks Registred.");
        TcwmCreativeModeTab.ITEMS.register();
        LOGGER.info("Reality City Construction Mod Initialized.");
        //? < 1.20.5 {
        ClientPlayNetworking.registerGlobalReceiver(PacketScreen.PACKET_SHOW_SCREEN, LegacyPacketScreen.INSTANCE::receive);
        ServerPlayNetworking.registerGlobalReceiver(PacketUpdateBlockEntity.PACKET_UPDATE_BLOCK_ENTITY, LegacyPacketUpdateBlockEntity.INSTANCE::receive);
        //? }
    }

    public static void server() {
        MVNetwork.registerC2S(PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.TYPE, PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.CODEC);
        MVNetwork.registerS2C(PacketScreen.PacketScreenPayload.TYPE, PacketScreen.PacketScreenPayload.CODEC);
        MVNetwork.registerReceiverC2S(PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.TYPE, PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.CODEC);
    }

    public static ResourceLocation modIdLocation(String path) {
        return MVIdentifier.get(RealityCityConstruction.MOD_ID, path);
    }

    public static Item defaultBlockItem(Block block) {
        return new BlockItem(block, new Item.Properties());
    }
}
