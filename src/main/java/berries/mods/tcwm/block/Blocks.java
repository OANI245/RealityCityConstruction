package berries.mods.tcwm.block;

import berries.mods.tcwm.RealityCityConstruction;
import berries.mods.tcwm.mvapi.MVIdentifier;
import berries.mods.tcwm.mvapi.MVRegistry;
import berries.mods.tcwm.util.RegistryObject;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Collections;
import java.util.Set;

import static berries.mods.tcwm.RealityCityConstruction.modIdLocation;
import static net.minecraft.world.level.block.Blocks.*;

public interface Blocks {
    static BlockBehaviour.Properties copyProperties(BlockBehaviour block) {
        //? <= 1.20.2 {
        /*return BlockBehaviour.Properties.copy(block);
        *///? } else {
        return BlockBehaviour.Properties.ofFullCopy(block);
        //? }
    }

    static BlockBehaviour.Properties propertiesOf(ResourceLocation id, BlockBehaviour.Properties a) {
        //? > 1.21.2
        //a.setId(ResourceKey.create(Registries.BLOCK, id));
        return a;
    }
    
    Block YELLOW_BLIND_SIDEWALK = MVRegistry.BLOCK.register(modIdLocation("yellow_blind_sidewalk"), BlindSidewalk::new, Blocks.copyProperties(YELLOW_CONCRETE)/*.noOcclusion()*/);
    Block BLACK_BLIND_SIDEWALK = MVRegistry.BLOCK.register(modIdLocation("black_blind_sidewalk"), BlindSidewalk::new, Blocks.copyProperties(GRAY_CONCRETE)/*.noOcclusion()*/);
    Block ANDESITE_BLIND_SIDEWALK = MVRegistry.BLOCK.register(modIdLocation("andesite_blind_sidewalk"), BlindSidewalk::new, Blocks.copyProperties(ANDESITE)/*.noOcclusion()*/);
    Block YELLOW_BLIND_SIDEWALK_CORNER = MVRegistry.BLOCK.register(modIdLocation("yellow_blind_sidewalk_corner"), BlindSidewalk::new, Blocks.copyProperties(YELLOW_CONCRETE)/*.noOcclusion()*/);
    Block BLACK_BILND_SIDEWALK_CORNER = MVRegistry.BLOCK.register(modIdLocation("black_blind_sidewalk_corner"), BlindSidewalk::new, Blocks.copyProperties(GRAY_CONCRETE)/*.noOcclusion()*/);
    Block ANDESITE_BLIND_SIDEWALK_CORNER = MVRegistry.BLOCK.register(modIdLocation("andesite_blind_sidewalk_corner"), BlindSidewalk::new, Blocks.copyProperties(ANDESITE)/*.noOcclusion()*/);
    Block YELLOW_BLIND_SIDEWALK_SLAB = MVRegistry.BLOCK.register(modIdLocation("yellow_blind_sidewalk_slab"), BlindSidewalkSlab::new, Blocks.copyProperties(YELLOW_CONCRETE)/*.noOcclusion()*/);
    Block BLACK_BLIND_SIDEWALK_SLAB = MVRegistry.BLOCK.register(modIdLocation("black_blind_sidewalk_slab"), BlindSidewalkSlab::new, Blocks.copyProperties(GRAY_CONCRETE)/*.noOcclusion()*/);
    Block ANDESITE_BLIND_SIDEWALK_SLAB = MVRegistry.BLOCK.register(modIdLocation("andesite_blind_sidewalk_slab"), BlindSidewalkSlab::new, Blocks.copyProperties(ANDESITE)/*.noOcclusion()*/);

