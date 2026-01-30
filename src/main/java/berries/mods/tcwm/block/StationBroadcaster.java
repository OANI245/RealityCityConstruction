package berries.mods.tcwm.block;

import berries.mods.tcwm.RealityCityConstruction;
import berries.mods.tcwm.item.Items;
import berries.mods.tcwm.mvapi.MVBlockEntityComponent;
import berries.mods.tcwm.mvapi.MVComponent;
import berries.mods.tcwm.mvapi.MVIdentifier;
import berries.mods.tcwm.network.PacketOpenSoundPlayerScreen;
import berries.mods.tcwm.util.TcwmBlockEntity;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
//? >= 1.20.5 {
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
    //? < 1.21.5 {
    /*import net.minecraft.world.ItemInteractionResult;
    *///? }
//? }
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
//? >= 1.21.5 {
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.storage.ValueOutput;
//? }
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class StationBroadcaster extends Block implements EntityBlock {
    public static Function<BlockPos, Screen> propertiesScreen = null;
    private static final BooleanProperty POWERED = BooleanProperty.create("powered");

    public StationBroadcaster(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(POWERED, false));
    }

    //此处放广播器代码
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StationBroadcasterEntity(pos, state);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return (BlockState) this.defaultBlockState().setValue(POWERED, blockPlaceContext.getLevel().hasNeighborSignal(blockPlaceContext.getClickedPos()));
    }

    public StationBroadcasterEntity getBlockEntity(Level level, BlockPos blockPos) {
        StationBroadcasterEntity blockEntity;
        BlockEntity temp = level.getBlockEntity(blockPos);
        if (temp instanceof StationBroadcasterEntity) {
            blockEntity = (StationBroadcasterEntity) temp;
            return blockEntity;
        } else {
            return null;
        }
    }

    //? < 1.20.5 {
    /*@Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (player.isHolding(Items.FORGE_TOOL)) {
            if (!level.isClientSide) {
                PacketOpenSoundPlayerScreen.sendScreenBlockS2C((ServerPlayer) player, blockPos);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
    *///? } else if < 1.21.5 {
    /*@Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.isHolding(Items.FORGE_TOOL)) {
            if (!level.isClientSide) {
                PacketOpenSoundPlayerScreen.sendScreenBlockS2C((ServerPlayer) player, pos);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.FAIL;
    }
    *///? } else {
    @Override
    protected @NotNull InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.isHolding(Items.FORGE_TOOL)) {
            if (!level.isClientSide()) {
                PacketOpenSoundPlayerScreen.sendScreenBlockS2C((ServerPlayer) player, pos);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
    //? }

    //deprecated
    /*@Override
    public InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        //open BroadCaster Screen
        if (player.isHolding(Items.FORGE_TOOL)) {
            if (!level.isClientSide) {
                LegacyPacketScreen.sendScreenBlockS2C((ServerPlayer) player, "SSBAS", blockPos);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }*/

    //? < 1.21.5 {
    /*@Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (!level.isClientSide) {
            boolean bl2 = (Boolean) blockState.getValue(POWERED);
            if (bl2 != level.hasNeighborSignal(blockPos)) {
                if (bl2) {
                    level.setBlock(blockPos, (BlockState) blockState.setValue(POWERED, false), 1 | 2);
                } else {
                    level.setBlock(blockPos, blockState.setValue(POWERED, true), 1 | 2);
                    playSound(level, blockPos);
                }
            }
        }
    }
    *///? } else {
    @Override
    protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, @Nullable Orientation orientation, boolean bl) {
        if (!level.isClientSide()) {
            boolean bl2 = (Boolean) blockState.getValue(POWERED);
            if (bl2 != level.hasNeighborSignal(blockPos)) {
                if (bl2) {
                    level.setBlock(blockPos, (BlockState) blockState.setValue(POWERED, false), 1 | 2);
                } else {
                    level.setBlock(blockPos, blockState.setValue(POWERED, true), 1 | 2);
                    playSound(level, blockPos);
                }
            }
        }
    }
    //? }

    public void playSound(Level level, BlockPos blockPos) {
        StationBroadcasterEntity s = getBlockEntity(level, blockPos);
        Identifier soundEventId = MVIdentifier.get(s.getSoundID());

        if (!level.isClientSide()) {
            Vec3 vpos = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            level.players().forEach((player) ->
            {
                Holder<SoundEvent> hse = Holder.direct(SoundEvent.createVariableRangeEvent(soundEventId));
                Packet<?> packet = new ClientboundSoundPacket(hse, SoundSource.BLOCKS, vpos.x, vpos.y, vpos.z, s.getRange(), s.getPitch().fLoat, level.getRandom().nextLong());
                ((ServerPlayer) player).connection.send(packet);
            });
        } else {
            RealityCityConstruction.LOGGER.error("Failed to Play Sound to Player.");
        }
    }

    @Override
    public Item asItem() {
        Item item = super.asItem();
        return item;
    }

    public static class StationBroadcasterEntity extends TcwmBlockEntity {
        //存放方块实体的变量
        private boolean isJustPlacedNow = true;
        private String name = "";
        private String soundID = "tcwm:music.example";
        private float range = 1.4F;
        private Pitch pitch = Pitch.DEFAULT;

        //方块实体代码
        public StationBroadcasterEntity(BlockPos blockPos, BlockState blockState) {
            super(Blocks.BlockEntityTypes.HOMO_STATION_BROADCASTER, blockPos, blockState);
        }

        @Override
        public final void loadTag(MVBlockEntityComponent compoundTag) {
            super.loadTag(compoundTag);
            //? < 1.20.5 {
            /*Component nameComponent = null;
            *///? } else {
            var nameComponent = components().get(DataComponents.CUSTOM_NAME);
            //? }
            name = compoundTag.contains("name") ? compoundTag.getString("name") : (nameComponent != null ? nameComponent.getString() : "");
            soundID = compoundTag.getString("soundID");
            range = compoundTag.getFloat("range");
            pitch = Pitch.getValue(compoundTag.getFloat("pitch"));
            isJustPlacedNow = compoundTag.getBoolean("isJustPlacedNow");
        }

        public String getName() {
            //? >= 1.20.5 {
            if (!name.trim().isEmpty()) {
                var components = DataComponentMap.builder().set(DataComponents.CUSTOM_NAME, MVComponent.text(this.name)).build();
                this.setComponents(components);
            } else {
                this.setComponents(DataComponentMap.EMPTY);
            }
            //? }
            return name;
        }

        public float getRange() {
            return range;
        }

        public Pitch getPitch() {
            return pitch;
        }

        public String getSoundID() {
            return soundID;
        }

        public void setName(String name) {
            this.name = name;
            //? >= 1.20.5 {
            if (!name.trim().isEmpty()) {
                var components = DataComponentMap.builder().set(DataComponents.CUSTOM_NAME, MVComponent.text(this.name)).build();
                this.setComponents(components);
            } else {
                this.setComponents(DataComponentMap.EMPTY);
            }
            //? }
        }

        public void setPitch(Pitch pitch) {
            this.pitch = pitch;
        }

        public void setSoundID(String soundID) {
            this.soundID = soundID;
        }

        public void setRange(float range) {
            this.range = range;
        }

        //? < 1.20.5 {
        /*@Override
        public void saveToItem(ItemStack itemStack) {
            CompoundTag compoundTag = new CompoundTag();
            CompoundTag displayTag = new CompoundTag();
            displayTag.putString("Name", "[\"" + this.name + "\"]");
            compoundTag.put("display", displayTag);
            itemStack.setTag(itemStack.getTag().merge(compoundTag));
        }
        *///? } else if < 1.21.5 {
        /*@Override
        public void saveToItem(ItemStack stack, HolderLookup.Provider registries) {
            CompoundTag compoundTag = this.saveCustomOnly(registries);
            compoundTag.remove("name");
            compoundTag.putBoolean("isJustPlacedNow", true);
            this.removeComponentsFromTag(compoundTag);
            BlockItem.setBlockEntityData(stack, this.getType(), compoundTag);
            stack.applyComponents(this.collectComponents());
        }
        *///? } else {
        @Override
        public void saveCustomOnly(ValueOutput valueOutput) {
            valueOutput.putString("soundID", soundID);
            valueOutput.putFloat("range", range);
            valueOutput.putFloat("pitch", pitch.getFloat());
        }
        //? }

        @Override
        public final void saveTag(MVBlockEntityComponent compoundTag) {
            super.saveTag(compoundTag);
            /*var components = DataComponentMap.builder().set(DataComponents.CUSTOM_NAME, MVComponent.text(this.name)).build();
            this.setComponents(components);*/
            /*if (!compoundTag.contains("components")) {
                compoundTag.put("components", new CompoundTag());
            }
            Identifier customItemNameKey = BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(DataComponents.CUSTOM_NAME);
            if (customItemNameKey != null) {
                ((CompoundTag)compoundTag.get("components")).putString(customItemNameKey.toString(), name);
            }*/
            //? < 1.20.5 {
            /*Component nameComponent = MVComponent.text(this.name);
            *///? } else {
            var nameComponent = components().get(DataComponents.CUSTOM_NAME);
            //? }
            if (nameComponent != null && isJustPlacedNow) {
                compoundTag.putString("name", nameComponent.getString());
            } else {
                compoundTag.putString("name", name);
            }
            isJustPlacedNow = false;
            compoundTag.putString("soundID", soundID);
            compoundTag.putFloat("range", range);
            compoundTag.putFloat("pitch", pitch.getFloat());
            compoundTag.putBoolean("isJustPlacedNow", isJustPlacedNow);
        }

        @Override
        public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
        }

        //? >= 1.20.5 {
        @Override
        public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
            return getTag();
        }
        //? } else {
        /*@Override
        public CompoundTag getUpdateTag() {
            return super.getUpdateTag();
        }
        *///? }

        public static enum Pitch {
            VERY_SLOW(0.5F), SLOW(0.75F), DEFAULT(1.0F), FAST(1.5F), VERY_FAST(2.0F);
            final float fLoat;

            Pitch(float pitch) {
                this.fLoat = pitch;
            }

            public float getFloat() {
                return fLoat;
            }

            public static Pitch getValue(float f) {
              if (f == 0.5F) {
                return VERY_SLOW;
              } else if (f == 0.75F) {
                return SLOW;
              } else if (f == 1.0F) {
                return DEFAULT;
              } else if (f == 1.5F) {
                return FAST;
              } else if (f == 2.0F) {
                return VERY_FAST;
              } else {
                return DEFAULT;
              }
            }
        }
    }
}
