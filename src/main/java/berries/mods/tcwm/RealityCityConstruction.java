package berries.mods.tcwm;

import berries.mods.tcwm.block.*;
import berries.mods.tcwm.item.StationBroadCasterBlockItem;
import berries.mods.tcwm.mvapi.MVIdentifier;
import berries.mods.tcwm.mvapi.MVNetwork;
import berries.mods.tcwm.mvapi.MVRegistry;
import berries.mods.tcwm.network.PacketOpenSoundPlayerScreen;
import berries.mods.tcwm.network.PacketUpdateBlockEntity;
import berries.mods.tcwm.item.Items;
import berries.mods.tcwm.item.TcwmCreativeModeTab;
//? < 1.20.5 {
/*
import berries.mods.tcwm.network.legacynetwork.LegacyPacketUpdateBlockEntity;
*///? }
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.world.level.block.Blocks.*;

public class RealityCityConstruction implements ModInitializer {
    public static final String MOD_ID = "tcwm";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Map<String, String> REPLACE_BLOCKS = new HashMap<>();

    @SuppressWarnings("all")
    public void onInitialize() {
        MVRegistry.BLOCK.register(modIdLocation("marble"), Block::new, Blocks.copyProperties(QUARTZ_BLOCK));
        MVRegistry.BLOCK.register(modIdLocation("white_marble"), Block::new, Blocks.copyProperties(QUARTZ_BLOCK));

        //Moved from TBM (Tiancheng Blocks Mod)
        MVRegistry.BLOCK.register(modIdLocation("ceiling_normal"), Ceiling::new, Blocks.copyProperties(IRON_BLOCK).noOcclusion());
        MVRegistry.BLOCK.register(modIdLocation("ceiling_light"), (pf) -> new Ceiling(pf, 15), Blocks.copyProperties(IRON_BLOCK).noOcclusion());
        MVRegistry.BLOCK.register(modIdLocation("ceiling_normal_grille"), CeilingNoDirection::new, Blocks.copyProperties(IRON_BLOCK).noOcclusion());
        MVRegistry.BLOCK.register(modIdLocation("ceiling_light_grille"), (pf) -> new Ceiling(pf, 15), Blocks.copyProperties(IRON_BLOCK).noOcclusion());

        MVRegistry.BLOCK.register(modIdLocation("tunnel_wires_style_1"), TunnelWires1::new, Blocks.copyProperties(LIGHT_GRAY_CONCRETE).noOcclusion());
        MVRegistry.BLOCK.register(modIdLocation("tunnel_wires_style_2"), TunnelWires2::new, Blocks.copyProperties(COPPER_BLOCK).noOcclusion());
        MVRegistry.BLOCK.register(modIdLocation("tunnel_light"), TunnelLight::new, Blocks.copyProperties(SEA_LANTERN).noOcclusion());
        /*MVRegistry.BLOCK.register(modIdLocation("platform"), Blocks.PLATFORM.get());
        MVRegistry.ITEM.register(modIdLocation("platform"), defaultBlockItem(Blocks.PLATFORM.get()));
        MVRegistry.BLOCK.register(modIdLocation("platform_type_quartz"), Blocks.PLATFORM_TYP_QUARTZ.get());
        MVRegistry.ITEM.register(modIdLocation("platform_type_quartz"), defaultBlockItem(Blocks.PLATFORM_TYP_QUARTZ.get()));
        MVRegistry.BLOCK.register(modIdLocation("platform_indented"), Blocks.PLATFORM_INDENTED.get());
        MVRegistry.ITEM.register(modIdLocation("platform_indented"), defaultBlockItem(Blocks.PLATFORM_INDENTED.get()));*/
        //? >= 1.21.5 {
        /*MVRegistry.ITEM.register(modIdLocation("homo_station_broadcaster"), (pf) -> new StationBroadCasterBlockItem((StationBroadcaster) Blocks.STATION_BROADCASTER, pf), new Item.Properties().useBlockDescriptionPrefix());*///?} else {
        MVRegistry.ITEM.register(modIdLocation("homo_station_broadcaster"), (pf) -> new StationBroadCasterBlockItem((StationBroadcaster) Blocks.STATION_BROADCASTER, pf), new Item.Properties());
        //? }
        MVRegistry.BLOCK.register(modIdLocation("conveyer_belt"), ConveyerBelt::new, Blocks.copyProperties(DEEPSLATE));
        MVRegistry.BLOCK.register(modIdLocation("expwy_caution_bar"), ExpwyCautionBar::new, Blocks.copyProperties(LIGHT_GRAY_CONCRETE));
        MVRegistry.BLOCK.register(modIdLocation("anti_glare_board_type_1"), AntiGlareBoard::new, Blocks.copyProperties(GREEN_STAINED_GLASS));
        MVRegistry.BLOCK.register(modIdLocation("anti_glare_board_type_2"), AntiGlareBoard::new, Blocks.copyProperties(GREEN_STAINED_GLASS));
        //sys.bi("air_conditioner", Blocks.AIR_CONDITIONER);
        //sys.bi("air_conditioner_external_unit", Blocks.AIR_CONDITIONER_EU);
        MVRegistry.BLOCK_ENTITY_TYPE.register(modIdLocation("homo_station_broadcaster_entity"), null, Blocks.BlockEntityTypes.HOMO_STATION_BROADCASTER);
        Items.FORGE_TOOL.asItem();
        LOGGER.info("Reality City Construction Mod Items and Blocks Registred.");
        TcwmCreativeModeTab.ITEMS.register();
        LOGGER.info("Reality City Construction Mod Initialized.");
        //? < 1.20.5 {
        /*
        ServerPlayNetworking.registerGlobalReceiver(PacketUpdateBlockEntity.PACKET_UPDATE_BLOCK_ENTITY, LegacyPacketUpdateBlockEntity.INSTANCE::receive);
        *///? }

        REPLACE_BLOCKS.put("tcwm:yellow_blind", "tcwm:yellow_blind_sidewalk");
        REPLACE_BLOCKS.put("tcwm:black_blind", "tcwm:black_blind_sidewalk");
        REPLACE_BLOCKS.put("tcwm:yellow_blind_corner", "tcwm:yellow_blind_sidewalk_corner");
        REPLACE_BLOCKS.put("tcwm:black_blind_corner", "tcwm:black_blind_sidewalk_corner");
        REPLACE_BLOCKS.put("tcwm:yellow_blind_slab", "tcwm:yellow_blind_sidewalk_slab");
        REPLACE_BLOCKS.put("tcwm:black_blind_slab", "tcwm:black_blind_sidewalk_slab");
        REPLACE_BLOCKS.put("tcwm:transmission_line", "tcwm:conveyer_belt");
        REPLACE_BLOCKS.put("tcwm:platform", "mtr:platform");
        REPLACE_BLOCKS.put("tcwm:platform_indented", "mtr:platform_indented");
        REPLACE_BLOCKS.put("tcwm:platform_type_quartz", "minecraft:quartz_block");
        REPLACE_BLOCKS.put("tcwm:tunnelline", "tcwm:tunnel_wires_style_1");
        REPLACE_BLOCKS.put("tcwm:tunnellinenoblock", "tcwm:tunnel_wires_style_2");
        REPLACE_BLOCKS.put("tcwm:tunnellight", "tcwm:tunnel_light");
        REPLACE_BLOCKS.put("tcwm:shading_panel_type_1", "tcwm:anti_glare_board_type_1");
        REPLACE_BLOCKS.put("tcwm:shading_panel_type_2", "tcwm:anti_glare_board_type_2");
    }

    public static void server() {
        //? >= 1.20.5 {
        MVNetwork.registerC2S(PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.TYPE, PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.CODEC);
        MVNetwork.registerS2C(PacketOpenSoundPlayerScreen.PacketScreenPayload.TYPE, PacketOpenSoundPlayerScreen.PacketScreenPayload.CODEC);
        MVNetwork.registerReceiverC2S(PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.TYPE, PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.CODEC);
        //? }
    }

    public static ResourceLocation modIdLocation(String path) {
        return MVIdentifier.get(RealityCityConstruction.MOD_ID, path);
    }

    public static Item defaultBlockItem(Block block) {
        return new BlockItem(block, new Item.Properties());
    }
}
