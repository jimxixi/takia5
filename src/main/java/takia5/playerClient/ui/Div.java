package takia5.playerClient.ui;

import takia5.playerClient.GameApp;

public abstract class Div {
    public boolean loaded = false;
    public boolean resize = false;
    public boolean show = false;
    public int width = 200;
    public int height = 200;
    public int left;
    public int top;

    public abstract void onLoad();

    public abstract void onResize();

    public void drawComponent() {

    }

    public void render() {
        if (!loaded) {
            loaded = true;
            onLoad();
        }
        if (show) {
            GameApp.startImGui();
            drawComponent();
            GameApp.endImGui();
        }
    }

    public void setShow(boolean v) {
        show = v;
    }
}
