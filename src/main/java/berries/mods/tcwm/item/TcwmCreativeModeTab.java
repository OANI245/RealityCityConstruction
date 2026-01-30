package berries.mods.tcwm.item;

import berries.mods.tcwm.block.Blocks;
import berries.mods.tcwm.mvapi.MVCreativeModeTab;
import berries.mods.tcwm.mvapi.MVIdentifier;
import berries.mods.tcwm.mvapi.MVRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TcwmCreativeModeTab {
    public static final MVCreativeModeTab ITEMS = new MVCreativeModeTab(MVIdentifier.get("tcwm", "core"), () -> new ItemStack(() -> Blocks.LOGO_BLOCK.asItem()), (net.minecraft.world.item.CreativeModeTab.DisplayItemsGenerator) (c, e) -> {
        for (Item i : MVRegistry.ITEM.registryObjects) {
            e.accept(i);
        }
    });
    //public static final CreativeModeTabs.Wrapper RAILS = new CreativeModeTabs.Wrapper(new Identifier("tcwm","rails"), () -> new ItemStack(() -> Items.RAIL_CONNECTOR_HOMO.get()));
}
