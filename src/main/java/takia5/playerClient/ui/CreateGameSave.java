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
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImString;
import takia5.playerClient.GameApp;
import takia5.playerClient.Main;
import takia5.playerClient.MenuScreen;
import takia5.utils.GameSaveUtil;

public class CreateGameSave extends NinePatchDiv {
    private ImString saveName = new ImString(30);
    private float windowWidth;
    private float windowHeight;

    public CreateGameSave() {
    }

    @Override
    public void onLoad() {
        saveName.set("HelloTakia汉字！!");

        // 设置弹窗大小和位置
        this.windowWidth = 800.0f;
        this.windowHeight = 400.0f;
        ImGui.setNextWindowSize(windowWidth, windowHeight);
        ImGui.setNextWindowPos((Gdx.graphics.getWidth() - windowWidth) / 2,
                (Gdx.graphics.getHeight() - windowHeight) / 2);

    }

    @Override
    public void onResize() {
        windowWidth = (Gdx.graphics.getWidth() / 2) - 64;
        windowHeight = Gdx.graphics.getHeight() - 64;
        ImGui.setNextWindowSize(windowWidth, windowHeight);
        ImGui.setNextWindowPos((Gdx.graphics.getWidth() - windowWidth) / 2,
                (Gdx.graphics.getHeight() - windowHeight) / 2);
    }

    @Override
    public void drawComponent() {
        super.drawComponent();
        boolean createdButtonDisabled = false;
        // 创建ImGui界面
        if (ImGui.begin("Save Game啦啦啦にほ", ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoTitleBar)) {
            ImGui.text("Enter save name输入存档名称:");
            // ImGui.text("saveName: ");
            // ImGui.sameLine();
            if (ImGui.inputText("##saveName", saveName)) {
                // ImGui.newLine();
            } else {
                if (!isValidFolderName(saveName.get())) {
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
                } else if (saveName.get().getBytes().length >= saveName.getBufferSize() - 1) {
                    ImVec2 cursorPos = ImGui.getCursorPos();
                    ImGui.pushStyleColor(ImGuiCol.Text, ImColor.rgba(96, 96, 96, 255));
                    ImGui.text("too long"); // 恢复字体颜色
                    ImGui.popStyleColor();
                    ImGui.setCursorPos(cursorPos);
                    createdButtonDisabled = true;
                } else if (isNameUsed(saveName.get())) {
                    ImVec2 cursorPos = ImGui.getCursorPos();
                    ImGui.pushStyleColor(ImGuiCol.Text, ImColor.rgba(96, 96, 96, 255));
                    ImGui.text("name used"); // 恢复字体颜色
                    ImGui.popStyleColor();
                    ImGui.setCursorPos(cursorPos);
                    createdButtonDisabled = true;
                } else {
                    // System.out.println(isNameUsed(saveName.get()));
                }
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
            ImGui.end();
        }

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