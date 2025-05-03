package takia5.playerClient.ui;

import java.util.HashMap;
import java.util.LinkedList;

import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import takia5.utils.GameSaveUtil;

public class GameSaveList extends NinePatchDiv {

    LinkedList<HashMap<String, String>> gameSaves;

    public GameSaveList() {
        super(50, 100, Corner.RIGHTTOP, 8);
        gameSaves = GameSaveUtil.scanGameSaves();
    }
    
    @Override
    public void drawComponent() {
        if (ImGui.begin("GameSaveList")) {
            ImGui.text("Yo");
            for (HashMap<String, String> gameSave : gameSaves) {
                ImGui.pushStyleColor(ImGuiCol.Border, new ImVec4(1f, 1, 1f, 1));
                ImGui.beginChild(gameSave.get("name"), new ImVec2(this.width - 16, 24*4), true);
                ImGui.labelText("name", gameSave.get("name"));
                ImGui.labelText("gameprogress", gameSave.get("游戏进度gameprogress"));
                ImGui.labelText("note", gameSave.get("存档备注note"));
                ImGui.endChild();
                ImGui.popStyleColor();
                ImGui.newLine();
            }
        }
        ImGui.end();
    }

}
