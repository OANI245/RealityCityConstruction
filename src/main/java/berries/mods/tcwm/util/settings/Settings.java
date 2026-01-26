package berries.mods.tcwm.util.settings;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import berries.mods.tcwm.RealityCityConstruction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.function.Function;

public class Settings {
    private Path jsonPath;
    public boolean customRailwaySignIsOpen = true;
    public boolean disabledMixins = false;
    private static final JsonParser JSON_PARSER = new JsonParser();
    private static Function<Screen, Screen> screenCreator = null;

    private Settings(Path path) {
        this.jsonPath = path;
    }

    public static Settings read(Path jsonPath) {
        try {
            JsonObject jsonObject = JSON_PARSER.parse(String.join("", Files.readAllLines(jsonPath))).getAsJsonObject();
            Settings io = new Settings(jsonPath);
            io.customRailwaySignIsOpen = jsonObject.get("EnableCustomSign").getAsBoolean();
            io.disabledMixins = jsonObject.get("DisabledMixin").getAsBoolean();

            return io;
        } catch (IOException ie) {
            RealityCityConstruction.LOGGER.error("Fatal! path is null");
            ie.printStackTrace();
            Settings io = new Settings(jsonPath);
            Settings.write(io);
            return io;
        } catch (NullPointerException ne) {
            ne.printStackTrace();
            RealityCityConstruction.LOGGER.warn("continue run...");
            Settings io = new Settings(jsonPath);
            Settings.write(io);
            return io;
        }
    }

    public static void write(Settings io) {
        if (io.jsonPath == null) return;
        try {
            JsonObject configObject = new JsonObject();
            configObject.addProperty("EnableCustomSign", io.customRailwaySignIsOpen);
            configObject.addProperty("DisabledMixin", io.disabledMixins);
            Files.write(io.jsonPath, Collections.singleton(new GsonBuilder().setPrettyPrinting().create().toJson(configObject)));
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public static Settings get(Path path) {
        return new Settings(path);
    }

    public static void setScreenCreator(Function<Screen, Screen> creator) {
        screenCreator = creator;
    }

    public static Screen getConfigScreen(Screen parent) {
        return screenCreator.apply(parent);
    }

    public static Exception createConfigScreen(Screen parent) {
        try {
            Minecraft.getInstance().setScreen(screenCreator.apply(parent));
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }
}
