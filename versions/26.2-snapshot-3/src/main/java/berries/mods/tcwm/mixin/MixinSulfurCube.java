package berries.mods.tcwm.mixin;

import berries.mods.tcwm.RealityCityConstruction;
import berries.mods.tcwm.block.StationBroadcaster;
import berries.mods.tcwm.item.StationBroadCasterBlockItem;
import berries.mods.tcwm.mvapi.MVIdentifier;
import berries.mods.tcwm.mvapi.MVNetwork;
import berries.mods.tcwm.util.Packets;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.cubemob.AbstractCubeMob;
import net.minecraft.world.entity.monster.cubemob.SulfurCube;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SulfurCube.class)
public abstract class MixinSulfurCube extends AbstractCubeMob {
    protected MixinSulfurCube(EntityType<? extends AbstractCubeMob> type, Level level) {
        super(type, level);
    }

    @Shadow
    public abstract boolean hasBodyItem();

    @Shadow
    protected abstract SoundEvent getAbsorbSound();

    @Inject(
            method = "hurtServer",
            at = @At("HEAD")
    )
    public void hurtServer(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        var stack = this.getItemBySlot(EquipmentSlot.BODY);
        if (this.hasBodyItem() && source.is(DamageTypeTags.SULFUR_CUBE_WITH_BLOCK_IMMUNE_TO) && stack.getItem() instanceof StationBroadCasterBlockItem && (stack.has(DataComponents.BLOCK_ENTITY_DATA) || stack.has(DataComponents.CUSTOM_DATA))) {
            CompoundTag bet;
            var c = stack.getComponents();
            if (c.get(DataComponents.CUSTOM_DATA) != null && c.get(DataComponents.CUSTOM_DATA).copyTag().contains("station_broadcaster_entity_data")) {
                bet = Packets.getReadValue(c.get(DataComponents.CUSTOM_DATA).copyTag().getCompound("station_broadcaster_entity_data"), new CompoundTag());
            } else {
                bet = c.get(DataComponents.BLOCK_ENTITY_DATA).getUnsafe();
            }
            String soundID = "tcwm:music.example";
            float range = 1.4f;
            float pitch = 1.0f;
            if (bet.contains("soundID")) {
                soundID = Packets.getReadValue(bet.getString("soundID"));
                if (bet.contains("range")) {
                    range = Packets.getReadValue(bet.getFloat("range"));
                }
                if (bet.contains("pitch")) {
                    pitch = Packets.getReadValue(bet.getFloat("pitch"));
                }
            }
            this.playSound(SoundEvent.createVariableRangeEvent(MVIdentifier.get(soundID)), range, pitch);
        }
    }

    @Inject(
            method = "equipItem",
            at = @At("RETURN"),
            cancellable = true)
    public void equipItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        ItemStack swallowedItem1 = this.getItemBySlot(EquipmentSlot.BODY);
        if (!cir.getReturnValue() && stack.is(swallowedItem1.getItem()) && !stack.getComponents().equals(swallowedItem1.getComponents())) {
            Vec3 equipmentSpawnOffset = this.getAttachments().getAverage(EntityAttachment.PASSENGER);
            Level var5 = this.level();
            if (var5 instanceof ServerLevel serverLevel) {
                this.spawnAtLocation(serverLevel, this.getItemBySlot(EquipmentSlot.BODY), equipmentSpawnOffset);
            }
            this.setItemSlotAndDropWhenKilled(EquipmentSlot.BODY, stack.copyWithCount(1));
            this.playSound(this.getAbsorbSound());
            cir.setReturnValue(true);
        }
    }

    /*@Unique
    public void playSound(Level level, String id, float r, float p, Vec3 s) {
        Identifier soundEventId = MVIdentifier.get(id);

        if (!level.isClientSide()) {
            Vec3 vpos = new Vec3(s.x, s.y, s.z);
            level.players().forEach((player) ->
            {
                Holder<SoundEvent> hse = Holder.direct(SoundEvent.createVariableRangeEvent(soundEventId));
                Packet<?> packet1 = new ClientboundSoundPacket(hse, SoundSource.BLOCKS, vpos.x, vpos.y, vpos.z, r, p, level.getRandom().nextLong());
                ((ServerPlayer) player).connection.send(packet1);
            });
        } else {
            RealityCityConstruction.LOGGER.error("Failed to Play Sound to Player.");
        }
    }*/
}