    Block LOGO_BLOCK = MVRegistry.BLOCK.register(modIdLocation("logo"), Block::new, Blocks.copyProperties(BEDROCK).lightLevel(s -> 5).noOcclusion());
    Block WINDOW = MVRegistry.BLOCK.register(modIdLocation("window"), Window::new, Blocks.copyProperties(GLASS));
    Block WINDOW_PANE = MVRegistry.BLOCK.register(modIdLocation("windowpane"), WindowPane::new, Blocks.copyProperties(GLASS_PANE));
    /*Block PLATFORM = getPlatform(copyProperties(STONE).noOcclusion(), false);
    Block PLATFORM_TYP_QUARTZ = getPlatform(copyProperties(SMOOTH_QUARTZ).noOcclusion(), false);
    Block PLATFORM_INDENTED = getPlatform(copyProperties(STONE).noOcclusion(), true);*/
    Block EXPWY_BAR_TYPE_1 = MVRegistry.BLOCK.register(modIdLocation("road_railings_concrete_bj"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_1_BEIJING_4 = MVRegistry.BLOCK.register(modIdLocation("road_railings_concrete_bj_4"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_1_SHANGHAI = MVRegistry.BLOCK.register(modIdLocation("road_railings_concrete_sh"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_1_OC_NEW = MVRegistry.BLOCK.register(modIdLocation("road_railings_concrete_gb_fa"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_1_OC_NEW_REF = MVRegistry.BLOCK.register(modIdLocation("road_railings_concrete_gb_fa_ref"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_1_OC_NEW_2 = MVRegistry.BLOCK.register(modIdLocation("road_railings_concrete_gb_f"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_1_OC_NEW_2_REF = MVRegistry.BLOCK.register(modIdLocation("road_railings_concrete_gb_f_ref"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2 = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_2w_gray"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_DOUBLE = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_4w_gray"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_NEW = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_3w_gray"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_NEW_DOUBLE = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_5w_gray"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_GR = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_2w_green"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_DOUBLE_GR = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_4w_green"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_NEW_GR = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_3w_green"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_NEW_DOUBLE_GR = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_5w_green"), ExpwyBarType1Or2::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_3 = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_4p"), ExpwyBarType3::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_PLACEHOLDER = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_2w_v_gray"), ExpwyBarType2Placeholder::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_4w_v_gray"), ExpwyBarType2Placeholder::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_3w_v_gray"), ExpwyBarType2Placeholder::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_5w_v_gray"), ExpwyBarType2Placeholder::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_PLACEHOLDER_GR = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_2w_v_green"), ExpwyBarType2Placeholder::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER_GR = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_4w_v_green"), ExpwyBarType2Placeholder::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER_GR = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_3w_v_green"), ExpwyBarType2Placeholder::new, Blocks.copyProperties(IRON_BARS));
    Block EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER_GR = MVRegistry.BLOCK.register(modIdLocation("road_railings_iron_5w_v_green"), ExpwyBarType2Placeholder::new, Blocks.copyProperties(IRON_BARS));
    Block STATION_BROADCASTER = MVRegistry.BLOCK_ONLY.register(modIdLocation("homo_station_broadcaster"), StationBroadcaster::new, Blocks.copyProperties(STONE));

    Block AIR_CONDITIONER = MVRegistry.BLOCK.register(modIdLocation("air_conditioner"), AirConditioner::new, Blocks.copyProperties(QUARTZ_BLOCK).noOcclusion());
    Block AIR_CONDITIONER_EXTERNAL_UNIT = MVRegistry.BLOCK.register(modIdLocation("air_conditioner_external_unit"), AirConditionerExternalUnit::new, Blocks.copyProperties(QUARTZ_BLOCK).noOcclusion());

    /*static Block getPlatform(BlockBehaviour.Properties properties, boolean i) {
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
        //? < 1.21.2 {
        BlockEntityType<StationBroadcaster.StationBroadcasterEntity> HOMO_STATION_BROADCASTER = new BlockEntityType<>(StationBroadcaster.StationBroadcasterEntity::new, Collections.singleton(Blocks.STATION_BROADCASTER), null);
        BlockEntityType<AirConditioner.AirConditionerEntity> AIR_CONDITIONER = new BlockEntityType<>(AirConditioner.AirConditionerEntity::new, Collections.singleton(Blocks.AIR_CONDITIONER), null);
        //? } else {
        /*BlockEntityType<?> HOMO_STATION_BROADCASTER = FabricBlockEntityTypeBuilder.create(StationBroadcaster.StationBroadcasterEntity::new, Blocks.STATION_BROADCASTER).build();
        BlockEntityType<?> AIR_CONDITIONER = FabricBlockEntityTypeBuilder.create(AirConditioner.AirConditionerEntity::new, Blocks.AIR_CONDITIONER).build();
        *///? }
    }
}
