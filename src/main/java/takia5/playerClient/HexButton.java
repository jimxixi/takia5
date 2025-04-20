package takia5.playerClient;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class HexButton extends Actor {
    private Polygon hexPolygon;
    private ShapeRenderer hexRenderer;
    // private float width;
    // private float height;
    HashMap<ButtonState, Texture> texMap;
    HashMap<ButtonLogo, Texture> logoMap;
    float startX = 0;
    float startY = 0;
    float spacing = 10;
    float buttonWidth = 111;
    float buttonHeight = 128;

    public enum ButtonState {
        DEFAULT, // 默认状态
        FOCUSED, // 聚焦状态
        PRESSED, // 按下状态
        DISABLED;
    }

    public enum ButtonLogo {
        NEWGAME, LOAD, SETTING, QUIT,
    }

    public ButtonState state = ButtonState.DEFAULT;
    public ButtonLogo logo = ButtonLogo.NEWGAME;

    public static ShaderProgram invertShaderProgram;
    static {

        ShaderProgram.pedantic = false;
        FileHandle vsh = new FileHandle(Main.ResPath + "invert.vsh");
        FileHandle fsh = new FileHandle(Main.ResPath + "invert.fsh");
        invertShaderProgram = new ShaderProgram(vsh, fsh);
        if (!invertShaderProgram.isCompiled()) {
            System.out.println("compile error: " + invertShaderProgram.getLog());
        }

    }

    public HexButton(HashMap<ButtonState, Texture> texMap, HashMap<ButtonLogo, Texture> logoMap, int i, int j,
            String callback) {
        this.texMap = texMap;
        this.logoMap = logoMap;
        // 生成六边形的顶点
        setBounds(getX(), getY(), buttonWidth, buttonHeight);
        float[] vertices = new float[] { buttonWidth / 2, 0, buttonWidth, buttonHeight / 4, buttonWidth,
                3 * buttonHeight / 4, buttonWidth / 2, buttonHeight, 0, 3 * buttonHeight / 4, 0, buttonHeight / 4, };
        hexPolygon = new Polygon(vertices);
        hexRenderer = new ShapeRenderer(20);
        float x = startX + (j * (buttonWidth + spacing)) + (i * (buttonWidth + spacing) / 2);
        float y = startY + (i * (buttonHeight * 3 / 4 + spacing));
        setPosition(x, y);
        addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (state == ButtonState.PRESSED) {
                    System.out.println("clicked! invoke: " + callback);
                    GameApp app = (GameApp) (Gdx.app.getApplicationListener());
                    try {
                        app.getScreen().getClass().getDeclaredMethod(callback).invoke(app.getScreen());
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException
                            | SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (state == ButtonState.FOCUSED) {
                    state = ButtonState.PRESSED;
                }
                return true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                state = ButtonState.DEFAULT;
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                if (state == ButtonState.DEFAULT) {
                    if (isMouseOver(x, y)) {
                        state = ButtonState.FOCUSED;
                    }
                } else if (state == ButtonState.FOCUSED) {
                    if (isMouseOver(x, y)) {
                    } else {
                        state = ButtonState.DEFAULT;
                    }
                }
                return false;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return super.keyUp(event, keycode);
            }

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                return super.keyTyped(event, character);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texMap.get(state), getX(), getY());
        if (state == ButtonState.PRESSED) {
            batch.setShader(invertShaderProgram);
        }
        batch.draw(logoMap.get(logo), getX(), getY());
        batch.end();
        batch.setShader(null);
        hexRenderer.begin(ShapeRenderer.ShapeType.Line);
        hexRenderer.setColor(0, 1, 0, 1);
        hexRenderer.polygon(hexPolygon.getTransformedVertices());
        hexRenderer.end();
        batch.begin();
        // batch.draw(texture, 0, 0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // 更新六边形的位置
        // Vector2 pos = localToStageCoordinates(new Vector2(getX(), getY()));
        hexPolygon.setPosition(getParent().getX() + getX(), getParent().getY() + getY());
    }

    public boolean isMouseOver(float mouseX, float mouseY) {
        // 获取鼠标位置
        Vector2 mousePos = getStage().screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        return hexPolygon.contains(mousePos);
    }
    //
    // public void changeState(ButtonState state) {
    // this.state = state;
    // }

}
