package takia5.playerClient.ui;

import java.util.HashMap;
import java.util.LinkedList;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiMouseCursor;
import imgui.flag.ImGuiWindowFlags;
import takia5.utils.GameSaveUtil;

public class GameSaveList extends NinePatchDiv {

    LinkedList<HashMap<String, String>> gameSaves;
    String hoveringSaveName = "";
    String selectedSaveName = "";

    public GameSaveList() {
        super(50, 100, Corner.RIGHTTOP, 8);
        gameSaves = GameSaveUtil.scanGameSaves();
    }

    @Override
    public void drawComponent() {
        if (ImGui.begin("GameSaveList", ImGuiWindowFlags.NoResize)) {
            ImGui.beginChild("save area", new ImVec2(this.width - 16, this.height - 128), true);
            for (HashMap<String, String> gameSave : gameSaves) {
                if (gameSave.get("name").equals(selectedSaveName)) {
                    ImGui.pushStyleColor(ImGuiCol.ChildBg, new ImVec4(0.4f, 0.4f, 0.4f, 1));
                } else if (gameSave.get("name").equals(hoveringSaveName)) {
                    ImGui.pushStyleColor(ImGuiCol.ChildBg, new ImVec4(0.2f, 0.2f, 0.2f, 1));
                } else {
                    ImGui.pushStyleColor(ImGuiCol.ChildBg, new ImVec4(0, 0, 0, 1));
                }
                // ImGui.pushStyleColor(ImGuiCol.Border, new ImVec4(1f, 1, 1f, 1));
                ImGui.beginChild(gameSave.get("name"), new ImVec2(this.width - 48, 24 * 6), true);
                // ImGui.popStyleColor();
                ImGui.popStyleColor();
                ImGui.text("name");
                ImGui.sameLine(256);
                ImGui.text(gameSave.get("name"));
                ImGui.separatorText(gameSave.get("游戏进度gameprogress"));
                ImGui.text("note");
                ImGui.sameLine(256);
                ImGui.text(gameSave.get("存档备注note"));
                ImGui.text("create time");
                ImGui.sameLine(256);
                ImGui.text(gameSave.get("创建时间createtime"));
                ImGui.endChild();
                if (ImGui.isItemHovered()) {
                    hoveringSaveName = gameSave.get("name");
                    ImGui.setMouseCursor(ImGuiMouseCursor.Hand);
                } else {
                    if (hoveringSaveName.equals(gameSave.get("name"))) {
                        hoveringSaveName = "";
                    }
                }
                if (ImGui.isItemClicked()) {
                    selectedSaveName = gameSave.get("name");
                }
                ImGui.newLine();
            }
            ImGui.endChild();
            ImGui.beginChild("buttonArea", new ImVec2(this.width - 16, 64), true);
            if (selectedSaveName.equals("")) {
                ImGui.beginDisabled();
            }
            ImGui.button("load");
            ImGui.sameLine();
            ImGui.button("delete");
            if (selectedSaveName.equals("")) {
                ImGui.endDisabled();
            }
            ImGui.endChild();

        }
        ImGui.end();
    }

}
