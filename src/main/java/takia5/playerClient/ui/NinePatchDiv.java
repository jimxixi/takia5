package takia5.playerClient.ui;

import takia5.playerClient.GameApp;

public abstract class NinePatchDiv {
    public boolean loaded = false;
    public boolean resize = false;
    public boolean show = true;
    public int width;
    public int height;
    public int left;
    public int top;

    public enum Corner {
        LEFTTOP, RIGHTTOP,
        LEFTBOTTOM, RIGHTBOTTOM
    }

    public NinePatchDiv(int width, int height, Corner corner, int margin) {
        this.width = width - (margin * 2);
        this.height = height - (margin * 2);
        if (corner == Corner.LEFTTOP) {
            left = margin;
            top = margin;
        } else if (corner == Corner.RIGHTTOP) {
            left;
        }
    }

    public abstract void onLoad();

    public abstract void onResize();

    public void drawComponent() {

    }

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
