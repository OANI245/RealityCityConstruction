package berries.mods.tcwm.mvapi;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class MVRegistry<V, T, U, R> {
    public static final MVRegistry<Block, ResourceLocation, Function<BlockBehaviour.Properties, Block>, BlockBehaviour.Properties> BLOCK_ONLY = new MVRegistry<>("block", (k, f, v) -> {
        return Blocks.register(ResourceKey.create(Registries.BLOCK, k), f, v);
    });
    public static final MVRegistry<Item, ResourceLocation, Function<Item.Properties, Item>, Item.Properties> ITEM = new MVRegistry<>("item", (k, f, v) -> {
        return Items.registerItem(ResourceKey.create(Registries.ITEM, k), f, v);
    });
    public static final MVRegistry<BlockEntityType<? extends BlockEntity>, ResourceLocation, Object, BlockEntityType<? extends BlockEntity>> BLOCK_ENTITY_TYPE = new MVRegistry<>("blockEntityType", (k, f, v) -> {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, k, v);
    });

    public static final MVRegistry<Block, ResourceLocation, Function<BlockBehaviour.Properties, Block>, BlockBehaviour.Properties> BLOCK = new MVRegistry<>("block", (k, f, v) -> {
        var block = Blocks.register(ResourceKey.create(Registries.BLOCK, k), f, v);
        var item = Items.registerBlock(block);
        ITEM.registryObjects.add(item);
        return block;
    });
    private final RegisterConsumer<V, T, U, R> registerFunc;
    public final List<V> registryObjects = new ArrayList<>();

    private MVRegistry(String name, RegisterConsumer<V, T, U, R> registerFunc) {
        this.registerFunc = registerFunc;
    }

    public V register(@NotNull T t, @Nullable U u, R r) {
        var v = registerFunc.register(t, u, r);
        registryObjects.add(v);
        return v;
    }

    @FunctionalInterface
    public interface RegisterConsumer<W, X, Y, Z> {
        W register(@NotNull X k, Y f, Z v);
    }
}
