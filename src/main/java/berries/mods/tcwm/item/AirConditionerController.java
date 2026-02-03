package berries.mods.tcwm.item;

import berries.mods.tcwm.block.AirConditioner;
import berries.mods.tcwm.mvapi.MVComponent;
import berries.mods.tcwm.network.PacketModifyAirConditionerState;
import berries.mods.tcwm.util.Packets;
import net.minecraft.core.BlockPos;
//? >= 1.20.5 {
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.component.CustomData;
//? }
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
//? < 1.21.5
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AirConditionerController extends Item {
    public AirConditionerController(Properties properties) {
        super(properties);
    }

    @Nullable
    public BlockPos getLinkedBlockPos(ItemStack stack) {
        //? >= 1.20.5 {
        var temp = stack.getComponents().get(DataComponents.CUSTOM_DATA);
        if (temp == null) return null;
        CompoundTag tag = temp.copyTag();
        //? } else {
        /*CompoundTag tag = stack.getOrCreateTag();
        *///? }
        if (tag.contains("LinkedBlockPos")) {
            int[] lt = Packets.getReadValue(tag.getIntArray("LinkedBlockPos")
                    , new int[] {});
            if (lt.length != 3) return null;
            return new BlockPos(Packets.getReadValue(lt[0]), Packets.getReadValue(lt[1]), Packets.getReadValue(lt[2]));
        }
        return null;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        if (useOnContext.isSecondaryUseActive() && useOnContext.getPlayer() != null && getLinkedBlockPos(useOnContext.getPlayer().getItemInHand(useOnContext.getHand())) == null) {
            BlockPos blockPos = useOnContext.getClickedPos();
            BlockState blockState = useOnContext.getLevel().getBlockState(blockPos);
            BlockPos shouldLinkBlockPos;
            if (!blockState.hasProperty(AirConditioner.OPEN) || !blockState.hasProperty(AirConditioner.SIDE)) {
                return InteractionResult.PASS;
            }
            if (!useOnContext.getLevel().isClientSide()) {
                if (blockState.getValue(AirConditioner.SIDE) == AirConditioner.EnumSide.RIGHT) {
                    shouldLinkBlockPos = blockPos.relative(blockState.getValue(AirConditioner.FACING).getClockWise());
                } else {
                    shouldLinkBlockPos = blockPos;
                }
                CompoundTag customTag = new CompoundTag();
                customTag.putIntArray("LinkedBlockPos", new int[]{shouldLinkBlockPos.getX(), shouldLinkBlockPos.getY(), shouldLinkBlockPos.getZ()});
                ItemStack stack = useOnContext.getPlayer().getItemInHand(useOnContext.getHand()).copy();
                //? >= 1.20.5 {
                ((PatchedDataComponentMap) stack.getComponents()).set(DataComponents.CUSTOM_DATA, CustomData.of(customTag));
                //? } else {
                /*stack.setTag(customTag);
                *///? }
                useOnContext.getPlayer().setItemInHand(useOnContext.getHand(), stack);

                ((ServerPlayer) useOnContext.getPlayer()).connection.send(new ClientboundSetActionBarTextPacket(MVComponent.translatable("gui.tcwm.air_conditioner.linked", shouldLinkBlockPos.getX(), shouldLinkBlockPos.getY(), shouldLinkBlockPos.getZ())));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull
        //? >= 1.21.5 {
    /*InteractionResult
    *///? } else {
    InteractionResultHolder<ItemStack> //? }
    use(@NotNull Level level, Player player, @NotNull InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        BlockPos blockPos = getLinkedBlockPos(stack);
        if (blockPos == null) {
            //? >= 1.21.5
            //return InteractionResult.PASS;
            //? < 1.21.5
            return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
        }
        long l =
                (long) Math.floor(
                        Math.sqrt(Math.pow((long) Math.abs(blockPos.getX() - player.position().x), 2) +
                                Math.pow((long) Math.abs(blockPos.getZ() - player.position().z), 2))
                );
        if (l >= 6) {
            if (!level.isClientSide()) {
                ((ServerPlayer) player).connection.send(new ClientboundSetActionBarTextPacket(MVComponent.translatable("gui.tcwm.air_conditioner.too_far")));
            }
            //? >= 1.21.5
            //return InteractionResult.FAIL;
            //? < 1.21.5
            return InteractionResultHolder.fail(player.getItemInHand(interactionHand));
        }
        BlockState blockState = level.getBlockState(blockPos);
        if (blockState.hasProperty(AirConditioner.OPEN)) {
            if (!level.isClientSide()) {
                PacketModifyAirConditionerState.modifyState(level.getServer(), player, blockState.getValue(AirConditioner.OPEN) == 0 ? 0 : 1, blockPos);
            }
            //? >= 1.21.5
            //return InteractionResult.SUCCESS;
            //? < 1.21.5
            return InteractionResultHolder.success(player.getItemInHand(interactionHand));
        }
        return super.use(level, player, interactionHand);
    }
}
