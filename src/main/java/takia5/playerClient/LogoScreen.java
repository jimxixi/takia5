package takia5.playerClient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class LogoScreen implements Screen {
    private SpriteBatch batch;
    private Texture image;
    private Texture image2;
    private Texture image3;

    // private DisplayMode displayMode;

    public LogoScreen() {
    }

    @Override
    public void show() {
        // this.displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
        // GameApp.initImgui();
        // System.out.println("screen: " + this.displayMode);
        batch = new SpriteBatch();
        image = new Texture(Main.ResPath + "libgdx.png");
        image2 = new Texture(Main.ResPath + "openjdk.png");
        image3 = new Texture(Main.ResPath + "moremay.png");
        System.out.println("logo screen show");

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, (Gdx.graphics.getWidth() / 2) - (image.getWidth() / 2),
                (Gdx.graphics.getHeight() / 2) - (image.getHeight() / 2));
        batch.draw(image2, (Gdx.graphics.getWidth() / 2) - (image2.getWidth() / 2),
                (Gdx.graphics.getHeight() / 2) - (image2.getHeight() / 2) - 100);
        batch.draw(image3, (Gdx.graphics.getWidth() / 2) - (image3.getWidth() / 2),
                (Gdx.graphics.getHeight() / 2) - (image3.getHeight() / 2) - 200);
        batch.end();
        // if (new Date().getTime() - start_time > duration) {
        // GameApp app = (GameApp) (Gdx.app.getApplicationListener());
        // app.change_screen(MenuScreen.class);
        // } else {
        // }
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("logo screen resize");
    }

    @Override
    public void pause() {
        System.out.println("logo screen pause");
    }

    @Override
    public void resume() {
        System.out.println("logo screen resume");
    }

    @Override
    public void hide() {
        System.out.println("logo screen hide");
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        image2.dispose();
        image3.dispose();

        System.out.println("logo screen dispose");
    }

}
