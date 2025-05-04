package takia5.playerClient;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

import takia5.playerClient.sceneTree.SceneTree;

class TakiaInputProcessor implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyDown'");
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyUp'");
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'touchDown'");
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'touchUp'");
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'touchCancelled'");
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'touchDragged'");
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scrolled'");
    }

    
}

public class TakiaScreen implements Screen {
    SceneTree sceneTree = new SceneTree();
    InputProcessor inputProcessor = new TakiaInputProcessor();

    @Override
    public void show() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resize'");
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hide'");
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }

}
