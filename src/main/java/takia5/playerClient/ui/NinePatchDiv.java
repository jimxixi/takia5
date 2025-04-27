package takia5.playerClient.ui;

import com.badlogic.gdx.Gdx;

import imgui.ImGui;
import takia5.playerClient.GameApp;

public abstract class NinePatchDiv {
    public boolean loaded = false;
    // public boolean resize = false;
    public boolean show = true;
    public int widthPP;
    public int heightPP;
    public int left;
    public int top;
    public int margin;
    public Corner corner;

    public enum Corner {
        LEFTTOP, RIGHTTOP, LEFTBOTTOM, RIGHTBOTTOM, CENTER, 
    }

    public NinePatchDiv(int widthPP, int heightPP, Corner corner, int margin) {
//        this.width = Gdx.graphics.getWidth() * (widthPP / 100) - (margin * 2);
//        this.height = Gdx.graphics.getHeight() * (heightPP / 100) - (margin * 2);
        this.widthPP = widthPP;
        this.heightPP = heightPP;
        this.corner = corner;
        this.margin = margin;
    }

    public void onLoad() {
        onResize();
    }

    public void onResize() {
        int width = Gdx.graphics.getWidth() * (widthPP / 100) - (margin * 2);;
        int height = Gdx.graphics.getHeight() * (heightPP / 100) - (margin * 2);
        if (corner == Corner.LEFTTOP) {
            left = margin + margin;
            top = margin + margin;
        } else if (corner == Corner.RIGHTTOP) {
            left = Gdx.graphics.getWidth() - width + margin;
            top = margin + margin;
        } else if (corner == Corner.LEFTBOTTOM) {
            left = margin + margin;
            top = Gdx.graphics.getHeight() - height + margin;
        } else if (corner == Corner.RIGHTBOTTOM) {
            left = Gdx.graphics.getWidth() - width + margin;
            top = Gdx.graphics.getHeight() - height + margin;
        } else if (corner == Corner.CENTER) {
//            left = Gdx.graphics.getWidth() * (100 - widthPP) / 2;
//            top = Gdx.graphics.getHeight() * (100 - heightPP) / 2;
            left = 0;
            top = 0;
        }
        ImGui.setNextWindowSize(width, height);
        ImGui.setNextWindowPos(left, top);

    }

    public abstract void drawComponent();

    public void render() {
        if (show) {
            GameApp.startImGui();
            if (!loaded) {
                loaded = true;
                onLoad();
            }
            drawComponent();
            GameApp.endImGui();
        }
    }

    public void setShow(boolean v) {
        show = v;
    }
}
