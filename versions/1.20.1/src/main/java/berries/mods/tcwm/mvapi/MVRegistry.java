package berries.mods.tcwm.mvapi;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.List;

public final class MVRegistry<T> {
    public static final MVRegistry<Block> BLOCK = new MVRegistry<>("block", (k, v) -> {
        Registry.register(BuiltInRegistries.BLOCK, k, v);
    });
    public static final MVRegistry<Item> ITEM = new MVRegistry<>("item", (k, v) -> {
        Registry.register(BuiltInRegistries.ITEM, k, v);
    });
    public static final MVRegistry<BlockEntityType<? extends BlockEntity>> BLOCK_ENTITY_TYPE = new MVRegistry<>("blockEntityType", (k, v) -> {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, k, v);
    });

    private final RegisterConsumer<T> registerFunc;
    public final List<T> registryObjects = new ArrayList<>();

    private MVRegistry(String name, RegisterConsumer<T> registerFunc) {
        this.registerFunc = registerFunc;
    }

    public void register(ResourceLocation id, T obj) {
        registerFunc.register(id, obj);
        registryObjects.add(obj);
    }

    @FunctionalInterface
    public interface RegisterConsumer<Y> {
        void register(ResourceLocation k, Y v);
    }
}
