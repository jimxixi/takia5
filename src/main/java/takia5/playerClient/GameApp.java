package takia5.playerClient;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import imgui.ImFontAtlas;
import imgui.ImFontConfig;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
// import com.badlogic.gdx.utils.async.AsyncExecutor;

/**
 * 主界面
 */
public class GameApp extends Game {
//    public static Skin skin;
    static public ImGuiImplGlfw imGuiGlfw;
    static public ImGuiImplGl3 imGuiGl3;
    static public Screen nextScreen;
    static public boolean nextScreenReady = false;
//    static public InputProcessor tmpProcessor;

    @Override
    public void create() {
        // change_screen(LogoScreen.class);
        LogoScreen logoScreen = new LogoScreen();
        setScreen(logoScreen);
        // CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
        //     System.out.println("开始初始化imgui");
        //     GameApp.initImgui();
        //     System.out.println("imgui已初始化。");
        //     return null;
        // });
        // future.thenRun(() -> {
        //     System.out.println("imgui 初始化结束");
        //     change_screen(MenuScreen.class);
        // });
        initImgui(() -> {
            System.out.println("imgui已初始化");
            change_screen(MenuScreen.class);
        });
    }

    public static void initImgui(Runnable callback) {
        System.out.println("开始初始化imgui");
        imGuiGlfw = new ImGuiImplGlfw();
        imGuiGl3 = new ImGuiImplGl3();
        long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);
        System.out.println(io.getKeyRepeatDelay());
        io.setKeyRepeatDelay(1);
        // io.getFonts().addFontDefault();
        // io.getFonts().build();
        // io.getFonts().setFreeTypeRenderer(true);
        imGuiGlfw.init(windowHandle, true);
        imGuiGl3.init("#version 410");
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            // 加载OTF字体
            ImFontAtlas fontAtlas = io.getFonts();
            fontAtlas.setFreeTypeRenderer(true);
            ImFontConfig fontConfig = new ImFontConfig();
            fontConfig.setGlyphRanges(new short[] { 0x0020, (short) 0xffff, 0, });
    
            // fontConfig.setGlyphRanges(fontAtlas.getGlyphRangesDefault());
            fontAtlas.addFontFromFileTTF(Main.ResPath + "uiskin/unifont-16.0.02.otf", 24, fontConfig);
            fontConfig.destroy();
    
            fontAtlas.build();
    
            ImGui.styleColorsDark();
            ImGuiStyle style = ImGui.getStyle();
            style.setWindowBorderSize(1f);
            style.setWindowRounding(10f);
    
            style.setColor(ImGuiCol.Border, 0f, 1f, 0f, 1f);
        });
        System.out.println("正在初始化imgui");
        future.thenRun(callback);
        // startImGui();
        // endImGui();
    }

    public static void startImGui() {
//        if (tmpProcessor != null) {
//            Gdx.input.setInputProcessor(tmpProcessor);
//            tmpProcessor = null;
//        }
        imGuiGl3.newFrame();
        imGuiGlfw.newFrame();
        ImGui.newFrame();
        // System.out.println("start");
    }

    public static void endImGui() {
        ImGui.endFrame();
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());
        // System.out.println("end");

        // If ImGui wants to capture the input, disable libGDX's input processor
//        if (ImGui.getIO().getWantCaptureKeyboard() || ImGui.getIO().getWantCaptureMouse()) {
//            System.out.println("input");
//           tmpProcessor = Gdx.input.getInputProcessor();
//           Gdx.input.setInputProcessor(null);
//        }
    }

    @Override
    public void render() {
        super.render();
        if (nextScreenReady) {
            nextScreenReady = false;
            System.out.println("下一个Screen已就绪，现在切换: " + nextScreen.getClass().getName());
            setScreen(nextScreen);
        } else {
        }
    }

    public static void disposeImGui() {
        imGuiGl3.shutdown();
        imGuiGl3 = null;
        imGuiGlfw.shutdown();
        imGuiGlfw = null;
        ImGui.destroyContext();
    }

    @Override
    public void dispose() {
        super.dispose();
        setScreen(null);
        disposeImGui();
    }

    public <T> void change_screen(Class<T> screenClass) {
        try {
            // 获取无参构造函数
            Constructor<T> constructor = screenClass.getDeclaredConstructor();
            CompletableFuture<T> future = CompletableFuture.supplyAsync(() -> {
                try {
                    T screen = constructor.newInstance();
                    // System.out.println("初始化screen结果: " + screen);
                    return screen;
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                    return null;
                }
            });
            future.thenApply(screen -> {
                // System.out.println(screenClass.getName() + " 创建成功");
                return screen;
            }).thenAccept(screen -> {
                if (screen != null) {
                    // setScreen((Screen) screen);
                    nextScreen = (Screen) screen;
                    nextScreenReady = true;
                } else {
                    // System.out.println(screenClass.getName() + " 创建失败");
                }
            });
            // T screen = constructor.newInstance();
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

}
