package takia5.playerClient.ui;

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
    

}
