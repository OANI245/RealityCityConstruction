package berries.mods.tcwm.mvapi;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MVBlockEntity extends BlockEntity {
    public MVBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public CompoundTag getTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, null);
        return tag;
    }

    public void setTag(CompoundTag tag) {
        loadAdditional(tag, null);
    }

    @Override
    protected final void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        loadTag(compoundTag);
    }

    @Override
    protected final void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        saveTag(compoundTag);
    }

    public void loadTag(CompoundTag tag) {
    }

    public void saveTag(CompoundTag tag) {
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return super.getUpdatePacket();
    }
}
