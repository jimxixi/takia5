package takia5.playerClient.ui;

import java.util.HashMap;

import imgui.ImGui;

public class GameSettings extends NinePatchDiv {

    public GameSettings() {
        super(50, 100, NinePatchDiv.Corner.RIGHTTOP, 8);
    }

    @Override
    public void drawComponent() {
        if (ImGui.begin("GameSettings")) {
            ImGui.text("settings");
        }
        ImGui.end();
    }

    public HashMap<String, String> loadConfig() {
        HashMap<String, String> result = new HashMap<>();
        return result;
    }

    public void dumpConfig(HashMap<String, String> config) {

    }

}
