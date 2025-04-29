package takia5.playerClient.ui;

import com.badlogic.gdx.Gdx;

import imgui.ImGui;
import imgui.flag.ImGuiCond;
import takia5.playerClient.GameApp;

public abstract class NinePatchDiv {
    public boolean loaded = false;
     public boolean resize = false;
    public boolean show = true;
    public int widthPP;
    public int heightPP;
    public float left;
    public float top;
    public float margin;
    public Corner corner;

    public enum Corner {
        LEFTTOP, RIGHTTOP, LEFTBOTTOM, RIGHTBOTTOM, CENTER,
    }

    public NinePatchDiv(int widthPP, int heightPP, Corner corner, int margin) {
        // this.width = Gdx.graphics.getWidth() * (widthPP / 100) - (margin * 2);
        // this.height = Gdx.graphics.getHeight() * (heightPP / 100) - (margin * 2);
        this.widthPP = widthPP;
        this.heightPP = heightPP;
        this.corner = corner;
        this.margin = margin;
    }

    public void onLoad() {
        onResize();
    }

    public void onResize() {
        float width = (Gdx.graphics.getWidth() * (widthPP / 100f)) - (2 * margin);
        float height = (Gdx.graphics.getHeight() * (heightPP / 100f)) - (2 * margin);
        if (corner == Corner.LEFTTOP) {
            left = margin;
            top = margin;
        } else if (corner == Corner.RIGHTTOP) {
            left = Gdx.graphics.getWidth() - width - margin;
            top = margin;
        } else if (corner == Corner.LEFTBOTTOM) {
            left = margin;
            top = Gdx.graphics.getHeight() - height - margin;
        } else if (corner == Corner.RIGHTBOTTOM) {
            left = Gdx.graphics.getWidth() - width - margin;
            top = Gdx.graphics.getHeight() - height - margin;
        } else if (corner == Corner.CENTER) {
            left = Gdx.graphics.getWidth() * (100 - widthPP) / 200f + margin;
            top = Gdx.graphics.getHeight() * (100 - heightPP) / 200f + margin;
        }
        System.out.println("resize: " + width + ":" + height + ", " + left + ":" + top + ".");
        ImGui.setNextWindowSize(width, height, ImGuiCond.Always);
        ImGui.setNextWindowPos(left, top, ImGuiCond.Always);
//        ImGui.setWindowSize(width, height);
//        ImGui.setWindowPos(left, top);

    }

    public abstract void drawComponent();

    public void render() {
        if (show) {
            GameApp.startImGui();
            if (!loaded) {
                loaded = true;
                onLoad();
            }
            if (resize) {
                onResize();
                resize = false;
            }
            drawComponent();
            GameApp.endImGui();
        }
    }

    public void setShow(boolean v) {
        show = v;
    }
}
