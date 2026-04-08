package berries.mods.tcwm.gui;

import berries.mods.tcwm.RealityCityConstruction;
import berries.mods.tcwm.mvapi.MVIdentifier;
import net.minecraft.resources.Identifier;

public interface Icons {
    Identifier EDIT = of("edit");
    Identifier ARROW_DOWN = of("arrow_down");
    Identifier ARROW_UP = of("arrow_up");
    Identifier PLAY_ARROW = of("play_arrow");
    Identifier PAUSE = of("pause");
    Identifier CHECK = of("check");
    Identifier ARROW_FORWARD = of("arrow_forward");
    Identifier ARROW_BACK = of("arrow_back");
    Identifier ARROW_BACK_BLUE = of("arrow_back_blue");

    static Identifier of(String filename) {
        return MVIdentifier.get(RealityCityConstruction.MOD_ID, "textures/gui/icon/" + filename + ".png");
    }
}
