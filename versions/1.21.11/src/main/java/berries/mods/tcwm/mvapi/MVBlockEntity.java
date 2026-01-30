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
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public class MVBlockEntity extends BlockEntity {
    public MVBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public CompoundTag getTag() {
        CompoundTag tag = null;
        if (level != null) {
            tag = saveWithoutMetadata(level.registryAccess());
        }
        return tag;
    }

    public void setTag(CompoundTag tag) {
        try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(this.problemPath(), RealityCityConstruction.LOGGER)) {
            if (level == null) return;
            ValueInput in = TagValueInput.create(scopedCollector, level.registryAccess(), tag);
            loadAdditional(in);
        }
    }

    @Override
    protected final void loadAdditional(ValueInput in) {
        super.loadAdditional(in);
        loadTag(new MVBlockEntityComponent(in));
    }

    @Override
    protected final void saveAdditional(ValueOutput out) {
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
