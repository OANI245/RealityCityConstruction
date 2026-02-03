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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
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
    public static record PacketChangeAirConditionerStatePayload(Integer typ, BlockPos linkedPos) implements MVCustomPayload {
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
        ServerLevel level = (ServerLevel) ((ServerPlayer) player).level();
        BlockState state = level.getBlockState(linkedPos);
        if (!(state.getBlock() instanceof AirConditioner)) return;
        switch (typ) {
            case OPEN_AC -> {
                if (state.getValue(AirConditioner.OPEN) == 0) {
                    state = state.setValue(AirConditioner.OPEN, 2);
                }
            }
            case STOP_AC -> {
                if (state.getValue(AirConditioner.OPEN) > 0) {
                    state = state.setValue(AirConditioner.OPEN, 0);
                }
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
            case UP_TEMPERATURE -> {}
            case DOWN_TEMPERATURE -> {}
        }
        level.setBlockAndUpdate(linkedPos, state);
        BlockPos npos;
        if (state.getValue(AirConditioner.SIDE) == AirConditioner.EnumSide.RIGHT) {
            npos = linkedPos.relative(state.getValue(AirConditioner.FACING).getClockWise());
        } else {
            npos = linkedPos.relative(state.getValue(AirConditioner.FACING).getCounterClockWise());
        }
        level.setBlockAndUpdate(npos, level.getBlockState(npos).setValue(AirConditioner.OPEN, state.getValue(AirConditioner.OPEN)));
    }

    public static void sendC2S(int type, BlockPos linkedPos) {
        //? < 1.20.5 {
        /*LegacyPacketModifyAirConditionerState.INSTANCE.send(type, linkedPos);
        *///? } else {
        MVNetwork.sendC2S(new PacketChangeAirConditionerStatePayload(type, linkedPos));
        //? }
    }
}
