package takia5.playerClient;

import java.io.File;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {

    /**
     * 资源文件路径 游戏存档路径 开发暂时写死 后续改成获取exe所在路径
     */
    public static String ResPath = "D:/takia/takia_resource/";
    public static String SavePath = "D:/takia/takia_gamesave/";
    // public static String ResPath = "./";

    public static void main(String[] args) {
        if (new File("D:/takia/takia_resource/").exists()) {
        } else {
            ResPath = "C:/takia/takia_resource/";
            SavePath = "C:/takia/takia_gamesave/";
        }

        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("takia4");
        //// Vsync limits the frames per second to what your hardware can display, and
		//// helps eliminate
		//// screen tearing. This setting doesn't always work on Linux, so the line
		//// after is a safeguard.
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor, plus 1 to
		//// try to match fractional
		//// refresh rates. The Vsync setting above should limit the actual FPS to match
		//// the monitor.
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        //// If you remove the above line and set Vsync to false, you can get unlimited
		//// FPS, which can be
		//// useful for testing performance, but can also be very stressful to some
		//// hardware.
		//// You may also need to configure GPU drivers to fully disable Vsync; this can
		//// cause screen tearing.
        // configuration.setWindowedMode(1280, 720);
        // configuration.setFullscreenMode(new DisplayMode());
        Monitor monitor = Lwjgl3ApplicationConfiguration.getPrimaryMonitor();
        DisplayMode[] modes = Lwjgl3ApplicationConfiguration.getDisplayModes(monitor);
        configuration.setWindowedMode(modes[modes.length - 7].width, modes[modes.length - 7].height);
        configuration.setWindowedMode(1280, 720);
        // configuration.setFullscreenMode(modes[modes.length - 1]);
        // 后续再添加窗口化支持，目前先以默认分辨率运行
        // for (DisplayMode mode : modes) {
        // // System.out.println(mode.toString());
        // }

        //// You can change these files; they are in lwjgl3/src/main/resources/ .
        // configuration.setResizable(true);
        // configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png",
        // "libgdx16.png");
        try {
            new Lwjgl3Application(new GameApp(), configuration);
        } catch (Exception e) {
            // System.out.println(e);
            // System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("运行结束");
        }
        // int virtualX = app.getGraphics().getPrimaryMonitor().virtualX;
        // int virtualY = app.getGraphics().getPrimaryMonitor().virtualY;
        // app.getGraphics().setWindowedMode(virtualX, virtualY);
        // Gdx.graphics.getPrimaryMonitor().virtualX;
    }

}
