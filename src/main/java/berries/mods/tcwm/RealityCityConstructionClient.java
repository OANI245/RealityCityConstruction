package berries.mods.tcwm;

import berries.mods.tcwm.block.AirConditioner;
import berries.mods.tcwm.block.Blocks;
import berries.mods.tcwm.item.AirConditionerController;
import berries.mods.tcwm.mvapi.MVBlockRenderLayerMap;
import berries.mods.tcwm.mvapi.MVComponent;
import berries.mods.tcwm.mvapi.MVIdentifier;
import berries.mods.tcwm.mvapi.MVNetwork;
import berries.mods.tcwm.network.PacketModifyAirConditionerState;
import berries.mods.tcwm.network.PacketOpenSoundPlayerScreen;
import berries.mods.tcwm.network.PacketUpdateBlockEntity;
import berries.mods.tcwm.render.AirConditionerRenderer;
import berries.mods.tcwm.util.settings.Settings;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.lwjgl.glfw.GLFW;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
//? < 1.20.5 {
/*import berries.mods.tcwm.network.legacynetwork.LegacyPacketOpenSoundPlayerScreen;
 *///? }
//? < 1.21.6
import org.slf4j.LoggerFactory;

public class RealityCityConstructionClient implements ClientModInitializer {
    public String MIN_MTRVERSION = "3.2.0";
    public final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RealityCityConstruction.MOD_ID);
    //private Runtime rn = Runtime.getRuntime();
    //? >= 1.21.9
    //protected KeyMapping.Category category;

    @SuppressWarnings("all")
    public void onInitializeClient() {
        LOGGER.info("RCCmod Client Task Loading");

        if (!Minecraft.getInstance().gameDirectory.toPath().resolve("config").resolve("tcwm.json").toFile().exists()) {
            Settings io = Settings.get(Minecraft.getInstance().gameDirectory.toPath().resolve("config").resolve("tcwm.json"));
            Settings.write(io);
        }

        ModContainer container = FabricLoader.getInstance().getModContainer(UFEInfo.MOD_ID).get();
        ResourceManagerHelper.registerBuiltinResourcePack(
                MVIdentifier.get(UFEInfo.MOD_ID, "tcwm_pbr"),
                container, MVComponent.translatable("pack.tcwm.pbr_texture"),
                ResourcePackActivationType.NORMAL
        );

        //? >= 1.21.9
        //category = KeyMapping.Category.register(MVIdentifier.get(RealityCityConstruction.MOD_ID, "general"));

        KeyMapping binding0 = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.tcwm.air_conditioner.direction_down",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_DOWN,
                        //? >= 1.21.9 {
                        /*category*///? } else {
                        "key.category.tcwm.general"
                        //? }
                )
        );

        KeyMapping binding1 = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.tcwm.air_conditioner.direction_up",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_UP,                         
                        //? >= 1.21.9 {
                        /*category*///? } else {
                        "key.category.tcwm.general"
                        //? }
                )
        );

        KeyMapping binding2 = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.tcwm.air_conditioner.temperature_up",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_RIGHT,
                        //? >= 1.21.9 {
                        /*category*///? } else {
                        "key.category.tcwm.general"
                         //? }
                )
        );

        KeyMapping binding3 = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.tcwm.air_conditioner.temperature_down",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_LEFT,
                        //? >= 1.21.9 {
                        /*category*///? } else {
                        "key.category.tcwm.general"
                         //? }
                )
        );

        AtomicInteger tc = new AtomicInteger(0);
        ClientTickEvents.END_CLIENT_TICK.register((mc) -> {
            if (mc.player != null && mc.player.isHolding((stack) -> stack.getItem() instanceof AirConditionerController)) {
                ItemStack stack0 = mc.player.getMainHandItem();
                BlockPos blockPos0 = ((AirConditionerController) stack0.getItem()).getLinkedBlockPos(stack0);
                if (blockPos0 == null) {
                    return;
                }

                long l =
                        (long) Math.floor(
                                Math.sqrt(Math.pow((long) Math.abs(blockPos0.getX() - mc.player.position().x), 2) +
                                        Math.pow((long) Math.abs(blockPos0.getZ() - mc.player.position().z), 2))
                        );
                if (tc.intValue() != 0) {
                    tc.set(tc.intValue() >= 4 ? 0 : tc.intValue() + 1);
                }
                if (binding0.isDown()) {
                    if (tc.intValue() != 0) {
                        return;
                    }
                    if (l >= 6) {
                        mc.gui.setOverlayMessage(MVComponent.translatable("gui.tcwm.air_conditioner.too_far"), false);
                        return;
                    }
                    PacketModifyAirConditionerState.sendC2S(2, blockPos0);
                    tc.set(1);
                } else if (binding1.isDown()) {
                    if (tc.intValue() != 0) {
                        return;
                    }
                    if (l >= 6) {
                        mc.gui.setOverlayMessage(MVComponent.translatable("gui.tcwm.air_conditioner.too_far"), false);
                        return;
                    }
                    PacketModifyAirConditionerState.sendC2S(3, blockPos0);
                    tc.set(1);
                } else if (binding2.isDown()) {
                    if (tc.intValue() != 0) {
                        return;
                    }
                    if (l >= 6) {
                        mc.gui.setOverlayMessage(MVComponent.translatable("gui.tcwm.air_conditioner.too_far"), false);
                        return;
                    }
                    PacketModifyAirConditionerState.sendC2S(4, blockPos0);
                    tc.set(1);
                } else if (binding3.isDown()) {
                    if (tc.intValue() != 0) {
                        return;
                    }
                    if (l >= 6) {
                        mc.gui.setOverlayMessage(MVComponent.translatable("gui.tcwm.air_conditioner.too_far"), false);
                        return;
                    }
                    PacketModifyAirConditionerState.sendC2S(5, blockPos0);
                    tc.set(1);
                }
            }
        });

        MVBlockRenderLayerMap.put(2, Blocks.WINDOW);
        MVBlockRenderLayerMap.put(2, Blocks.WINDOW_PANE);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1_BEIJING_4);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1_OC_NEW);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1_OC_NEW_REF);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1_OC_NEW_2);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1_OC_NEW_2_REF);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_1_SHANGHAI);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_PLACEHOLDER);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_PLACEHOLDER_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_DOUBLE);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_DOUBLE_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_DOUBLE_PLACEHOLDER_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_PLACEHOLDER_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_2_NEW_DOUBLE_PLACEHOLDER_GR);
        MVBlockRenderLayerMap.put(1, Blocks.EXPWY_BAR_TYPE_3);
        MVBlockRenderLayerMap.put(1, Blocks.AIR_CONDITIONER);
        MVBlockRenderLayerMap.put(1, Blocks.AIR_CONDITIONER_EXTERNAL_UNIT);
        AirConditionerRenderer.register((BlockEntityType<AirConditioner.AirConditionerEntity>) Blocks.BlockEntityTypes.AIR_CONDITIONER, AirConditionerRenderer::new);

        MVNetwork.registerC2S(PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.TYPE, PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.CODEC);
        MVNetwork.registerC2S(PacketModifyAirConditionerState.PacketChangeAirConditionerStatePayload.TYPE, PacketModifyAirConditionerState.PacketChangeAirConditionerStatePayload.CODEC);
        MVNetwork.registerS2C(PacketOpenSoundPlayerScreen.PacketScreenPayload.TYPE, PacketOpenSoundPlayerScreen.PacketScreenPayload.CODEC);
        MVNetwork.registerReceiverS2C(PacketOpenSoundPlayerScreen.PacketScreenPayload.TYPE, PacketOpenSoundPlayerScreen.PacketScreenPayload.CODEC);
        MVNetwork.registerReceiverC2S(PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.TYPE, PacketUpdateBlockEntity.PacketUpdateBlockEntityPayload.CODEC);
        MVNetwork.registerReceiverC2S(PacketModifyAirConditionerState.PacketChangeAirConditionerStatePayload.TYPE, PacketModifyAirConditionerState.PacketChangeAirConditionerStatePayload.CODEC);
        //? < 1.20.5 {
        /*ClientPlayNetworking.registerGlobalReceiver(PacketOpenSoundPlayerScreen.PACKET_SHOW_SCREEN, LegacyPacketOpenSoundPlayerScreen.INSTANCE::receive);
         *///? }
        //nrr.accept(LegacyPacketScreen.PACKET_SHOW_SCREEN, LegacyPacketScreen::receiveScreenS2C);
    }
}
