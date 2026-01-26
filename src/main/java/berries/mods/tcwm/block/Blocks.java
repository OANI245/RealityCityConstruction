package berries.mods.tcwm.block;

import berries.mods.tcwm.util.RegistryObject;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Collections;

import static net.minecraft.world.level.block.Blocks.*;

public interface Blocks {
    static BlockBehaviour.Properties copyProperties(BlockBehaviour block) {
        //? <= 1.20.2 {
        /*return BlockBehaviour.Properties.copy(block);
        *///? } else {
        return BlockBehaviour.Properties.ofFullCopy(block);
        //? }
    }

    RegistryObject<Block> MARBLE = new RegistryObject<>(() -> new Block(copyProperties(DIORITE).requiresCorrectToolForDrops()));
    RegistryObject<Block> WHITE_MARBLE = new RegistryObject<>(() -> new Block(copyProperties(DIORITE).requiresCorrectToolForDrops()));
    //RegistryObject<Block> METAL = new RegistryObject<>(() -> new MetalBlock(copyProperties(IRON_BLOCK).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    //RegistryObject<Block> D_YELLOW_BLIND = new RegistryObject<>(() -> new DeprecatedBlind(copyProperties(YELLOW_CONCRETE).requiresCorrectToolForDrops().noOcclusion()));
    //RegistryObject<Block> D_BLACK_BLIND = new RegistryObject<>(() -> new DeprecatedBlind(copyProperties(GRAY_CONCRETE).requiresCorrectToolForDrops().noOcclusion()));
    RegistryObject<Block> YELLOW_BLIND_SIDEWALK = new RegistryObject<>(() -> new BlindSidewalk(copyProperties(YELLOW_CONCRETE).requiresCorrectToolForDrops().noOcclusion()));
    RegistryObject<Block> BLACK_BLIND_SIDEWALK = new RegistryObject<>(() -> new BlindSidewalk(copyProperties(GRAY_CONCRETE).requiresCorrectToolForDrops().noOcclusion()));
    RegistryObject<Block> YELLOW_BLIND_SIDEWALK_CORNER = new RegistryObject<>(() -> new BlindSidewalk(copyProperties(YELLOW_CONCRETE).requiresCorrectToolForDrops().noOcclusion()));
    RegistryObject<Block> BLACK_BLIND_SIDEWALK_CORNER = new RegistryObject<>(() -> new BlindSidewalk(copyProperties(GRAY_CONCRETE).requiresCorrectToolForDrops().noOcclusion()));
    RegistryObject<Block> ANDESITE_BLIND_SIDEWALK = new RegistryObject<>(() -> new BlindSidewalk(copyProperties(ANDESITE).requiresCorrectToolForDrops().noOcclusion()));
    RegistryObject<Block> ANDESITE_BLIND_SIDEWALK_CORNER = new RegistryObject<>(() -> new BlindSidewalk(copyProperties(ANDESITE).requiresCorrectToolForDrops().noOcclusion()));
    RegistryObject<Block> YELLOW_BLIND_SIDEWALK_SLAB = new RegistryObject<>(() -> new BlindSidewalkSlab(copyProperties(YELLOW_CONCRETE).requiresCorrectToolForDrops()));
    RegistryObject<Block> BLACK_BLIND_SIDEWALK_SLAB = new RegistryObject<>(() -> new BlindSidewalkSlab(copyProperties(GRAY_CONCRETE).requiresCorrectToolForDrops()));
    RegistryObject<Block> ANDESITE_BLIND_SIDEWALK_SLAB = new RegistryObject<>(() -> new BlindSidewalkSlab(copyProperties(ANDESITE).requiresCorrectToolForDrops()));
    RegistryObject<Block> TUNNEL_WIRES_STYLE_1 = new RegistryObject<>(() -> new TunnelWires1(copyProperties(LIGHT_GRAY_CONCRETE).requiresCorrectToolForDrops().noOcclusion()));
    RegistryObject<Block> LOGO_BLOCK = new RegistryObject<>(() -> new Block(copyProperties(BEDROCK).lightLevel(s -> 5).requiresCorrectToolForDrops().noOcclusion()));
    RegistryObject<Block> TUNNEL_WIRES_STYLE_2 = new RegistryObject<>(() -> new TunnelWires2(copyProperties(IRON_BARS).requiresCorrectToolForDrops().noOcclusion()));
    //RegistryObject<Block> D_YELLOW_BLIND_SLAB = new RegistryObject<>(() -> new DeprecatedBlindSlab(copyProperties(YELLOW_CONCRETE).requiresCorrectToolForDrops()));
    //RegistryObject<Block> D_BLACK_BLIND_SLAB = new RegistryObject<>(() -> new DeprecatedBlindSlab(copyProperties(GRAY_CONCRETE).requiresCorrectToolForDrops()));
    RegistryObject<Block> TUNNEL_LIGHT = new RegistryObject<>(() -> new TunnelLight(copyProperties(GLOWSTONE).lightLevel(s -> 9).noOcclusion().requiresCorrectToolForDrops()));
    RegistryObject<Block> WINDOW = new RegistryObject<>(() -> new Window(copyProperties(GLASS).requiresCorrectToolForDrops().sound(SoundType.GLASS).noOcclusion()));
    RegistryObject<Block> WINDOW_PANE = new RegistryObject<>(() -> new WindowPane(DyeColor.BLACK, copyProperties(GLASS_PANE).requiresCorrectToolForDrops().sound(SoundType.GLASS).noOcclusion()));
    /*RegistryObject<Block> PLATFORM = getPlatform(copyProperties(STONE).requiresCorrectToolForDrops().noOcclusion(), false);
    RegistryObject<Block> PLATFORM_TYP_QUARTZ = getPlatform(copyProperties(SMOOTH_QUARTZ).requiresCorrectToolForDrops().noOcclusion(), false);
    RegistryObject<Block> PLATFORM_INDENTED = getPlatform(copyProperties(STONE).requiresCorrectToolForDrops().noOcclusion(), true);*/
    RegistryObject<Block> BOAT_DOCK = new RegistryObject<>(() -> new Block(copyProperties(OAK_PLANKS).noOcclusion()));
    RegistryObject<Block> EXPWY_BAR_TYPE_1 = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(WHITE_CONCRETE)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            , "tooltip.expwy_bar_type_1", 1));
    RegistryObject<Block> EXPWY_BAR_TYPE_1_BEIJING_4 = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(EXPWY_BAR_TYPE_1.get()), "tooltip.expwy_bar_type_1", 1));
    RegistryObject<Block> EXPWY_BAR_TYPE_1_SHANGHAI = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(EXPWY_BAR_TYPE_1.get()), "tooltip.expwy_bar_type_1", 1));
    RegistryObject<Block> EXPWY_BAR_TYPE_1_OC_NEW = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(EXPWY_BAR_TYPE_1.get()), "tooltip.expwy_bar_type_1", 1));
    RegistryObject<Block> EXPWY_BAR_TYPE_1_OC_NEW_REF = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(EXPWY_BAR_TYPE_1.get()), "tooltip.expwy_bar_type_1", 1));
    RegistryObject<Block> EXPWY_BAR_TYPE_1_OC_NEW_2 = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(EXPWY_BAR_TYPE_1.get()), "tooltip.expwy_bar_type_1", 1));
    RegistryObject<Block> EXPWY_BAR_TYPE_1_OC_NEW_2_REF = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(EXPWY_BAR_TYPE_1.get()), "tooltip.expwy_bar_type_1", 1));
    RegistryObject<Block> EXPWY_BAR_TYPE_2 = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(IRON_BARS)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            , "tooltip.expwy_bar_type_2", 2));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_DOUBLE = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(EXPWY_BAR_TYPE_2.get()), "tooltip.expwy_bar_type_2", 2));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_NEW = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(EXPWY_BAR_TYPE_2.get()), "tooltip.expwy_bar_type_2", 2));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_NEW_DOUBLE = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(EXPWY_BAR_TYPE_2.get()), "tooltip.expwy_bar_type_2", 2));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_GR = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(EXPWY_BAR_TYPE_2.get()), "tooltip.expwy_bar_type_2", 2));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_DOUBLE_GR = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(EXPWY_BAR_TYPE_2.get()), "tooltip.expwy_bar_type_2", 2));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_NEW_GR = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(EXPWY_BAR_TYPE_2.get()), "tooltip.expwy_bar_type_2", 2));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_NEW_DOUBLE_GR = new RegistryObject<>(() -> new ExpwyBarType1Or2(copyProperties(EXPWY_BAR_TYPE_2.get()), "tooltip.expwy_bar_type_2", 2));
    RegistryObject<Block> EXPWY_BAR_TYPE_3 = new RegistryObject<>(() -> new ExpwyBarType3(copyProperties(IRON_BARS)
            .requiresCorrectToolForDrops()
            .noOcclusion()
    ));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_PLACEHOLDER = new RegistryObject<>(() -> new ExpwyBarType2Placeholder(Direction.NORTH));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER = new RegistryObject<>(() -> new ExpwyBarType2Placeholder(Direction.NORTH));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER = new RegistryObject<>(() -> new ExpwyBarType2Placeholder(Direction.NORTH));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER = new RegistryObject<>(() -> new ExpwyBarType2Placeholder(Direction.NORTH));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_PLACEHOLDER_GR = new RegistryObject<>(() -> new ExpwyBarType2Placeholder(Direction.NORTH));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER_GR = new RegistryObject<>(() -> new ExpwyBarType2Placeholder(Direction.NORTH));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER_GR = new RegistryObject<>(() -> new ExpwyBarType2Placeholder(Direction.NORTH));
    RegistryObject<Block> EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER_GR = new RegistryObject<>(() -> new ExpwyBarType2Placeholder(Direction.NORTH));
    RegistryObject<Block> STATION_BROADCASTER = new RegistryObject<>(StationBroadcaster::new);
    RegistryObject<Block> CONVEYER_BELT = new RegistryObject<>(() -> new ConveyerBelt(copyProperties(net.minecraft.world.level.block.Blocks.IRON_BLOCK)));

    RegistryObject<Block> EXPWY_CAUTION_BAR = new RegistryObject<>(() -> new ExpwyCautionBar(copyProperties(net.minecraft.world.level.block.Blocks.STONE).noOcclusion()));
    RegistryObject<Block> ANTI_GLARE_BOARD_1 = new RegistryObject<>(AntiGlareBoard::new);
    RegistryObject<Block> ANTI_GLARE_BOARD_2 = new RegistryObject<>(AntiGlareBoard::new);
    //RegistryObject<Block> AIR_CONDITIONER = new RegistryObject<>(() -> new AirConditioner(copyProperties(net.minecraft.world.level.block.Blocks.SMOOTH_QUARTZ).noOcclusion()));
    //RegistryObject<Block> AIR_CONDITIONER_EU = new RegistryObject<>(() -> new AirConditionerExternalUnit(copyProperties(net.minecraft.world.level.block.Blocks.SMOOTH_QUARTZ).noOcclusion()));

    /*static RegistryObject<Block> getPlatform(BlockBehaviour.Properties properties, boolean i) {
        if (Info.isMTRInstalled) {
            return new RegistryObject<>(() ->
            {
                try {
                    return (Block) Class.forName("mtr.block.BlockPlatform").getConstructor(BlockBehaviour.class, Boolean.TYPE).newInstance(properties, i);
                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                         IllegalAccessException | InvocationTargetException e) {
                    return new PlatformBlock(properties, i);
                }
            });
        } else {
            return new RegistryObject<>(() -> new PlatformBlock(properties, i));
        }
    }
*/
    interface BlockEntityTypes {
        RegistryObject<BlockEntityType<StationBroadcaster.StationBroadcasterEntity>> HOMO_STATION_BROADCASTER = new RegistryObject<>(() -> new BlockEntityType<>(StationBroadcaster.StationBroadcasterEntity::new, Collections.singleton(Blocks.STATION_BROADCASTER.get()), null));
    }
}
