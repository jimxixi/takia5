package takia5.playerClient.ui;

import java.util.HashMap;
import java.util.LinkedList;

import imgui.ImGui;
import takia5.utils.GameSaveUtil;

public class GameSaveList extends NinePatchDiv {

    LinkedList<HashMap<String, String>> gameSaves;

    public GameSaveList() {
        super(50, 100, Corner.RIGHTTOP, 8);
        gameSaves = GameSaveUtil.scanGameSaves();
        System.out.println();
    }

    @Override
    public void drawComponent() {
        if (ImGui.begin("GameSaveList")) {
            ImGui.text("Yo");
        }
        ImGui.end();
    }

}
