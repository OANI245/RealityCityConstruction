package berries.mods.tcwm.mixin;

import berries.mods.tcwm.RealityCityConstruction;
import berries.mods.tcwm.util.Packets;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
//? >= 1.21.9
//import net.minecraft.world.level.chunk.PalettedContainerFactory;
import net.minecraft.world.level.chunk.ProtoChunk;
//? < 1.21.5 {
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
//? } else {
/*import net.minecraft.world.level.chunk.storage.SerializableChunkData;
*///? }
//? > 1.20.5
import net.minecraft.world.level.chunk.storage.RegionStorageInfo;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

//? < 1.21.5 {
@Mixin(ChunkSerializer.class)
public class MixinChunkSerializer {
    @Inject(
        method = "read",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/ListTag;getCompound(I)Lnet/minecraft/nbt/CompoundTag;", ordinal = 0, shift = At.Shift.AFTER)
    )
    //? > 1.20.5 {
    private static void read(ServerLevel serverLevel, PoiManager poiManager, RegionStorageInfo regionStorageInfo, ChunkPos chunkPos, CompoundTag compoundTag, CallbackInfoReturnable<ProtoChunk> cir, @Local(ordinal = 0) CompoundTag compoundTag2) {
        //? } else {
    /*private static void read(ServerLevel serverLevel, PoiManager poiManager, ChunkPos chunkPos, CompoundTag compoundTag, CallbackInfoReturnable<ProtoChunk> cir, @Local(ordinal = 0) CompoundTag compoundTag2) {
        *///? }
        //? } else {
/*@Mixin(SerializableChunkData.class)
public class MixinChunkSerializer {
    @Shadow
    @Final
    private static Logger LOGGER;

    @Inject(
        method = "parse",
        at = @At(value = "INVOKE", target = "Ljava/util/Optional;get()Ljava/lang/Object;", ordinal = 0, shift = At.Shift.AFTER)
    )
    //? < 1.21.9 {
    private static void read(LevelHeightAccessor levelHeightAccessor, RegistryAccess registryAccess, CompoundTag compoundTag, CallbackInfoReturnable<SerializableChunkData> cir, @Local(ordinal = 0) CompoundTag compoundTag3) {
    //? } else {
    /^private static void read(LevelHeightAccessor levelHeightAccessor, PalettedContainerFactory palettedContainerFactory, CompoundTag compoundTag, CallbackInfoReturnable<SerializableChunkData> cir, @Local(ordinal = 0) CompoundTag compoundTag3) {
    ^///? }
        *///? }
        //? >= 1.21.5 {
        /*Tag sections = compoundTag3.get("sections");
        if (sections instanceof ListTag) {
            for (int i = 0; i < ((ListTag) sections).size(); i++) {
                Tag v = ((ListTag) sections).get(i);
                if (v instanceof CompoundTag && ((CompoundTag) v).contains("block_states")) {
                    CompoundTag blockStates = ((CompoundTag) v).getCompound("block_states").orElse(new CompoundTag());
                    if (blockStates.isEmpty()) return;
                    ListTag palette = blockStates.getList("palette").orElse(new ListTag());
                    if (palette.isEmpty()) return;
                    for (int j = 0; j < palette.size(); j++) {
                        CompoundTag block = Packets.getReadValue(palette.getCompound(j), new CompoundTag());
                        if (block.isEmpty()) continue;
                        String id = Packets.getReadValue(block.getString("Name"), "");
                        String newId = RealityCityConstruction.REPLACE_BLOCKS.getOrDefault(id, id);
                        block.putString("Name", newId);
                        palette.set(j, block);
                    }
                    blockStates.put("palette", palette);
                    ((CompoundTag) v).put("block_states", blockStates);
                    ((ListTag) sections).set(i, v);
                }
            }

            compoundTag3.put("sections", sections);
        }
        *///? } else {
        Tag sections = compoundTag2.get("sections");
        if (sections instanceof ListTag) {
            for (int i = 0; i < ((ListTag) sections).size(); i++) {
                Tag v = ((ListTag) sections).get(i);
                if (v instanceof CompoundTag && ((CompoundTag) v).contains("block_states")) {
                    CompoundTag blockStates = ((CompoundTag) v).getCompound("block_states");
                    if (blockStates.isEmpty()) return;
                    ListTag palette = blockStates.getList("palette", 10);
                    if (palette.isEmpty()) return;
                    for (int j = 0; j < palette.size(); j++) {
                        CompoundTag block = Packets.getReadValue(palette.getCompound(j), new CompoundTag());
                        if (block.isEmpty()) continue;
                        String id = Packets.getReadValue(block.getString("Name"), "");
                        String newId = RealityCityConstruction.REPLACE_BLOCKS.getOrDefault(id, id);
                        block.putString("Name", newId);
                        palette.set(j, block);
                    }
                    blockStates.put("palette", palette);
                    ((CompoundTag) v).put("block_states", blockStates);
                    ((ListTag) sections).set(i, v);
                }
            }

            compoundTag2.put("sections", sections);
        }
        //? }
        /*
        * CompoundTag blockStatesBlock = Packets.getReadValue(compoundTag2.getCompound("block_states"), new CompoundTag());
        if (blockStatesBlock.isEmpty()) return;
        ListTag list = blockStatesBlock.getList("palette", 10);
        if (list.isEmpty()) return;
        for (int j = 0; j < list.size(); j++) {
            CompoundTag block = Packets.getReadValue(list.getCompound(j), new CompoundTag());
            if (block.isEmpty()) continue;
            String id = Packets.getReadValue(block.getString("Name"), "");
            String newId = RealityCityConstruction.REPLACE_BLOCKS.getOrDefault(id, id);
            block.putString("Name", newId);
            list.set(j, block);
        }
        blockStatesBlock.put("palette", list);
        compoundTag2.put("block_states", blockStatesBlock);*/
    }
}
