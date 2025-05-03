package takia5.playerClient.ui;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;

import imgui.ImColor;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImString;
import takia5.playerClient.GameApp;
import takia5.playerClient.Main;
import takia5.playerClient.MenuScreen;
import takia5.utils.GameSaveUtil;

public class CreateGameSave extends NinePatchDiv {
    // private ImString saveName = new ImString(30);
    private String saveNameString = "HelloTakia汉字！!";
    private int maxLength = 128;
    ImString saveName = new ImString(saveNameString, maxLength);

    public CreateGameSave() {
        super(40, 40, NinePatchDiv.Corner.CENTER, 8);
        // System.out.println(Arrays.toString(saveName.getData()));
        // saveName.set("HelloTakia汉字！!");
        // System.out.println(Arrays.toString(saveName.getData()));
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    // 相信就是迷信 语言就是谎言 感觉就是幻觉
    // 宗教就是邪教 理解就是曲解 公有就是私有
    @Override
    public void drawComponent() {
        boolean createdButtonDisabled = false;
//        saveNameString = saveName.get();
        // 创建ImGui界面
        if (ImGui.begin("Save Game啦啦啦にほ", ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoTitleBar)) {
            ImGui.text("Enter save name输入存档名称: " + Gdx.graphics.getFramesPerSecond());
            // ImGui.text("saveName: ");
            // ImGui.sameLine();
            ImGui.inputTextWithHint("SaveName", "hint", saveName);
            
            if (ImGui.inputTextWithHint("##saveName", "game save name", saveName)) {

                // ImGui.newLine();
//                 System.out.println("render input: " + saveName.get());
            } else {
                // System.out.println("else");
            }
            saveNameString = saveName.get();
            if (!isValidFolderName(saveNameString)) {
                // 获取当前光标位置
                ImVec2 cursorPos = ImGui.getCursorPos();
                // 设置红色字体
                ImGui.pushStyleColor(ImGuiCol.Text, ImColor.rgba(255, 0, 0, 255)); //
                // 显示提示文字
                // ImGui.sameLine();
                ImGui.text("invalid filename"); // 恢复字体颜色
                ImGui.popStyleColor();
                // 恢复光标位置
                ImGui.setCursorPos(cursorPos);
                createdButtonDisabled = true;
            } else if (saveNameString.getBytes().length >= maxLength - 1) {
                ImVec2 cursorPos = ImGui.getCursorPos();
                ImGui.pushStyleColor(ImGuiCol.Text, ImColor.rgba(96, 96, 96, 255));
                ImGui.text("too long"); // 恢复字体颜色
                ImGui.popStyleColor();
                ImGui.setCursorPos(cursorPos);
                createdButtonDisabled = true;
            } else if (isNameUsed(saveNameString)) {
                ImVec2 cursorPos = ImGui.getCursorPos();
                ImGui.pushStyleColor(ImGuiCol.Text, ImColor.rgba(96, 96, 96, 255));
                ImGui.text("name used"); // 恢复字体颜色
                ImGui.popStyleColor();
                ImGui.setCursorPos(cursorPos);
                createdButtonDisabled = true;
            } else {
                // System.out.println(isNameUsed(saveName.get()));
            }
            ImGui.newLine();
            if (createdButtonDisabled) {
                ImGui.beginDisabled();
            }
            if (ImGui.button("Create Save")) {
                // 在这里添加创建存档的逻辑
                createSaveFolder(saveName.get());
                // 创建以后关闭窗口并打开存档列表
                setShow(false);
                GameApp app = (GameApp) (Gdx.app.getApplicationListener());
                MenuScreen screen = (MenuScreen) app.getScreen();
                screen.loadGame();
            }
            if (createdButtonDisabled) {
                ImGui.endDisabled();
            }
            ImGui.sameLine();
            if (ImGui.button("Cancel")) {
                System.out.println("Save creation canceled.");
                // 在这里添加取消操作的逻辑
                setShow(false);
            }
            // ImGui.newLine();
        }
        ImGui.end();

    }

    public static boolean isNameUsed(String name) {
        LinkedList<HashMap<String, String>> mapList = GameSaveUtil.scanGameSaves();
        boolean nameUsed = false;
        for (HashMap<String, String> map : mapList) {
            if (name.equals((map.get("name")))) {
                nameUsed = true;
                break;
            }
        }
        return nameUsed;
    }

    public static boolean isValidFolderName(String folderName) {
        Pattern ILLEGAL_CHARACTERS = Pattern.compile("[\\\\/\\.:!*?'\"<>|]");
        if (folderName == null || folderName.isEmpty()) {
            return false;
        }
        if (ILLEGAL_CHARACTERS.matcher(folderName).find()) {
            return false;
        }
        return true;
    }

    public void createSaveFolder(String saveName) {
        if (!isValidFolderName(saveName)) {
            return;
        }
        File dir = new File(Main.SavePath + saveName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // File sourceFile = new File(Main.ResPath + "saveTemplate.sqlite");
        // File destFile = new File(Main.SavePath + saveName + ".sqlite");
        // try {
        // Files.copy(sourceFile.toPath(), destFile.t
        // }
    }
}