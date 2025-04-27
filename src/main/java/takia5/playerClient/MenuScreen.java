package takia5.playerClient;

import java.util.HashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import takia5.playerClient.HexButton.ButtonLogo;
import takia5.playerClient.HexButton.ButtonState;
import takia5.playerClient.ui.CreateGameSave;
// import takia5.playerClient.ui.UiContainer;
// import takia5.playerClient.ui.CreateGameSave;
import takia5.playerClient.ui.NinePatchDiv;
// import takia5.playerClient.ui.GameSaveList;
// import takia5.playerClient.ui.SettingsUI;

public class MenuScreen implements Screen {

    public Stage stage;
    // public Texture backgroundTexture;
    // public Image backgroundImage;
    // public Texture bgCircle1;
    // public Texture bgCircle2;
    // public Texture bgCircle3;
    // public Image circleImage1;
    // public Image circleImage2;
    // public Image circleImage3;
    public HashMap<ButtonState, Texture> buttonTexMap = new HashMap<HexButton.ButtonState, Texture>();
    public HashMap<ButtonLogo, Texture> logoMap = new HashMap<HexButton.ButtonLogo, Texture>();
    public Group menuGroup = new Group();

    public NinePatchDiv uiComponent;

    public MenuScreen() {
        super();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        // stage.getViewport().getCamera();
        Gdx.input.setInputProcessor(stage);
        // backgroundTexture = new Texture(Main.ResPath +
        // "menu_background.png");

        buttonTexMap.put(ButtonState.DEFAULT, new Texture(Main.ResPath + "button_light.png"));
        buttonTexMap.put(ButtonState.FOCUSED, new Texture(Main.ResPath + "button.png"));
        buttonTexMap.put(ButtonState.PRESSED, new Texture(Main.ResPath + "button_pressed.png"));
        buttonTexMap.put(ButtonState.DISABLED, new Texture(Main.ResPath + "button_disabled.png"));
        logoMap.put(ButtonLogo.NEWGAME, new Texture(Main.ResPath + "create_game.png"));
        logoMap.put(ButtonLogo.LOAD, new Texture(Main.ResPath + "load_game.png"));
        logoMap.put(ButtonLogo.SETTING, new Texture(Main.ResPath + "setting_game.png"));
        logoMap.put(ButtonLogo.QUIT, new Texture(Main.ResPath + "quit_game.png"));

        addChild();

        menuGroup.addAction(Actions.moveTo(0, 0, 1));
    }

    public void addChild() {
        // 在你的Screen类中创建按钮并添加到舞台
        HexButton buttonNewGame = new HexButton(buttonTexMap, logoMap, 5, -2, "createGame");
        buttonNewGame.logo = ButtonLogo.NEWGAME;
        HexButton buttonLoad = new HexButton(buttonTexMap, logoMap, 4, -1, "loadGame");
        buttonLoad.logo = ButtonLogo.LOAD;
        HexButton buttonSetting = new HexButton(buttonTexMap, logoMap, 3, 0, "settingGame");
        buttonSetting.logo = ButtonLogo.SETTING;
        HexButton buttonQuit = new HexButton(buttonTexMap, logoMap, 2, 0, "quitGame");
        buttonQuit.logo = ButtonLogo.QUIT;

        menuGroup.addActor(buttonNewGame);
        menuGroup.addActor(buttonLoad);
        menuGroup.addActor(buttonSetting);
        menuGroup.addActor(buttonQuit);

        menuGroup.setX(-Gdx.graphics.getWidth());

        stage.addActor(menuGroup);

    }

    public void createGame() {
        CreateGameSave com = new CreateGameSave();
        this.uiComponent = com;
    }

    public void loadGame() {
        // GameSaveList com = new GameSaveList();
        // this.uiComponent = com;
    }

    public void settingGame() {
        // SettingsUI com = new SettingsUI();
        // this.uiComponent = com;
    }

    public void quitGame() {
        System.out.println("quitGame");
        Gdx.app.exit();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1f);

        stage.act(delta);
        stage.draw();

        if (this.uiComponent != null) {
            this.uiComponent.render();
            this.uiComponent.render();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        if (this.uiComponent != null) {
            // String uiClass = this.uiComponent.getClass().getName();
            // 为component实现一种自动resize的机制
//            this.uiComponent.resize = true;
            this.uiComponent.onResize();
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        for (HashMap.Entry<HexButton.ButtonState, Texture> entry : buttonTexMap.entrySet()) {
            Texture val = entry.getValue();
            if (val != null) {
                val.dispose();
            }
        }
        for (HashMap.Entry<HexButton.ButtonLogo, Texture> entry : logoMap.entrySet()) {
            Texture val = entry.getValue();
            if (val != null) {
                val.dispose();
            }
        }
        HexButton.invertShaderProgram.dispose();
    }

}
