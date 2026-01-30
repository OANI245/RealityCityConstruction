package berries.mods.tcwm.item;

import berries.mods.tcwm.mvapi.MVRegistry;
import berries.mods.tcwm.util.RegistryObject;
import net.minecraft.world.item.Item;

import static berries.mods.tcwm.RealityCityConstruction.modIdLocation;

public interface Items {
  //deprecated
//  RegistryObject<Item> RAIL_CONNECTOR_HOMO = new RegistryObject<>(() -> new ItemRailModifier(true, false, true, false, RailType.HOMO));
//  RegistryObject<Item> RAIL_CONNECTOR_140 = new RegistryObject<>(() -> new ItemRailModifier(true, false, true, false, RailType.NETHER));
//  RegistryObject<Item> RAIL_CONNECTOR_250 = new RegistryObject<>(() -> new ItemRailModifier(true, false, true, false, RailType.REDSTONE));
//  RegistryObject<Item> RAIL_CONNECTOR_350 = new RegistryObject<>(() -> new ItemRailModifier(true, false, true, false, RailType.END));
//  RegistryObject<Item> RAIL_CONNECTOR_420 = new RegistryObject<>(() -> new ItemRailModifier(true, false, true, false, RailType.NETHERITE));
//  RegistryObject<Item> RAIL_CONNECTOR_600 = new RegistryObject<>(() -> new ItemRailModifier(true, false, true, false, RailType.BEDROCK));
//  RegistryObject<Item> RAIL_CONNECTOR_HOMO_ONE_WAY = new RegistryObject<>(() -> new ItemRailModifier(true, false, true, false, RailType.HOMO));
//  RegistryObject<Item> RAIL_CONNECTOR_140_ONE_WAY = new RegistryObject<>(() -> new ItemRailModifier(true, false, true, true, RailType.NETHER));
//  RegistryObject<Item> RAIL_CONNECTOR_250_ONE_WAY = new RegistryObject<>(() -> new ItemRailModifier(true, false, true, true, RailType.REDSTONE));
//  RegistryObject<Item> RAIL_CONNECTOR_350_ONE_WAY = new RegistryObject<>(() -> new ItemRailModifier(true, false, true, true, RailType.END));
//  RegistryObject<Item> RAIL_CONNECTOR_420_ONE_WAY = new RegistryObject<>(() -> new ItemRailModifier(true, false, true, true, RailType.NETHERITE));
//  RegistryObject<Item> RAIL_CONNECTOR_600_ONE_WAY = new RegistryObject<>(() -> new ItemRailModifier(true, false, true, true, RailType.BEDROCK));\
  Item FORGE_TOOL = MVRegistry.ITEM.register(modIdLocation("forge_tool"), Item::new, new Item.Properties());
}
