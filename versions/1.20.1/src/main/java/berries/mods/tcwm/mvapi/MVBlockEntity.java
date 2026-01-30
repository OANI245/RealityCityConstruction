package berries.mods.tcwm.mvapi;

import berries.mods.tcwm.RealityCityConstruction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
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
        CompoundTag tag = null;
        if (level != null) {
            tag = saveWithoutMetadata();
        }
        return tag;
    }

    public void setTag(CompoundTag tag) {
        if (level != null) {
            load(tag);
        }
    }

    @Override
    public final void load(CompoundTag in) {
        super.load(in);
        loadTag(new MVBlockEntityComponent(in));
    }

    @Override
    protected final void saveAdditional(CompoundTag out) {
        super.saveAdditional(out);
        saveTag(new MVBlockEntityComponent(out));
    }

    public void loadTag(MVBlockEntityComponent tag) {
    }

    public void saveTag(MVBlockEntityComponent tag) {
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return super.getUpdatePacket();
    }
}
