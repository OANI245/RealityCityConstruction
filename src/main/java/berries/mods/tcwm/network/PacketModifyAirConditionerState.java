package berries.mods.tcwm.network;

import berries.mods.tcwm.RealityCityConstruction;
import berries.mods.tcwm.block.AirConditioner;
import berries.mods.tcwm.mvapi.*;
//? < 1.20.5
//import berries.mods.tcwm.network.legacynetwork.LegacyPacketModifyAirConditionerState;
import net.minecraft.core.BlockPos;
//? >= 1.20.5
import net.minecraft.network.codec.StreamCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PacketModifyAirConditionerState {
    public static final int OPEN_AC = 0;
    public static final int STOP_AC = 1;
    public static final int UP_WIND_DIRECTION = 2;
    public static final int DOWN_WIND_DIRECTION = 3;
    public static final int UP_TEMPERATURE = 4;
    public static final int DOWN_TEMPERATURE = 5;

    public static final ResourceLocation PACKET_MODIFY_AIR_CONDITIONER_STATE = MVIdentifier.get(RealityCityConstruction.MOD_ID, "modify_air_conditioner_state");
    public record PacketChangeAirConditionerStatePayload(Integer typ, BlockPos linkedPos) implements MVCustomPayload {
        public static final MVPayloadType<PacketChangeAirConditionerStatePayload> TYPE = new MVPayloadType<>(PACKET_MODIFY_AIR_CONDITIONER_STATE);
        public static final MVPayloadCodec<PacketChangeAirConditionerStatePayload> CODEC = new MVPayloadCodec<>(
                //? >= 1.20.5 {
                StreamCodec.composite(
                        new StreamCodec<ByteBuf, Integer>() {
                            @Override
                            public void encode(ByteBuf object, Integer object2) {
                                object.writeInt(object2);
                            }

                            @Override
                            public Integer decode(ByteBuf object) {
                                return object.readInt();
                            }
                        },
                        PacketChangeAirConditionerStatePayload::typ,
                        BlockPos.STREAM_CODEC,
                        PacketChangeAirConditionerStatePayload::linkedPos,
                        PacketChangeAirConditionerStatePayload::new
                )
                //? }
        );

        @Override
        public @NotNull
            //? < 1.20.5 {
            /*Object
             *///? } else {
        Type<? extends MVCustomPayload>
        //? }
        type() {
            return TYPE.type();
        }

        @Override
        public void receive(Object platform, Player player) {
            modifyState(platform, player, typ, linkedPos);
        }
    }

    public static void modifyState(Object platform, Player player, int typ, BlockPos linkedPos) {
        if (!(platform instanceof MinecraftServer)) return;
        //? < 1.21.5
        ((MinecraftServer) platform).tell(new TickTask(0, () -> {
            ServerLevel level = (ServerLevel) ((ServerPlayer) player).level();
            BlockState state = level.getBlockState(linkedPos);
            BlockEntity entity = level.getBlockEntity(linkedPos);
            if (!(state.getBlock() instanceof AirConditioner) || !(entity instanceof AirConditioner.AirConditionerEntity)) return;
            switch (typ) {
                case OPEN_AC -> {
                    if (state.getValue(AirConditioner.OPEN) == 0) {
                        state = state.setValue(AirConditioner.OPEN, 2);
                    }
                    level.playSound(null, linkedPos, SoundEvents.NOTE_BLOCK_GUITAR.value(), SoundSource.PLAYERS, 0.8f, 1);
                }
                case STOP_AC -> {
                    if (state.getValue(AirConditioner.OPEN) > 0) {
                        state = state.setValue(AirConditioner.OPEN, 0);
                    }
                    level.playSound(null, linkedPos, SoundEvents.NOTE_BLOCK_GUITAR.value(), SoundSource.PLAYERS, 0.8f, 1);
                }
                case UP_WIND_DIRECTION -> {
                    int v = state.getValue(AirConditioner.OPEN);
                    if (v > 0 && v < 3) {
                        state = state.setValue(AirConditioner.OPEN, v + 1);
                    }
                }
                case DOWN_WIND_DIRECTION -> {
                    int v = state.getValue(AirConditioner.OPEN);
                    if (v > 1) {
                        state = state.setValue(AirConditioner.OPEN, Math.max(1, v - 1));
                    }
                }
                case UP_TEMPERATURE -> {
                    float v = ((AirConditioner.AirConditionerEntity) entity).getTemperature();
                    ((AirConditioner.AirConditionerEntity) entity).setTemperature(Math.max(19.0f, Math.min(32.0f, v + 1)));
                    ((AirConditioner.AirConditionerEntity) entity).syncTwoSidesData(level, 0);
                    level.playSound(null, linkedPos, SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.PLAYERS, 0.8f, 1.3f);
                    entity.setChanged();
                    level.players().forEach((p) -> {
                        long l =
                                (long) Math.floor(
                                        Math.sqrt(Math.pow((long) Math.abs(linkedPos.getX() - p.position().x), 2) +
                                                Math.pow((long) Math.abs(linkedPos.getZ() - p.position().z), 2))
                                );
                        //? < 1.20.5 {
                        /*if (l > 32 * 16) {
                            return;
                        }
                        *///? } else {
                    if (l > p.requestedViewDistance() * 16) {
                        return;
                    }
                    //? }
                        p.connection.send(((AirConditioner.AirConditionerEntity) entity).getUpdatePacket());
                    });
                }
                case DOWN_TEMPERATURE -> {
                    float v = ((AirConditioner.AirConditionerEntity) entity).getTemperature();
                    ((AirConditioner.AirConditionerEntity) entity).setTemperature(Math.max(19.0f, Math.min(32.0f, v - 1)));
                    ((AirConditioner.AirConditionerEntity) entity).syncTwoSidesData(level, 0);
                    level.playSound(null, linkedPos, SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.PLAYERS, 0.8f, 1.3f);
                    entity.setChanged();
                    level.players().forEach((p) -> {
                        long l =
                                (long) Math.floor(
                                        Math.sqrt(Math.pow((long) Math.abs(linkedPos.getX() - p.position().x), 2) +
                                                Math.pow((long) Math.abs(linkedPos.getZ() - p.position().z), 2))
                                );
                        //? < 1.20.5 {
                        /*if (l > 32 * 16) {
                            return;
                        }
                        *///? } else {
                    if (l > p.requestedViewDistance() * 16) {
                        return;
                    }
                    //? }
                        p.connection.send(((AirConditioner.AirConditionerEntity) entity).getUpdatePacket());
                    });
                }
            }

            updateTwoSides(level, linkedPos, state);
            //? < 1.21.5
        }));
    }

    public static void updateTwoSides(Level level, BlockPos linkedPos, BlockState state) {
        level.setBlockAndUpdate(linkedPos, state);
        BlockPos npos;
        if (state.getValue(AirConditioner.SIDE) == AirConditioner.EnumSide.RIGHT) {
            npos = linkedPos.relative(state.getValue(AirConditioner.FACING).getClockWise());
        } else {
            npos = linkedPos.relative(state.getValue(AirConditioner.FACING).getCounterClockWise());
        }
        level.setBlockAndUpdate(npos, state.setValue(AirConditioner.SIDE, level.getBlockState(npos).getValue(AirConditioner.SIDE)));
    }

    public static void sendC2S(int type, BlockPos linkedPos) {
        //? < 1.20.5 {
        /*LegacyPacketModifyAirConditionerState.INSTANCE.send(type, linkedPos);
        *///? } else {
        MVNetwork.sendC2S(new PacketChangeAirConditionerStatePayload(type, linkedPos));
        //? }
    }
}
